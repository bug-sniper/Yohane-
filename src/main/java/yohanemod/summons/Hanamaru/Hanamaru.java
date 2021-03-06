package yohanemod.summons.Hanamaru;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.unique.ExhumeAction;
import com.megacrit.cardcrawl.actions.utility.LoseBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import kobting.friendlyminions.characters.AbstractPlayerWithMinions;
import kobting.friendlyminions.monsters.AbstractFriendlyMonster;
import kobting.friendlyminions.monsters.MinionMove;
import kobting.friendlyminions.monsters.MinionMoveGroup;
import yohanemod.actions.HanamaruBlock;
import yohanemod.actions.HanamaruExhumeAction;
import yohanemod.actions.HanamaruSin;
import yohanemod.powers.Sin;
import yohanemod.summons.AbstractYohaneMinion;
import yohanemod.tools.TextureLoader;

import java.util.ArrayList;

public class Hanamaru extends AbstractYohaneMinion {
    public static String NAME = "Hanamaru";
    public static String ID = "Hanamaru";
    private int upgradeCount;
    private boolean hasAttacked = false;
    private AbstractMonster target;
    private AbstractPlayer p = AbstractDungeon.player;
    private AbstractPlayerWithMinions player = (AbstractPlayerWithMinions) p;
    public static boolean canExhume = true;
    private static Texture intentOne = TextureLoader.getTexture("summons/intents/hanamaru/attack_intent_1.png");
    private static Texture intentTwo = TextureLoader.getTexture("summons/intents/hanamaru/attack_intent_2.png");
    private static Texture intentThree = TextureLoader.getTexture("summons/intents/hanamaru/attack_intent_3.png");
    private static Texture intentFour = TextureLoader.getTexture("summons/intents/hanamaru/attack_intent_4.png");
    private static Texture intentFive = TextureLoader.getTexture("summons/intents/hanamaru/attack_intent_5.png");
    private static Texture intentSix = TextureLoader.getTexture("summons/intents/hanamaru/attack_intent_6.png");
    private static Texture intentSeven = TextureLoader.getTexture("summons/intents/hanamaru/attack_intent_7.png");
    private static Texture[] intentImgs = {intentOne, intentTwo, intentThree, intentFour, intentFive, intentSix, intentSeven};

    public Hanamaru(float offSetX, boolean slotOne) {
        super(NAME, ID, HanamaruNumbers.hanamaruHP, -2.0F, 10.0F, 230.0F, 240.0F,
                "summons/Hanamaru.png", offSetX, 0, intentImgs, slotOne);
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
        ArrayList<MinionMove> hanamaruMoves = new ArrayList<>();
        if (this.hasPower(HanamaruStrength.POWER_ID) && this.getPower(HanamaruStrength.POWER_ID).amount != 0) {
            upgradeCount = this.getPower(HanamaruStrength.POWER_ID).amount;
        }
        int sinAmount = (HanamaruNumbers.hanamaruSin);
        int blockAmount = (HanamaruNumbers.hanamaruBlock);
        String sinDesc = String.format("Apply %d Sin to ALL enemies.", sinAmount);
        String blockDesc = String.format("Give %d Block to Yohane and ALL Summons.", blockAmount);
        hanamaruMoves.add(new MinionMove("rubyPic", this, TextureLoader.getTexture("summons/bubbles/hanamarububble.png")
                , "", () -> this.setTakenTurn(false)));
        hanamaruMoves.add(new MinionMove("Sin", this, new Texture("summons/bubbles/sin_bubble.png")
                , sinDesc, () -> {
            AbstractDungeon.actionManager.addToBottom(new HanamaruSin(this));
        }));
        hanamaruMoves.add(new MinionMove("Block", this, new Texture("summons/bubbles/block_bubble.png")
                , blockDesc, () -> {
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, this, blockAmount));
            AbstractDungeon.actionManager.addToBottom(new HanamaruBlock(this));
        }));
        hanamaruMoves.add(new MinionMove("Exhume", this, new Texture("summons/bubbles/exhume_bubble.png")
                , "Add one card from your Exhaust pile to your hand. NL You can only do this once.", () -> {
            if (canExhume)     {
                AbstractDungeon.actionManager.addToBottom(new HanamaruExhumeAction());
            }
                canExhume = false;
        }));
        if (slotOne) {
            this.moves = new MinionMoveGroup(hanamaruMoves, 350F * Settings.scale, -200F * Settings.scale);
        } else {
            this.moves = new MinionMoveGroup(hanamaruMoves, 350F * Settings.scale, -300F * Settings.scale);
        }
    }


    //Not needed unless doing some kind of random move like normal Monsters
    @Override
    protected void getMove(int i) {
    }

}
