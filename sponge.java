
///usr/bin/env jbang "$0" "$@" ; exit $?
//REPOS sponge=https://repo.spongepowered.org/maven/
//REPOS minecraftforge=https://files.minecraftforge.net/maven
//DEPS org.spongepowered:spongevanilla:1.12.2-7.3.0
//JAVA 8

import static java.lang.System.*;

import java.io.File;
import java.nio.file.Files;

public class sponge {

    public static void main(String... args) throws Exception {
        if(!new File("eula.txt").exists()) {
            Files.write(new File("eula.txt").toPath(), "eula=true".getBytes());
        }
        org.spongepowered.server.launch.VersionCheckingMain.main(args);
    }
}
