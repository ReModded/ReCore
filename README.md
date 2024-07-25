# ReCore

A base ReModded plugin, containing wide variety of utilities and abstraction for multiple Minecraft platfroms.

## Supported Platforms

Servers:
- [PaperMC](https://papermc.io/software/paper)
- [Sponge 12+](https://spongepowered.org/)
- ~~[Folia](https://papermc.io/software/folia)~~ UNDER CONSIDIRATION
- ~~[FabricMC](https://fabricmc.net/)~~ UNDER CONSIDIRATION

Proxies:
- [Velocity](https://papermc.io/software/velocity)


## How to use

build.gradle.kts
```
repositories {
  maven("https://repo.remodded.dev/repositories/maven-public/")
}

dependencies {
  implementation("dev.remodded.recore:ReCore-API:1.0.0-SNAPSHOT")
}
```
