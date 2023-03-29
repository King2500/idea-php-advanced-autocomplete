package net.king2500.plugins.PhpAdvancedAutoComplete.index;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Ref;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.containers.ContainerUtil;
import com.intellij.util.indexing.*;
import com.intellij.util.indexing.FileBasedIndex.InputFilter;
import com.intellij.util.io.DataExternalizer;
import com.intellij.util.io.EnumeratorStringDescriptor;
import com.intellij.util.io.KeyDescriptor;
import com.jetbrains.php.PhpClassHierarchyUtils;
import com.jetbrains.php.codeInsight.controlFlow.PhpControlFlowUtil;
import com.jetbrains.php.lang.PhpFileType;
import com.jetbrains.php.lang.psi.PhpFile;
import com.jetbrains.php.lang.psi.elements.Function;
import com.jetbrains.php.lang.psi.elements.PhpClassMember;
import gnu.trove.THashMap;
import net.king2500.plugins.PhpAdvancedAutoComplete.utils.PhpMetaUtil;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author Thomas Schulz <mail@king2500.net>
 */
public class PhpMetaCompletionIndex extends FileBasedIndexExtension<String, PhpMetaCompletion> {
    @NonNls
    public static final ID<String, PhpMetaCompletion> KEY = ID.create("php.advanced.meta.completion");
    public static final InputFilter INPUT_FILTER;
    public static final DataExternalizer<PhpMetaCompletion> VALUE_EXTERNALIZER;

    public static PhpMetaCompletion getMetaCompletion(@NotNull Project project, @NotNull Function function, int argumentIndex) {
        FileBasedIndex index = FileBasedIndex.getInstance();
        GlobalSearchScope scope = GlobalSearchScope.allScope(project);
        Ref<List<PhpMetaCompletion>> result = new Ref<>(ContainerUtil.emptyList());
        result.set(index.getValues(KEY, function.getFQN() + ":" + argumentIndex, scope));

        if (result.get().isEmpty() && function instanceof PhpClassMember) {
            PhpClassHierarchyUtils.processSuperMembers((PhpClassMember)function, (classMember, subClass, baseClass) -> {
                List<PhpMetaCompletion> values = index.getValues(KEY, classMember.getFQN() + ":" + argumentIndex, scope);
                if (values.isEmpty()) {
                    return true;
                } else {
                    result.set(values);
                    return false;
                }
            });
        }

        if (result.get().isEmpty()) {
            return null;
        }
        return result.get().get(0);
    }

    public PhpMetaCompletionIndex() {
    }

    @NotNull
    public ID<String, PhpMetaCompletion> getName() {
        return KEY;
    }

    @NotNull
    public DataIndexer<String, PhpMetaCompletion, FileContent> getIndexer() {
        return inputData -> {
            Map<String, PhpMetaCompletion> map = new THashMap<>();
            PsiFile file = inputData.getPsiFile();
            if (file instanceof PhpFile) {
                PhpControlFlowUtil.processFile((PhpFile)file, new PhpMetaCompletionCollector(map));
            }
            return map;
        };
    }

    @NotNull
    public DataExternalizer<PhpMetaCompletion> getValueExternalizer() {
        return VALUE_EXTERNALIZER;
    }

    @NotNull
    public KeyDescriptor<String> getKeyDescriptor() {
        return EnumeratorStringDescriptor.INSTANCE;
    }

    public int getVersion() {
        return 1;
    }

    @NotNull
    public InputFilter getInputFilter() {
        return INPUT_FILTER;
    }

    public boolean dependsOnFileContent() {
        return true;
    }

    static {
        INPUT_FILTER = new DefaultFileTypeSpecificInputFilter(PhpFileType.INSTANCE) {
            public boolean acceptInput(@NotNull VirtualFile file) {
                return PhpMetaUtil.isMetaFilename(file.getNameSequence()) && super.acceptInput(file);
            }
        };
        VALUE_EXTERNALIZER = new DataExternalizer<PhpMetaCompletion>() {
            @Override
            public void save(@NotNull DataOutput out, PhpMetaCompletion value) throws IOException {
                PhpMetaCompletionIndex.write(out, value);
            }

            @Override
            public PhpMetaCompletion read(@NotNull DataInput in) throws IOException {
                return PhpMetaCompletionIndex.read(in);
            }
        };
    }

    private static PhpMetaCompletion read(DataInput in) throws IOException {
        int argumentIndex = in.readInt();
        String completionList = StringUtil.nullize(in.readUTF());
        return new PhpMetaCompletion(argumentIndex, completionList);
    }

    private static void write(DataOutput out, PhpMetaCompletion metaCompletion) throws IOException {
        out.writeInt(metaCompletion.getArgumentIndex());
        out.writeUTF(StringUtil.notNullize(metaCompletion.getCompletionList()));
    }
}
