package yohanemod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import yohanemod.powers.FallenEnergy;

public class AngelWings extends CustomRelic {
    public static final String ID = "Yohane:AngelWings";
    private static final String IMG = "relics/AngelWings.png";
    private static int FALLEN_ENERGY = 5;
    public static final Logger logger = LogManager.getLogger(AngelWings.class.getName());

    public AngelWings() {
        super(ID, new Texture(IMG), RelicTier.STARTER, LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStart()
     {
         AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new FallenEnergy(AbstractDungeon.player, FALLEN_ENERGY)));
     }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new AngelWings();
    }
}