# Cosmic Reach Wayland
Wayland Support For Cosmic Reach. Now as a Fabric Mod!
</br></br>See [https://github.com/Alex-MacLean/Cosmic-Reach-Wayland-Outdated](https://github.com/Alex-MacLean/Cosmic-Reach-Wayland-Outdated) for the older outdated version of the mod based off glfw-wayland for the "vanilla" game
</br></br>I am a Linux user who has an Nvidia graphics card and Xwayland tends to have many rendering bugs when running Cosmic Reach and many other games, so I added wayland support to Cosmic Reach.
## How to Install
Set up Fabric or Quilt mod loader for Cosmic Reach and drag the jar file to your mods folder then run your modded install of Cosmic Reach (Just like Minecraft)
## Known Issues
No Window Icon,</br>Wayland makes it difficult to add window icons to your program as there is no one standard way just yet and I wanted to get the important stuff out sooner rather than later
</br></br>GLFW Errors are logged,</br>The API used for initializing glfw, libdgx, tends to act funky with Wayland, I should be able to fix this with some more mixins
