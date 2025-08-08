package dev.anvilcraft.addon.template.client;

import dev.anvilcraft.addon.template.AddonConfig;
import dev.anvilcraft.addon.template.AnvilCraftAddonTemplate;
import me.shedaniel.autoconfig.AutoConfig;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import org.jetbrains.annotations.NotNull;

@Mod(value = AnvilCraftAddonTemplate.MOD_ID, dist = Dist.CLIENT)
public class AnvilCraftAddonTemplateClient {
    public AnvilCraftAddonTemplateClient(@NotNull IEventBus modBus, @NotNull ModContainer container) {
        container.registerExtensionPoint(
            IConfigScreenFactory.class,
            (c, s) -> AutoConfig.getConfigScreen(AddonConfig.class, s).get()
        );
    }
}
