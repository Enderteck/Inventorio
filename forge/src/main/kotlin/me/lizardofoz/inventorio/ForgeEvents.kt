package me.lizardofoz.inventorio

import me.lizardofoz.inventorio.client.control.InventorioKeyHandler
import me.lizardofoz.inventorio.client.ui.HotbarHUDRenderer.renderHotbarAddons
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import net.minecraftforge.client.event.CustomizeGuiOverlayEvent
import net.minecraftforge.event.TickEvent
import net.minecraftforge.eventbus.api.SubscribeEvent

object ForgeEvents
{
    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    fun onClientTick(event: TickEvent.ClientTickEvent)
    {
        InventorioKeyHandler.tick()
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    fun onClientTick(event: CustomizeGuiOverlayEvent)
    {
        renderHotbarAddons(event.guiGraphics)
    }
}