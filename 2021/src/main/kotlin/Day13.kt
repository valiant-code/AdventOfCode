private fun main() {
    //pt 1 -
    //pt 2 -
    TimeUtil.startClock(1, ::partOne)
    TimeUtil.startClock(2, ::partTwo)
}

private fun foldMap(map: MutableMap<Pair<Int, Int>, Char>, instruction: Pair<Char, Int>) {
    //map<y, x>   (col, row)
    val fold = instruction.second;
    if (instruction.first == 'x') {

        val keysToAdd = map.keys.filter { it.second > fold }.map{ keyBeingFolded ->
            keyBeingFolded.copy(second = fold - (keyBeingFolded.second - fold))
        }
        val keysToRemove = map.keys.filter { it.second > fold }
        keysToAdd.forEach { map.putIfAbsent(it, '#') }
        keysToRemove.forEach { map.remove(it) }
    } else {
        val keysToAdd = map.keys.filter { it.first > fold }.map{ keyBeingFolded ->
            keyBeingFolded.copy(first = fold - (keyBeingFolded.first - fold))
        }
        val keysToRemove = map.keys.filter { it.first > fold }
        keysToAdd.forEach { map.putIfAbsent(it, '#') }
        keysToRemove.forEach { map.remove(it) }
    }
}

private fun partOne(pt: Int = 1) {
    val input = InputUtil.readFileAsStringList("day13/input.txt", "\n\n")
    val instructions: List<Pair<Char, Int>> = input[1].split("\n").map {
        val direction = if (it.contains("x")) { 'x' } else { 'y' }
        direction to it.substringAfter("=").toInt()
    }
    val map: MutableMap<Pair<Int, Int>, Char> = input[0].split("\n").map {
        val col = it.substringBefore(",").toInt();
        val row = it.substringAfter(",").toInt();
        Pair(row, col) to '#'
    }.toMap().toMutableMap()

    foldMap(map, instructions[0])

    val answer = map.values.count();
    println("pt $pt answer: ${answer colorize ConsoleColor.CYAN_BOLD}")
}

private fun partTwo(pt: Int = 2) {
    val input = InputUtil.readFileAsStringList("day13/input.txt", "\n\n")
    val instructions: List<Pair<Char, Int>> = input[1].split("\n").map {
        val direction = if (it.contains("x")) { 'x' } else { 'y' }
        direction to it.substringAfter("=").toInt()
    }
    val map: MutableMap<Pair<Int, Int>, Char> = input[0].split("\n").map {
        val col = it.substringBefore(",").toInt();
        val row = it.substringAfter(",").toInt();
        Pair(row, col) to '#'
    }.toMap().toMutableMap()

    instructions.forEach { instruction -> foldMap(map, instruction) }

    val maxFirst = map.keys.map { it.first }.maxOrNull()!!
    val maxSecond = map.keys.map { it.second }.maxOrNull()!!

    for (i in 0..maxFirst) {
        for (j in 0..maxSecond) {
            print(map.getOrDefault(i to j, " ") colorize ConsoleColor.BLUE)
        }
        println();
    }
//    val answer = map.values.count();
//    println("pt $pt answer: ${answer colorize ConsoleColor.CYAN_BOLD}")
}


