package year_2021.day_07

import readInput
import kotlin.math.abs
import kotlin.math.min

// This solution is suboptimal as it calculates every possible scenario
fun main() {
    // Goal: Help align crab submarines
    fun part1(input: List<String>): Int {
        val crabPositions = input.first()
            .split(",")
            .map { it.toInt() }
        // These will never return null
        val maxPosition = crabPositions.maxOrNull() ?: 0
        val minPosition = crabPositions.minOrNull() ?: 0

        var minFuel = Int.MAX_VALUE
        for (position in minPosition..maxPosition) {
            minFuel = min(crabPositions.sumOf { abs(it - position) }, minFuel)
        }
        return minFuel
    }

    fun positionChangeFuelCost(currentPosition: Int, newPosition: Int): Int =
        (1..abs(currentPosition - newPosition)).sum()

    fun part2(input: List<String>): Int {
        val crabPositions = input.first()
            .split(",")
            .map { it.toInt() }
        // These will never return null
        val maxPosition = crabPositions.maxOrNull() ?: 0
        val minPosition = crabPositions.minOrNull() ?: 0

        var minFuel = Int.MAX_VALUE
        for (position in minPosition..maxPosition) {
            minFuel = min(crabPositions.sumOf { positionChangeFuelCost(it, position) }, minFuel)
        }
        return minFuel
    }

    val testInput = readInput("year_2021/day_07/Day07_test")
    val input = readInput("year_2021/day_07/Day07")

    println(part1(testInput))
    check(part1(testInput) == 37)
    println(part1(input))

    println(part2(testInput))
    check(part2(testInput) == 168)
    println(part2(input))
}
