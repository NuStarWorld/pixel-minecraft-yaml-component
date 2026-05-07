package top.nustar.pixel.minecraft.yaml.component.spigot.provider;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pers.neige.neigeitems.item.ItemInfo;
import pers.neige.neigeitems.manager.ItemManager;
import top.nustar.pixel.minecraft.yaml.component.api.ComponentContext;
import top.nustar.pixel.minecraft.yaml.component.api.provider.ProviderType;

/**
 * @author NuStar
 * @since 2026/5/8 01:53
 */
@SuppressWarnings("unused")
public class NeigeItemProvider extends AbstractItemProvider {
    @Override
    @SuppressWarnings("unchecked")
    public <T> T getItem(ComponentContext<?> context, String itemId) {
        Player player = (Player) context.getHolder();
        return (T) ItemManager.INSTANCE.getItemStack(itemId, player);
    }

    @Override
    public <T> boolean isItem(T item, String itemId) {
        ItemStack itemStack = (ItemStack) item;
        ItemInfo niItem = ItemManager.INSTANCE.isNiItem(itemStack);
        return niItem != null && niItem.getId().equals(itemId);
    }

    @Override
    public boolean isSupport() {
        return Bukkit.getPluginManager().isPluginEnabled("NeigeItems");
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public ProviderType getType() {
        return ProviderType.NI;
    }
}
