import java.util.Deque

fun main() {
    class Command(val name: String, val param: String = "")

    fun String.command(): Command {
        val parts = split(" ")
        return when (parts[1]) {
            "cd" -> Command(name = "cd", param = parts[2])
            "ls" -> Command(name = "ls")
            else -> error("invalid command")
        }
    }

    fun Map<String, Int>.sizes(directories: ArrayDeque<String>): String {
        return filter {
            directories.contains(it.key)
        }.toList().joinToString { "${it.first}:${it.second}" }
    }

    fun ArrayDeque<String>.hierarchy() = joinToString(">")

    fun calculateFolderStructure(input: List<String>): MutableMap<String, Int> {
        val map = mutableMapOf<String, Int>()
        val currentDir = ArrayDeque<String>()
        for (line in input) {
            val space = (1..currentDir.size).joinToString(separator = " ") { " " }
            when {
                line.startsWith("$ ") ->
                    line.command().let { command ->
                        if (command.name == "cd") {
                            if (command.param == "..") currentDir.removeFirst()
                            else currentDir.addFirst("${currentDir.hierarchy()}>${command.param}")
                            println("${space}cd: ${currentDir.first()}")
                        }
                        if (command.name == "ls") println("${space}ls: ${currentDir.first()}")
                    }

                line.startsWith("dir ") -> println("${space}dir: $line")

                line.split(" ").first().toIntOrNull() != null ->
                    currentDir.forEach {
                        map[it] = map.getOrDefault(it, 0) + line.split(" ").first().toInt()
                    }.also {
                        println("${space}file: $line - ${map.sizes(currentDir)}")
                    }
            }
        }
        println(map)
        return map
    }

    fun part1(input: List<String>): Int =
        calculateFolderStructure(input)
            .filter { it.value <= 100_000 }
            .toList()
            .sumOf { it.second }

    fun part2(input: List<String>): Int {
        val folders = calculateFolderStructure(input)
        val spaceLeft = 70000000 - (folders[">/"] ?: 0)
        val spaceRequired = 30000000 - spaceLeft

        return folders.toList()
            .sortedBy { it.second }
            .first {
                it.second >= spaceRequired
            }.let {
                println(it)
                it.second
            }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    println(testInput)
    check(part1(testInput) == 95437)
    check(part2(testInput) == 24933642)

    val input = readInput("Day07")
    println(part1(input)) // first guess 1097622, second 1449447
    println(part2(input))
}
