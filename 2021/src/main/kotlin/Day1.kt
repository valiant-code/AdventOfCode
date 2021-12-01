private var currentPart = 0;

private fun main() {
    //pt 1 - 307
    var runPart1 = true
//  runPart1 = false
    //pt 2 - 165
    var runPart2 = true
    runPart2 = true
    if (runPart1) {
        currentPart = 1;
        TimeUtil.startClock(1)
        partOne()
        TimeUtil.time()
    }
    if (runPart2) {
        currentPart = 2;
        TimeUtil.startClock(2)
        partTwo()
        TimeUtil.time()
    }
}

private fun partOne() {
//    val input = InputUtil.readFileAsStringList("day1/input.txt", ", ").map { str -> Direction(str[0], str.substring(1).toInt()) }


//    println("final position ${position} = ${position.first.absoluteValue + position.second.absoluteValue} blocks away")
}

private fun partTwo() {

}