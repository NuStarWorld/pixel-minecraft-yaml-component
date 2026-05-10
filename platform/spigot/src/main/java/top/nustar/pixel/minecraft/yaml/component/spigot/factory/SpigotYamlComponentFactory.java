package top.nustar.pixel.minecraft.yaml.component.spigot.factory;

import org.bukkit.configuration.ConfigurationSection;
import top.nustar.pixel.minecraft.yaml.component.api.component.YamlComponent;
import top.nustar.pixel.minecraft.yaml.component.api.factory.YamlComponentFactory;
import top.nustar.pixel.minecraft.yaml.component.spigot.component.NeedMaterialComponentImpl;
import top.nustar.pixel.minecraft.yaml.component.spigot.component.PlayerAttributeComponentImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @author NuStar
 * @since 2026/5/9 17:32
 */
@SuppressWarnings("unused")
public class SpigotYamlComponentFactory implements YamlComponentFactory<ConfigurationSection> {

    private final static Map<String, Function<ConfigurationSection, YamlComponent>> builderMap = new HashMap<>();

    public SpigotYamlComponentFactory() {
        registerComponent(NeedMaterialComponentImpl.class, NeedMaterialComponentImpl::new);
        registerComponent(PlayerAttributeComponentImpl.class, PlayerAttributeComponentImpl::new);
    }

    @Override
    public <T extends YamlComponent> T create(ConfigurationSection configurationPoint) {
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends YamlComponent> T create(ConfigurationSection configurationPoint, String name) {
        if (!builderMap.containsKey(name)) {
            throw new NullPointerException("YamlComponentFactory does not contain a component with name " + name);
        }
        return (T)builderMap.get(name).apply(configurationPoint);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends YamlComponent> void registerComponent(Class<T> clazz, Function<ConfigurationSection, T> builder) {
        String name = getConfigKey(clazz);
        if (builderMap.containsKey(name)) {
            throw new IllegalArgumentException("YamlComponentFactory already contains a component with name " + name);
        }
        builderMap.put(name, (Function<ConfigurationSection, YamlComponent>) builder);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends YamlComponent> void registerComponent(String configKey, Function<ConfigurationSection, T> builder) {
        if (builderMap.containsKey(configKey)) {
            throw new IllegalArgumentException("YamlComponentFactory 已经注册过了配置组件 " + configKey);
        }
        builderMap.put(configKey, (Function<ConfigurationSection, YamlComponent>) builder);
    }
}
