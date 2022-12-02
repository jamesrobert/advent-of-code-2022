enum class Outcome(val value: String) {
    Lose("X"),
    Draw("Y"),
    Win("Z");

    companion object {
        fun parse(input: String): Outcome = when (input) {
            "X" -> Lose
            "Y" -> Draw
            "Z" -> Win
            else -> error("incorrect outcome")
        }
    }
}

enum class RockPaperScissors(val value: Int) {
    Rock(1), Paper(2), Scissors(3);

    fun fight(opponent: RockPaperScissors): Int = when {
        this == opponent -> 3
        (this == Rock && opponent == Scissors) ||
                (this == Paper && opponent == Rock) ||
                (this == Scissors && opponent == Paper) -> 6

        else -> 0
    }

    companion object {
        fun parse(input: String): RockPaperScissors = when (input) {
            "X", "A" -> Rock
            "Y", "B" -> Paper
            "Z", "C" -> Scissors
            else -> error("invalid choice")
        }

        fun parse(outcome: Outcome, opponent: RockPaperScissors): RockPaperScissors {
            return when (opponent to outcome) {
                Rock to Outcome.Win -> Paper
                Rock to Outcome.Lose -> Scissors

                Paper to Outcome.Win -> Scissors
                Paper to Outcome.Lose -> Rock

                Scissors to Outcome.Win -> Rock
                Scissors to Outcome.Lose -> Paper

                else -> opponent
            }
        }
    }
}

fun main() {
    fun List<String>.gather(): List<List<String>> = map { it.split(" ") }

    fun List<List<String>>.calculateRows(
        makeMyChoice: (opponent: RockPaperScissors, input: String) -> RockPaperScissors
    ): Int {
        val mapped = map {
            val (opp, me) = it
            val opponentChoice = RockPaperScissors.parse(opp)
            val myChoice = makeMyChoice(opponentChoice, me)
            val fightOutcome = myChoice.fight(opponentChoice)
            myChoice.value + fightOutcome
        }

        return mapped.sumOf { it }
    }

    fun part1(input: List<String>): Int = input.gather().calculateRows { _, it -> RockPaperScissors.parse(it) }

    fun part2(input: List<String>): Int =
        input.gather().calculateRows { oc, it -> RockPaperScissors.parse(Outcome.parse(it), oc) }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 12)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}