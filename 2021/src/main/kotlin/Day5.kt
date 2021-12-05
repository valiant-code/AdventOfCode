private fun main() {
    //pt 1 - 7473
    //pt 2 - 24164
    TimeUtil.startClock(1, ::partOne)
    TimeUtil.startClock(2, ::partTwo)
}

data class Line(val start: Pair<Int, Int>, val end: Pair<Int, Int>) {
    val isVertical = start.first == end.first;
    val isHorizontal = start.second == end.second;
}

private fun Pair<Int, Int>.moveTowards(other: Pair<Int, Int>): Pair<Int, Int> {
    val x = when {
        this.first == other.first -> { this.first }
        this.first > other.first -> { this.first - 1 }
        else -> this.first + 1
    };
    val y = when {
        this.second == other.second -> { this.second }
        this.second > other.second -> { this.second - 1 }
        else -> this.second + 1
    };
    return x to y;
}

private fun partOne() {
    //0,9 -> 5,9
    //8,0 -> 0,8
    val input = InputUtil.readFileAsStringList("day5/input.txt");
    val lines = input.map {
        val vals = it.split(" -> ")
        Line(
            Pair(vals.get(0).split(",")[0].toInt(), vals.get(0).split(",")[1].toInt()),
            Pair(vals.get(1).split(",")[0].toInt(), vals.get(1).split(",")[1].toInt())
        )
    }.filter { it.isVertical || it.isHorizontal }

    val ventMap = HashMap<Pair<Int,Int>, Int>();

    //0,9 -> 5,9
    //3,4 -> 1,4
    lines.forEach {
        ventMap.put(it.start, ventMap.getOrDefault(it.start, 0) + 1)
        var lastInsert = it.start;
        while (lastInsert != it.end) {
            val nextInsert = lastInsert.moveTowards(it.end)
            ventMap.put(nextInsert, ventMap.getOrDefault(nextInsert, 0) + 1);
            lastInsert = nextInsert
        }

        val j = 0;
    }

    //At how many points do at least two lines overlap?
    println("pt 1 answer: ${(ventMap.values.filter { it > 1 }.count()).toString().colorize(ConsoleColor.CYAN_BOLD)}")
}

private fun partTwo() {
    //0,9 -> 5,9
    //8,0 -> 0,8
    val input = InputUtil.readFileAsStringList("day5/input.txt");
    val lines = input.map {
        val vals = it.split(" -> ")
        Line(
            Pair(vals.get(0).split(",")[0].toInt(), vals.get(0).split(",")[1].toInt()),
            Pair(vals.get(1).split(",")[0].toInt(), vals.get(1).split(",")[1].toInt())
        )
    }

    val ventMap = HashMap<Pair<Int,Int>, Int>();

    //0,9 -> 5,9
    //3,4 -> 1,4
    lines.forEach {
        ventMap.put(it.start, ventMap.getOrDefault(it.start, 0) + 1)
        var lastInsert = it.start;
        while (lastInsert != it.end) {
            val nextInsert = lastInsert.moveTowards(it.end)
            ventMap.put(nextInsert, ventMap.getOrDefault(nextInsert, 0) + 1);
            lastInsert = nextInsert
        }
    }

    //At how many points do at least two lines overlap?
    println("pt 2 answer: ${(ventMap.values.filter { it > 1 }.count()).toString().colorize(ConsoleColor.CYAN_BOLD)}")
}


