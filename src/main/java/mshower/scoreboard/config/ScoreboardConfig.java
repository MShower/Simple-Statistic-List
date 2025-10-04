package mshower.scoreboard.config;

import mshower.scoreboard.SimpleStatisticList;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Properties;

public class ScoreboardConfig {

    public static final String CONFIG_FILE_NAME = "SimpleStatisticList.properties";
    private final File file;
    public final Properties ScoreboardProperties;
    private final Properties defaultProps;

    public ScoreboardConfig(final String filePath) {
        file = new File(filePath + File.separator + CONFIG_FILE_NAME);

        defaultProps = new Properties();
        defaultProps.setProperty("MiningListDisplayName", "MiningList");
        defaultProps.setProperty("PlacingListDisplayName", "PlacingList");
        defaultProps.setProperty("DeathListDisplayName", "DeathList");
        defaultProps.setProperty("MiningListName", "MiningList");
        defaultProps.setProperty("PlacingListName", "PlacingList");
        defaultProps.setProperty("DeathListName", "DeathList");
        defaultProps.setProperty("DisplayMode", "Cycle"); //Mining Placing Death Off Cycle
        defaultProps.setProperty("CycleDelay", "1200");

        if (!file.exists()) {
            createConfigFileWithDefaults();
        }

        ScoreboardProperties = new Properties();
        try (Reader reader = new InputStreamReader(Files.newInputStream(file.toPath()), StandardCharsets.UTF_8)) {
            ScoreboardProperties.load(reader);
        } catch (Exception e) {
            SimpleStatisticList.LOGGER.warn("ERROR OCCURRED WHILE READING CONFIG.");
        }

        fillMissingDefaults();
    }

    private void createConfigFileWithDefaults() {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(file.toPath()), StandardCharsets.UTF_8))) {
            for (String key : defaultProps.stringPropertyNames()) {
                writer.write(key + " = " + defaultProps.getProperty(key));
                writer.newLine();
            }
        } catch (Exception e) {
            SimpleStatisticList.LOGGER.warn("ERROR OCCURRED WHILE CREATING DEFAULT CONFIG.");
        }
    }

    private void fillMissingDefaults() {
        boolean changed = false;
        for (String key : defaultProps.stringPropertyNames()) {
            if (!ScoreboardProperties.containsKey(key)) {
                ScoreboardProperties.setProperty(key, defaultProps.getProperty(key));
                changed = true;
            }
        }
        if (changed) {
            try {
                ScoreboardProperties.store(Files.newOutputStream(file.toPath()), null);
            } catch (IOException e) {
                SimpleStatisticList.LOGGER.warn("ERROR OCCURRED WHILE UPDATING CONFIG WITH DEFAULTS.");
            }
        }
    }

    public String GetValue(final String key) {
        return ScoreboardProperties.getProperty(key);
    }

    public void UpdateValue(final String key, final String val) throws IOException {
        ScoreboardProperties.setProperty(key, val);
        ScoreboardProperties.store(Files.newOutputStream(file.toPath()), null);
    }
}
