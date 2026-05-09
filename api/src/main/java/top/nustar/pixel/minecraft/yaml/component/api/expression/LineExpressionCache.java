package top.nustar.pixel.minecraft.yaml.component.api.expression;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import top.nustar.pixel.minecraft.yaml.component.api.ComponentContext;

/**
 * @author NuStar
 * @since 2026/5/9 23:18
 */
@Data
@RequiredArgsConstructor
public class LineExpressionCache<T> {
    private final String expression;

    private final LineExpression<T> lineExpression;

    public static <T> LineExpressionCache<T> of(String expression, LineExpression<T> lineExpression) {
        return new LineExpressionCache<>(expression, lineExpression);
    }

    public LineExpressionResult calculate(ComponentContext<T> componentContext) {
        return LineExpressionResult.of(lineExpression.calculate(componentContext, expression));
    }
}
