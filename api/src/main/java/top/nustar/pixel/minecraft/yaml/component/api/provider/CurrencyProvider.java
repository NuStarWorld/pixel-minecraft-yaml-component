package top.nustar.pixel.minecraft.yaml.component.api.provider;

import top.nustar.pixel.minecraft.yaml.component.common.SafeServiceLoader;
import top.nustar.pixel.minecraft.yaml.component.common.Validation;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author NuStar
 * @since 2026/5/13 15:42
 */
public interface CurrencyProvider extends EnvironmentAware, MaterialConsumer {

    class Holder {
        private static volatile Map<MaterialProviderType, CurrencyProvider> currencyProviders;

        public static CurrencyProvider getCurrencyProvider(MaterialProviderType type) {
            if (currencyProviders == null) {
                synchronized (Holder.class) {
                    if (currencyProviders == null) {
                        currencyProviders = SafeServiceLoader.getServices(CurrencyProvider.class).stream()
                                .filter(EnvironmentAware::isSupport)
                                .collect(Collectors.toMap(MaterialConsumer::getType, currencyProvider -> currencyProvider));
                    }
                }
            }
            CurrencyProvider currencyProvider = currencyProviders.get(type);
            Validation.notNull(currencyProvider, "currencyProvider " + type.name());
            return currencyProvider;
        }
    }

}
