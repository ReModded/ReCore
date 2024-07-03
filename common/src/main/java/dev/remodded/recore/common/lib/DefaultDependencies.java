package dev.remodded.recore.common.lib;

import java.util.ArrayList;
import java.util.Arrays;

public class DefaultDependencies {

    private static final ArrayList<String> dependencies = new ArrayList<>(Arrays.asList(
            "org.jetbrains.kotlin:kotlin-stdlib:2.0.0",
            "org.spongepowered:configurate-hocon:4.1.2",
            "org.spongepowered:configurate-extra-kotlin:4.1.2",
            "com.google.code.gson:gson:2.11.0",
            "org.redisson:redisson:3.32.0",
            "com.zaxxer:HikariCP:5.1.0",
            "com.impossibl.pgjdbc-ng:pgjdbc-ng:0.8.9"
    ));

    public static ArrayList<String> getDependencies() {
        return dependencies;
    }
}
