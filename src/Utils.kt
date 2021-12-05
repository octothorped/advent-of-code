import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt").readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)

fun String.parseAsBinary(inverse: Boolean = false): Int {
    var twosValue = 1
    var result = 0
    forEachIndexed { index, _ ->
        if (this[length - index - 1] == if (inverse) '0' else '1') {
            result += twosValue
        }
        twosValue *= 2
    }
    return result
}

fun <T>List<List<T>>.print() =
    forEach {
        println(it.joinToString(" "))
    }
