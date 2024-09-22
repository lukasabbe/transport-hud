package me.lukasabbe.simpleelytrahud.mixin;

import me.lukasabbe.simpleelytrahud.Simpleelytrahud;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public class InGameHud {
    @Inject(method = "render", at=@At("TAIL"))
    public void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci){
        if(Simpleelytrahud.listener != null){
            Simpleelytrahud.listener.hudRender(guiGraphics,deltaTracker);
        }
    }
}
