package yohanemod.summons.Hanamaru;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.unique.ExhumeAction;
import com.megacrit.cardcrawl.actions.utility.LoseBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import kobting.friendlyminions.characters.AbstractPlayerWithMinions;
import kobting.friendlyminions.monsters.AbstractFriendlyMonster;
import kobting.friendlyminions.monsters.MinionMove;
import yohanemod.powers.Sin;

import java.util.ArrayList;

public class Hanamaru extends AbstractFriendlyMonster {
    public static String NAME = "Hanamaru";
    public static String ID = "Hanamaru";
    private int upgradeCount;
    private boolean hasAttacked = false;
    private AbstractMonster target;
    private AbstractPlayer p = AbstractDungeon.player;
    private AbstractPlayerWithMinions player = (AbstractPlayerWithMinions) p;
    public static boolean canExhume = true;

    public Hanamaru(float offSetX) {
        super(NAME, ID, HanamaruNumbers.hanamaruHP, -2.0F, 10.0F, 230.0F, 240.0F, "summons/Hanamaru.png", offSetX, 0);
        addMoves();
    }

    @Override
    public void applyEndOfTurnTriggers() {
        super.applyEndOfTurnTriggers();
        this.hasAttacked = false;
    }

    @Override
    public void applyStartOfTurnPowers() {
        AbstractDungeon.actionManager.addToBottom(new LoseBlockAction(this, this, this.currentBlock));
        System.out.println(this.name + " " + this.currentHealth);
    }

    public void addMoves() {
        if (this.hasPower(HanamaruStrength.POWER_ID) && this.getPower(HanamaruStrength.POWER_ID).amount != 0) {
            upgradeCount = this.getPower(HanamaruStrength.POWER_ID).amount;
        }
        int sinAmount = (HanamaruNumbers.hanamaruSin + upgradeCount);
        int blockAmount = (HanamaruNumbers.hanamaruBlock + upgradeCount);
        String sinDesc = String.format("Apply %d Sin to ALL enemies.", sinAmount);
        String blockDesc = String.format("Give %d Block to Yohane and ALL Summons.", blockAmount);
        this.moves.addMove(new MinionMove("Sin", this, new Texture("summons/bubbles/sin_bubble.png")
                , sinDesc, () -> {
            for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (mo != null) {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, AbstractDungeon.player, new Sin(mo, sinAmount), sinAmount));
                }
            }
        }));
        this.moves.addMove(new MinionMove("Block", this, new Texture("summons/bubbles/block_bubble.png")
                , blockDesc, () -> {
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, this, blockAmount));
            for (AbstractMonster mo : player.minions.monsters) {
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(mo, p, blockAmount));
            }
        }));
        this.moves.addMove(new MinionMove("Exhume", this, new Texture("images/monsters/exhume_bubble.png")
                , "Add one card from your Exhaust pile to your hand. NL You can only do this once until Evolved again.", () -> {
                AbstractDungeon.actionManager.addToBottom(new ExhumeAction(false));
                canExhume = false;
                this.removeMove("Exhume");
        }));
    }


    //Not needed unless doing some kind of random move like normal Monsters
    @Override
    protected void getMove(int i) {
    }

}
