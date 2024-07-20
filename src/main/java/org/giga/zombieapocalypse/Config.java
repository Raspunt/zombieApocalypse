package org.giga.zombieapocalypse;

import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class Config {
    public static int waveInterval = 5 * 24000; // Adjust to milliseconds if necessary
    public static int spawnRadius = 20;
    public static int safeRadius = 5;

    public static void loadConfig() {
        Path configPath = Paths.get(FabricLoader.getInstance().getConfigDir().toString(), "zombiewaves.properties");
        Properties properties = new Properties();

        if (Files.exists(configPath)) {
            try {
                properties.load(Files.newInputStream(configPath));
                waveInterval = Integer.parseInt(properties.getProperty("waveInterval", String.valueOf(waveInterval)));
                spawnRadius = Integer.parseInt(properties.getProperty("spawnRadius", String.valueOf(spawnRadius)));
                safeRadius = Integer.parseInt(properties.getProperty("safeRadius", String.valueOf(safeRadius)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            saveConfig();
        }
    }

    public static void saveConfig() {
        Path configPath = Paths.get(FabricLoader.getInstance().getConfigDir().toString(), "zombiewaves.properties");
        Properties properties = new Properties();

        properties.setProperty("waveInterval", String.valueOf(waveInterval));
        properties.setProperty("spawnRadius", String.valueOf(spawnRadius));
        properties.setProperty("safeRadius", String.valueOf(safeRadius));

        try {
            Files.createDirectories(configPath.getParent());
            properties.store(Files.newOutputStream(configPath), null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}