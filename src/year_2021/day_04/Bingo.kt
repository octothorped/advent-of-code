package year_2021.day_04

import print

class Bingo(setup: List<String>) {
    private val balls = setup.first().split(",")
    private val boards: List<BingoBoard> = loadGame(setup)

    fun printBoards() {
        boards.forEach {
            it.print()
            println()
        }
    }

    fun findWinningBoard(): Int =
        balls.forEach { ball ->
            boards.forEach { board ->
                board.attemptBingoOrNull(ball)?.let { return it }
            }
        }.let { error("All balls played") }

    fun findLastWinningBoard(): Int {
        var remainingBoards = boards
        balls.forEach { ball ->
            var lastScore = 0
            val boardsNotWon = mutableListOf<BingoBoard>()
            remainingBoards.forEach { board ->
                board.attemptBingoOrNull(ball)
                    ?.let { lastScore = it }
                    ?: boardsNotWon.add(board)
            }
            if (boardsNotWon.isEmpty()) return lastScore
            remainingBoards = boardsNotWon
        }.let { error("All balls played") }
    }

    private fun loadGame(setup: List<String>): List<BingoBoard> =
        mutableListOf<BingoBoard>()
            .also { boards ->
                var rows = mutableListOf<List<BingoBoardSquare>>()
                setup.drop(2).forEach {
                    if (it.isBlank()) {
                        boards.add(BingoBoard(rows))
                        rows = mutableListOf()
                    } else {
                        rows.add(getBoardRow(it))
                    }
                }
                boards.add(BingoBoard(rows))
            }


    private fun getBoardRow(line: String): List<BingoBoardSquare> =
        line.split(" ")
            .filter { it.isNotBlank() }
            .map { BingoBoardSquare(it) }
}

class BingoBoard(private val squares: List<List<BingoBoardSquare>>) {
    // returns score if bingo is found else null
    fun attemptBingoOrNull(value: String): Int? {
        squares.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { columnIndex, square ->
                if (square.value == value) {
                    square.mark()
                    if (isBingo(rowIndex, columnIndex)) { return score(value) }
                }
            }
        }
        return null
    }

    fun print() = squares.print()

    private fun score(lastValue: String): Int {
        var unmarkedSum = 0

        squares.forEach { row ->
            row.forEach { square ->
                square.takeIf { !it.isMarked }?.let { unmarkedSum += it.value.toInt() }
            }
        }

        return unmarkedSum * lastValue.toInt()
    }

    private fun isBingo(horizontal: Int, vertical: Int): Boolean {
        return squares[horizontal].find { !it.isMarked } == null ||
                squares.find { !it[vertical].isMarked } == null
    }
}

class BingoBoardSquare(val value: String) {
    var isMarked = false
    fun mark() { isMarked = true }

    override fun toString(): String = value.padStart(2, ' ')
}