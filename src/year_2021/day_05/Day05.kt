package year_2021.day_05

import print
import readInput

fun main() {
    data class Point(val x: Int, val y: Int)
    data class Line(val startPoint: Point, val endPoint: Point)
    class ThermalVentsMap(vents: List<String>, allowDiagonal: Boolean = false) {
        val ventsMap: List<List<Int>> = generateMap(vents, allowDiagonal)

        fun countOverlappingVents(): Int =
            ventsMap.flatten().filter { it > 1 }.size

        private fun generateMap(vents: List<String>, allowDiagonal: Boolean): List<List<Int>> =
            mutableListOf<MutableList<Int>>()
                .also { map ->
                    val lines = vents.map { it.buildLine() }
                    val maxX =
                        lines.maxWithOrNull(Comparator.comparingInt { it.startPoint.x })!!.startPoint.x
                            .coerceAtLeast(
                                lines.maxWithOrNull(Comparator.comparingInt { it.endPoint.x })!!.endPoint.x
                            )

                    val maxY =
                        lines.maxWithOrNull(Comparator.comparingInt { it.startPoint.y })!!.startPoint.y
                            .coerceAtLeast(
                                lines.maxWithOrNull(Comparator.comparingInt { it.endPoint.y })!!.endPoint.y
                            )

                    for (i in 0..maxX) {
                        map.add(MutableList(maxY + 1) { 0 } )
                    }

                    lines.forEach { map.addLine(it, allowDiagonal) }
                }

        private fun String.buildLine(): Line =
            split(" -> ").let { Line(it[0].toPoint(), it[1].toPoint()) }

        private fun String.toPoint(): Point =
            split(",").let { Point(it[0].toInt(), it[1].toInt()) }

        private fun MutableList<MutableList<Int>>.addLine(line: Line, allowDiagonal: Boolean) {
            when {
                line.startPoint.x == line.endPoint.x -> {
                    val start = minOf(line.startPoint.y, line.endPoint.y)
                    val end = maxOf(line.startPoint.y, line.endPoint.y)
                    for (i in start..end) {
                        this[i][line.startPoint.x]++
                    }
                }
                line.startPoint.y == line.endPoint.y -> {
                    val start = minOf(line.startPoint.x, line.endPoint.x)
                    val end = maxOf(line.startPoint.x, line.endPoint.x)
                    for (i in start..end) {
                        this[line.startPoint.y][i]++
                    }
                }
                allowDiagonal -> {
                    val leftPoint = line.startPoint.takeIf { it.x < line.endPoint.x } ?: line.endPoint
                    val rightPoint = line.startPoint.takeIf { it.x > line.endPoint.x } ?: line.endPoint
                    val increaseDepth = leftPoint.y < rightPoint.y
                    var startY = leftPoint.y
                    for (i in leftPoint.x..rightPoint.x) {
                        this[startY][i]++
                        startY += if (increaseDepth) 1 else -1
                    }
                }
            }
        }
    }

    // Goal: Avoid thermal vents
    fun part1(input: List<String>): Int {
        return ThermalVentsMap(input).countOverlappingVents()
    }

    fun part2(input: List<String>): Int {
        return ThermalVentsMap(input, allowDiagonal = true).countOverlappingVents()
    }

    val testInput = readInput("year_2021/day_05/Day05_test")
    val input = readInput("year_2021/day_05/Day05")

    ThermalVentsMap(testInput).ventsMap.print()
    println(part1(testInput))
    check(part1(testInput) == 5)
    println(part1(input))

    ThermalVentsMap(testInput, allowDiagonal = true).ventsMap.print()
    check(part2(testInput) == 12)
    println(part2(input))
}
