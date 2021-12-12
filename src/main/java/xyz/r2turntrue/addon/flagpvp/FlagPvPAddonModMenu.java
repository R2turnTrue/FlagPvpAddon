package xyz.r2turntrue.addon.flagpvp;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.LiteralText;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class FlagPvPAddonModMenu implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return (ConfigScreenFactory<Screen>) parent -> {
            ConfigBuilder builder = ConfigBuilder.create()
                    .setParentScreen(parent)
                    .setTitle(new LiteralText("FlagPvP Addon 세팅"))
                    .setSavingRunnable(() -> {
                        JsonObject jo = new JsonObject();
                        jo.addProperty("autoGgEnabled", FlagPvPAddon.autoGgEnabled);
                        jo.addProperty("lowFire", FlagPvPAddon.lowFire);
                        //jo.addProperty("fpsShow", FlagPvPAddon.fpsShow);
                        jo.addProperty("windowsFpsOptimization", FlagPvPAddon.windowsFpsOptimization);
                        Gson gson = new Gson();
                        try {
                            Files.writeString(new File(MinecraftClient.getInstance().runDirectory, "flagpvpaddon.json").toPath(), gson.toJson(jo), StandardCharsets.UTF_8);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
            ConfigEntryBuilder entryBuilder = builder.entryBuilder();
            ConfigCategory category = builder.getOrCreateCategory(new LiteralText("일반"));
            category.addEntry(entryBuilder.startBooleanToggle(new LiteralText("AutoGG"), FlagPvPAddon.autoGgEnabled)
                    .setDefaultValue(true)
                    .setTooltip(new LiteralText("게임이 끝난 뒤 자동으로 GG를 칩니다."))
                    .setSaveConsumer(newValue -> FlagPvPAddon.autoGgEnabled = newValue)
                    .build());
            category.addEntry(entryBuilder.startBooleanToggle(new LiteralText("창 비활성화시 FPS 줄이기"), FlagPvPAddon.windowsFpsOptimization)
                    .setDefaultValue(true)
                    .setTooltip(new LiteralText("마크 창이 아닌 다른 창이 선택되어있을 때 FPS를 줄여서 최적화합니다."))
                    .setSaveConsumer(newValue -> FlagPvPAddon.windowsFpsOptimization = newValue)
                    .build());
            category.addEntry(entryBuilder.startBooleanToggle(new LiteralText("불 낮추기"), FlagPvPAddon.lowFire)
                    .setDefaultValue(true)
                    .setTooltip(new LiteralText("화면에 보이는 불의 위치를 낮춥니다."))
                    .setSaveConsumer(newValue -> FlagPvPAddon.lowFire = newValue)
                    .build());
            /*
            category.addEntry(entryBuilder.startBooleanToggle(new LiteralText("FPS HUD"), FlagPvPAddon.fpsShow)
                    .setDefaultValue(true)
                    .setTooltip(new LiteralText("화면에 FPS를 표시합니다."))
                    .setSaveConsumer(newValue -> FlagPvPAddon.fpsShow = newValue)
                    .build());
             */
            return builder.build();
        };
    }
}
