package org.thefacejd.nocoords.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.text.MutableText;
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
        if (player != null && (player.getWorld().getRegistryKey().equals(World.OVERWORLD) && onHands(player, Items.COMPASS)
                || !player.getWorld().getRegistryKey().equals(World.OVERWORLD) && onHands(player, Items.RECOVERY_COMPASS))) {
            updateActionBar("%s %s %s %s".formatted(
                    getSide(player).getString(),
                    player.getBlockX(), player.getBlockY(), player.getBlockZ()));
        }
    }

    @Unique
    private void updateActionBar(String message) {
        MinecraftClient.getInstance().inGameHud.setOverlayMessage(Text.literal(message), false);
    }

    @Unique
    private boolean onHands(ClientPlayerEntity player, Item item) {
        return player.getMainHandStack().getItem().equals(item) || player.getOffHandStack().getItem().equals(item);
    }

    @Unique
    private MutableText getSide(ClientPlayerEntity player) {
        return Text.translatable("sides." + player.getFacing().toString());
    }
}
