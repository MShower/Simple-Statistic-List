package mshower.scoreboard.functions;

import net.minecraft.scoreboard.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;

import static mshower.scoreboard.functions.Utils.stripColor;

public class RemoveScoresWithSpecifiedPlayer {
    public static int remove(MinecraftServer server, String playerName) {
        if (playerName == null) return 0;

        int removed = 0;
        String cleanName = stripColor(playerName);
        Scoreboard scoreboard = server.getScoreboard();

        //#if MC>=12100
        //$$ for (ScoreboardObjective objective : scoreboard.getObjectives()) {
        //$$     for (ScoreboardEntry entry : scoreboard.getScoreboardEntries(objective)) {
        //$$         Text nameText = entry.name();
        //$$         if (nameText == null) continue;
        //$$         String holderName = stripColor(nameText.getString());
        //$$         if (holderName.equalsIgnoreCase(cleanName)) {
        //$$             server.getCommandManager().executeWithPrefix(
        //$$                 server.getCommandSource(),
        //$$                 "scoreboard players reset " + holderName + " " + objective.getName()
        //$$             );
        //$$             removed++;
        //$$         }
        //$$     }
        //$$ }
        //#else
        for (ScoreboardObjective objective : scoreboard.getObjectives()) {
            for (ScoreboardPlayerScore s : scoreboard.getAllPlayerScores(objective)) {
                String holderName = s.getPlayerName();
                if (holderName == null) continue;
                if (stripColor(holderName).equalsIgnoreCase(cleanName)) {
                    scoreboard.resetPlayerScore(holderName, objective);
                    removed++;
                }
            }
        }
        //#endif

        return removed;
    }
}
