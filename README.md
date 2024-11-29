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

### [ReGradle](https://github.com/ReModded/ReGradle)

1. [Add ReGradle to your project](https://github.com/ReModded/ReGradle#how-to-use)
2. Add ReCore as a platform dependency:
```kts
regradle {
    addPlatformDependency("dev.remodded", "ReCore", "1.0.0-SNAPSHOT")
}
```


### Manual
build.gradle.kts
```kts
repositories {
  maven("https://repo.remodded.dev/repositories/maven-public/")
}

dependencies {
  implementation("dev.remodded.recore:ReCore-API:1.0.0-SNAPSHOT")
}
```
