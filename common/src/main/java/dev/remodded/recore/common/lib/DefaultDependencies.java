package dev.remodded.recore.common.lib;

import java.util.ArrayList;
import java.util.Arrays;

public class DefaultDependencies {

    private static final ArrayList<String> dependencies = new ArrayList<String>(Arrays.asList(
            "org.jetbrains.kotlin:kotlin-stdlib:1.9.21",
            "org.spongepowered:configurate-hocon:4.1.2",
            "org.spongepowered:configurate-extra-kotlin:4.1.2"
    ));

    public static ArrayList<String> getDependencies() {
        return dependencies;
    }
}
