import kotlin.math.abs

private fun main() {
    //pt 1 - 374994
    //pt 2 - 1686252324092
    TimeUtil.startClock(1, ::partOne)
    TimeUtil.startClock(2, ::partTwo)
}

private fun partOne(pt: Int = 1) {
    //at what position can we make all the numbers meet up, with the least amount of moves
    val input = InputUtil.readFileAsIntList("day7/input.txt", ",").sorted()

    var minMoves = Int.MAX_VALUE;
    positionLoop@for (position in input.minOrNull()!!..input.maxOrNull()!!) {
        var moves = 0;
        for (num in input) {
            moves += abs(position - num)
            if (moves > minMoves) break@positionLoop
        }
        minMoves = moves;
    }


    println("pt $pt answer: ${minMoves colorize ConsoleColor.CYAN_BOLD}")
}

fun Long.nthPartialSum(): Long {
    //Triangle Numbers - https://en.wikipedia.org/wiki/1_%2B_2_%2B_3_%2B_4_%2B_%E2%8B%AF
    return (this * (this + 1)) / 2
}

private fun partTwo(pt: Int = 2) {
    //but now the energy it takes for 1 crab to move position by X is  1+2+3..+X
    val input = InputUtil.readFileAsLongList("day7/input.txt", ",").sorted()

    var minMoves = Long.MAX_VALUE;
    inputLoop@for (position in input.minOrNull()!!..input.maxOrNull()!!) {
        var moves = 0L;
        for (num in input) {
            moves += (abs(position - num)).nthPartialSum()
            if (moves > minMoves) break@inputLoop
        }
        minMoves = moves;
    }


    println("pt $pt answer: ${minMoves colorize ConsoleColor.CYAN_BOLD}")
}


