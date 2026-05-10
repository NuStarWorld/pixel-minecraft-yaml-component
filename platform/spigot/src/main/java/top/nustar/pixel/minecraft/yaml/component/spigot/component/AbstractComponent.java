package top.nustar.pixel.minecraft.yaml.component.spigot.component;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;
import top.nustar.pixel.minecraft.yaml.component.api.expression.LineExpression;
import top.nustar.pixel.minecraft.yaml.component.api.provider.AttributeProvider;

/**
 * @author NuStar
 * @since 2026/5/9 23:06
 */
public abstract class AbstractComponent {
    protected final LineExpression<Player> lineExpression = ((componentContext, expression)
            -> LineExpression
            .simple(componentContext, expression)
            .applyCalculator(componentContext, expression, (string)
                    -> PlaceholderAPI.setPlaceholders(componentContext.getHolder(), string))
    );

    protected static final AttributeProvider attributeProvider = AttributeProvider.Holder.getInstance();
}
