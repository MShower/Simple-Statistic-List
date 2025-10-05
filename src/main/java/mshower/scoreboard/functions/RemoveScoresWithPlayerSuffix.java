package mshower.scoreboard.functions;

import net.minecraft.scoreboard.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;

import static mshower.scoreboard.functions.Utils.stripColor;

public class RemoveScoresWithPlayerSuffix {
    public static int remove(MinecraftServer server, String suffix) {
        if (suffix == null) {
            return 0;
        }

        int removed = 0;
        String cleanSuffix = stripColor(suffix).toLowerCase();
        Scoreboard scoreboard = server.getScoreboard();

        //#if MC>=12004
        //$$ for (ScoreboardObjective objective : scoreboard.getObjectives()) {
        //$$    for (ScoreboardEntry entry : scoreboard.getScoreboardEntries(objective)) {
        //$$        Text nameText = entry.name();
        //$$        if (nameText == null) continue;
        //$$        String holderName = nameText.getString();
        //$$
        //$$        if (stripColor(holderName).toLowerCase().endsWith(cleanSuffix)) {
        //$$            server.getCommandManager().executeWithPrefix(
        //$$                server.getCommandSource(),
        //$$                "scoreboard players reset " + holderName + " " + objective.getName()
        //$$            );
        //$$            removed++;
        //$$        }
        //$$    }
        //$$}
        //#elseif MC<=1.20.1
        for (ScoreboardObjective objective : scoreboard.getObjectives()) {
            for (ScoreboardPlayerScore s : scoreboard.getAllPlayerScores(objective)) {
                String holderName = s.getPlayerName();
                if (holderName == null) continue;
                if (stripColor(holderName).toLowerCase().endsWith(cleanSuffix)) {
                    scoreboard.resetPlayerScore(holderName, objective);
                    removed++;
                }
            }
        }
        //#endif
        return removed;
    }
}
