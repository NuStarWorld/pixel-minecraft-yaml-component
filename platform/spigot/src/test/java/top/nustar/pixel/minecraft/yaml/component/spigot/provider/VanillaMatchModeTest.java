package top.nustar.pixel.minecraft.yaml.component.spigot.provider;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class VanillaMatchModeTest {

    @Test
    void 未声明时默认为严格模式() {
        assertEquals(VanillaMatchMode.STRICT, VanillaMatchMode.of(null));
        assertEquals(VanillaMatchMode.STRICT, VanillaMatchMode.of(""));
        assertEquals(VanillaMatchMode.STRICT, VanillaMatchMode.of("strict"));
    }

    @Test
    void 模式名支持连字符与大小写() {
        assertEquals(VanillaMatchMode.STRIP_COLOR, VanillaMatchMode.of("strip-color"));
        assertEquals(VanillaMatchMode.STRIP_COLOR, VanillaMatchMode.of("STRIP_COLOR"));
        assertEquals(VanillaMatchMode.CONTAINS, VanillaMatchMode.of("contains"));
    }

    @Test
    void 未知模式直接报错() {
        assertThrows(IllegalArgumentException.class, () -> VanillaMatchMode.of("fuzzy"));
    }

    @Test
    void 严格模式把配置的颜色码归一化后全等() {
        assertTrue(VanillaMatchMode.STRICT.matches("&a神契碎片", "\u00a7a神契碎片"));
        assertFalse(VanillaMatchMode.STRICT.matches("&a神契碎片", "\u00a7b神契碎片"));
        assertFalse(VanillaMatchMode.STRICT.matches("&a神契碎片", "神契碎片"));
    }

    @Test
    void 去色模式忽略颜色差异() {
        assertTrue(VanillaMatchMode.STRIP_COLOR.matches("&a神契碎片", "\u00a7b神契碎片"));
        assertTrue(VanillaMatchMode.STRIP_COLOR.matches("神契碎片", "\u00a7b神契碎片"));
        assertFalse(VanillaMatchMode.STRIP_COLOR.matches("&a神契碎片", "\u00a7b神契残片"));
    }

    @Test
    void 子串模式只要求包含() {
        assertTrue(VanillaMatchMode.CONTAINS.matches("碎片", "\u00a7a神契碎片"));
        assertFalse(VanillaMatchMode.CONTAINS.matches("神剑", "\u00a7a神契碎片"));
    }

    @Test
    void 显示名缺失时按空字符串处理() {
        assertFalse(VanillaMatchMode.STRICT.matches("&a神契碎片", null));
        assertTrue(VanillaMatchMode.STRICT.matches("", null));
    }

    @Test
    void 严格模式的lore要求行数相同且逐行全等() {
        assertTrue(VanillaMatchMode.STRICT.matchesLore(
                Arrays.asList("&7来自古代", "&7不可交易"),
                Arrays.asList("\u00a77来自古代", "\u00a77不可交易")));

        assertFalse(VanillaMatchMode.STRICT.matchesLore(
                Arrays.asList("&7来自古代", "&7不可交易"),
                Collections.singletonList("\u00a77来自古代")));

        assertFalse(VanillaMatchMode.STRICT.matchesLore(
                Arrays.asList("&7不可交易", "&7来自古代"),
                Arrays.asList("\u00a77来自古代", "\u00a77不可交易")));
    }

    @Test
    void 子串模式的lore行数不必相同且顺序无关() {
        assertTrue(VanillaMatchMode.CONTAINS.matchesLore(
                Collections.singletonList("不可交易"),
                Arrays.asList("\u00a77来自古代", "\u00a77不可交易", "\u00a77等级 10")));

        assertTrue(VanillaMatchMode.CONTAINS.matchesLore(
                Arrays.asList("不可交易", "来自古代"),
                Arrays.asList("\u00a77来自古代", "\u00a77不可交易")));

        assertFalse(VanillaMatchMode.CONTAINS.matchesLore(
                Arrays.asList("不可交易", "绑定灵魂"),
                Arrays.asList("\u00a77来自古代", "\u00a77不可交易")));
    }

    @Test
    void 空lore与无lore物品在严格模式下不相等() {
        assertFalse(VanillaMatchMode.STRICT.matchesLore(
                Collections.singletonList("&7来自古代"),
                Collections.<String>emptyList()));

        assertTrue(VanillaMatchMode.STRICT.matchesLore(
                Collections.<String>emptyList(),
                Collections.<String>emptyList()));
    }
}
