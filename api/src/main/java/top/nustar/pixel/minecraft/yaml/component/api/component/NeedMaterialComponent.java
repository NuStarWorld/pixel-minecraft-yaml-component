package top.nustar.pixel.minecraft.yaml.component.api.component;

import top.nustar.pixel.minecraft.yaml.component.api.ComponentContext;
import top.nustar.pixel.minecraft.yaml.component.api.component.result.MaterialCheckResult;
import top.nustar.pixel.minecraft.yaml.component.api.expression.LineExpressionResult;
import top.nustar.pixel.minecraft.yaml.component.api.provider.MaterialProviderType;

import java.util.Map;

/**
 * @author NuStar
 * @since 2026/5/7 23:20
 */
public interface NeedMaterialComponent extends YamlComponent {

    /**
     * 获取材料消耗提供者类型
     * @return 材料消耗提供者类型
     */
    MaterialProviderType getType();

    /**
     * 获取材料消耗
     * @return 材料消耗
     */
    LineExpressionResult getAmount(ComponentContext<?> context);

    /**
     * 获取元数据
     *
     * @return 元数据
     */
    Map<String, String> getMetaData();

    /**
     * 检查是否满足条件
     * @param context 组件上下文
     * @return 材料检查结果
     */
    MaterialCheckResult check(ComponentContext<?> context);

    /**
     * 执行材料消耗
     *
     * @param context 组件上下文
     */
    void take(ComponentContext<?> context);

}
