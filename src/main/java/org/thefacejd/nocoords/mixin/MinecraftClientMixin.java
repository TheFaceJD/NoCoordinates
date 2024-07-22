package org.thefacejd.nocoords.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {

    @Inject(method = "tick", at = @At("TAIL"))
    private void showCoordinates(CallbackInfo ci) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player != null && (player.getWorld().getRegistryKey().equals(World.OVERWORLD) && player.getMainHandStack().getItem().equals(Items.COMPASS)
                || !player.getWorld().getRegistryKey().equals(World.OVERWORLD) && player.getMainHandStack().getItem().equals(Items.RECOVERY_COMPASS))) {
            updateActionBar("%s %s %s %s".formatted(
                    player.getFacing().toString().substring(0, 1).toUpperCase() + player.getFacing().toString().substring(1),
                    player.getBlockX(), player.getBlockY(), player.getBlockZ()));
        }
    }

    @Unique
    private void updateActionBar(String message) {
        MinecraftClient.getInstance().inGameHud.setOverlayMessage(Text.literal(message), false);
    }
}
