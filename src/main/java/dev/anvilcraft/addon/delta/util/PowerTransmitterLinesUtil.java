package dev.anvilcraft.addon.delta.util;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.dubhe.anvilcraft.api.power.SimplePowerGrid;
import dev.dubhe.anvilcraft.client.init.ModRenderTargets;
import dev.dubhe.anvilcraft.client.init.ModRenderTypes;
import dev.dubhe.anvilcraft.client.renderer.Line;
import dev.dubhe.anvilcraft.client.support.PowerGridSupport;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class PowerTransmitterLinesUtil {
    public static void render(
        PoseStack poseStack,
        MultiBufferSource.BufferSource bufferSource,
        Vec3 camera,
        RenderType type
    ) {
        ClientLevel level1 = Minecraft.getInstance().level;
        if (level1 == null) return;
        VertexConsumer consumer = bufferSource.getBuffer(type);
        String level = level1.dimension().location().toString();
        if (type == ModRenderTypes.LINE_BLOOM && ModRenderTargets.getBloomTarget() != null) {
            ModRenderTargets.getBloomTarget().setClearColor(0, 0, 0, 0);
            ModRenderTargets.getBloomTarget().clear(Minecraft.ON_OSX);
            ModRenderTargets.getBloomTarget().copyDepthFrom(Minecraft.getInstance().getMainRenderTarget());
        }
        for (SimplePowerGrid grid : PowerGridSupport.getGridMap().values()) {
            if (!grid.shouldRender(camera)) continue;
            if (!grid.getLevel().equals(level)) continue;
            PowerTransmitterLinesUtil.getPowerTransmitterLines(grid)
                .forEach(it -> it.render(poseStack, consumer, camera, 0x9966ccff));
        }
        if (type == ModRenderTypes.LINE_BLOOM) bufferSource.endBatch();
    }

    public static @NotNull Collection<Line> getPowerTransmitterLines(SimplePowerGrid grid) {
        ISimplePowerGridExtension extension = (ISimplePowerGridExtension) grid;
        extension.delta$createPowerTransmitterLines();
        extension.delta$getPowerTransmitterLines();
        return extension.delta$getPowerTransmitterLines();
    }

    public static boolean isOverlap(@NotNull Vec3 a, int rangeA, @NotNull Vec3 b, int rangeB) {
        AABB aAABB = new AABB(
            a.x - rangeA - 0.5, a.y - rangeA - 0.5, a.z - rangeA - 0.5,
            a.x + rangeA + 0.5, a.y + rangeA + 0.5, a.z + rangeA + 0.5
        );
        AABB bAABB = new AABB(
            b.x - rangeB - 0.5, b.y - rangeB - 0.5, b.z - rangeB - 0.5,
            b.x + rangeB + 0.5, b.y + rangeB + 0.5, b.z + rangeB + 0.5
        );
        return aAABB.intersects(bAABB);
    }
}
