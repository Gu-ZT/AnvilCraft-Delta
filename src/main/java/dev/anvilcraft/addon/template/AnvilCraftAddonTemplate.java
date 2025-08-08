package dev.anvilcraft.addon.template;

import com.mojang.logging.LogUtils;
import com.tterrag.registrate.Registrate;
import dev.anvilcraft.addon.template.data.ModDatagen;
import dev.anvilcraft.addon.template.init.AddonBlocks;
import dev.anvilcraft.addon.template.init.AddonItemGroups;
import dev.anvilcraft.addon.template.init.AddonItems;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

@Mod(AnvilCraftAddonTemplate.MOD_ID)
public class AnvilCraftAddonTemplate {
    public static final String MOD_ID = "anvilcraft_addon_template";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static AddonConfig config = AutoConfig.register(AddonConfig.class, JanksonConfigSerializer::new).getConfig();
    public static final Registrate REGISTRATE = Registrate.create(MOD_ID);

    public AnvilCraftAddonTemplate(@NotNull IEventBus modEventBus, @NotNull ModContainer modContainer) {
        AddonItemGroups.register(modEventBus);
        AddonBlocks.register();
        AddonItems.register();
        ModDatagen.init();
    }

    public static @NotNull ResourceLocation of(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }
}
