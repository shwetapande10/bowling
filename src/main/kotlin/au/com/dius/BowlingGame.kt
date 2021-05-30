package au.com.dius

class BowlingGame {
    private val noOfFrames = 10
    private val strikeScore = 10
    private val pinsPerRoll = mutableListOf<Int>()
    private val maxNumberOfRolls = 21

    fun roll(noOfPins: Int) {
        check(hasExceededMaxRolls()) { "Can only roll for $maxNumberOfRolls times" }
        pinsPerRoll.add(noOfPins)
    }

    fun score(): Int {
        var currentRoll = 0
        var score = 0
        repeat(noOfFrames) { currentFrame ->
            check(isCurrentRollPlayed(currentRoll)) { "Current Roll #$currentRoll not played for frame $currentFrame"}
            when {
                isStrike(currentRoll) -> {
                    check(hasNextTwoRolls(currentRoll)) { "Next two rolls after the strike is not available for $currentFrame" }
                    score += strikeScore + scoreForNextTwoRollsAfterStrike(currentRoll)
                    currentRoll++
                }
                isSpare(currentRoll) -> {
                    check(hasNextTwoRolls(currentRoll)) { "Next roll after the spare is not available for $currentFrame" }
                    score += addPinsForTwoRollsInFrame(currentRoll) +
                            scoreForNextRollAfterSpare(currentRoll)
                    currentRoll += 2
                }
                else -> {
                    score += addPinsForTwoRollsInFrame(currentRoll)
                    currentRoll += 2
                }
            }
        }
        return score
    }

    private fun hasExceededMaxRolls() = pinsPerRoll.size < maxNumberOfRolls

    private fun isCurrentRollPlayed(currentRoll: Int) = pinsPerRoll.size > currentRoll

    private fun hasNextTwoRolls(currentRoll: Int) = pinsPerRoll.size > currentRoll + 2

    private fun hasNextRoll(currentRoll: Int) = pinsPerRoll.size > currentRoll + 1

    private fun isSpare(currentRoll: Int) = addPinsForTwoRollsInFrame(currentRoll) == 10

    private fun isStrike(currentRoll: Int) = pinsPerRoll[currentRoll] == 10

    private fun scoreForNextTwoRollsAfterStrike(currentRoll: Int) = pinsPerRoll[currentRoll + 1] + pinsPerRoll[currentRoll + 2]

    private fun addPinsForTwoRollsInFrame(currentRoll: Int): Int {
        check(hasNextRoll(currentRoll)) { "Next roll not available for adding pins" }
        return pinsPerRoll[currentRoll] + pinsPerRoll[currentRoll + 1]
    }

    private fun scoreForNextRollAfterSpare(currentBallInFrame: Int) = pinsPerRoll[currentBallInFrame + 2]
}