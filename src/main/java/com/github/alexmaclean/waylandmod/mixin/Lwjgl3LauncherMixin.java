package com.github.alexmaclean.waylandmod.mixin;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import finalforeach.cosmicreach.lwjgl3.Lwjgl3Launcher;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Lwjgl3Launcher.class)
public class Lwjgl3LauncherMixin {
    @Inject(method = "getDefaultConfiguration", at = @At("HEAD"))
    private static void injectWaylandSupport(CallbackInfoReturnable<Lwjgl3Application> cir) {
        if (GLFW.glfwPlatformSupported(GLFW.GLFW_PLATFORM_WAYLAND)) {
            System.out.println("[WaylandMod/INFO]: Enabling GLFW Native Wayland Window Support...");
            GLFW.glfwInitHint(GLFW.GLFW_PLATFORM, GLFW.GLFW_PLATFORM_WAYLAND);
            GLFW.glfwWindowHint(GLFW.GLFW_FOCUS_ON_SHOW, GLFW.GLFW_TRUE);
            GLFW.glfwWindowHint(GLFW.GLFW_AUTO_ICONIFY, GLFW.GLFW_FALSE);
            System.out.println("[WaylandMod/INFO]: It may take up to 30 seconds longer than normal for the window to appear. This is normal as libgdx doesn't officially support Wayland");
            System.out.println("[WaylandMod/INFO]: The following 4 GLFW errors are to be expected with a native Wayland window");
        }
    }
}