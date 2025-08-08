package dev.anvilcraft.addon.delta.util;

import dev.dubhe.anvilcraft.client.renderer.Line;

import java.util.Set;

public interface ISimplePowerGridExtension {
    default Set<Line> delta$getPowerTransmitterLines() {
        throw new AssertionError();
    }

    default void delta$createPowerTransmitterLines() {
        throw new AssertionError();
    }
}
