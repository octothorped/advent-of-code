package year_2021.day_09

import readInput
import java.util.*

fun main() {
    fun List<List<Int>>.checkAdjacentHeights(row: Int, column: Int): Boolean {
        if (row != 0 && this[row - 1][column] <= this[row][column]) {
            return false
        }
        if (row != this.size - 1 && this[row + 1][column] <= this[row][column]) {
            return false
        }
        if (column != 0 && this[row][column - 1] <= this[row][column]) {
            return false
        }
        if (column != this.first().size - 1 && this[row][column + 1] <= this[row][column]) {
            return false
        }
        return true
    }

    // Goal: Find risk levels of cave system
    fun part1(input: List<String>): Int {
        var riskLevel = 0
        input.map { line -> line.map { it.toString().toInt() } }
            .let {
                it.forEachIndexed { rowIndex, columns ->
                    columns.forEachIndexed { columnIndex, height ->
                        if (it.checkAdjacentHeights(rowIndex, columnIndex)) {
                            riskLevel += 1 + height
                        }
                    }
                }
            }
        return riskLevel
    }

    data class Position(val row: Int, val column: Int)

    fun List<List<Int>>.getNeighbors(position: Position, visited: List<List<Boolean>>): List<Position> {
        val positions = mutableListOf<Position>()
        if (position.row != 0 &&
            !visited[position.row - 1][position.column] &&
            this[position.row - 1][position.column] != 9) {
            positions.add(position.copy(row = position.row - 1))
        }
        if (position.row != this.size - 1 &&
            !visited[position.row + 1][position.column] &&
            this[position.row + 1][position.column] != 9) {
            positions.add(position.copy(row = position.row + 1))
        }
        if (position.column != 0 &&
            !visited[position.row][position.column - 1] &&
            this[position.row][position.column - 1] != 9) {
            positions.add(position.copy(column = position.column - 1))
        }
        if (position.column != this.first().size - 1 &&
            !visited[position.row][position.column + 1] &&
            this[position.row][position.column + 1] != 9) {
            positions.add(position.copy(column = position.column + 1))
        }
        return positions
    }

    fun part2(input: List<String>): Int {
        val basins = mutableListOf<Int>()
        input.map { line -> line.map { it.toString().toInt() } }
            .let { heights ->
                val visited = MutableList(heights.size) { MutableList(heights.first().size) { false } }
                heights.forEachIndexed { rowIndex, columns ->
                    columns.forEachIndexed { columnIndex, _ ->
                        if (heights.checkAdjacentHeights(rowIndex, columnIndex)) {
                            val queue = mutableListOf(Position(rowIndex, columnIndex))
                            var basinSize = 0
                            while (queue.isNotEmpty()) {
                                val position = queue.removeAt(0)
                                if (!visited[position.row][position.column]) {
                                    visited[position.row][position.column] = true
                                    basinSize++
                                    queue.addAll(heights.getNeighbors(position, visited))
                                }
                            }
                            basins.add(basinSize)
                        }
                    }
                }
            }
        return basins.sorted().reversed().let { it[0] * it[1] * it[2] }
    }

    val testInput = readInput("year_2021/day_09/Day09_test")
    val input = readInput("year_2021/day_09/Day09")

    println(part1(testInput))
    check(part1(testInput) == 15)
    println(part1(input))

    println(part2(testInput))
    check(part2(testInput) == 1134)
    println(part2(input))
}
