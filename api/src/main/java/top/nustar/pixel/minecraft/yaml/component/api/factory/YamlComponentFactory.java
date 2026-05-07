package top.nustar.pixel.minecraft.yaml.component.api.factory;

import top.nustar.pixel.minecraft.yaml.component.api.component.YamlComponent;
import top.nustar.pixel.minecraft.yaml.component.common.SafeServiceLoader;
import top.nustar.pixel.minecraft.yaml.component.common.Validation;

import java.util.function.Function;

/**
 * @author NuStar
 * @since 2026/5/9 17:29
 */
public interface YamlComponentFactory<C> {

    class Holder {
        private static volatile YamlComponentFactory<?> INSTANCE;

        public static YamlComponentFactory<?> getInstance() {
            if (INSTANCE == null) {
                synchronized (Holder.class) {
                    if (INSTANCE == null) {
                        INSTANCE = SafeServiceLoader.getServices(YamlComponentFactory.class).stream().findFirst().orElse(null);
                    }
                }
            }
            Validation.notNull(INSTANCE, "YamlComponentFactory");
            return INSTANCE;
        }
    }

    /**
     * 创建一个 yaml 组件
     * @param configurationPoint 配置点
     * @return yaml 组件
     * @param <T> yaml 组件
     */
    <T extends YamlComponent> T create(C configurationPoint);

    /**
     * 创建一个 yaml 组件
     * @param configurationPoint 配置点
     * @param name 组件名称
     * @return yaml 组件
     * @param <T> yaml 组件
     */
    <T extends YamlComponent> T create(C configurationPoint, String name);

    /**
     * 注册一个组件构建器
     * @param name 组件名称
     * @param builder 组件构建器
     * @param <T> 组件类型
     */
    <T extends YamlComponent> void registerComponent(String name, Function<C, T> builder);
}
