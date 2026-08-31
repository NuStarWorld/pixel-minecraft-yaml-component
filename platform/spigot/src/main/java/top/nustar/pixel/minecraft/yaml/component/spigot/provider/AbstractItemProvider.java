package top.nustar.pixel.minecraft.yaml.component.spigot.provider;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.ItemStack;
import top.nustar.pixel.minecraft.yaml.component.api.ComponentContext;
import top.nustar.pixel.minecraft.yaml.component.api.provider.ItemProvider;

import java.util.Map;

/**
 * @author NuStar
 * @since 2026/5/8 02:25
 */
public abstract class AbstractItemProvider implements ItemProvider {

    /**
     * 玩家主背包槽位数。
     * <p>
     * 各版本的玩家背包布局一致：0-35 是主背包（0-8 快捷栏，9-35 储物区），36-39 是盔甲槽，40 是副手。
     * 因此按索引遍历前 36 格就能只吃主背包，且不依赖 1.9 才加入的 {@code getStorageContents()}，
     * 在 1.8.8 上同样可用。
     */
    private static final int STORAGE_SIZE = 36;

    @Override
    public double getAmount(ComponentContext<?> context, String itemId, Map<String, String> metaData) {
        Player player = (Player) context.getHolder();
        PlayerInventory inventory = player.getInventory();
        int storageSize = Math.min(inventory.getSize(), STORAGE_SIZE);

        int count = 0;
        for (int i = 0; i < storageSize; i++) {
            ItemStack content = inventory.getItem(i);
            if (content == null || content.getType() == Material.AIR) {
                continue;
            }

            if (isItem(content, itemId, metaData)) {
                count += content.getAmount();
            }
        }
        return count;
    }

    /**
     * 从玩家主背包扣除物品。
     * <p>
     * 先统计总量，不足时直接抛出且不动背包，避开“扣了一半就返回”造成的不可逆损失。
     *
     * @param context 组件上下文
     * @param materialId 材料ID
     * @param amount 扣除数量
     * @param metaData 材料元数据
     */
    @Override
    public void take(ComponentContext<?> context, String materialId, int amount, Map<String, String> metaData) {
        Player player = (Player) context.getHolder();
        PlayerInventory inventory = player.getInventory();
        int storageSize = Math.min(inventory.getSize(), STORAGE_SIZE);

        int total = 0;
        for (int i = 0; i < storageSize; i++) {
            ItemStack content = inventory.getItem(i);
            if (content == null || content.getType() == Material.AIR) {
                continue;
            }
            if (isItem(content, materialId, metaData)) {
                total += content.getAmount();
            }
        }
        if (total < amount) {
            throw new IllegalStateException("材料不足，需要 " + amount + " 个，实际 " + total + " 个: " + materialId);
        }

        int reaming = amount;
        for (int i = 0; i < storageSize && reaming > 0; i++) {
            ItemStack content = inventory.getItem(i);
            if (content == null || content.getType() == Material.AIR) {
                continue;
            }

            if (isItem(content, materialId, metaData)) {
                int takeAmount = Math.min(reaming, content.getAmount());
                int left = content.getAmount() - takeAmount;
                if (left == 0) {
                    inventory.setItem(i, null);
                } else {
                    content.setAmount(left);
                    inventory.setItem(i, content);
                }
                reaming -= takeAmount;
            }
        }
    }
}
