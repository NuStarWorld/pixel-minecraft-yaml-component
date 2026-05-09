package top.nustar.pixel.minecraft.yaml.component.spigot.component;

import lombok.Getter;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import top.nustar.pixel.minecraft.yaml.component.api.ComponentContext;
import top.nustar.pixel.minecraft.yaml.component.api.LineExpression;
import top.nustar.pixel.minecraft.yaml.component.api.LineExpressionResult;
import top.nustar.pixel.minecraft.yaml.component.api.component.NeedMaterialComponent;
import top.nustar.pixel.minecraft.yaml.component.api.component.annotation.ComponentMetaData;
import top.nustar.pixel.minecraft.yaml.component.api.provider.ProviderType;
import top.nustar.pixel.minecraft.yaml.component.common.Validation;

import java.util.HashMap;
import java.util.Map;

/**
 * @author NuStar
 * @since 2026/5/7 23:57
 */
@ComponentMetaData(name = "need-material")
@Getter
public class NeedMaterialComponentImpl implements NeedMaterialComponent {

    private final ProviderType type;

    private final String amountExpression;

    private final LineExpression<Player> amount = ((componentContext, expression)
            -> LineExpression
            .simple(componentContext, expression)
            .applyCalculator(componentContext, expression, (string)
                    -> PlaceholderAPI.setPlaceholders(componentContext.getHolder(), string))
    );

    private final Map<String, String> metaData = new HashMap<>();

    public NeedMaterialComponentImpl(ConfigurationSection section) {
        Validation.notNull(section, "section");
        Validation.notNull(section.getString("type"), "type");
        Validation.notNull(section.getString("amount"), "amount");
        Validation.notNull(section.getConfigurationSection("meta-data"), "meta-data");
        this.type = ProviderType.of(section.getString("type"));
        this.amountExpression = section.getString("amount");
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
        return LineExpressionResult.of(amount.calculate((ComponentContext<Player>) context, amountExpression));
    }

}
