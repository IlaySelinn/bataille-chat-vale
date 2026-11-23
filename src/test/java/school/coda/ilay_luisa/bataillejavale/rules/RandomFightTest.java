package school.coda.ilay_luisa.bataillejavale.rules;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import school.coda.ilay_luisa.bataillejavale.fight.RandomFight;
import school.coda.ilay_luisa.bataillejavale.model.CoordinatePoints;

import static org.assertj.core.api.Assertions.assertThat;

class RandomFightTest {

    @Test
    @DisplayName("chooseAttack should return non-null coordinates within grid bounds [0-9]")
        void chooseAttack_ShouldReturnCoordinatesWithinGridBounds() {
            //arrange
            RandomFight fight = new RandomFight();

            //act
            CoordinatePoints attack = fight.chooseAttack();

            //assert
            assertThat(attack).isNotNull();
            assertThat(attack.row()).isBetween(0, 9);
            assertThat(attack.col()).isBetween(0, 9);
    }
}

//une commit apres chaque test!
