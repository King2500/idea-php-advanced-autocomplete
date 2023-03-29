package net.king2500.plugins.PhpAdvancedAutoComplete.index;

/**
 * @author Thomas Schulz <mail@king2500.net>
 */
public class PhpMetaCompletion {
    private final int argumentIndex;
    private final String completionList;

    PhpMetaCompletion(int argumentIndex, String completionList) {
        this.argumentIndex = argumentIndex;
        this.completionList = completionList;
    }

    public int getArgumentIndex() {
        return argumentIndex;
    }

    public String getCompletionList() {
        return completionList;
    }
}
