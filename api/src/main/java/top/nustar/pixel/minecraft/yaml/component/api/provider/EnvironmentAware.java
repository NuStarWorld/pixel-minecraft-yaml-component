package top.nustar.pixel.minecraft.yaml.component.api.provider;

/**
 * @author NuStar
 * @since 2026/5/8 02:00
 */
public interface EnvironmentAware {
    boolean isSupport();

    int getPriority();
}
