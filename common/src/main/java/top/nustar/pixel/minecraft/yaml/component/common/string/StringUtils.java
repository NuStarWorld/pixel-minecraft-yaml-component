package top.nustar.pixel.minecraft.yaml.component.common.string;

import org.jetbrains.annotations.NotNull;
import top.nustar.pixel.minecraft.yaml.component.common.Validation;

import java.util.Objects;

/**
 * @author NuStar
 * @since 2026/5/8 00:20
 */
public class StringUtils {
    public static boolean isEmpty(@NotNull CharSequence charSequence) {
        Validation.notNull(charSequence, "Char sequence must not be null");
        return charSequence.length() == 0;
    }

    public static boolean isNullOrEmpty(CharSequence charSequence) {
        return Objects.isNull(charSequence) || charSequence.length() == 0;
    }

    public static boolean isNotEmpty(@NotNull CharSequence charSequence) {
        return !isEmpty(charSequence);
    }

    public static boolean isNotNullOrEmpty(CharSequence charSequence) {
        return !isNullOrEmpty(charSequence);
    }

    public static boolean isBlank(@NotNull CharSequence charSequence) {
        Validation.notNull(charSequence, "Char sequence must not be null");
        if (isEmpty(charSequence)) {
            return true;
        }
        final int length = charSequence.length();
        for (int i = 0; i < length; i++) {
            if (!Character.isWhitespace(charSequence.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNullOrBlank(CharSequence charSequence) {
        if (isNullOrEmpty(charSequence)) {
            return true;
        }
        assert charSequence != null;
        final int length = charSequence.length();
        for (int i = 0; i < length; i++) {
            if (!Character.isWhitespace(charSequence.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotBlank(@NotNull CharSequence charSequence) {
        Validation.notNull(charSequence, "Char sequence must not be null");
        return !isBlank(charSequence);
    }

    public static boolean isNotNullOrBlank(CharSequence charSequence) {
        return !isNullOrBlank(charSequence);
    }

    public static boolean isChar(CharSequence charSequence) {
        if (isNullOrBlank(charSequence)) {
            return false;
        }
        assert charSequence != null;
        return charSequence.length() == 1;
    }

    public static boolean isNotChar(CharSequence charSequence) {
        return !isChar(charSequence);
    }

    public static boolean isBoolean(CharSequence charSequence) {
        if (isNullOrBlank(charSequence)) {
            return false;
        }
        int length = charSequence.length();
        switch (charSequence.charAt(0)) {
            case 't':
            case 'T':
                if (length != 4) {
                    return false;
                }
                return Boolean.TRUE.toString().equalsIgnoreCase(charSequence.toString());
            case 'f':
            case 'F':
                if (length != 5) {
                    return false;
                }
                return Boolean.FALSE.toString().equalsIgnoreCase(charSequence.toString());
            default:
                return false;
        }
    }

    public static boolean isNotBoolean(CharSequence charSequence) {
        return !isBoolean(charSequence);
    }

    @SuppressWarnings("DuplicatedCode")
    public static boolean isNumeric(CharSequence charSequence) {
        if (isNullOrBlank(charSequence)) {
            return false;
        }
        assert charSequence != null;
        int length = charSequence.length();
        char c = charSequence.charAt(0);
        if (length == 1) {
            return c >= '0' && c <= '9';
        }
        if (c != '-' && (c < '0' || c > '9')) {
            return false;
        }
        c = charSequence.charAt(length - 1);
        if (c < '0' || c > '9') {
            return false;
        }
        length = length - 1;
        boolean dotAlreadyExists = false;
        for (int i = 1; i < length; i++) {
            c = charSequence.charAt(i);
            if (c == '.') {
                if (dotAlreadyExists) {
                    return false;
                }
                dotAlreadyExists = true;
                continue;
            }
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotNumeric(CharSequence charSequence) {
        return !isNumeric(charSequence);
    }

    @SuppressWarnings("DuplicatedCode")
    public static boolean isInteger(CharSequence charSequence) {
        if (isNullOrBlank(charSequence)) {
            return false;
        }
        assert charSequence != null;
        int length = charSequence.length();
        char c = charSequence.charAt(0);
        if (length == 1) {
            return c >= '0' && c <= '9';
        }
        if (c != '-' && (c < '0' || c > '9')) {
            return false;
        }
        c = charSequence.charAt(length - 1);
        if (c < '0' || c > '9') {
            return false;
        }
        length = length - 1;
        for (int i = 1; i < length; i++) {
            c = charSequence.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotInteger(CharSequence charSequence) {
        return !isInteger(charSequence);
    }

    public static boolean isDecimal(CharSequence charSequence) {
        return isNumeric(charSequence) && countMatches(charSequence, '.') == 1;
    }

    public static boolean isNotDecimal(CharSequence charSequence) {
        return !isDecimal(charSequence);
    }

    public static int countMatches(CharSequence charSequence, char matched) {
        if (isNullOrEmpty(charSequence)) {
            return 0;
        }
        assert charSequence != null;
        final int stringLen = charSequence.length();
        int count = 0;
        for (int i = 0; i < stringLen; i++) {
            char c = charSequence.charAt(i);
            if (c == matched) {
                count = count + 1;
            }
        }
        return count;
    }

    public static int countMatches(CharSequence charSequence, CharSequence matched) {
        if (isNullOrEmpty(charSequence) || isNullOrEmpty(matched)) {
            return 0;
        }
        assert charSequence != null;
        assert matched != null;
        final int stringLen = charSequence.length();
        final int matchedLen = matched.length();
        if (stringLen < matchedLen) {
            return 0;
        }
        int count = 0;
        int matching = 0;
        for (int i = 0; i < stringLen; i++) {
            char c = charSequence.charAt(i);
            if (c == matched.charAt(matching)) {
                matching = matching + 1;
                if (matching >= matchedLen) {
                    matching = 0;
                    count = count + 1;
                }
            } else {
                matching = 0;
            }
        }
        return count;
    }
}
