package mshower.scoreboard;

import mshower.scoreboard.command.SimpleStatisticListCommand;
import mshower.scoreboard.config.ScoreboardConfig;
import mshower.scoreboard.event.CycleScoreboardEvent;
import mshower.scoreboard.event.HookPlayerBreakBlockEvent;
import mshower.scoreboard.event.HookPlayerDeadEvent;
import mshower.scoreboard.event.HookPlayerPlaceBlockEvent;
import mshower.scoreboard.functions.CreateScoreboards;
import net.fabricmc.api.ModInitializer;
//#if MC >= 11900
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
//#endif
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.metadata.ModMetadata;

//#if MC >= 11802
//$$ import com.mojang.logging.LogUtils;
//$$ import org.slf4j.Logger;
//#else
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
//#endif

import net.minecraft.scoreboard.*;

//#if MC >= 11900
//#endif
import java.io.File;

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
    public static ScoreboardObjective DeathScoreboardObj;
    public static ScoreboardConfig Config;

    @Override
    public void onInitialize()
    {
        ModMetadata metadata = FabricLoader.getInstance().getModContainer(MOD_ID).orElseThrow(RuntimeException::new).getMetadata();
        MOD_NAME = metadata.getName();
        MOD_VERSION = metadata.getVersion().getFriendlyString();
        FabricLoader loader = FabricLoader.getInstance();
        File config_file_path = loader.getConfigDir().toFile();
        Config = new ScoreboardConfig(config_file_path.getPath());
        String miningListDisplayName = Config.GetValue("MiningListDisplayName");
        String placingListDisplayName = Config.GetValue("PlacingListDisplayName");
        String deathListDisplayName = Config.GetValue("DeathListDisplayName");
        String miningListName = Config.GetValue("MiningListName");
        String placingListName = Config.GetValue("PlacingListName");
        String deathListName = Config.GetValue("DeathListDisplayName");
        HookPlayerBreakBlockEvent.hook();
        HookPlayerPlaceBlockEvent.hook();
        HookPlayerDeadEvent.hook();
        CycleScoreboardEvent.register();
        CreateScoreboards.create(miningListName,placingListName,deathListName,miningListDisplayName,placingListDisplayName,deathListDisplayName);

        //#if MC<11900
        //$$ net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> SimpleStatisticListCommand.register(dispatcher));
        //#else
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> SimpleStatisticListCommand.register(dispatcher));
        //#endif
        SimpleStatisticListMod.init();

        LOGGER.info("Simple Statistic List Loaded.");
    }
}
