package top.nustar.pixel.minecraft.yaml.component.common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;

/**
 * @author NuStar
 * @since 2025/9/3 12:27
 */
public class SafeServiceLoader {
    public static <S> List<S> getServices(Class<S> clazz) {
        return getServices(clazz, false);
    }

    /**
     * 加载服务实现。
     *
     * @param clazz 服务接口
     * @param skipServiceConfigurationError 是否跳过无法链接或实例化的服务实现
     * @param <S> 服务类型
     * @return 可用服务实现
     */
    public static <S> List<S> getServices(Class<S> clazz, boolean skipServiceConfigurationError) {
        ServiceLoader<S> spi = ServiceLoader.load(clazz, SafeServiceLoader.class.getClassLoader());
        List<S> services = new ArrayList<>();
        Iterator<S> iterator = spi.iterator();

        while (true) {
            try {
                if (!iterator.hasNext()) {
                    break;
                }
                services.add(iterator.next());
            } catch (ServiceConfigurationError error) {
                if (!skipServiceConfigurationError) {
                    throw error;
                }
            }
        }

        return services;
    }
}
