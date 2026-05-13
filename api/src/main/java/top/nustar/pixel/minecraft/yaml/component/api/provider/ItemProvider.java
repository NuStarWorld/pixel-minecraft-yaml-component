package top.nustar.pixel.minecraft.yaml.component.api.provider;

import top.nustar.pixel.minecraft.yaml.component.api.ComponentContext;
import top.nustar.pixel.minecraft.yaml.component.common.SafeServiceLoader;
import top.nustar.pixel.minecraft.yaml.component.common.Validation;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author NuStar
 * @since 2026/5/8 01:41
 */
public interface ItemProvider extends EnvironmentAware, MaterialConsumer {

    class Holder {

        private static volatile Map<MaterialProviderType, ItemProvider> itemProviders;

        public static ItemProvider getItemProvider(MaterialProviderType type) {
            if (itemProviders == null) {
                synchronized (Holder.class) {
                    if (itemProviders == null) {
                        itemProviders = SafeServiceLoader.getServices(ItemProvider.class).stream()
                                .filter(ItemProvider::isSupport)
                                .collect(Collectors.toMap(ItemProvider::getType, itemProvider -> itemProvider));
                    }
                }
            }
            ItemProvider itemProvider = itemProviders.get(type);
            Validation.notNull(itemProvider, "itemProvider " + type.name());
            return itemProvider;
        }

    }

    /**
     * 获取物品
     * @param context 上下文
     * @param itemId 物品id
     * @return 物品
     * @param <T> 物品类型
     */
    <T> T getItem(ComponentContext<?> context, String itemId);

    <T> boolean isItem(T item, String itemId);

}
