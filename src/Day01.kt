fun main() {
    fun mapCalories(input: List<String>): Map<Int, Int> {
        val calorieMap = mutableMapOf<Int, Int>()
        var elfIndex = 0
        for (line in input) {
            if (line.isBlank()) elfIndex++
            else calorieMap[elfIndex] = (calorieMap[elfIndex] ?: 0) + line.toInt()
        }
        return calorieMap
    }

    fun part1(input: List<String>): Int = mapCalories(input).maxOf { it.value }

    fun part2(input: List<String>): Int = mapCalories(input)
        .map { it.value }
        .sortedByDescending { it }
        .take(3)
        .sumOf { it }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 24000)
    check(part2(testInput) == 45000)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
