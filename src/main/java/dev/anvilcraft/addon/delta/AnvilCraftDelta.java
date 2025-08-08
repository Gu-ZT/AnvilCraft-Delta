package dev.anvilcraft.addon.delta;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

@Mod(AnvilCraftDelta.MOD_ID)
public class AnvilCraftDelta {
    public static final String MOD_ID = "anvilcraft_delta";
    public static final Logger LOGGER = LogUtils.getLogger();

    public AnvilCraftDelta(@NotNull IEventBus modEventBus, @NotNull ModContainer modContainer) {
    }

    public static @NotNull ResourceLocation of(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }
}
