package dev.anvilcraft.addon.template;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;
import net.minecraft.world.item.Item;

import java.util.HashSet;
import java.util.Set;

@Config(name = AnvilCraftAddonTemplate.MOD_ID)
public class AddonConfig implements ConfigData {
    @Comment("Whether to log the dirt block on common setup")
    @ConfigEntry.Gui.Tooltip
    public boolean logDirtBlock = false;

    @Comment("A magic number")
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(max = 24, min = 2)
    public int magicNumber = 2;

    @Comment("What you want the introduction message to be for the magic number")
    @ConfigEntry.Gui.Tooltip
    public String magicNumberIntroduction = "";

    @Comment("A list of items to log on common setup.")
    @ConfigEntry.Gui.Tooltip
    public Set<Item> items = HashSet.newHashSet(1);
}
