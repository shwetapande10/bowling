package au.com.dius

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import java.lang.IllegalStateException


internal class BowlingGameTest {
    private lateinit var bowlingGame: BowlingGame

    @BeforeEach
    fun setUp() {
        bowlingGame = BowlingGame()
    }

    @Test
    fun rollAllGutters() {
        repeat(20) { bowlingGame.roll(0) }
        assertEquals(0,bowlingGame.score())
    }

    @Test
    fun rollAllOnes() {
        repeat(20) { bowlingGame.roll(1) }
        assertEquals(20,bowlingGame.score())
    }

    @Test
    fun rollOneFrameRestAllGutters() {
        bowlingGame.roll(4)
        bowlingGame.roll(4)
        repeat(18) { bowlingGame.roll(0) }
        val score = bowlingGame.score()
        assertEquals(8,score)
    }

    @Test
    fun rollOneSpareNextBall5AndRestGutters() {
        bowlingGame.roll(4)
        bowlingGame.roll(6)
        bowlingGame.roll(5)
        repeat(17) { bowlingGame.roll(0) }
        assertEquals(20,bowlingGame.score())
    }

    @Test
    fun rollOneStrikeNextFrame9AndRestGutters() {
        bowlingGame.roll(10)
        bowlingGame.roll(5)
        bowlingGame.roll(4)
        repeat(16) { bowlingGame.roll(0) }
        assertEquals(28,bowlingGame.score())
    }

    @Test
    fun rollLastFrameWithSpareAndRestAllGutters() {
        repeat(18) { bowlingGame.roll(0) }
        repeat(3) { bowlingGame.roll(5) }
        assertEquals(15,bowlingGame.score())
    }

    @Test
    fun rollLastFrameWithSStrikeAndRestAllGutters() {
        repeat(18) { bowlingGame.roll(0) }
        bowlingGame.roll(10)
        bowlingGame.roll(5)
        bowlingGame.roll(5)
        assertEquals(20,bowlingGame.score())
    }

    @Test
    fun rollAllStrikes() {
        repeat(12) { bowlingGame.roll(10) }
        assertEquals(300,bowlingGame.score())
    }

    @Test
    fun rollMoreThanMax() {
        assertThrows<IllegalStateException> {
            repeat(30) { bowlingGame.roll(10) }
        }
    }

    @Test
    fun rollStrikeWithoutNextTwoRolls() {
        assertThrows<IllegalStateException> {
            bowlingGame.roll(10)
            bowlingGame.score()
        }
    }

    @Test
    fun rollSpareWithoutNextRoll() {
        assertThrows<IllegalStateException> {
            repeat(2) { bowlingGame.roll(5) }
            bowlingGame.score()
        }
    }

    @Test
    fun rollWithoutNextRoll() {
        assertThrows<IllegalStateException> {
            bowlingGame.roll(5)
            bowlingGame.score()
        }
    }

}