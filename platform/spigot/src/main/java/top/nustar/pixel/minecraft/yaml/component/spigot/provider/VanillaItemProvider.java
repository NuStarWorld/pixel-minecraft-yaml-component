package top.nustar.pixel.minecraft.yaml.component.spigot.provider;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import top.nustar.pixel.minecraft.yaml.component.api.ComponentContext;
import top.nustar.pixel.minecraft.yaml.component.api.provider.MaterialProviderType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 原版物品 Provider，不依赖任何第三方插件，直接统计 / 扣除玩家原生背包里的物品。
 * <p>
 * meta-data 字段：
 * <ul>
 *     <li>{@code material}：必填，原版材质名。</li>
 *     <li>{@code data}：可选，1.12 子 ID / 损伤值，只在显式声明时参与比较。</li>
 *     <li>{@code name}：可选，显示名。</li>
 *     <li>{@code lore}：可选，YAML 列表，组件层已按 \n 连接。</li>
 *     <li>{@code name-match} / {@code lore-match}：可选，strict（默认）/ strip-color / contains。</li>
 * </ul>
 * 命中条件 = material 相等 且 每个已声明维度各自命中；未声明的维度不参与判定。
 * 附魔与自定义 NBT 不参与判定，因此只写 material 时，以该材质为底材的自定义物品也会被统计和扣除。
 *
 * @author NuStar
 * @since 2026/5/8 02:25
 */
@SuppressWarnings("unused")
public class VanillaItemProvider extends AbstractItemProvider {

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getItem(ComponentContext<?> context, String itemId) {
        return (T) getItem(context, itemId, Collections.<String, String>emptyMap());
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getItem(ComponentContext<?> context, String itemId, Map<String, String> metaData) {
        ItemStack itemStack = new ItemStack(parseMaterial(itemId), 1, parseData(metaData));

        String name = metaData.get("name");
        String lore = metaData.get("lore");
        if (name == null && lore == null) {
            return (T) itemStack;
        }

        ItemMeta itemMeta = itemStack.getItemMeta();
        if (name != null) {
            itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        }
        if (lore != null) {
            List<String> lines = new ArrayList<>();
            for (String line : splitLore(lore)) {
                lines.add(ChatColor.translateAlternateColorCodes('&', line));
            }
            itemMeta.setLore(lines);
        }
        itemStack.setItemMeta(itemMeta);
        return (T) itemStack;
    }

    @Override
    public <T> boolean isItem(T item, String itemId) {
        return isItem(item, itemId, Collections.<String, String>emptyMap());
    }

    @Override
    public <T> boolean isItem(T item, String itemId, Map<String, String> metaData) {
        ItemStack itemStack = (ItemStack) item;
        if (itemStack.getType() != parseMaterial(itemId)) {
            return false;
        }

        String data = metaData.get("data");
        if (data != null && itemStack.getDurability() != parseData(metaData)) {
            return false;
        }

        String name = metaData.get("name");
        String lore = metaData.get("lore");
        if (name == null && lore == null) {
            return true;
        }

        ItemMeta itemMeta = itemStack.hasItemMeta() ? itemStack.getItemMeta() : null;
        if (name != null) {
            String displayName = itemMeta != null && itemMeta.hasDisplayName() ? itemMeta.getDisplayName() : "";
            if (!VanillaMatchMode.of(metaData.get("name-match")).matches(name, displayName)) {
                return false;
            }
        }
        if (lore != null) {
            List<String> actualLore = itemMeta != null && itemMeta.hasLore()
                    ? itemMeta.getLore() : Collections.<String>emptyList();
            if (!VanillaMatchMode.of(metaData.get("lore-match")).matchesLore(splitLore(lore), actualLore)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 解析原版材质名。
     *
     * @param material 材质名，支持 minecraft:diamond 写法
     * @return 材质
     */
    private static Material parseMaterial(String material) {
        Material parsed = Material.matchMaterial(material);
        if (parsed == null) {
            throw new IllegalArgumentException("无法识别的原版材质: " + material);
        }
        return parsed;
    }

    /**
     * 解析子 ID / 损伤值，未声明时为 0。
     *
     * @param metaData 材料元数据
     * @return 子 ID
     */
    private static short parseData(Map<String, String> metaData) {
        String data = metaData.get("data");
        if (data == null || data.trim().isEmpty()) {
            return 0;
        }
        try {
            return Short.parseShort(data.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("meta-data.data 不是合法数字: " + data, e);
        }
    }

    /**
     * 把组件层用 \n 连接的 lore 拆回多行。
     *
     * @param lore 连接后的 lore
     * @return 按行拆分的 lore
     */
    private static List<String> splitLore(String lore) {
        return Arrays.asList(lore.split("\n", -1));
    }

    @Override
    public boolean isSupport() {
        return true;
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public MaterialProviderType getType() {
        return MaterialProviderType.MC;
    }
}
