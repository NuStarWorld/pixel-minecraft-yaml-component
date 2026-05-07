package top.nustar.pixel.minecraft.yaml.component.api;

import java.util.Map;

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
    String calculate(T holder, String expression, Map<String, String> placeholderMap);
}
