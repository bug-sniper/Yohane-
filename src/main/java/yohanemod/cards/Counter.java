package yohanemod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import yohanemod.patches.AbstractCardEnum;
import yohanemod.powers.FallenEnergy;

public class Counter extends CustomCard {
    public static final String ID = "Counter";
    public static final String NAME = "Counter";
    public static final String DESCRIPTION = "Pay !M! Fallen Energy. NL If the enemy intends to attack, deal !D! damage.";
    public static final String IMG_PATH = "cards/Counter.png";
    private static final int COST = 0;
    private static final int DAMAGE_AMOUNT = 12;
    private static final int FALLEN_ENERGY = 8;
    private static final int POOL = 1;
    private static final CardRarity rarity = CardRarity.UNCOMMON;
    private static final CardTarget target = CardTarget.ENEMY;

    public Counter() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.ATTACK, AbstractCardEnum.GREY,
                rarity, target, POOL);
        this.damage = this.baseDamage = DAMAGE_AMOUNT;
        this.magicNumber = this.baseMagicNumber = FALLEN_ENERGY;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        if ((p.hasPower("FallenEnergy")) && (p.getPower("FallenEnergy").amount >= this.magicNumber)) {
            if ((m != null) && ((m.intent ==
                    AbstractMonster.Intent.ATTACK) || (m.intent == AbstractMonster.Intent.ATTACK_BUFF)
                    || (m.intent == AbstractMonster.Intent.ATTACK_DEBUFF) || (m.intent == AbstractMonster.Intent.ATTACK_DEFEND))) {
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new FallenEnergy(p, 0), -this.magicNumber));
                AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.DamageAction(m,
                        new DamageInfo(p, this.damage, this.damageTypeForTurn),
                        AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
            } else {
                AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "The enemy doesn't intend to attack!", 1.0F, 2.0F));
            }
        } else {
            AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "I don't have enough Fallen Energy!", 1.0F, 2.0F));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Counter();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(3);
            this.upgradeMagicNumber(-2);
        }
    }
}