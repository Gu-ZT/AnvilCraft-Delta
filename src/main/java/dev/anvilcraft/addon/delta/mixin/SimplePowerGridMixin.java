package dev.anvilcraft.addon.delta.mixin;

import dev.anvilcraft.addon.delta.util.ISimplePowerGridExtension;
import dev.dubhe.anvilcraft.api.power.SimplePowerGrid;
import dev.dubhe.anvilcraft.client.renderer.Line;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.Set;

@Mixin(SimplePowerGrid.class)
public class SimplePowerGridMixin implements ISimplePowerGridExtension {
    @Unique
    private Set<Line> delta$powerTransmitterLines = Set.of();

    @Override
    public Set<Line> delta$getPowerTransmitterLines() {
        return this.delta$powerTransmitterLines;
    }

    @Override
    public void delta$setPowerTransmitterLines(Set<Line> lines) {
        this.delta$powerTransmitterLines = lines;
    }
}
