package me.lizardofoz.inventorio.mixin;

import me.lizardofoz.inventorio.player.PlayerScreenHandlerAddon;
import me.lizardofoz.inventorio.util.ScreenHandlerDuck;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.PlayerScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * This mixin fixes shift-clicks and other inventory shortcuts for Player's inventory
 */
@Mixin(value = PlayerScreenHandler.class, priority = 9999)
public class PlayerScreenHandlerMixin implements ScreenHandlerDuck
{
    @Unique public PlayerScreenHandlerAddon addon;

    @Inject(method = "transferSlot", at = @At("HEAD"), cancellable = true)
    public void transferSlot(PlayerEntity player, int index, CallbackInfoReturnable<ItemStack> cir)
    {
        ItemStack result = addon.transferSlot(index);
        if (result != null)
            cir.setReturnValue(result);
    }

    @Override
    public PlayerScreenHandlerAddon getAddon()
    {
        return addon;
    }

    @Override
    public void setAddon(PlayerScreenHandlerAddon addon)
    {
        this.addon = addon;
    }
}