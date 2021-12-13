private fun main() {
    //pt 1 - 678
    //pt 2 - ECFHLHZF
    TimeUtil.startClock(1, ::partOne)
    TimeUtil.startClock(2, ::partTwo)
}

private fun foldMap(map: MutableMap<Pair<Int, Int>, Char>, instruction: Pair<Char, Int>) {
    //map<x, y> to Char  (col, row)
    //map<col, row> -
    val fold = instruction.second;
    when (instruction.first) {
        'x' -> { //flip col
            val keysToAdd = map.keys.filter { it.first > fold }.map { keyBeingFolded ->
                keyBeingFolded.copy(first = 2 * fold - keyBeingFolded.first)
            }
            val keysToRemove = map.keys.filter { it.first > fold }
            keysToAdd.forEach { map.putIfAbsent(it, '#') }
            keysToRemove.forEach { map.remove(it) }
        }
        'y' -> { //flip row
            val keysToAdd = map.keys.filter { it.second > fold }.map { keyBeingFolded ->
                keyBeingFolded.copy(second = 2 * fold - keyBeingFolded.second)
            }
            val keysToRemove = map.keys.filter { it.second > fold }
            keysToAdd.forEach { map.putIfAbsent(it, '#') }
            keysToRemove.forEach { map.remove(it) }
        }
    }
}

private fun partOne(pt: Int = 1) {
    val input = InputUtil.readFileAsStringList("day13/input.txt", "\n\n")
    val instructions: List<Pair<Char, Int>> = input[1].split("\n").map {
        val direction = if (it.contains("x")) 'x' else 'y'
        direction to it.substringAfter("=").toInt()
    }

    val map: MutableMap<Pair<Int, Int>, Char> = input[0].split("\n").map {
        val col = it.substringBefore(",").toInt();
        val row = it.substringAfter(",").toInt();
        Pair(col, row) to '#'
    }.toMap().toMutableMap()

    foldMap(map, instructions[0])

    val answer = map.values.count();
    println("pt $pt answer: ${answer colorize ConsoleColor.CYAN_BOLD}")
}

private fun partTwo(pt: Int = 2) {
    val input = InputUtil.readFileAsStringList("day13/input.txt", "\n\n")
    val instructions: List<Pair<Char, Int>> = input[1].split("\n").map {
        val direction = if (it.contains("x")) 'x' else 'y'
        direction to it.substringAfter("=").toInt()
    }
    val map: MutableMap<Pair<Int, Int>, Char> = input[0].split("\n").map {
        val col = it.substringBefore(",").toInt();
        val row = it.substringAfter(",").toInt();
        Pair(col, row) to '#'
    }.toMap().toMutableMap()

    instructions.forEach { instruction -> foldMap(map, instruction) }

    printMap(map)
//    val answer = map.values.count();
//    println("pt $pt answer: ${answer colorize ConsoleColor.CYAN_BOLD}")
}

private fun printMap(map: MutableMap<Pair<Int, Int>, Char>) {
    val maxFirst = map.keys.map { it.first }.maxOrNull()!!
    val maxSecond = map.keys.map { it.second }.maxOrNull()!!
    for (j in 0..maxSecond) {
        for (i in 0..maxFirst) {
            print(map.getOrDefault(i to j, ".") colorize ConsoleColor.BLUE)
        }
        println();
    }
}


