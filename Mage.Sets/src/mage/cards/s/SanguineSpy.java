package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfYourEndStepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.DifferentManaValuesInGraveCondition;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.hint.common.DifferentManaValuesInGraveHint;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author weirddan455
 */
public final class SanguineSpy extends CardImpl {

    public SanguineSpy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // {1}, Sacrifice another creature: Look at the top card of your library. You may put that card into your graveyard.
        Ability ability = new SimpleActivatedAbility(new SanguineSpyEffect(), new GenericManaCost(1));
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE));
        this.addAbility(ability);

        // At the beginning of your end step, if there are five or more mana values among cards in your graveyard, you may pay 2 life. If you do, draw a card.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfYourEndStepTriggeredAbility(
                        new DoIfCostPaid(new DrawCardSourceControllerEffect(1), new PayLifeCost(2)),
                        false
                ),
                DifferentManaValuesInGraveCondition.FIVE,
                "At the beginning of your end step, if there are five or more mana values among cards in your graveyard, you may pay 2 life. If you do, draw a card."
        ).addHint(DifferentManaValuesInGraveHint.instance));
    }

    private SanguineSpy(final SanguineSpy card) {
        super(card);
    }

    @Override
    public SanguineSpy copy() {
        return new SanguineSpy(this);
    }
}

class SanguineSpyEffect extends OneShotEffect {

    public SanguineSpyEffect() {
        super(Outcome.Benefit);
        this.staticText = "Look at the top card of your library. You may put that card into your graveyard";
    }

    private SanguineSpyEffect(final SanguineSpyEffect effect) {
        super(effect);
    }

    @Override
    public SanguineSpyEffect copy() {
        return new SanguineSpyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Card card = player.getLibrary().getFromTop(game);
        if (card == null) {
            return false;
        }
        player.lookAtCards("Top card of your library", card, game);
        if (player.chooseUse(Outcome.AIDontUseIt, "Put the top card of your library into your graveyard?", source, game)) {
            player.moveCards(card, Zone.GRAVEYARD, source, game);
        }
        return true;
    }
}
