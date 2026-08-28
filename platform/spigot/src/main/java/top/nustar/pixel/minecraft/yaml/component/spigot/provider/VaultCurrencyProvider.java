package top.nustar.pixel.minecraft.yaml.component.spigot.provider;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import top.nustar.pixel.minecraft.yaml.component.api.ComponentContext;
import top.nustar.pixel.minecraft.yaml.component.api.provider.CurrencyProvider;
import top.nustar.pixel.minecraft.yaml.component.api.provider.MaterialProviderType;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * @author NuStar
 * @since 2026/8/29 00:45
 */
@SuppressWarnings("unused")
public class VaultCurrencyProvider implements CurrencyProvider {

    private Economy economy;

    @Override
    public boolean isSupport() {
        if (!Bukkit.getPluginManager().isPluginEnabled("Vault")) {
            economy = null;
            return false;
        }

        RegisteredServiceProvider<Economy> registration =
                Bukkit.getServicesManager().getRegistration(Economy.class);
        if (registration == null) {
            economy = null;
            return false;
        }

        economy = registration.getProvider();
        return economy != null;
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public double getAmount(ComponentContext<?> context, String materialId) {
        return requireEconomy().getBalance(Bukkit.getOfflinePlayer(context.getHolderUid()));
    }

    @Override
    public void take(ComponentContext<?> context, String materialId, int amount) {
        EconomyResponse economyResponse = requireEconomy().withdrawPlayer(
                Bukkit.getOfflinePlayer(context.getHolderUid()), amount);
        if (economyResponse == null) {
            throw new IllegalStateException("Vault 扣除货币未返回结果");
        }
        if (economyResponse.type != EconomyResponse.ResponseType.SUCCESS) {
            String errorMessage = economyResponse.errorMessage;
            throw new IllegalStateException("扣除 Vault 货币失败"
                    + (errorMessage == null || errorMessage.isEmpty() ? "" : ": " + errorMessage));
        }
    }

    private Economy requireEconomy() {
        if (economy == null) {
            throw new IllegalStateException("Vault Economy 服务不可用");
        }
        return economy;
    }

    @Override
    public MaterialProviderType getType() {
        return MaterialProviderType.Vault;
    }
}
