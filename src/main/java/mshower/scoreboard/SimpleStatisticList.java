package mshower.scoreboard;

import mshower.scoreboard.commands.SimpleStatisticListCommand;
import mshower.scoreboard.config.ScoreboardConfig;
import mshower.scoreboard.event.HookPlayerBreakBlockEvent;
import mshower.scoreboard.functions.CreateScoreboards;
import net.fabricmc.api.ModInitializer;
//#if MC < 11900
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
//#else
//$$ import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
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
import org.spongepowered.asm.mixin.transformer.Config;
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

    @Override
    public void onInitialize()
    {
        ModMetadata metadata = FabricLoader.getInstance().getModContainer(MOD_ID).orElseThrow(RuntimeException::new).getMetadata();
        MOD_NAME = metadata.getName();
        MOD_VERSION = metadata.getVersion().getFriendlyString();
        FabricLoader loader = FabricLoader.getInstance();
        File config_file_path = loader.getConfigDir().toFile();
        ScoreboardConfig config = new ScoreboardConfig(config_file_path.getPath());
        String miningListDisplayName = config.GetValue("MiningListDisplayName");
        String placingListDisplayName = config.GetValue("PlacingListDisplayName");
        String miningListName = config.GetValue("MiningListName");
        String placingListName = config.GetValue("PlacingListName");
        HookPlayerBreakBlockEvent.hook();
        SimpleStatisticListCommand.Config = new ScoreboardConfig(config_file_path.getPath());
        CreateScoreboards.create(miningListName,placingListName,miningListDisplayName,placingListDisplayName);

        //#if MC<11900
        // 注册命令以切换计分板的可见/隐藏状态
        net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> SimpleStatisticListCommand.register(dispatcher));
        //#else
        //$$ CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> SimpleStatisticListCommand.register(dispatcher));
        //#endif
        SimpleStatisticListMod.init();

        LOGGER.info("Simple Statistic List Loaded.");
    }
}
