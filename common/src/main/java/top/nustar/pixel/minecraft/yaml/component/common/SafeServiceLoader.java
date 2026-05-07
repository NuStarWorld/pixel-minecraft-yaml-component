package top.nustar.pixel.minecraft.yaml.component.common;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

/**
 * @author NuStar
 * @since 2025/9/3 12:27
 */
public class SafeServiceLoader {
    public static <S> List<S> getServices(Class<S> clazz) {
        ServiceLoader<S> spi = ServiceLoader.load(clazz, SafeServiceLoader.class.getClassLoader());
        List<S> services = new ArrayList<>();

        for (S service : spi) {
            services.add(service);
        }

        return services;
    }
}
