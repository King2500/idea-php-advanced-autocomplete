package net.king2500.plugins.PhpAdvancedAutoComplete.index;

/**
 * @author Thomas Schulz <mail@king2500.net>
 */
public class PhpInjectFileReference {
    private final int argumentIndex;
    private final RelativeMode relativeMode;
    private final String prefix;

    public PhpInjectFileReference(int argumentIndex) {
        this.argumentIndex = argumentIndex;
        this.relativeMode = RelativeMode.AUTO;
        this.prefix = "";
    }
    public PhpInjectFileReference(int argumentIndex, RelativeMode relativeMode) {
        this.argumentIndex = argumentIndex;
        this.relativeMode = relativeMode;
        this.prefix = "";
    }
    public PhpInjectFileReference(int argumentIndex, RelativeMode relativeMode, String prefix) {
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

    public String getPrefix() {
        return prefix;
    }

    public static enum RelativeMode {
        AUTO,
        TOP_LEVEL,
        CURRENT_FILE
    }
}
