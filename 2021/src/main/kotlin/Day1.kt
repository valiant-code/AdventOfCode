var currentPart = 0;

fun main() {
    //pt 1 -
    var runPart1 = true
//  runPart1 = false
    //pt 2 -
    var runPart2 = true
    runPart2 = false
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

fun partOne() {
    var j: String = "null";
    var res = InputUtil.readFileAsIntList("day1/input.txt");
    j = res[0].toString();

    println("Hello, World! and ${res.get(0)}")
}

fun partTwo() {}