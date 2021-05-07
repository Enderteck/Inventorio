package me.lizardofoz.inventorio.mixin.client;

import me.lizardofoz.inventorio.client.InventorioKeyHandler;
import me.lizardofoz.inventorio.player.PlayerInventoryAddon;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.Packet;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(MinecraftClient.class)
@Environment(EnvType.CLIENT)
public class MinecraftClientMixin
{
    /**
     * This redirect replaced vanilla QuickBar slot selection with ours (in case if Simplified QuickBar is enabled)
     */
    @Redirect(method = "handleInputEvents", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/PlayerInventory;selectedSlot:I"))
    private void handleSlotSelection(PlayerInventory inventory, int selectedSlot)
    {
        InventorioKeyHandler.INSTANCE.handleSlotSelection(inventory, selectedSlot);
    }

    /**
     * This redirect enables the ability to bind the Offhand/Utility to a separate button.
     *
     * If option is enabled, a regular RightClick won't attempt to use an Offhand item,
     * but a dedicated key will attempt to use ONLY an Offhand item.
     */
    @Redirect(method = "doItemUse", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Hand;values()[Lnet/minecraft/util/Hand;"))
    private Hand[] doItemUse()
    {
        if (!InventorioKeyHandler.INSTANCE.hasDedicatedUseUtilityButton())
            return Hand.values();
        else if (PlayerInventoryAddon.Client.triesToUseUtility)
            return new Hand[]{Hand.OFF_HAND};
        return new Hand[]{Hand.MAIN_HAND};
    }

    /**
     * This is one of several redirects to remove the offhand swap hotkey
     */
    @Redirect(method = "handleInputEvents", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayNetworkHandler;sendPacket(Lnet/minecraft/network/Packet;)V"))
    private void removeOffhandSwap(ClientPlayNetworkHandler clientPlayNetworkHandler, Packet<?> packet)
    {
    }
}