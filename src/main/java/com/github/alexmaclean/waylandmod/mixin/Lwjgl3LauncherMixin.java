package com.github.alexmaclean.waylandmod.mixin;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Window;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3WindowListener;
import finalforeach.cosmicreach.BlockGame;
import finalforeach.cosmicreach.gamestates.GameState;
import finalforeach.cosmicreach.gamestates.PauseMenu;
import finalforeach.cosmicreach.lwjgl3.Lwjgl3Launcher;
import finalforeach.cosmicreach.settings.GraphicsSettings;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Lwjgl3Launcher.class)
public class Lwjgl3LauncherMixin {

    @Inject(method = "createApplication", at = @At("HEAD"), cancellable = true)
    private static void injectWaylandSupport(CallbackInfoReturnable<Lwjgl3Application> cir) { // Hijacks createApplication to return an instance of Lwjgl3Application made using getWaylandConfiguration instead of getDefaultConfiguration if Wayland is detected
        if (GLFW.glfwPlatformSupported(GLFW.GLFW_PLATFORM_WAYLAND)) {
            Lwjgl3Application w = new Lwjgl3Application(new BlockGame(), getWaylandConfiguration());
            cir.setReturnValue(w);
        }
    }

    @Unique
    private static Lwjgl3ApplicationConfiguration getWaylandConfiguration() { // Basically a rewrite of getDefaultConfiguration with Wayland enabled and the features Wayland doesn't support taken out
        // IDK What fixed the mod for 0.1.13, but I refuse to change anything now that it works, Sorry not sorry for the janky code
        Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
        GLFW.glfwInitHint(GLFW.GLFW_PLATFORM, GLFW.GLFW_PLATFORM_WAYLAND);
        GLFW.glfwWindowHint(GLFW.GLFW_FOCUS_ON_SHOW, GLFW.GLFW_TRUE); // Somehow this may have fixed the mod for 0.1.13 even though this is not a protocol supported on Wayland
        GLFW.glfwWindowHint(GLFW.GLFW_AUTO_ICONIFY, GLFW.GLFW_FALSE);
        configuration.setTitle("Cosmic Reach");
        configuration.useVsync(GraphicsSettings.vSyncEnabled.getValue());
        configuration.setForegroundFPS(0);
        configuration.setIdleFPS(0);
        configuration.setAutoIconify(false);
        configuration.setWindowedMode(1024, 576);
        configuration.setWindowListener(new Lwjgl3WindowListener() {
            public void focusLost() {
                BlockGame.isFocused = false;
                if (GameState.currentGameState == GameState.IN_GAME) {
                    GameState.switchToGameState(new PauseMenu(Gdx.input.isCursorCatched()));
                    Gdx.input.setCursorCatched(false);
                }

            }

            public void focusGained() {
                BlockGame.isFocused = true;
            }

            public void created(Lwjgl3Window window) {
            }

            public void iconified(boolean isIconified) {
            }

            public void maximized(boolean isMaximized) {
            }

            public boolean closeRequested() {
                return true;
            }

            public void filesDropped(String[] files) {
            }

            public void refreshRequested() {
            }
        });
        System.out.println("[WaylandMod/INFO]: Enabling GLFW Native Wayland Window Support...");
        System.out.println("[WaylandMod/INFO]: It may take up to 30 seconds longer than normal for the window to appear. This is normal as libgdx doesn't officially support Wayland");
        System.out.println("[WaylandMod/INFO]: The following three GLFW errors are to be expected with a native Wayland window");
        return configuration;
    }
}