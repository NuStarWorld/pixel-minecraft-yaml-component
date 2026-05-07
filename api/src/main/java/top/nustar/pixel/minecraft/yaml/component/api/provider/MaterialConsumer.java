package top.nustar.pixel.minecraft.yaml.component.api.provider;

import top.nustar.pixel.minecraft.yaml.component.api.ComponentContext;

/**
 * @author NuStar
 * @since 2026/5/8 02:41
 */
public interface MaterialConsumer {
    boolean check(ComponentContext<?> context, String materialId, int amount);

    void take(ComponentContext<?> context, String materialId, int amount);
}
