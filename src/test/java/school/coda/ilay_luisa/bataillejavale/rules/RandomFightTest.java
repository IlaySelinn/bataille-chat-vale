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

    @ParameterizedTest(name = "Run {0}: attack must stay within valid board boundaries [0-9]")
    @ValueSource(ints = {1,2,3,4,5})
    @DisplayName("chooseAttack should produce within bounds across multiple runs")
        void chooseAttack_shouldAlwaysStayWithinBounds_AcrossMultipleRuns(int runNumber)
    {
        // arrange
        RandomFight fight = new RandomFight();

        // act
        CoordinatePoints attack = fight.chooseAttack();

        // assert
        assertThat(attack.row()).isBetween(0, 9);
        assertThat(attack.col()).isBetween(0, 9);
    }

    @Test
    @DisplayName("chooseAttack coordinates should never be negative")
        void chooseAttack_ShouldNeverReturnNegativeCoordinates() {

            // Arrange
           RandomFight fight = new RandomFight();

           // Act
           CoordinatePoints attack = fight.chooseAttack();

           // Assert
           assertThat(attack.row()).isGreaterThanOrEqualTo(0);
           assertThat(attack.col()).isGreaterThanOrEqualTo(0);
       }

       @Test
       @DisplayName("chooseAttack coordinates should never exceed maximum grid index of 9")
       void chooseAttack_ShouldNeverExceedMaximumGridIndex()  {

           // arrange
           RandomFight fight = new RandomFight();

           // act
           CoordinatePoints attack = fight.chooseAttack();

           // assert
           assertThat(attack.row()).isLessThanOrEqualTo(9);
           assertThat(attack.col()).isLessThanOrEqualTo(9);
       }

       @Test
       @DisplayName("chooseAttack should return a new CoordinatesPoints instance on each invocation")
        void chooseAttack_ShouldReturnNewInstanceOnEachCall() {

            //arrange
            RandomFight fight = new RandomFight();

            //act
            CoordinatePoints firstAttack = fight.chooseAttack();
            CoordinatePoints secondAttack = fight.chooseAttack();

            //assert
            assertThat(firstAttack).isNotSameAs(secondAttack);
        }
}

//une commit apres chaque test!
