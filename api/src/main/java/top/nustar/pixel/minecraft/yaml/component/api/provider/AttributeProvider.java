package top.nustar.pixel.minecraft.yaml.component.api.provider;

import top.nustar.pixel.minecraft.yaml.component.common.SafeServiceLoader;
import top.nustar.pixel.minecraft.yaml.component.common.Validation;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

/**
 * @author NuStar
 * @since 2026/5/10 02:42
 */
public interface AttributeProvider extends EnvironmentAware {

    class Holder {
        private static volatile AttributeProvider INSTANCE;

        public static AttributeProvider getInstance() {
            if (INSTANCE == null) {
                synchronized (Holder.class) {
                    if (INSTANCE == null) {
                        INSTANCE = SafeServiceLoader.getServices(AttributeProvider.class).stream()
                                .filter(AttributeProvider::isSupport)
                                .max(Comparator.comparingInt(EnvironmentAware::getPriority))
                                .orElse(null);
                    }
                }
            }
            Validation.notNull(INSTANCE, "AttributeProvider");
            return INSTANCE;
        }
    }

    /**
     * 应用属性
     * @param holder 属性持有者
     * @param attributes 属性列表
     */
    void applyAttributes(UUID holder, Object attributeName, List<String> attributes);

    /**
     * 移除属性
     * @param holder 属性持有者
     * @param attributeName 属性名称
     */
    void removeAttributes(UUID holder, Object attributeName);
}
