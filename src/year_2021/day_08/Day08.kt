package year_2021.day_08

import readInput
import java.util.*

fun main() {
    data class SegmentDisplayMap(val map: Map<Char, Int>)
    data class SevenSegmentDisplay(val number: Set<Char>) {
        val segmentsOn: Int = number.size

        fun decodeNumberAsChar(decoder: SegmentDisplayMap): Char =
            number
                .map { decoder.map[it] ?: error("no associated position found for char $it") }
                .toSortedSet()
                .let { numberMap[it] ?: error("no associated mapping found for set $it") }

        fun findDifferencesWith(display: SevenSegmentDisplay): Set<Char> =
            number.union(display.number) - number.intersect(display.number)

        fun findSimilaritiesWith(display: SevenSegmentDisplay): Set<Char> =
            number.intersect(display.number)

        private val numberMap: Map<SortedSet<Int>, Char> =
            mapOf(
                sortedSetOf(0, 1, 2, 4, 5, 6) to '0',
                sortedSetOf(2, 5) to '1',
                sortedSetOf(0, 2, 3, 4, 6) to '2',
                sortedSetOf(0, 2, 3, 5, 6) to '3',
                sortedSetOf(1, 2, 3, 5) to '4',
                sortedSetOf(0, 1, 3, 5, 6) to '5',
                sortedSetOf(0, 1, 3, 4, 5, 6) to '6',
                sortedSetOf(0, 2, 5) to '7',
                sortedSetOf(0, 1, 2, 3, 4, 5, 6) to '8',
                sortedSetOf(0, 1, 2, 3, 5, 6) to '9',
            )
    }

    data class DataPoint(val numbers: List<SevenSegmentDisplay>, val output: List<SevenSegmentDisplay>) {
        fun getNumbersBySegmentCount(count: Int): List<SevenSegmentDisplay> =
            numbers.filter { it.segmentsOn == count }

        fun getOutputBySegmentCounts(counts: List<Int>): List<SevenSegmentDisplay> =
            output.filter { it.segmentsOn in counts }
    }

    fun decodeMultiDigitNumber(displays: List<SevenSegmentDisplay>, decoder: SegmentDisplayMap): Int =
        displays.map { it.decodeNumberAsChar(decoder) }
            .joinToString("")
            .toInt()

    fun List<String>.toDataPoints() =
        map { it.split("|") }
            .map {
                DataPoint(
                    it[0].trim().split(" ").map { SevenSegmentDisplay(it.toSet()) },
                    it[1].trim().split(" ").map { SevenSegmentDisplay(it.toSet()) }
                )
            }

    // Goal: Help decode seven segment display
    fun part1(input: List<String>): Int {
        return input.toDataPoints()
            .sumOf { dataPoint ->
                dataPoint.getOutputBySegmentCounts(listOf(2, 3, 4, 7)).size
            }
    }

    fun decodeSegmentMap(dataPoint: DataPoint): SegmentDisplayMap {
        val segmentMap = mutableMapOf<Char, Int>()
        // find difference of 3 count and 2 count to get position 0
        val position0 = dataPoint
            .getNumbersBySegmentCount(3).first()
            .findDifferencesWith(dataPoint.getNumbersBySegmentCount(2).first())
            .first()
        // store positions 2 and 5
        val positions2And5 = dataPoint
            .getNumbersBySegmentCount(3).first()
            .findSimilaritiesWith(dataPoint.getNumbersBySegmentCount(2).first())
        // find difference of 4 count and 2 count to get positions 1 and 3
        val position1And3 = dataPoint
            .getNumbersBySegmentCount(4).first()
            .findDifferencesWith(dataPoint.getNumbersBySegmentCount(2).first())
        // determine the number 5 by seeing which 5 count contains the same position 1 and 3 from part 2
        val number5Display = dataPoint
            .getNumbersBySegmentCount(5)
            .first { it.number.containsAll(position1And3) }
        // exclude positions 0, 1, 3, 5 (everything from part 1 and 2) from the number 5 to get position 6
        val position6 = number5Display.number.asSequence()
            .minus(position1And3).minus(positions2And5).minus(position0)
            .first()
        // exclude positions 0, 1, 3, 6 (0 from part 1, 1 and 3 from part 2, 6 from part 4) from the number 5 to get position 5
        val position5 = number5Display.number.asSequence()
            .minus(position1And3).minus(position0).minus(position6)
            .first()
        // exclude position 5 from part 1 to get position 2
        val position2 = positions2And5.first { it != position5 }
        // At this point we know 0, 2, 5, 6
        // find difference between remaining 5 counts to get position 4 and 5
        val remaining5Counts = dataPoint.getNumbersBySegmentCount(5) - number5Display
        val position4And5 = remaining5Counts[0].findDifferencesWith(remaining5Counts[1])
        // subtract position 5 from previous to get position 4
        val position4 = position4And5.first { it != position5 }
        // filter 6 counts that have positions 1 and 3 found in part 2 to get number 0
        val number0Display = dataPoint
            .getNumbersBySegmentCount(6)
            .first { !it.number.containsAll(position1And3) }
        // exclude all known positions from 0 number to get position 1
        val position1 = number0Display.number.asSequence()
            .minus(position0).minus(positions2And5).minus(position4).minus(position6)
            .first()
        // exclude position 1 from part 2 to get position 3
        val position3 = position1And3.first { it != position1 }
        segmentMap[position0] = 0
        segmentMap[position1] = 1
        segmentMap[position2] = 2
        segmentMap[position3] = 3
        segmentMap[position4] = 4
        segmentMap[position5] = 5
        segmentMap[position6] = 6
        // We should have full map by this point
        return SegmentDisplayMap(segmentMap)
    }

    fun part2(input: List<String>): Int {
        return input.toDataPoints()
            .associateWith { decodeSegmentMap(it) }
            .map { (dataPoint, decoder) ->
                decodeMultiDigitNumber(dataPoint.output, decoder)
            }.sum()
    }

    val testInput = readInput("year_2021/day_08/Day08_test")
    val input = readInput("year_2021/day_08/Day08")

    println(part1(testInput))
    check(part1(testInput) == 26)
    println(part1(input))

    println(part2(testInput))
    check(part2(testInput) == 61229)
    println(part2(input))
}
