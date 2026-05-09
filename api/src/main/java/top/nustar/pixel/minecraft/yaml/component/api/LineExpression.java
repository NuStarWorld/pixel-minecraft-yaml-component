package top.nustar.pixel.minecraft.yaml.component.api;

import java.util.Map;
import java.util.function.Function;

/**
 * @author NuStar
 * @since 2026/5/7 23:25
 */
public interface LineExpression<T> {

    /**
     * 计算表达式
     * @param expression 表达式
     * @return 计算结果
     */
    String calculate(ComponentContext<T> componentContext, String expression);

    /**
     * 追加计算器
     * @param calculator 计算器
     * @return 计算结果
     */
    default String applyCalculator(ComponentContext<T> componentContext, String expression, Function<String, String> calculator) {
        return calculator.apply(calculate(componentContext, expression));
    }

    static <C> LineExpression<C> simple(ComponentContext<C> componentContext, String expression) {
        return (lambdaHolder, lambdaExpression) -> {
            String replace = expression;

            for (Map.Entry<String, String> placeholder : componentContext.getPlaceholderMap().entrySet()) {
                replace = replace.replace(placeholder.getKey(), placeholder.getValue());
            }
            return replace;
        };
    }
}
