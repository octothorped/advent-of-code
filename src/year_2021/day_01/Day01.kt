package year_2021.day_01

import readInput

fun main() {
    // Goal: count increasing values
    fun part1(input: List<String>): Int {
        var increasingCount = 0
        input
            .map { it.toInt() }
            .also { inputAsInt ->
                inputAsInt.forEachIndexed { index, value ->
                    index.takeIf { it > 0 && value > inputAsInt[index - 1] }
                        ?.also { increasingCount++ }
                }
            }
        return increasingCount
    }

    fun part2(input: List<String>): Int {
        var increasingCount = 0
        input
            .map { it.toInt() }
            .let { inputAsInt ->
                inputAsInt.forEachIndexed { index, value ->
                    index.takeIf { index > 2 }
                        ?.let {
                            (value + inputAsInt[index - 1] + inputAsInt[index - 2]) to
                                    (inputAsInt[index - 1] + inputAsInt[index - 2] + inputAsInt[index - 3])
                        }
                        ?.takeIf { (currentValue, previousValue) ->
                            currentValue > previousValue
                        }
                        ?.also { increasingCount++ }
                }
            }
        return increasingCount
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("year_2021/day_01/Day01_test")
    check(part1(testInput) == 7)
    check(part2(testInput) == 5)

    val input = readInput("year_2021/day_01/Day01")
    println(part1(input))
    println(part2(input))
}
