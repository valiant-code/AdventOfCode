private fun main() {
    //pt 1 - 1989014
    //pt 2 - 2006917119
    TimeUtil.startClock(1, ::partOne)
    TimeUtil.startClock(2, ::partTwo)
}

private fun partOne() {
    //forward X increases the horizontal position by X units.
    //down X increases the depth by X units.
    //up X decreases the depth by X units.
    var depth = 0;
    var horizontal = 0;
    val input = InputUtil.readFileAsStringList("day2/input.txt")
        .map { Pair(it.substringBefore(" "), it.substringAfter(" ").toInt()) }
        .forEach {
            when (it.first) {
                "forward" -> horizontal += it.second;
                "down" -> depth += it.second;
                "up" -> depth -= it.second;
            }
        }

    println("pt 1 answer: ${horizontal * depth}")
}

private fun partTwo() {
//    down X increases your aim by X units.
//    up X decreases your aim by X units.
//    forward X does two things:
//      It increases your horizontal position by X units.
//      It increases your depth by your aim multiplied by X.
    var depth = 0;
    var horizontal = 0;
    var aim = 0;

    val input = InputUtil.readFileAsStringList("day2/input.txt")
        .map { Pair(it.substringBefore(" "), it.substringAfter(" ").toInt()) }
        .forEach {
            when (it.first) {
                "forward" -> { horizontal += it.second; depth += aim * it.second}
                "down" -> aim += it.second;
                "up" -> aim -= it.second;
            }
        }

    println("pt 2 answer: ${horizontal * depth}")
}