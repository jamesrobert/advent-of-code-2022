fun main() {
    fun itemWeight(commonItem: Char) = if (commonItem <= 'Z') commonItem - 'A' + 27
    else commonItem - 'a' + 1

    fun List<List<String>>.sumWeightCommonItems() = sumOf {strings ->
        strings.map { it.toSet() }
            .reduce { acc, chars -> acc.intersect(chars) }
            .sumOf { itemWeight(it) }
    }

    fun part1(input: List<String>): Int = input.map {
        listOf(it.substring(0, it.length / 2), it.substring(it.length / 2))
    }.sumWeightCommonItems()

    fun part2(input: List<String>): Int = input
        .chunked(3)
        .sumWeightCommonItems()

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 157)
    check(part2(testInput) == 70)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}