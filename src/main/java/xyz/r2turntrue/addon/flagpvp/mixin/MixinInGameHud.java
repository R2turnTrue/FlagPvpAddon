package xyz.r2turntrue.addon.flagpvp.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.r2turntrue.addon.flagpvp.FlagPvPAddon;

@Mixin(InGameHud.class)
public class MixinInGameHud {

    @Shadow @Final private MinecraftClient client;

    int lastMaxFps = -1;

    @Inject(method = "render", at = @At("HEAD"))
    public void render(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
        if(FlagPvPAddon.windowsFpsOptimization) {
            if (lastMaxFps == -1) lastMaxFps = client.options.maxFps;
            if (!client.isWindowFocused() && client.getWindow().getFramerateLimit() != 15) {
                //System.out.println("Windows is not focused, so maxFPS changed to 30!");
                lastMaxFps = client.options.maxFps;
                //System.out.println("FPS: " + lastMaxFps);
                client.getWindow().setFramerateLimit(15);
            } else {
                client.getWindow().setFramerateLimit(lastMaxFps);
            }
        }

        //if(FlagPvPAddon.fpsShow) client.textRenderer.drawWithShadow(matrices, client.fpsDebugString, 0, 0, 0xFFFFFF);
    }

    @Inject(method = "setTitle", at = @At("RETURN"))
    public void setTitle(Text title, CallbackInfo ci) {
        if(FlagPvPAddon.autoGgEnabled) {
            Scoreboard sb = this.client.world.getScoreboard();
            if (sb != null) {
                ScoreboardObjective objective = sb.getObjectiveForSlot(1);
                if (objective != null) {
                    Text name = objective.getDisplayName();
                    if (name.getString().equals("FlagPvP") && name.getStyle().getColor().getName() != null && name.getStyle().getColor().getName().equals("#C70000")) {
                        if (title.getString().equals("승리!") || title.getString().equals("패배!")) {
                            client.player.sendChatMessage("gg");
                        }
                    }
                }
            }
        }
    }

}
