private fun main() {
    //pt 1 - 1374
    //pt 2 - 1418
    val runPart1 = true
    val runPart2 = true
    if (runPart1) {
        TimeUtil.startClock(1, ::partOne)
    }
    if (runPart2) {
        TimeUtil.startClock(2, ::partTwo)
    }
}

private fun partOne() {
    val input = InputUtil.readFileAsIntList("day1/input.txt");
    var count = 0;

    var prevVal = Int.MAX_VALUE;

    input.forEach { num ->
        if (prevVal < num) count++
        prevVal = num;
    }
    println("pt 1 count: $count")
}

private fun partTwo() {
    val input = InputUtil.readFileAsIntList("day1/input.txt");
    var count = 0;

    var prevList = input.subList(0, 3);
    for (i in 4..input.size) {
        val nextList = input.subList(i - 3, i);
        if (prevList.sum() < nextList.sum()) count++;
        prevList = nextList;
    }
    println("pt2 count: $count")
}