package top.nustar.pixel.minecraft.yaml.component.spigot.provider;

import org.bukkit.Bukkit;
import top.nustar.pixel.minecraft.yaml.component.api.ComponentContext;
import top.nustar.pixel.minecraft.yaml.component.api.provider.CurrencyProvider;
import top.nustar.pixel.minecraft.yaml.component.api.provider.MaterialProviderType;
import cn.hamster3.currency.api.CurrencyAPI;

/**
 * @author NuStar
 * @since 2026/5/13 16:24
 */
@SuppressWarnings("unused")
public class HamsterCurrencyProvider implements CurrencyProvider {
    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    public boolean isSupport() {
        return Bukkit.getPluginManager().isPluginEnabled("HamsterCurrency");
    }

    @Override
    public double getAmount(ComponentContext<?> context, String materialId) {
        return CurrencyAPI.getPlayerCurrency(context.getHolderUid(), materialId);
    }

    @Override
    public void take(ComponentContext<?> context, String materialId, int amount) {
        CurrencyAPI.takePlayerCurrency(context.getHolderUid(), materialId, amount);
    }

    @Override
    public MaterialProviderType getType() {
        return MaterialProviderType.HamsterCurrency;
    }
}
