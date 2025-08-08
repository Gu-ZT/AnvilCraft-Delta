package dev.anvilcraft.addon.template.data.lang;

import com.google.gson.annotations.SerializedName;
import com.tterrag.registrate.providers.RegistrateLangProvider;
import dev.anvilcraft.addon.template.AnvilCraftAddonTemplate;
import dev.anvilcraft.addon.template.AddonConfig;
import dev.dubhe.anvilcraft.util.FormattingUtil;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;

public class ConfigScreenLang {
    private static final String OPTION_STRING = "text.autoconfig.%s.option.%s";
    private static final String OPTION_TOOLTIP_STRING = "text.autoconfig.%s.option.%s.@Tooltip";

    /**
     * 初始化配置语言
     *
     * @param provider 提供器
     */
    public static void init(@NotNull RegistrateLangProvider provider) {
        provider.add("text.autoconfig.%s.title".formatted(AnvilCraftAddonTemplate.MOD_ID), "Mod Config");
        readConfigClass(AddonConfig.class, provider, null);
    }

    @SuppressWarnings("SameParameterValue")
    private static void readConfigClass(
        @NotNull Class<?> configClass, RegistrateLangProvider provider, @Nullable String parent) {
        for (Field field : configClass.getDeclaredFields()) {
            String fieldName = field.getName();
            String name;
            if (field.isAnnotationPresent(ConfigEntry.Gui.CollapsibleObject.class)
                || field.isAnnotationPresent(ConfigEntry.Gui.TransitiveObject.class)) {
                readConfigClass(field.getType(), provider, fieldName);
            }
            if (field.isAnnotationPresent(SerializedName.class)) {
                name = field.getAnnotation(SerializedName.class).value();
            } else {
                name = FormattingUtil.toEnglishName(FormattingUtil.toLowerCaseUnder(fieldName));
            }
            if (parent != null) {
                fieldName = parent + "." + fieldName;
            }
            provider.add(OPTION_STRING.formatted(AnvilCraftAddonTemplate.MOD_ID, fieldName), name);
            if (field.isAnnotationPresent(Comment.class)) {
                Comment comment = field.getAnnotation(Comment.class);
                provider.add(OPTION_TOOLTIP_STRING.formatted(AnvilCraftAddonTemplate.MOD_ID, fieldName), comment.value());
            }
        }
    }
}
