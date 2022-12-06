fun main() {
    fun String.hasUniqueChars(): Boolean {
        for (i in indices)
            if (substring(i + 1).contains(this[i])) return false
        return true
    }

    fun String.indexAfterFirstNUnique(n: Int): Int {
        for (i in indices)
            if (substring(i, i + n).hasUniqueChars()) return i + n
        error("No unique sequence.")
    }

    fun part1(input: String): Int {
        return input.indexAfterFirstNUnique(4)
    }

    fun part2(input: String): Int {
        return input.indexAfterFirstNUnique(14)
    }

    // test if implementation meets criteria from the description, like:
    check(part1("mjqjpqmgbljsphdztnvjfqwrcgsmlb") == 7)
    check(part1("bvwbjplbgvbhsrlpgdmjqwftvncz") == 5)
    check(part1("nppdvjthqldpwncqszvftbrmjlhg") == 6)
    check(part1("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg") == 10)
    check(part1("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw") == 11)

    check(part2("mjqjpqmgbljsphdztnvjfqwrcgsmlb") == 19)
    check(part2("bvwbjplbgvbhsrlpgdmjqwftvncz") == 23)
    check(part2("nppdvjthqldpwncqszvftbrmjlhg") == 23)
    check(part2("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg") == 29)
    check(part2("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw") == 26)

    val input = readInput("Day06")
    println(part1(input.first()))
    println(part2(input.first()))
}