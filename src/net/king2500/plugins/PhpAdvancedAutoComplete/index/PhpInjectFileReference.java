package net.king2500.plugins.PhpAdvancedAutoComplete.index;

/**
 * @author Thomas Schulz <mail@king2500.net>
 */
public class PhpInjectFileReference {
    private final int argumentIndex;
    private final RelativeMode relativeMode;
    private final String prefix;

    PhpInjectFileReference(int argumentIndex, RelativeMode relativeMode, String prefix) {
        this.argumentIndex = argumentIndex;
        this.relativeMode = relativeMode;
        this.prefix = prefix;
    }

    public int getArgumentIndex() {
        return argumentIndex;
    }

    public RelativeMode getRelativeMode() {
        return relativeMode;
    }

    String getPrefix() {
        return prefix;
    }

    public enum RelativeMode {
        AUTO,
        TOP_LEVEL,
        CURRENT_FILE
    }
}
