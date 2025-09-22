package mshower.scoreboard;

import mshower.scoreboard.config.ScoreboardConfig;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.metadata.ModMetadata;

//#if MC >= 11802
//$$ import com.mojang.logging.LogUtils;
//$$ import org.slf4j.Logger;
//#else
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
//#endif

//#if MC>=12003
import net.minecraft.scoreboard.*;
//#else
//$$ import net.minecraft.scoreboard.ScoreboardPlayerScore;
//#endif

//#if MC >= 11900
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import org.spongepowered.asm.mixin.transformer.Config;

import java.io.File;
//#else
//$$ import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
//#endif

public class SimpleStatisticList implements ModInitializer
{
    public static final Logger LOGGER =
            //#if MC >= 11802
            //$$ LogUtils.getLogger();
            //#else
            LogManager.getLogger();
    //#endif

    public static final String MOD_ID = "simple-statistic-list";
    public static String MOD_VERSION = "1.0.0";
    public static String MOD_NAME = "Simple Statistic List";
    public static Scoreboard StatisticListScoreboard;
    public static ScoreboardObjective MiningScoreboardObj;
    public static ScoreboardObjective PlacingScoreboardObj;

    @Override
    public void onInitialize()
    {
        ModMetadata metadata = FabricLoader.getInstance().getModContainer(MOD_ID).orElseThrow(RuntimeException::new).getMetadata();
        MOD_NAME = metadata.getName();
        MOD_VERSION = metadata.getVersion().getFriendlyString();
        FabricLoader loader = FabricLoader.getInstance();
        File config_file_path = loader.getConfigDir().toFile();
        ScoreboardConfig config = new ScoreboardConfig(config_file_path.getPath());

        LOGGER.info("Simple Statistic List Loaded.");
    }
}
