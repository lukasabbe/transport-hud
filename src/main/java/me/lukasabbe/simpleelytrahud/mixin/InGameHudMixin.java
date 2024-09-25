package me.lukasabbe.simpleelytrahud.mixin;

import me.lukasabbe.simpleelytrahud.SimpleElytraHudMod;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin {
    @Inject(method = "render", at=@At("TAIL"))
    public void render(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci){
        if(SimpleElytraHudMod.listener != null){
            SimpleElytraHudMod.listener.hudRender(context,tickCounter);
        }
    }
}
