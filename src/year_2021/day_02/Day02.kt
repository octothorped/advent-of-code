package year_2021.day_02

import readInput

fun main() {
    // Goal: Calculate final position
    fun part1(input: List<String>): Int {
        var xAxis = 0
        var depth = 0
        input
            .map { move ->
                move.split(" ")
                    .let { it[0] to it[1].toInt() }
            }
            .forEach { (command, value) ->
                when (command) {
                    "forward" -> xAxis += value
                    "up" -> depth -= value
                    "down" -> depth += value
                }
            }
        return xAxis * depth
    }

    // Goal: Calculate final position with aim
    fun part2(input: List<String>): Int {
        var aim = 0
        var xAxis = 0
        var depth = 0

        input
            .map { move ->
                move.split(" ")
                    .let { it[0] to it[1].toInt() }
            }
            .forEach { (command, value) ->
                when (command) {
                    "forward" -> {
                        xAxis += value
                        depth += (aim * value)
                    }
                    "up" -> aim -= value
                    "down" -> aim += value
                }
            }

        return xAxis * depth
    }

    val testInput = readInput("year_2021/day_02/Day02_test")
    check(part1(testInput) == 150)
    check(part2(testInput) == 900)

    val input = readInput("year_2021/day_02/Day02")
    println(part1(input))
    println(part2(input))
}
