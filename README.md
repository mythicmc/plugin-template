# plugin-template

Quick template to start writing a Paper/Spigot/BungeeCord/Velocity plugin in Kotlin/Java 17

## Quick Start

1. Replace `TemplatePlugin` and `templateplugin` throughout the project, including folder names. Replace `org.mythicmc` if wanted as well.
2. Edit `build.gradle.kts` and resolve the comments starting with `TODO`.
3. This project contains both Java and Kotlin versions of the starter plugins. You can remove the entire Java or Kotlin version by removing `src/main/java` or `src/main/kotlin`. If you want to keep Kotlin for some platforms and Java for others, delete the appropriate folders under `java` and `kotlin`.
4. To remove Spigot/Paper support, delete `plugin.yml` and `spigot` folders. If using the Paper API, rename `spigot` to `paper` and change `plugin.yml` accordingly.
5. To remove BungeeCord support, delete `bungee.yml` and `bungee` folders.
6. To remove Velocity support, delete `velocity-plugin.json` and `velocity` folders.
7. Velocity: If your main class is Java, read the comment about using `@Plugin` at the start.
8. If you're only targeting a single platform (e.g. Paper, Velocity, etc), then move the source code out of the `platform` folder, delete the `platform` folder and edit `plugin.yml`/`bungee.yml`/`velocity-plugin.json`.
9. Edit `plugin.yml`, `bungee.yml` and/or `velocity-plugin.json` and edit relevant properties/add new ones.
10. Last but not least, remove this Quick Start and change the top heading and description!
