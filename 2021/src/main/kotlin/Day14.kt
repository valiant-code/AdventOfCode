private fun main() {
    //pt 1 - 3118
    //pt 2 - 4332887448171
    TimeUtil.startClock(1, ::partOne)
    TimeUtil.startClock(2, ::partTwo)
}


private fun partOne(pt: Int = 1) {
    val input = InputUtil.readFileAsStringList("day14/input.txt", "\n\n")
    var polymer = input[0].toMutableList()
    val pairRules = input[1].split("\n")
        .map { it.substringBefore(" -> ") to it.last() }
        .toMap()

    for (i in 1..10) {
        val first = polymer.first();
        polymer = polymer.windowed(2).map {
            val insertion = it.toMutableList()
            insertion.removeAt(0)
            insertion.add(0, pairRules[it.joinToString("")]!!)
            insertion
        }
            .flatMap { it }
//            .onEach { println(it) }
            .toMutableList()
        polymer.add(0, first)
    }
//    println(polymer.joinToString (""))
    val counts = polymer.groupBy { it }.map { it.value.count() }.sortedDescending();
    val answer = counts.first() - counts.last()
    println("pt $pt answer: ${answer colorize ConsoleColor.CYAN_BOLD}")
}

private fun partTwo(pt: Int = 2) {
    val input = InputUtil.readFileAsStringList("day14/input.txt", "\n\n")
    var polymerPairs: Map<String, Long> = input[0].toMutableList().windowed(2)
        .map { "${it[0]}${it[1]}" }
        .groupBy { it }
        .map { it.key to it.value.count().toLong() }
        .toMap()

    val pairRules = input[1].split("\n")
        .map { it.substringBefore(" -> ") to listOf("${it[0]}${it.last()}", "${it.last()}${it[1]}") }
        .toMap()
    val firstAndLast = listOf(input[0].first(), input[0].last())

    for (i in 1..40) {
        polymerPairs = polymerPairs.keys.flatMap { pair -> pairRules[pair]!!.map { newPair -> newPair to (polymerPairs[pair] ?: 0L) } }
            .groupBy { it.first }
            .map { groupBy -> groupBy.key to groupBy.value.sumOf { it.second } }
            .toMap()
    }
    val finishedMap = polymerPairs.entries
        .flatMap { entry -> entry.key.toList().map { it to entry.value } }
        .groupBy { it.first }
        .map { groupBy -> groupBy.key to (groupBy.value.sumOf { it.second } + if (groupBy.key in firstAndLast) 1 else 0) / 2 }
        .toMap();
    val counts = finishedMap.values.sortedDescending();
    val answer = counts.first() - counts.last()
    println("pt $pt answer: ${answer colorize ConsoleColor.CYAN_BOLD}")
}



