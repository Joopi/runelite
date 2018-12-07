package net.runelite.client.plugins.playerindicators;

import net.runelite.api.ClanMemberRank;
import java.awt.*;
import javax.inject.Inject;
import net.runelite.api.Player;
import net.runelite.api.Point;
import net.runelite.client.game.ClanManager;
import net.runelite.client.ui.overlay.*;

public class PlayerIndicatorsZMIOverlay extends Overlay
{
    private final PlayerIndicatorsService playerIndicatorsService;
    private final ClanManager clanManager;
    private final PlayerIndicatorsConfig config;
    int tradeState;


    @Inject
    private PlayerIndicatorsZMIOverlay(PlayerIndicatorsConfig config, PlayerIndicatorsService playerIndicatorsService, ClanManager clanManager)
    {
        this.config = config;
        this.playerIndicatorsService = playerIndicatorsService;
        this.clanManager = clanManager;
        setLayer(OverlayLayer.ABOVE_WIDGETS);
        setPosition(OverlayPosition.DYNAMIC);
        setPriority(OverlayPriority.MED);
    }

    @Override
    public Dimension render(Graphics2D graphics) {

        if (!config.highlightZMI()){
            return null;
        }

        if (tradeState > 0)
        {
            Color[] colors = {Color.GRAY,Color.ORANGE,Color.MAGENTA};
            if (tradeState > 0){
                graphics.setColor(colors[tradeState-1]);
                graphics.fillRect(730, 58, 20, 20);
            }
        }
        playerIndicatorsService.forEachPlayer((player, color) -> renderZMIOverlay(graphics, player, color));
        return null;
    }


    private void renderZMIOverlay(Graphics2D graphics, Player actor, Color color)
    {
        ClanMemberRank rank = clanManager.getRank(actor.getName());
        int anim = actor.getAnimation();
        if (rank == ClanMemberRank.OWNER) {
            if (anim == -1) {
                graphics.setColor(color.GREEN);
            } else {
                graphics.setColor(color.RED);
            }
            graphics.fillRect(730, 10, 20, 20);
        } else if (anim == -1) {
            Point playerPos = actor.getWorldLocation().toPoint();
            int locX = playerPos.getX();
            int locY = playerPos.getY();
            if ((3054 <= locX) && (locX <= 3065) && (5573 <= locY) && (locY <= 5588)) {
                graphics.setColor(color.RED);
                graphics.fillRect(730, 34, 20, 20);
            }
        }
    }
}
