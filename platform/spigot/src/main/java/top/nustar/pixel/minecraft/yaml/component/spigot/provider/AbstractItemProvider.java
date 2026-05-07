package top.nustar.pixel.minecraft.yaml.component.spigot.provider;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import top.nustar.pixel.minecraft.yaml.component.api.ComponentContext;
import top.nustar.pixel.minecraft.yaml.component.api.provider.ItemProvider;

/**
 * @author NuStar
 * @since 2026/5/8 02:25
 */
public abstract class AbstractItemProvider implements ItemProvider {

    @Override
    public boolean check(ComponentContext<?> context, String itemId, int amount) {
        Player player = (Player) context.getHolder();
        int count = 0;
        for (ItemStack content : player.getInventory().getContents()) {
            if (count >= amount) {
                return true;
            }

            if (content == null || content.getType() == Material.AIR) {
                continue;
            }

            if (isItem(content, itemId)) {
                count += content.getAmount();
            }
        }
        return count >= amount;
    }

    @Override
    public void take(ComponentContext<?> context, String materialId, int amount) {
        Player player = (Player) context.getHolder();
        int reaming = amount;
        for (ItemStack content : player.getInventory().getContents()) {
            if (reaming == 0) {
                return;
            }

            if (content == null || content.getType() == Material.AIR) {
                continue;
            }

            if (isItem(content, materialId)) {
                int takeAmount = Math.min(reaming, content.getAmount());
                content.setAmount(content.getAmount() - takeAmount);
                reaming -= takeAmount;
            }
        }
    }
}
