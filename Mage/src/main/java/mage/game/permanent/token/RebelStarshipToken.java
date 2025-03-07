

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.abilities.keyword.SpaceflightAbility;

/**
 *
 * @author spjspj
 */
public final class RebelStarshipToken extends TokenImpl {

    public RebelStarshipToken() {
        super("B-Wing Token", "2/3 blue Rebel Starship artifact creature tokens with spaceflight name B-Wing", 2, 3);
        this.setOriginalExpansionSetCode("SWS");
        cardType.add(CardType.CREATURE);
        cardType.add(CardType.ARTIFACT);
        abilities.add(SpaceflightAbility.getInstance());
        color.setBlue(true);
        subtype.add(SubType.REBEL);
        subtype.add(SubType.STARSHIP);
    }

    public RebelStarshipToken(final RebelStarshipToken token) {
        super(token);
    }

    public RebelStarshipToken copy() {
        return new RebelStarshipToken(this);
    }
}
