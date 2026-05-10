package top.nustar.pixel.minecraft.yaml.component.spigot.component;

import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import top.nustar.pixel.minecraft.yaml.component.api.ComponentContext;
import top.nustar.pixel.minecraft.yaml.component.api.expression.LineExpressionCache;
import top.nustar.pixel.minecraft.yaml.component.api.expression.LineExpressionResult;
import top.nustar.pixel.minecraft.yaml.component.api.component.NeedMaterialComponent;
import top.nustar.pixel.minecraft.yaml.component.api.component.annotation.ComponentMetaData;
import top.nustar.pixel.minecraft.yaml.component.api.provider.MaterialProviderType;
import top.nustar.pixel.minecraft.yaml.component.common.Validation;

import java.util.HashMap;
import java.util.Map;

/**
 * @author NuStar
 * @since 2026/5/7 23:57
 */
@ComponentMetaData(type = ComponentMetaData.ComponentType.NEED_MATERIAL)
@Getter
public class NeedMaterialComponentImpl extends AbstractComponent implements NeedMaterialComponent {

    protected final MaterialProviderType type;

    private final LineExpressionCache<Player> amountExpression;

    protected final Map<String, String> metaData = new HashMap<>();

    public NeedMaterialComponentImpl(ConfigurationSection section) {
        Validation.notNull(section, "section");
        Validation.notNull(section.getString("type"), "type");
        Validation.notNull(section.getString("amount"), "amount");
        Validation.notNull(section.getConfigurationSection("meta-data"), "meta-data");
        this.type = MaterialProviderType.of(section.getString("type"));
        this.amountExpression = LineExpressionCache.of(section.getString("amount"), lineExpression);
        for (Map.Entry<String, Object> metaDataEntry : section.getConfigurationSection("meta-data").getValues(false).entrySet()) {
            metaData.put(metaDataEntry.getKey(), metaDataEntry.getValue().toString());
        }
    }

    @Override
    public boolean check(ComponentContext<?> context) {
        int amount = getAmount(context).toInt();
        return type.check(context, metaData, amount);
    }

    @Override
    public void take(ComponentContext<?> context) {
        int amount = getAmount(context).toInt();
        type.take(context, metaData, amount);
    }

    @SuppressWarnings("unchecked")
    @Override
    public LineExpressionResult getAmount(ComponentContext<?> context) {
        return amountExpression.calculate((ComponentContext<Player>) context);
    }

}
