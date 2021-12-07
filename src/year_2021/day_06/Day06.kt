package year_2021.day_06

import readInput

fun main() {
    fun simulateLaternFish(initialFishTimers: List<Int>, numOfDays: Int): Long {
        var fishTimers = initialFishTimers.groupBy { it }
            .mapValues { it.value.size.toLong() }

        for (day in 1..numOfDays) {
            val newFishAges = mutableMapOf<Int, Long>()
            fishTimers.forEach {
                when(it.key) {
                    0 -> {
                        newFishAges[8] = newFishAges[8]?.plus(it.value) ?: it.value
                        newFishAges[6] = newFishAges[6]?.plus(it.value) ?: it.value
                    }
                    else -> {
                        newFishAges[it.key - 1] = newFishAges[it.key - 1]?.plus(it.value) ?: it.value
                    }
                }
            }
            fishTimers = newFishAges
        }

        return fishTimers.map { it.value }.sum()
    }

    // Goal: Count lantern fish
    fun part1(input: List<String>): Long =
        input.first()
            .split(",")
            .map { it.toInt() }
            .let { simulateLaternFish(it, 80) }

    fun part2(input: List<String>): Long =
        input.first()
            .split(",")
            .map { it.toInt() }
            .let { simulateLaternFish(it, 256) }

    val testInput = readInput("year_2021/day_06/Day06_test")
    val input = readInput("year_2021/day_06/Day06")

    println(part1(testInput))
    check(part1(testInput) == 5934L)
    println(part1(input))

    println(part2(testInput))
    check(part2(testInput) == 26984457539L)
    println(part2(input))
}
