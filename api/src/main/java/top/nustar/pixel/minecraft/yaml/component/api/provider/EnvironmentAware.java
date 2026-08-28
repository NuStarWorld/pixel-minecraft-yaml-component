package top.nustar.pixel.minecraft.yaml.component.api.provider;

/**
 * @author NuStar
 * @since 2026/5/8 02:00
 */
public interface EnvironmentAware {
    boolean isSupport();

    /**
     * 获取 Provider 优先级。
     * <p>
     * 当多个已启用 Provider 提供同一种能力时，优先选择数值较大的 Provider；优先级相同时保留先加载的 Provider。
     *
     * @return Provider 优先级
     */
    int getPriority();
}
