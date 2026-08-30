package top.nustar.pixel.minecraft.yaml.component.spigot.component;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemoryConfiguration;
import org.junit.jupiter.api.Test;
import top.nustar.pixel.minecraft.yaml.component.api.provider.MaterialProviderType;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NeedMaterialMetaDataTest {

    @Test
    void meta_data里的yaml列表按换行连接() {
        MemoryConfiguration configuration = new MemoryConfiguration();
        configuration.set("type", "MC");
        configuration.set("amount", "8");
        ConfigurationSection metaData = configuration.createSection("meta-data");
        metaData.set("material", "DIAMOND");
        metaData.set("lore", Arrays.asList("&7来自古代", "&7不可交易"));

        NeedMaterialComponentImpl component = new NeedMaterialComponentImpl(configuration);

        assertEquals(MaterialProviderType.MC, component.getType());
        assertEquals("DIAMOND", component.getMetaData().get("material"));
        assertEquals("&7来自古代\n&7不可交易", component.getMetaData().get("lore"));
    }

    @Test
    void 单行列表不带换行符() {
        MemoryConfiguration configuration = new MemoryConfiguration();
        configuration.set("type", "MC");
        configuration.set("amount", "1");
        ConfigurationSection metaData = configuration.createSection("meta-data");
        metaData.set("material", "DIAMOND");
        metaData.set("lore", Collections.singletonList("&7来自古代"));

        NeedMaterialComponentImpl component = new NeedMaterialComponentImpl(configuration);

        assertEquals("&7来自古代", component.getMetaData().get("lore"));
    }

    @Test
    void 普通标量值仍按字符串存入() {
        MemoryConfiguration configuration = new MemoryConfiguration();
        configuration.set("type", "MC");
        configuration.set("amount", "1");
        ConfigurationSection metaData = configuration.createSection("meta-data");
        metaData.set("material", "WOOL");
        metaData.set("data", 14);
        metaData.set("name-match", "strip-color");

        NeedMaterialComponentImpl component = new NeedMaterialComponentImpl(configuration);

        assertEquals("WOOL", component.getMetaData().get("material"));
        assertEquals("14", component.getMetaData().get("data"));
        assertEquals("strip-color", component.getMetaData().get("name-match"));
    }
}
