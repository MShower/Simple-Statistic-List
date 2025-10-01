package mshower.scoreboard.config;

import mshower.scoreboard.SimpleStatisticList;
import org.lwjgl.system.CallbackI;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Properties;

public class ScoreboardConfig {

    public static final String CONFIG_FILE_NAME = "SimpleStatisticList.properties";
    File file;
    public Properties ScoreboardProperties;

    private void CreateDefaultConfigFile(File file,
                                         String MiningListDisplayName,
                                         String PlacingListDisplayName,
                                         String MiningListName,
                                         String PlacingListName,
                                         String DisplayMode,
                                         Integer CycleDelay
    ) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file)))
        {
            String DEFAULT_CONFIG_DATA =
                    "MiningListDisplayName = " + MiningListDisplayName +
                            "\nPlacingListDisplayName = " + PlacingListDisplayName +
                            "\nMiningListName = " + MiningListName +
                            "\nPlacingListName = " + PlacingListName +
                            "\nDisplayMode = " + DisplayMode +//Mining Placing Off Cycle
                            "\nCycleDelay = " + CycleDelay;
            writer.write(DEFAULT_CONFIG_DATA);
        } catch (Exception e) {
            SimpleStatisticList.LOGGER.warn("ERROR OCCURRED WHILE WRITING CONFIG.");
        }
    }
    public ScoreboardConfig(final String filePath) {
        file = new File(filePath + File.separator + CONFIG_FILE_NAME);
        if (!file.exists())
        {
            try {
                CreateDefaultConfigFile(file, "MiningList", "PlacingList", "MiningList", "PlacingList", "Cycle", 1200);
            } catch (Exception e) {
                SimpleStatisticList.LOGGER.warn("ERROR OCCURRED WHILE CREATING CONFIG.");
            }
        }

        this.ScoreboardProperties = new Properties();

        try (Reader reader = new InputStreamReader(Files.newInputStream(file.toPath()),
                StandardCharsets.UTF_8)) {
            this.ScoreboardProperties.load(reader);
        } catch (Exception e) {
            SimpleStatisticList.LOGGER.warn("ERROR OCCURRED WHILE READING CONFIG.");
        }
    }

    public String GetValue(final String key) {
        return this.ScoreboardProperties.getProperty(key);
    }

    public void UpdateValue(final String key, final String val) throws IOException {
        this.ScoreboardProperties.setProperty(key, val);
        this.ScoreboardProperties.store(Files.newOutputStream(this.file.toPath()), null);
    }

    public static void UpdateConfig() {
        //TODO
    }
}
