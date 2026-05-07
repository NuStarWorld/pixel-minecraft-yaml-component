package top.nustar.pixel.minecraft.yaml.component.api.component;

import top.nustar.pixel.minecraft.yaml.component.api.component.annotation.ComponentMetaData;

/**
 * @author NuStar
 * @since 2026/5/7 23:21
 */
public interface YamlComponent {

    default String getName() {
        return this.getClass().getAnnotation(ComponentMetaData.class).name();
    }


}
