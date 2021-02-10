# Transporter

![](https://github.com/LazuriteMC/Transporter/blob/main/src/main/resources/assets/transporter/transporter.png?raw=true)

[![GitHub](https://img.shields.io/github/license/LazuriteMC/Rayon?color=A31F34&label=License&labelColor=8A8B8C)](https://github.com/LazuriteMC/Transporter/blob/main/LICENSE)
[![Discord](https://img.shields.io/discord/719662192601071747?color=7289DA&label=Discord&labelColor=2C2F33&logo=Discord)](https://discord.gg/NNPPHN7b3P)

## What is it?
Transporter is a library written for the Fabric API. As a mod author, it gives you the ability to "beam" client render information to the
server in the form of a "pattern" which is then stored in the "pattern buffer".

I will admit, this lib is quite cursed in theory, but actually pretty useful in practice. It was designed with [Rayon](https://github.com/lazuritemc/rayon)
in mind in order to provide better and more accurate block collisions. As for any other use, I can't guarantee anything, so use at your own risk :)

## How do I use it in my mod?
In order to use Transporter with your mod, you have to put the following in your `build.gradle`:
```java
repositories {
    maven { url 'https://jitpack.io' }
}
        
dependencies {
    modImplementation 'com.github.LazuriteMC:Transporter:[version]'
        
    // Optional, for jar-in-jar:
    include 'com.github.LazuriteMC:Transporter:[version]'
}
```
The latest version can be found under [releases](https://github.com/lazuritemc/transporter/releases). Further documentation on 
how to use Transporter can be found [here](https://docs.lazurite.dev/).