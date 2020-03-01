package net.king2500.plugins.PhpAdvancedAutoComplete.utils;

/**
 * @author Thomas Schulz <mail@king2500.net>
 */
public class StringUtil {

    public static int getPrecedingCharNum(CharSequence charSequence, int offset, char character) {
        int num = 0;
        while (offset >= 1 && charSequence.charAt(offset - 1) == character) {
            offset--;
            num++;
        }

        return num;
    }

}
