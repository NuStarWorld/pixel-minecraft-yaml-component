package top.nustar.pixel.minecraft.yaml.component.api.provider;

import top.nustar.pixel.minecraft.yaml.component.api.ComponentContext;
import top.nustar.pixel.minecraft.yaml.component.api.component.result.MaterialCheckResult;

import java.util.Map;

/**
 * @author NuStar
 * @since 2026/5/8 02:41
 */
public interface MaterialConsumer {

    /**
     * 获取当前材料数量
     *
     * @param context 组件上下文
     * @param materialId 材料ID
     * @return 当前材料数量
     */
    double getAmount(ComponentContext<?> context, String materialId);

    /**
     * 检查材料是否满足需求
     *
     * @param context 组件上下文
     * @param materialId 材料ID
     * @param amount 需求数量
     * @param metaData 材料元数据
     * @return 材料检查结果
     */
    default MaterialCheckResult check(ComponentContext<?> context, String materialId, int amount, Map<String, String> metaData) {
        return MaterialCheckResult.of(getType(), materialId, amount, getAmount(context, materialId), metaData);
    }

    /**
     * 扣除材料
     *
     * @param context 组件上下文
     * @param materialId 材料ID
     * @param amount 扣除数量
     */
    void take(ComponentContext<?> context, String materialId, int amount);

    /**
     * 获取材料类型
     *
     * @return 材料类型
     */
    MaterialProviderType getType();
}
