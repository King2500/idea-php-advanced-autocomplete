package net.king2500.plugins.PhpAdvancedAutoComplete.reference;

import com.intellij.codeInsight.highlighting.HighlightUsagesHandlerBase;
import com.intellij.lang.injection.InjectedLanguageManager;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.util.Consumer;
import com.intellij.util.containers.ContainerUtil;
import com.intellij.util.containers.hash.HashMap;
import com.jetbrains.php.lang.intentions.stringDoc.PhpHeredocToStringIntention;
import com.jetbrains.php.lang.intentions.strings.converters.PhpConcatenationStringRepresentationConverter;
import com.jetbrains.php.lang.intentions.strings.converters.PhpInterpolationStringRepresentationConverter;
import com.jetbrains.php.lang.intentions.strings.converters.PhpStringPartDescriptor;
import com.jetbrains.php.lang.psi.PhpPsiUtil;
import com.jetbrains.php.lang.psi.elements.BinaryExpression;
import com.jetbrains.php.lang.psi.elements.StringLiteralExpression;
import gnu.trove.THashMap;
import one.util.streamex.StreamEx;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Thomas Schulz <mail@king2500.net>
 */
public class PhpHighlightPackParametersUsagesHandler extends HighlightUsagesHandlerBase<StringLiteralExpression> {
    private final int myFormatExpressionIndex;
    private final int mySelectedParameterIndex;
    private final PsiElement[] myParameters;

    PhpHighlightPackParametersUsagesHandler(@NotNull Editor editor, @NotNull PsiFile file, int formatExpressionIndex, int selectedParameterIndex, PsiElement[] parameters) {
        super(editor, file);
        this.myFormatExpressionIndex = formatExpressionIndex;
        this.mySelectedParameterIndex = selectedParameterIndex;
        this.myParameters = parameters;
    }

    @Override
    public List<StringLiteralExpression> getTargets() {
        PsiElement parameter = this.myParameters[this.myFormatExpressionIndex];
        if (parameter instanceof StringLiteralExpression) {
            return Collections.singletonList((StringLiteralExpression)parameter);
        }
        else {
            return PhpConcatenationStringRepresentationConverter.isConcatenation(parameter) ? getLiteralExpressions((BinaryExpression)parameter, this.myEditor) : Collections.emptyList();
        }
    }

    @NotNull
    private static List<StringLiteralExpression> getLiteralExpressions(BinaryExpression parameter, Editor editor) {
        List<PsiElement> parts = ContainerUtil.map(PhpConcatenationStringRepresentationConverter.INSTANCE.getStringParts(parameter, editor), PhpStringPartDescriptor::getElement);
        List<StringLiteralExpression> expressions = StreamEx.of(parts).select(StringLiteralExpression.class).collect(Collectors.toList());
        boolean allExpressionHaveSameQuotes = ((StreamEx)StreamEx.of(expressions).distinct(StringLiteralExpression::isSingleQuote)).limit(2L).count() <= 1L;

        return expressions.size() == parts.size() && allExpressionHaveSameQuotes ? expressions : Collections.emptyList();
    }

    @Override
    protected void selectTargets(List<StringLiteralExpression> targets, Consumer<List<StringLiteralExpression>> selectionConsumer) {
        selectionConsumer.consume(targets);
    }

    @Override
    public void computeUsages(List<StringLiteralExpression> targets) {
        StringLiteralExpression formatExpression = ContainerUtil.getFirstItem(targets);
        if (formatExpression == null) {
            return;
        }

        String contents = PhpInterpolationStringRepresentationConverter.createExpressionContent(targets);
        HashMap<Integer, PhpPackFormatSpecificationParser.PackSpecification> specificationsWithIndices = PhpPackFormatSpecificationParser.parseFormat(contents, formatExpression.isSingleQuote() || PhpHeredocToStringIntention.isNowdoc(formatExpression), this.myParameters.length);
        HashMap<Integer, RelativeRange> relativeSpecificationRanges = getRelativeSpecificationRanges(specificationsWithIndices, targets);

        int specificationIndex = this.mySelectedParameterIndex == this.myFormatExpressionIndex
            ? resolveSpecificationIndexFromCaret(relativeSpecificationRanges)
            : resolveSpecificationIndexFromParameter(specificationsWithIndices);

        RelativeRange pair = relativeSpecificationRanges.get(specificationIndex);
        if (pair == null) {
            return;
        }

        this.myReadUsages.add(getRangeInsideDocument(pair.getContainingExpression(), pair.getRangeInsideExpression()));
        int offset = 1;

        for (Map.Entry<Integer, PhpPackFormatSpecificationParser.PackSpecification> entry : specificationsWithIndices.entrySet()) {
            PhpPackFormatSpecificationParser.PackSpecification specification = entry.getValue();
            if (entry.getKey() == specificationIndex) {
                for (int r = 0; r < specification.getRepeater(); r++) {
                    int parameterIndex = offset + r >= 0 ? this.myFormatExpressionIndex + offset + r : offset + r;
                    if (parameterIndex >= 0 && parameterIndex < this.myParameters.length) {
                        this.addOccurrence(this.myParameters[parameterIndex]);
                    }
                }
                break;
            }
            offset += specification.getRepeater();
        }
    }

