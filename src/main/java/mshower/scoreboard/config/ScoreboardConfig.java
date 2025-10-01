package mshower.scoreboard.config;

import mshower.scoreboard.SimpleStatisticList;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Properties;

public class ScoreboardConfig {

    public static final String CONFIG_FILE_NAME = "SimpleStatisticList.properties";
    File file;
    public Properties ScoreboardProperties;

    private void CreateDefaultConfigFile(File file) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file)))
        {
            String DEFAULT_CONFIG_DATA =
                    "MiningListDisplayName = MiningList\n" +
                            "PlacingListDisplayName = PlacingList\n"+
                            "MiningListName = MiningList\n"+
                            "PlacingListName = PlacingList\n"+
                            "DisplayMode = Cycle\n"+ //Mining Placing Off Cycle
                            "CycleDelay = 1200";
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
                CreateDefaultConfigFile(file);
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
}
