# Transporter
![](common/src/main/resources/icon.png)

[![GitHub](https://img.shields.io/github/license/LazuriteMC/Transporter?color=A31F34&label=License&labelColor=8A8B8C)](https://github.com/LazuriteMC/Transporter/blob/main/LICENSE)
[![Discord](https://img.shields.io/discord/719662192601071747?color=7289DA&label=Discord&labelColor=2C2F33&logo=Discord)](https://discord.gg/NNPPHN7b3P)

## What is it?
Transporter is a library written for the Fabric API. As a mod author, it gives you the ability to "beam" client render information to the
server in the form of a "pattern" which is then stored in the "pattern buffer".

## How do I use it in my mod?
In order to use Transporter with your mod, put the following in your `build.gradle`:
```java
repositories {
    maven { 
        url 'https://lazurite.dev/releases' 
    }
}
        
dependencies {
    modImplementation 'dev.lazurite:transporter-fabric:[version]'
}
```
The latest version can be found on our [maven](https://lazurite.dev/maven/releases/dev/lazurite/transporter-fabric). Further documentation on 
how to use Transporter can be found [here](https://docs.lazurite.dev/).
