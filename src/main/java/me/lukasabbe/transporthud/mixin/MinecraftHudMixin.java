package me.lukasabbe.transporthud.mixin;

import me.lukasabbe.transporthud.TransportHud;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class MinecraftHudMixin {
    @Inject(
        method = "render", at= @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;enableDepthTest()V")
    )
    public void render(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci){

        if(TransportHud.hud.data.isFlying){
            TransportHud.hud.elytraHudRenderer(context,tickCounter);
        }

    }
}
