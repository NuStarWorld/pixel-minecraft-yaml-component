package top.nustar.pixel.minecraft.yaml.component.api.component.result;

import lombok.Getter;
import top.nustar.pixel.minecraft.yaml.component.api.provider.MaterialProviderType;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author NuStar
 * @since 2026/6/6 16:57
 */
@Getter
public class MaterialCheckResult {

    private final boolean success;

    private final MaterialProviderType type;

    private final String materialId;

    private final double requiredAmount;

    private final double currentAmount;

    private final double missingAmount;

    private final Map<String, String> metaData;

    /**
     * 创建材料检查结果
     *
     * @param success 是否满足需求
     * @param type 材料类型
     * @param materialId 材料ID
     * @param requiredAmount 需要数量
     * @param currentAmount 当前数量
     * @param missingAmount 缺少数量
     * @param metaData 材料元数据
     */
    private MaterialCheckResult(boolean success, MaterialProviderType type, String materialId, double requiredAmount, double currentAmount, double missingAmount, Map<String, String> metaData) {
        this.success = success;
        this.type = type;
        this.materialId = materialId;
        this.requiredAmount = requiredAmount;
        this.currentAmount = currentAmount;
        this.missingAmount = missingAmount;
        this.metaData = metaData == null ? Collections.emptyMap() : Collections.unmodifiableMap(new HashMap<>(metaData));
    }

    /**
     * 根据需求数量和当前数量创建检查结果
     *
     * @param type 材料类型
     * @param materialId 材料ID
     * @param requiredAmount 需要数量
     * @param currentAmount 当前数量
     * @param metaData 材料元数据
     * @return 材料检查结果
     */
    public static MaterialCheckResult of(MaterialProviderType type, String materialId, double requiredAmount, double currentAmount, Map<String, String> metaData) {
        double missingAmount = Math.max(requiredAmount - currentAmount, 0);
        return new MaterialCheckResult(missingAmount <= 0, type, materialId, requiredAmount, currentAmount, missingAmount, metaData);
    }

    /**
     * 判断材料是否不足
     *
     * @return 材料是否不足
     */
    public boolean isMissing() {
        return !success;
    }
}
