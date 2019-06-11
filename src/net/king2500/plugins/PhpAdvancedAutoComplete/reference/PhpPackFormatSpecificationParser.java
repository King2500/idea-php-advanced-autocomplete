package net.king2500.plugins.PhpAdvancedAutoComplete.reference;

import com.intellij.openapi.util.TextRange;
import com.intellij.util.containers.hash.HashMap;
import net.king2500.plugins.PhpAdvancedAutoComplete.PhpCompletionTokens;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

/**
 * @author Thomas Schulz <mail@king2500.net>
 */
class PhpPackFormatSpecificationParser {
    private PhpPackFormatSpecificationParser() {
    }

    static HashMap<Integer, PackSpecification> parseFormat(@NotNull String expression, boolean singleQuote, int parametersCount) {
        int index = 0;
        int num = 0;
        HashMap<Integer, PackSpecification> result = new HashMap<>();
        List<String> codesList = Arrays.asList(PhpCompletionTokens.packCodes);

        while (index < expression.length()) {
            char c = expression.charAt(index);

            // Cancel when we hit variables inside string
            if (!singleQuote && charAtEqualsToAny(expression, index, '$')) {
                return result;
            }

            if (!codesList.contains(Character.toString(c)) || charAtEqualsToAny(expression, index, 'x', 'X')) {
                ++index;
                continue;
            }

            int endIndex = index;
            int repeater = 1;

            TextRange width = parseUnsignedInt(expression, index+1);
            if (width != null && !charAtEqualsToAny(expression, index, 'a', 'A', 'h', 'H')) {
                endIndex = width.getEndOffset() - 1;
                repeater = Integer.parseUnsignedInt(width.substring(expression));
            }

            boolean repeatToEnd = charAtEqualsToAny(expression, index + 1, '*');
            if (repeatToEnd) {
                ++endIndex;
                repeater = parametersCount - num;
            }

            TextRange range = TextRange.create(index, endIndex + 1);
            result.put(num, new PackSpecification(range, repeater));

            if (repeatToEnd) {
                return result;
            }
            ++num;
            ++index;
        }
        return result;
    }

    private static boolean charAtEqualsToAny(@NotNull String s, int index, char... chars) {
        if (index >= 0 && index < s.length()) {
            for (char c : chars) {
                if (c == s.charAt(index)) {
                    return true;
                }
            }
        }

        return false;
    }

    @Nullable
    private static TextRange parseUnsignedInt(@NotNull String expression, int from) {
        if (!isDigit(expression, from)) {
            return null;
        } else {
            int start;
            start = from;
            while (isDigit(expression, from)) {
                ++from;
            }

            return TextRange.create(start, from);
        }
    }

    private static boolean isDigit(@NotNull String expression, int index) {
        return index >= 0 && index < expression.length() && Character.isDigit(expression.charAt(index));
    }


    public static class PackSpecification {
        @NotNull
        private final TextRange myRange;
        private final int myRepeater;

        private PackSpecification(@NotNull TextRange range, int repeater) {
            this.myRange = range;
            this.myRepeater = repeater;
        }

        public TextRange getRangeInElement() {
            return this.myRange;
        }

        public int getRepeater() {
            return this.myRepeater;
        }
    }
}
