package top.nustar.pixel.minecraft.yaml.component.spigot.provider;

import github.saukiya.sxitem.SXItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import top.nustar.pixel.minecraft.yaml.component.api.ComponentContext;
import top.nustar.pixel.minecraft.yaml.component.api.provider.MaterialProviderType;


/**
 * @author NuStar
 * @since 2026/8/30 05:46
 */
@SuppressWarnings("unused")
public class SXItemProvider extends AbstractItemProvider {
    @Override
    @SuppressWarnings("unchecked")
    public <T> T getItem(ComponentContext<?> context, String itemId) {
        Player player = (Player) context.getHolder();
        return (T) SXItem.getItemManager().getItem(itemId, player);
    }

    @Override
    public <T> boolean isItem(T item, String itemId) {
        ItemStack itemStack = (ItemStack) item;
        String sxItemId = SXItem.getItemManager().getItemKey(itemStack);
        return itemId.equals(sxItemId);
    }

    @Override
    public boolean isSupport() {
        return Bukkit.getPluginManager().isPluginEnabled("SX-Item");
    }

    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    public MaterialProviderType getType() {
        return MaterialProviderType.SXITEM;
    }
}
