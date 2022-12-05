fun main() {
    fun loadCrates(crateInput: List<String>): MutableMap<Int, String> {
        val crates = mutableMapOf<Int, String>()
        for (line in crateInput) {
            for ((index, chunk) in line.chunked(4).withIndex()) {
                val stackNumber = index + 1
                if (crates[stackNumber] == null) crates[stackNumber] = ""
                if (chunk.isBlank() || chunk[1].isDigit()) continue
                crates[stackNumber] = "${chunk[1]}${crates[stackNumber]}"
            }
        }
        return crates
    }

    fun getMoves(moveInput: List<String>) = moveInput.map {
        Triple(
            it.substring(5, it.indexOf(" from ")).toInt(),
            it.substring(it.indexOf(" from ") + 6, it.indexOf(" to ")).toInt(),
            it.substring(it.indexOf(" to ") + 4).toInt()
        )
    }

    fun move(crates: MutableMap<Int, String>, moveInput: List<String>): Map<Int, String> {
        for ((q, f, t) in getMoves(moveInput)) {
            var qq = q
            while (qq > 0) {
                crates[t] = crates[t] + crates[f]!!.last()
                crates[f] = crates[f]!!.dropLast(1)
                qq--
            }
        }
        return crates
    }

    fun moveMultiple(crates: MutableMap<Int, String>, moveInput: List<String>): Map<Int, String> {
        for ((q, f, t) in getMoves(moveInput)) {
            crates[t] = crates[t] + crates[f]!!.substring(crates[f]!!.length - q)
            crates[f] = crates[f]!!.dropLast(q)
        }
        return crates
    }

    fun moveCrates(input: List<String>, move: (MutableMap<Int, String>, List<String>) -> Map<Int, String>): String {
        val crateInput = input.takeWhile { it != "" }
        val moveInput = input.takeLastWhile { it != "" }
        val crates = loadCrates(crateInput)

        return move(crates, moveInput).map { it.value.last() }.joinToString(separator = "")
    }

    fun part1(input: List<String>): String = moveCrates(input, ::move)

    fun part2(input: List<String>): String = moveCrates(input, ::moveMultiple)

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == "CMZ")
    check(part2(testInput) == "MCD")

    val input = readInput("Day05")
    println(part1(input)) // BSDMQFLSP
    println(part2(input)) // PGSQBFLDP
}