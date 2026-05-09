package top.nustar.pixel.minecraft.yaml.component.api.expression;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.function.Supplier;

/**
 * @author NuStar
 * @since 2026/5/7 23:32
 */
@RequiredArgsConstructor
public class LineExpressionResult {
    @Getter
    private final String calculateResult;

    public static LineExpressionResult of(String calculateResult) {
        return new LineExpressionResult(calculateResult);
    }

    public int toInt() {
        return validation(() -> Integer.parseInt(calculateResult));
    }

    public double toDouble() {
        return validation(()->Double.parseDouble(calculateResult));
    }

    public boolean toBoolean() {
        return validation(()->Boolean.parseBoolean(calculateResult));
    }

    public float toFloat() {
        return validation(()->Float.parseFloat(calculateResult));
    }

    public long toLong() {
        return validation(()->Long.parseLong(calculateResult));
    }

    public short toShort() {
        return validation(()->Short.parseShort(calculateResult));
    }

    public byte toByte() {
        return validation(()->Byte.parseByte(calculateResult));
    }

    public char toChar() {
        return validation(()->calculateResult.charAt(0));
    }

    public <T> T validation(Supplier<T> supplier) {
        if (calculateResult == null || calculateResult.isEmpty()) {
            throw new IllegalArgumentException("表达式计算结果为空");
        }
        try {
            return supplier.get();
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("表达式计算结果不是数字");
        }
    }
}
