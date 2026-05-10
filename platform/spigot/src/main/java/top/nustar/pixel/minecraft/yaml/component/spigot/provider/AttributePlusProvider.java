package top.nustar.pixel.minecraft.yaml.component.spigot.provider;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.serverct.ersha.api.AttributeAPI;
import top.nustar.pixel.minecraft.yaml.component.api.provider.AttributeProvider;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author NuStar
 * @since 2026/5/10 03:04
 */
public class AttributePlusProvider implements AttributeProvider {
    @Override
    public void applyAttributes(UUID holder, Object attributeName, List<String> attributes) {
        Player player;
        if ((player = Bukkit.getPlayer(holder)) == null) return;
        AttributeAPI.addSourceAttribute(AttributeAPI.getAttrData(player), String.valueOf(attributeName), Stream.of(attributes).flatMap(List::stream).collect(Collectors.toList()));
        AttributeAPI.updateAttribute(player);
    }

    @Override
    public void removeAttributes(UUID holder, Object attributeName) {
        Player player;
        if ((player = Bukkit.getPlayer(holder)) == null) return;
        AttributeAPI.takeSourceAttribute(AttributeAPI.getAttrData(player), String.valueOf(attributeName));
    }

    @Override
    public boolean isSupport() {
        return Bukkit.getPluginManager().isPluginEnabled("AttributePlus");
    }

    @Override
    public int getPriority() {
        return 0;
    }
}
