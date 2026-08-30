package top.nustar.pixel.minecraft.yaml.component.spigot.provider;

import org.bukkit.ChatColor;

import java.util.List;

/**
 * 原版物品文本（显示名 / lore）的匹配档位。
 * <p>
 * 颜色码统一先做 {@code &} → {@code §} 归一化，再按档位比较。
 *
 * @author NuStar
 * @since 2026/5/8 02:25
 */
public enum VanillaMatchMode {

    /**
     * 归一化后全等；lore 逐行全等且行数相同。
     */
    STRICT,

    /**
     * 去掉颜色后全等；lore 逐行全等且行数相同。
     */
    STRIP_COLOR,

    /**
     * 显示名为去色后子串命中；lore 要求声明的每一行都能在物品某一行里找到，行数不必相同、顺序无关。
     */
    CONTAINS;

    /**
     * 解析匹配档位，未声明或空值时使用 {@link #STRICT}。
     *
     * @param value 配置值，形如 strict / strip-color / contains
     * @return 匹配档位
     */
    public static VanillaMatchMode of(String value) {
        if (value == null || value.trim().isEmpty()) {
            return STRICT;
        }
        String normalized = value.trim().toUpperCase().replace('-', '_');
        for (VanillaMatchMode mode : values()) {
            if (mode.name().equals(normalized)) {
                return mode;
            }
        }
        throw new IllegalArgumentException("未知的匹配模式: " + value + "，可选 strict / strip-color / contains");
    }

    /**
     * 判断单行文本是否匹配。
     *
     * @param expected 配置声明的文本
     * @param actual 物品上的实际文本
     * @return 是否匹配
     */
    public boolean matches(String expected, String actual) {
        String expectedText = translate(expected);
        String actualText = actual == null ? "" : actual;
        switch (this) {
            case STRICT:
                return expectedText.equals(actualText);
            case STRIP_COLOR:
                return strip(expectedText).equals(strip(actualText));
            case CONTAINS:
                return strip(actualText).contains(strip(expectedText));
            default:
                throw new IllegalStateException("未处理的匹配模式: " + name());
        }
    }

    /**
     * 判断 lore 是否匹配。
     * <p>
     * {@link #STRICT} 与 {@link #STRIP_COLOR} 要求行数相同且逐行匹配；
     * {@link #CONTAINS} 只要求声明的每一行都能在实际 lore 的某一行里找到。
     *
     * @param expected 配置声明的 lore，按行拆分
     * @param actual 物品上的实际 lore
     * @return 是否匹配
     */
    public boolean matchesLore(List<String> expected, List<String> actual) {
        if (this == CONTAINS) {
            for (String expectedLine : expected) {
                boolean hit = false;
                for (String actualLine : actual) {
                    if (matches(expectedLine, actualLine)) {
                        hit = true;
                        break;
                    }
                }
                if (!hit) {
                    return false;
                }
            }
            return true;
        }

        if (expected.size() != actual.size()) {
            return false;
        }
        for (int i = 0; i < expected.size(); i++) {
            if (!matches(expected.get(i), actual.get(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 把配置里的 {@code &} 颜色码转成 {@code §}。
     *
     * @param text 原始文本
     * @return 归一化后的文本
     */
    private static String translate(String text) {
        return text == null ? "" : ChatColor.translateAlternateColorCodes('&', text);
    }

    /**
     * 去掉颜色码。
     *
     * @param text 文本
     * @return 去色后的文本
     */
    private static String strip(String text) {
        String stripped = ChatColor.stripColor(text);
        return stripped == null ? "" : stripped;
    }
}
