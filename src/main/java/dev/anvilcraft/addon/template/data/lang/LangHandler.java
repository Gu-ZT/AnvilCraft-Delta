package dev.anvilcraft.addon.template.data.lang;

import com.tterrag.registrate.providers.RegistrateLangProvider;

public class LangHandler {

    /**
     * 语言文件初始化
     *
     * @param provider 提供器
     */
    public static void init(RegistrateLangProvider provider) {
        ConfigScreenLang.init(provider);
    }
}
