package dev.anvilcraft.addon.delta.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.anvilcraft.addon.delta.util.PowerTransmitterLinesUtil;
import dev.dubhe.anvilcraft.client.init.ModRenderTypes;
import dev.dubhe.anvilcraft.client.support.PowerGridSupport;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PowerGridSupport.class)
public class PowerGridSupportMixin {
    @Inject(
        method = "renderEnhancedTransmitterLine",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;getInstance()Lnet/minecraft/client/Minecraft;", ordinal = 1),
        cancellable = true
    )
    private static void renderEnhancedTransmitterLine(
        PoseStack poseStack,
        MultiBufferSource.BufferSource bufferSource,
        Vec3 camera,
        @NotNull CallbackInfo ci
    ) {
        PowerTransmitterLinesUtil.render(poseStack, bufferSource, camera, ModRenderTypes.LINE_BLOOM);
        ci.cancel();
    }

    @Inject(
        method = "renderTransmitterLine",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;getInstance()Lnet/minecraft/client/Minecraft;", ordinal = 1),
        cancellable = true
    )
    private static void renderTransmitterLine(
        PoseStack poseStack,
        MultiBufferSource.BufferSource bufferSource,
        Vec3 camera,
        @NotNull CallbackInfo ci
    ) {
        PowerTransmitterLinesUtil.render(poseStack, bufferSource, camera, RenderType.LINES);
        ci.cancel();
    }
}
