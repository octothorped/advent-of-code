package year_2021.day_04

import readInput

fun main() {
    // Goal: Playing Bingo!
    fun part1(input: List<String>): Int {
        return Bingo(input).findWinningBoard()
    }

    fun part2(input: List<String>): Int {
        return Bingo(input).findLastWinningBoard()
    }

    val testInput = readInput("year_2021/day_04/Day04_test")
    val input = readInput("year_2021/day_04/Day04")
    Bingo(testInput).printBoards()
    Bingo(input).printBoards()

    check(part1(testInput) == 4512)
    println(part1(input))

    check(part2(testInput) == 1924)
    println(part2(input))
}
