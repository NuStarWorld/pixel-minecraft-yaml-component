package top.nustar.pixel.minecraft.yaml.component.spigot.factory;

import org.bukkit.configuration.MemoryConfiguration;
import org.junit.jupiter.api.Test;
import top.nustar.pixel.minecraft.yaml.component.api.component.NeedMaterialComponent;
import top.nustar.pixel.minecraft.yaml.component.api.factory.YamlComponentFactory;
import top.nustar.pixel.minecraft.yaml.component.api.provider.MaterialProviderType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SpigotYamlComponentFactoryTest {

    @Test
    void loadsFactoryAndCreatesNeedMaterialWithoutAttributePlus() {
        assertThrows(ClassNotFoundException.class,
                () -> Class.forName("org.serverct.ersha.api.AttributeAPI"));

        YamlComponentFactory<?> loadedFactory = YamlComponentFactory.Holder.getInstance();
        SpigotYamlComponentFactory factory = assertInstanceOf(
                SpigotYamlComponentFactory.class, loadedFactory);

        MemoryConfiguration configuration = new MemoryConfiguration();
        configuration.set("type", "NI");
        configuration.set("amount", "1");
        configuration.createSection("meta-data").set("itemId", "example-item");

        NeedMaterialComponent component = factory.create(configuration, "need-material");

        assertEquals(MaterialProviderType.NI, component.getType());
        assertEquals("example-item", component.getMetaData().get("itemId"));
    }
}
