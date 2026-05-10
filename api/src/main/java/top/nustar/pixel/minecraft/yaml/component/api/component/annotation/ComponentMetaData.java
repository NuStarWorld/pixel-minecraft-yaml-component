package top.nustar.pixel.minecraft.yaml.component.api.component.annotation;

import lombok.Getter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author NuStar
 * @since 2026/5/9 17:42
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ComponentMetaData {
    ComponentType type();

    @Getter
    enum ComponentType {

        ATTRIBUTE("attribute"),
        NEED_MATERIAL("need-material")
        ;

        private final String configKey;

        ComponentType(String configKey) {
            this.configKey = configKey;
        }

        public static ComponentType of(String configKey) {
            for (ComponentType componentType : values()) {
                if (componentType.getConfigKey().equals(configKey)) {
                    return componentType;
                }
            }
            return null;
        }
    }
}
