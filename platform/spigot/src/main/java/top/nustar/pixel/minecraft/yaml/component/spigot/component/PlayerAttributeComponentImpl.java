package top.nustar.pixel.minecraft.yaml.component.spigot.component;

import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import top.nustar.pixel.minecraft.yaml.component.api.ComponentContext;
import top.nustar.pixel.minecraft.yaml.component.api.component.PlayerAttributeComponent;
import top.nustar.pixel.minecraft.yaml.component.api.component.annotation.ComponentMetaData;
import top.nustar.pixel.minecraft.yaml.component.api.expression.LineExpressionCache;
import top.nustar.pixel.minecraft.yaml.component.api.expression.LineExpressionResult;
import top.nustar.pixel.minecraft.yaml.component.common.Validation;

import java.util.ArrayList;
import java.util.List;

/**
 * @author NuStar
 * @since 2026/5/9 23:00
 */
@ComponentMetaData(type = ComponentMetaData.ComponentType.ATTRIBUTE)
public class PlayerAttributeComponentImpl extends AbstractComponent implements PlayerAttributeComponent {

    @Getter
    protected final String attributeName;

    protected List<LineExpressionCache<Player>> attributeExpressionList = new ArrayList<>();

    public PlayerAttributeComponentImpl(ConfigurationSection section) {
        this.attributeName = section.getString("attribute-name");
        List<String> defaultAttributes = section.getStringList("default-attributes");
        Validation.notNull(defaultAttributes, "defaultAttributes");

        for (String defaultAttribute : defaultAttributes) {
            attributeExpressionList.add(LineExpressionCache.of(defaultAttribute, lineExpression));
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<String> getAttributeList(ComponentContext<?> componentContext) {
        List<String> attributeList = new ArrayList<>();
        for (LineExpressionCache<Player> lineExpressionCache : attributeExpressionList) {
            LineExpressionResult expressionResult = lineExpressionCache.calculate((ComponentContext<Player>) componentContext);
            attributeList.add(expressionResult.getCalculateResult());
        }
        return attributeList;
    }

    @Override
    public void applyAttribute(ComponentContext<?> componentContext) {
        attributeProvider.applyAttributes(((Player) componentContext.getHolder()).getUniqueId(), attributeName, getAttributeList(componentContext));
    }
}
