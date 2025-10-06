package mshower.scoreboard.functions;

import net.minecraft.scoreboard.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;

import static mshower.scoreboard.functions.Utils.stripColor;

public class RemoveScoresWithPlayerPrefix {
    public static int remove(MinecraftServer server, String prefix) {
        if (prefix == null) {
            return 0;
        }

        int removed = 0;
        String cleanPrefix = stripColor(prefix).toLowerCase();
        Scoreboard scoreboard = server.getScoreboard();

        //#if MC>=12004
        //$$ for (ScoreboardObjective objective : scoreboard.getObjectives()) {
        //$$    for (ScoreboardEntry entry : scoreboard.getScoreboardEntries(objective)) {
        //$$        Text nameText = entry.name();
        //$$        if (nameText == null) continue;
        //$$        String holderName = nameText.getString();
        //$$
        //$$        if (stripColor(holderName).toLowerCase().startsWith(cleanPrefix)) {
        //$$            server.getCommandManager().executeWithPrefix(
        //$$                server.getCommandSource(),
        //$$                "scoreboard players reset " + holderName + " " + objective.getName()
        //$$            );
        //$$            removed++;
        //$$        }
        //$$    }
        //$$}
        //#else
        for (ScoreboardObjective objective : scoreboard.getObjectives()) {
            for (ScoreboardPlayerScore s : scoreboard.getAllPlayerScores(objective)) {
                String holderName = s.getPlayerName();
                if (holderName == null) continue;
                if (stripColor(holderName).toLowerCase().startsWith(cleanPrefix)) {
                    scoreboard.resetPlayerScore(holderName, objective);
                    removed++;
                }
            }
        }
        //#else
        //TODO
        //#endif

        return removed;
    }
}
