package year_2021.day_01

import readInput

fun main() {
    // Goal: count increasing values
    fun part1(input: List<String>): Int {
        var increasingCount = 0
        input.forEachIndexed { index, s ->
            if (index > 0) {
                s.toInt()
                    .takeIf { it > input[index - 1].toInt() }
                    ?.also { increasingCount++ }
            }
        }
        return increasingCount
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("year_2021/day_01/Day01_test")
    check(part1(testInput) == 7)

    val input = readInput("year_2021/day_01/Day01")
    println(part1(input))
    println(part2(input))
}
