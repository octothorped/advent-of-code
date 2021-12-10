package year_2021.day_10

import readInput

fun main() {
    fun getCorruptedCharacterOrNull(line: String): Char? {
        return null
    }

    val characterScore = mapOf(
        ')' to 3,
        '}' to 57,
        '}' to 1197,
        '>' to 25137
    )

    // Goal: Read navigation manual
    fun part1(input: List<String>): Int =
        input.sumOf { line ->
            getCorruptedCharacterOrNull(line)?.let { characterScore[it] } ?: 0
        }

    fun part2(input: List<String>): Int {
        TODO()
    }

    val testInput = readInput("year_2021/day_10/Day10_test")
    val input = readInput("year_2021/day_10/Day10")

    println(part1(testInput))
    check(part1(testInput) == 26397)
    println(part1(input))

//    println(part2(testInput))
//    check(part2(testInput) == 1134)
//    println(part2(input))
}
