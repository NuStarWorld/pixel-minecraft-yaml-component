package top.nustar.pixel.minecraft.yaml.component.common;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.nustar.pixel.minecraft.yaml.component.common.exception.ValidationException;
import top.nustar.pixel.minecraft.yaml.component.common.string.StringUtils;

import java.util.Objects;

/**
 * @author NuStar
 * @since 2026/5/8 00:18
 */
public class Validation {
    public static Boolean require(Boolean expression, String message) {
        is(expression, message);
        return expression;
    }

    public static void is(Boolean expression, String message) {
        if (!Boolean.TRUE.equals(expression)) {
            if (Objects.isNull(message)) {
                throw new ValidationException();
            }
            throw new ValidationException(message);
        }
    }

    public static Boolean requireNot(Boolean expression, String message) {
        not(expression, message);
        return expression;
    }

    public static void not(Boolean expression, String message) {
        if (Boolean.TRUE.equals(expression)) {
            if (Objects.isNull(message)) {
                throw new ValidationException();
            }
            throw new ValidationException(message);
        }
    }

    @Nullable
    public static <T> T requireNull(T object, String message) {
        isNull(object, message);
        return object;
    }

    public static void isNull(Object object, String message) {
        is(Objects.isNull(object), message);
    }

    @NotNull
    public static <T> T requireNotNull(T object, String message) {
        notNull(object, message);
        return object;
    }

    public static void notNull(Object object, String message) {
        is(Objects.nonNull(object), message);
    }

    public static void isInstanceOf(Object object, Class<?> clazz, String message) {
        is(clazz.isInstance(object), message);
    }

    @NotNull
    public static <T extends CharSequence> T requireBlank(T string, String message) {
        isBlank(string, message);
        return string;
    }

    public static void isBlank(CharSequence string, String message) {
        is(StringUtils.isBlank(string), message);
    }

    @Nullable
    public static <T extends CharSequence> T requireNullOrBlank(T string, String message) {
        isNullOrBlank(string, message);
        return string;
    }

    public static void isNullOrBlank(CharSequence string, String message) {
        is(StringUtils.isNullOrBlank(string), message);
    }

    public static <T extends CharSequence> T requireNotBlank(T string, String message) {
        notBlank(string, message);
        return string;
    }

    public static void notBlank(CharSequence string, String message) {
        is(StringUtils.isNotBlank(string), message);
    }

    public static <T extends CharSequence> T requireNotNullOrBlank(T string, String message) {
        notNullOrBlank(string, message);
        return string;
    }

    public static void notNullOrBlank(CharSequence string, String message) {
        is(StringUtils.isNotNullOrBlank(string), message);
    }

    public static <T extends CharSequence> T requireEmpty(T string, String message) {
        isEmpty(string, message);
        return string;
    }

    public static void isEmpty(CharSequence string, String message) {
        is(StringUtils.isEmpty(string), message);
    }

    public static <T extends CharSequence> T requireNullOrEmpty(T string, String message) {
        isNullOrEmpty(string, message);
        return string;
    }

    public static void isNullOrEmpty(CharSequence string, String message) {
        is(StringUtils.isNullOrEmpty(string), message);
    }

    public static <T extends CharSequence> T requireNotEmpty(T string, String message) {
        notEmpty(string, message);
        return string;
    }

    public static void notEmpty(CharSequence string, String message) {
        is(StringUtils.isNotEmpty(string), message);
    }

    public static <T extends CharSequence> T requireNotNullOrEmpty(T string, String message) {
        notNullOrEmpty(string, message);
        return string;
    }

    public static void notNullOrEmpty(CharSequence string, String message) {
        is(StringUtils.isNotNullOrEmpty(string), message);
    }

    public static <T extends CharSequence> T requireNumeric(T string, String message) {
        isNumeric(string, message);
        return string;
    }

    public static void isNumeric(CharSequence string, String message) {
        is(StringUtils.isNumeric(string), message);
    }

    public static <T extends CharSequence> T requireNotNumeric(T string, String message) {
        notNumeric(string, message);
        return string;
    }

    public static void notNumeric(CharSequence string, String message) {
        is(StringUtils.isNotNumeric(string), message);
    }

    public static <T extends CharSequence> T requireInteger(T string, String message) {
        isInteger(string, message);
        return string;
    }

    public static void isInteger(CharSequence string, String message) {
        is(StringUtils.isInteger(string), message);
    }

    public static <T extends CharSequence> T requireNotInteger(T string, String message) {
        notInteger(string, message);
        return string;
    }

    public static void notInteger(CharSequence string, String message) {
        is(StringUtils.isNotInteger(string), message);
    }
}
