typealias Section = Pair<Int, Int>

fun main() {
    fun parseSectionRange(item: String): Section =
        item.split('-')
            .map { it.toInt() }
            .let { (first, second) -> Pair(first, second) }

    fun calculateOverlap(input: List<String>, compare: (Section, Section) -> Int?): Int =
        input.map {
            it.split(',')
                .let { (first, second) ->
                    val left = parseSectionRange(first)
                    val right = parseSectionRange(second)
                    compare(left, right) ?: compare(right, left) ?: 0
                }
        }.sumOf { it }

    fun part1(input: List<String>): Int {
        fun compare(left: Section, right: Section) =
            if (left.first <= right.first && left.second >= right.second) 1 else null

        return calculateOverlap(input, ::compare)
    }

    fun part2(input: List<String>): Int {
        fun compare(left: Section, right: Section): Int? =
            if ((right.first in left.first..left.second) ||
                (right.second in left.first..left.second)
            ) 1 else null

        return calculateOverlap(input, ::compare)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}