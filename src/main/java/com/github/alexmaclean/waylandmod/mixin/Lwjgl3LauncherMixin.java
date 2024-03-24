package com.github.alexmaclean.waylandmod.mixin;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import finalforeach.cosmicreach.lwjgl3.Lwjgl3Launcher;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Lwjgl3Launcher.class)
public class Lwjgl3LauncherMixin {
    @Inject(method = "getDefaultConfiguration", at = @At("HEAD"))
    private static void injectWaylandSupport(CallbackInfoReturnable<Lwjgl3ApplicationConfiguration> ci) {
        if (GLFW.glfwPlatformSupported(GLFW.GLFW_PLATFORM_WAYLAND)) {
            GLFW.glfwInitHint(GLFW.GLFW_PLATFORM, GLFW.GLFW_PLATFORM_WAYLAND);
            System.out.println("[WaylandMod/INFO]: Enabling GLFW Native Wayland Window Support...");
            System.out.println("[WaylandMod/INFO]: It may take a few seconds longer than normal for the window to appear");
            System.out.println("[WaylandMod/INFO]: The following four GLFW errors are to be expected with a native Wayland window");
        }
    }

    @Inject(method = "getDefaultConfiguration", at = @At("TAIL"))
    private static void disableUnsupportedHints(CallbackInfoReturnable<Lwjgl3ApplicationConfiguration> ci) {
        if (GLFW.glfwGetPlatform() == GLFW.GLFW_PLATFORM_WAYLAND) {
            GLFW.glfwWindowHint(GLFW.GLFW_FOCUS_ON_SHOW, GLFW.GLFW_FALSE);
            GLFW.glfwWindowHint(GLFW.GLFW_FOCUSED, GLFW.GLFW_FALSE);
            GLFW.glfwWindowHint(GLFW.GLFW_ANY_POSITION, GLFW.GLFW_FALSE);
            GLFW.glfwWindowHint(GLFW.GLFW_POSITION_X, GLFW.GLFW_FALSE);
            GLFW.glfwWindowHint(GLFW.GLFW_POSITION_Y, GLFW.GLFW_FALSE);
            GLFW.glfwWindowHint(GLFW.GLFW_ICONIFIED, GLFW.GLFW_FALSE);
            GLFW.glfwWindowHint(GLFW.GLFW_AUTO_ICONIFY, GLFW.GLFW_FALSE);
        }
    }
}