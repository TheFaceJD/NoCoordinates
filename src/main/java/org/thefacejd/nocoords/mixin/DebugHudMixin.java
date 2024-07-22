package org.thefacejd.nocoords.mixin;

import net.minecraft.client.gui.hud.DebugHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(DebugHud.class)
public class DebugHudMixin {
    @Inject(method = "getLeftText", at = @At("RETURN"), cancellable = true)
    private void removeLines(CallbackInfoReturnable<List<String>> cir) {
        List<String> debugInfo = cir.getReturnValue();
        debugInfo.removeIf(line -> line.contains("XYZ:"));
        debugInfo.removeIf(line -> line.contains("Block:"));
        debugInfo.removeIf(line -> line.contains("Chunk:"));
        debugInfo.removeIf(line -> line.contains("Facing:"));
        cir.setReturnValue(debugInfo);
    }
}
