![](src/main/resources/icon.png)

# Transporter
[![Discord](https://discordapp.com/api/guilds/719662192601071747/widget.png?style=shield)](https://discord.gg/NNPPHN7b3P)

Transporter gives you the ability to "beam" vertex information to the server in the form of a "pattern" which is then stored in the "pattern buffer".

## Developing with Transporter
Add the following to your `build.gradle`:
```java
repositories {
    maven { url "https://lazurite.dev/releases" }
}
        
dependencies {
    modImplementation "dev.lazurite:transporter:$transporter_version"
}
```
The latest version can be found on our [maven](https://lazurite.dev/maven/releases/dev/lazurite/transporter).
