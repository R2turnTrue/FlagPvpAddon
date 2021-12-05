package xyz.r2turntrue.addon.flagpvp;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class FlagPvPAddon implements ModInitializer {

    public static boolean autoGgEnabled = true;
    public static boolean windowsFpsOptimization = true;
    public static boolean lowFire = true;
    public static boolean fpsShow = true;

    @Override
    public void onInitialize() {
        File config = new File(MinecraftClient.getInstance().runDirectory, "flagpvpaddon.json");
        if(config.exists()) {
            try {
                String read = Files.readString(config.toPath(), StandardCharsets.UTF_8);
                JsonObject jo = new JsonParser().parse(read).getAsJsonObject();
                autoGgEnabled = jo.get("autoGgEnabled").getAsBoolean();
                windowsFpsOptimization = jo.get("windowsFpsOptimization").getAsBoolean();
                lowFire = jo.get("lowFire").getAsBoolean();
                fpsShow = jo.get("fpsShow").getAsBoolean();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
