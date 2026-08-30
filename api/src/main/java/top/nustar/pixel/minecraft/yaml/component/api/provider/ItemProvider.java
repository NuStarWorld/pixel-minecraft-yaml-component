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
                                .collect(Collectors.toMap(
                                        ItemProvider::getType,
                                        itemProvider -> itemProvider,
                                        (first, second) -> first.getPriority() >= second.getPriority() ? first : second
                                ));
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

    /**
     * 获取物品，并使用材料元数据补充物品细节。
     * <p>
     * 原版物品没有物品 ID，显示名、lore 等信息只存在于 meta-data 中，因此需要这个重载。
     * 默认忽略 metaData，交由需要它的 Provider 覆写。
     *
     * @param context 上下文
     * @param itemId 物品id
     * @param metaData 材料元数据
     * @return 物品
     * @param <T> 物品类型
     */
    default <T> T getItem(ComponentContext<?> context, String itemId, Map<String, String> metaData) {
        return getItem(context, itemId);
    }

    <T> boolean isItem(T item, String itemId);

    /**
     * 判断物品是否匹配，并使用材料元数据参与判定。
     * <p>
     * 默认忽略 metaData，交由需要它的 Provider 覆写。
     *
     * @param item 待判定物品
     * @param itemId 物品id
     * @param metaData 材料元数据
     * @return 是否匹配
     * @param <T> 物品类型
     */
    default <T> boolean isItem(T item, String itemId, Map<String, String> metaData) {
        return isItem(item, itemId);
    }

}
