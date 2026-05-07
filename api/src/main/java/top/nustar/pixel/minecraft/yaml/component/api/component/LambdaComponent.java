package top.nustar.pixel.minecraft.yaml.component.api.component;

import top.nustar.pixel.minecraft.yaml.component.api.ComponentContext;
import top.nustar.pixel.minecraft.yaml.component.api.LineExpressionResult;

/**
 * @author NuStar
 * @since 2026/5/9 18:26
 */
public interface LambdaComponent {

    /**
     * 执行组件
     *
     * @param context 组件上下文
     * @return 执行结果
     */
    LineExpressionResult execute(ComponentContext<?> context);
}
