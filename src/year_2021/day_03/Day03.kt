package year_2021.day_03

import parseAsBinary
import readInput

fun main() {
    // Goal: Parse gamma and epsilon diagnostic results
    fun part1(input: List<String>): Int =
        MutableList(input.first().length) { 0 }
            .also { bitCount ->
                input.forEach {
                    it.forEachIndexed { index, c ->
                        bitCount[index] += c.toString().toInt()
                    }
                }
            }
            .map {
                if (it < input.size - it) 0 else 1
            }
            .joinToString("")
            .let { it.parseAsBinary() * it.parseAsBinary(inverse = true) }

    fun filterRatingsByBit(input: List<String>, mostCommonBit: Char, leastCommonBit: Char): String {
        var bitCounter = 0
        val filteredList = input.toMutableList()

        while (bitCounter < input.first().length) {
            val filteredListZeros = filteredList.filter { it[bitCounter] == '0' }.size
            if (filteredListZeros <= filteredList.size - filteredListZeros) {
                filteredList.removeIf { it[bitCounter] == mostCommonBit }
            } else {
                filteredList.removeIf { it[bitCounter] == leastCommonBit }
            }
            if (filteredList.size == 1) {
                break
            }
            bitCounter++
        }

        return filteredList.first()
    }

    // Goal: Parse oxygen and CO2 diagnostic results
    fun part2(input: List<String>): Int =
        filterRatingsByBit(input, '1', '0').parseAsBinary() *
            filterRatingsByBit(input, '0', '1').parseAsBinary()

    val testInput = readInput("year_2021/day_03/Day03_test")
    check(part1(testInput) == 198)
    check(part2(testInput) == 230)

    val input = readInput("year_2021/day_03/Day03")
    println(part1(input))
    println(part2(input))
}