    @NotNull
    private static HashMap<Integer, RelativeRange> getRelativeSpecificationRanges(HashMap<Integer, PhpPackFormatSpecificationParser.PackSpecification> specifications, List<StringLiteralExpression> targets) {
        Map<TextRange, StringLiteralExpression> rangesInsideResultingFormatString = getRangesWithExpressionInsideResultingFormatString(targets);
        HashMap<Integer, RelativeRange> result = new HashMap<>();

        for (Map.Entry<Integer, PhpPackFormatSpecificationParser.PackSpecification> entry : specifications.entrySet()) {
            PhpPackFormatSpecificationParser.PackSpecification specification = entry.getValue();
            for (Map.Entry<TextRange, StringLiteralExpression> e : rangesInsideResultingFormatString.entrySet()) {
                TextRange expressionRangeInsideFormatString = e.getKey();
                TextRange specificationRangeInsideFormatString = expressionRangeInsideFormatString.intersection(specification.getRangeInElement());
                if (specificationRangeInsideFormatString != null && !specificationRangeInsideFormatString.isEmpty()) {
                    result.put(entry.getKey(), new RelativeRange(e.getValue(), specificationRangeInsideFormatString.shiftLeft(expressionRangeInsideFormatString.getStartOffset())));
                }
            }
        }

        return result;
    }

    @NotNull
    private static Map<TextRange, StringLiteralExpression> getRangesWithExpressionInsideResultingFormatString(List<StringLiteralExpression> targets) {
        int lastOffset = 0;
        Map<TextRange, StringLiteralExpression> result = new THashMap<>();

        TextRange range;
        for (Iterator var3 = targets.iterator(); var3.hasNext(); lastOffset = range.getEndOffset()) {
            StringLiteralExpression target = (StringLiteralExpression)var3.next();
            int length = target.getContents().length();
            range = TextRange.create(lastOffset, lastOffset + length);
            result.put(range, target);
        }

        return result;
    }

    @NotNull
    private static TextRange getRangeInsideDocument(@NotNull StringLiteralExpression formatExpression, @NotNull TextRange rangeInsideExpression) {
        return InjectedLanguageManager.getInstance(formatExpression.getProject())
            .injectedToHost(formatExpression, rangeInsideExpression)
            .shiftRight(formatExpression.getTextOffset())
            .shiftRight(formatExpression.getValueRange().getStartOffset());
    }

    private int resolveSpecificationIndexFromParameter(HashMap<Integer, PhpPackFormatSpecificationParser.PackSpecification> specifications) {
        int offset = 1;

        for (Map.Entry<Integer, PhpPackFormatSpecificationParser.PackSpecification> entry : specifications.entrySet()) {
            PhpPackFormatSpecificationParser.PackSpecification specification = entry.getValue();
            for (int r = 0; r < specification.getRepeater(); r++) {
                int parameterIndex = offset + r >= 0 ? this.myFormatExpressionIndex + offset + r : offset + r;
                if (parameterIndex == this.mySelectedParameterIndex) {
                    return entry.getKey();
                }
            }
            offset += specification.getRepeater();
        }

        return -1;
    }

    private int resolveSpecificationIndexFromCaret(@NotNull HashMap<Integer, RelativeRange> specificationRelativeRanges) {
        int caretOffset = this.myEditor.getCaretModel().getOffset();
        StringLiteralExpression selectedLiteralExpression = PhpPsiUtil.getParentByCondition(this.myFile.findElementAt(caretOffset), false, StringLiteralExpression.INSTANCEOF);
        return StreamEx.of(specificationRelativeRanges.entrySet())
            .findFirst((e) -> specificationAtCaretOffsetExists(caretOffset, selectedLiteralExpression, e.getValue()))
            .map(Map.Entry::getKey).orElse(-1);
    }

    private static boolean specificationAtCaretOffsetExists(int caretOffset, StringLiteralExpression formatExpression, RelativeRange specification) {
        return specification.getContainingExpression() == formatExpression
            && getRangeInsideDocument(formatExpression, specification.getRangeInsideExpression()).containsOffset(caretOffset);
    }

    @Override
    public boolean highlightReferences() {
        return true;
    }

    public static class RelativeRange {
        @NotNull
        private final StringLiteralExpression containingExpression;
        @NotNull
        private final TextRange rangeInsideExpression;

        private RelativeRange(@NotNull StringLiteralExpression containingExpression, @NotNull TextRange rangeInsideExpression) {
            this.containingExpression = containingExpression;
            this.rangeInsideExpression = rangeInsideExpression;
        }

        @NotNull
        public StringLiteralExpression getContainingExpression() {
            return this.containingExpression;
        }

        @NotNull
        public TextRange getRangeInsideExpression() {
            return this.rangeInsideExpression;
        }
    }
}
