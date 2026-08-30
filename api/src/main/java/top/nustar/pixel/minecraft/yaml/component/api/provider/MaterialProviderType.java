package top.nustar.pixel.minecraft.yaml.component.api.provider;

import org.jetbrains.annotations.Nullable;
import top.nustar.pixel.minecraft.yaml.component.api.ComponentContext;
import top.nustar.pixel.minecraft.yaml.component.api.component.result.MaterialCheckResult;
import top.nustar.pixel.minecraft.yaml.component.common.Lazy;
import top.nustar.pixel.minecraft.yaml.component.common.Validation;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author NuStar
 * @since 2026/5/8 02:22
 */
public enum MaterialProviderType {
    NI(s -> s.equals("NI") || s.equals("NEIGEITEMS"),
            ItemProvider.Holder::getItemProvider,
            map -> {
                String itemId = map.get("itemId");
                Validation.notEmpty(itemId, "itemId");
                return itemId;
            }),

    MM(s -> s.equals("MM") || s.equals("MYTHICMOBS"),
            ItemProvider.Holder::getItemProvider,
            map -> {
                String itemId = map.get("itemId");
                Validation.notEmpty(itemId, "itemId");
                return itemId;
            }),

    SXITEM(s -> s.equals("SX") || s.equals("SXITEM") || s.equals("SX-ITEM"),
            ItemProvider.Holder::getItemProvider,
            map -> {
                String itemId = map.get("itemId");
                Validation.notEmpty(itemId, "itemId");
                return itemId;
            }),


    MC(s -> s.equals("MC") || s.equals("MINECRAFT") || s.equals("VANILLA"),
            ItemProvider.Holder::getItemProvider,
            map -> {
                String material = map.get("material");
                Validation.notEmpty(material, "material");
                return material;
            }),


    Vault(s -> s.equals("VAULT"),
            CurrencyProvider.Holder::getCurrencyProvider,
            map -> ""),

    HamsterCurrency(s -> s.equals("HAMSTERCURRENCY"),
            CurrencyProvider.Holder::getCurrencyProvider
            ,
            map -> {
                String currencyId = map.get("currencyId");
                Validation.notEmpty(currencyId, "currencyId");
                return currencyId;
            }),
    ;

    private final Predicate<String> predicate;
    private final Lazy<MaterialConsumer> materialConsumerLazy;
    private final Function<Map<String, String>, String> materialIdFunction;

    MaterialProviderType(Predicate<String> predicate, Function<MaterialProviderType, MaterialConsumer> consumerLoader, Function<Map<String, String>, String> materialIdFunction) {
        this.predicate = s -> {
            String upperCase = s.toUpperCase();

            return predicate.test(upperCase);
        };
        this.materialConsumerLazy = Lazy.of(() -> consumerLoader.apply(this));
        this.materialIdFunction = materialIdFunction;
    }

    @Nullable
    public static MaterialProviderType of(String name) {
        for (MaterialProviderType value : values()) {
            if (value.predicate.test(name)) {
                return value;
            }
        }
        return null;
    }

    public MaterialCheckResult check(ComponentContext<?> context, Map<String, String> metaData, int amount) {
        String materialId = materialIdFunction.apply(metaData);
        return materialConsumerLazy.get().check(context, materialId, amount, metaData);
    }

    public void take(ComponentContext<?> context, Map<String, String> metaData, int amount) {
        materialConsumerLazy.get().take(context, materialIdFunction.apply(metaData), amount, metaData);
    }
}
