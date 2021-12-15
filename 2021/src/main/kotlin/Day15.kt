private fun main() {
    //pt 1 - 3118
    //pt 2 - 4332887448171
    TimeUtil.startClock(1, ::partOne)
    TimeUtil.startClock(2, ::partTwo)
}

private data class CavernSquare(val riskLevel: Int,
                                val coordinate: Pair<Int, Int>,
                                var distanceFromStart: Int = Int.MAX_VALUE) {
    fun chooseLowerDistance(dist: Int): Boolean {
        return if (distanceFromStart > dist + riskLevel) {
            distanceFromStart = dist + riskLevel;
            true
        } else
            false
    }
}

private fun partOne(pt: Int = 1) {
    val input = InputUtil.readFileAsStringList("day15/input.txt")
    val map = input.flatMapIndexed { i, line ->
        line.mapIndexed { j, char -> Pair(i, j) to CavernSquare(char.digitToInt(), Pair(i, j)) }
    }.toMap()



    var currPosition = 0 to 0;
    var currNode = map[currPosition]!!
    var nextNode: CavernSquare? = currNode
    currNode.distanceFromStart = 0;
    val destination = map.keys.maxOf { it.first } to map.keys.maxOf { it.second }
    var destinationNode = map[destination]!!
    val visited = mutableSetOf<Pair<Int, Int>>();

    //Dijkstra's algorithm https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm#Algorithm
    while (nextNode != null && !visited.contains(destination)) {
        currNode = nextNode
        getAdjacentPoints(currNode.coordinate)
            .filter { map.containsKey(it) }
            .filterNot { visited.contains(it) }
            .map { map[it]!! }
            .forEach { it.chooseLowerDistance(currNode.distanceFromStart) }
        visited.add(currNode.coordinate)
        nextNode = map.values.filterNot { visited.contains(it.coordinate) }
            .minByOrNull { it.distanceFromStart }
    }

//    for (i in 0..destination.first) {
//        println()
//        for (j in 0..destination.second) {
//            print(map[i to j])
//        }
//    }
//    println()

    val answer = destinationNode.distanceFromStart
    println("pt $pt answer: ${answer colorize ConsoleColor.CYAN_BOLD}")
}


private fun partTwo(pt: Int = 2) {
    val input = InputUtil.readFileAsStringList("day15/input.txt")
    val map = input.flatMapIndexed { i, line ->
        line.mapIndexed { j, char -> Pair(i, j) to CavernSquare(char.digitToInt(), Pair(i, j)) }
    }.toMap().toMutableMap()
    //The entire cave is actually five times larger in both dimensions
    //risk levels increase by 1 for each extra iteration, wrapping 9 -> 1

    val bigMap = enlargeMap(map)


    var currNode = bigMap[0 to 0]!!
    var nextNode: CavernSquare? = currNode
    currNode.distanceFromStart = 0;
    val maxRow = bigMap.keys.maxOf { it.first }
    val maxCol = bigMap.keys.maxOf { it.second }
    val destination = maxRow to maxCol
    var destinationNode = bigMap[destination]!!
    val visited = HashSet<Pair<Int, Int>>(300000);
    var notVisited = mutableMapOf<Pair<Int, Int>, Int>()

    //Dijkstra's algorithm https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm#Algorithm
    while (nextNode != null) {
        currNode = nextNode
        getAdjacentPoints(currNode.coordinate)
            .filterNot { visited.contains(it) }
            .filter { it.first in 0..maxRow }
            .filter { it.second in 0..maxCol }
            .map { bigMap[it]!! }
            .forEach {
                if (it.chooseLowerDistance(currNode.distanceFromStart)) notVisited[it.coordinate] = it.distanceFromStart
            }
        visited.add(currNode.coordinate)
        notVisited.remove(currNode.coordinate)
        if (currNode == destinationNode) break;
        nextNode = bigMap[notVisited.entries.minByOrNull { it.value }!!.key]
    }

    val answer = destinationNode.distanceFromStart
    println("pt $pt answer: ${answer colorize ConsoleColor.CYAN_BOLD}")
}

private fun getRiskLevel(num: Int): Int {
    return if (num <= 9) num else num - 9
}

private fun enlargeMap(map: MutableMap<Pair<Int, Int>, CavernSquare>): Map<Pair<Int, Int>, CavernSquare> {
    //map row to col
    val maxRow = map.keys.maxOf { it.first } + 1
    val maxCol = map.keys.maxOf { it.second } + 1
    val bigMap = map.entries.flatMap { mapping ->
        val newEntries = mutableListOf<Pair<Pair<Int, Int>, CavernSquare>>()
        for (i in 0..4) {
            for (j in 0..4) {
                val newKey = maxRow * i + mapping.key.first to maxCol * j + mapping.key.second
                newEntries.add(newKey to CavernSquare(getRiskLevel(mapping.value.riskLevel + i + j), newKey))
            }
        }
        newEntries
    }.toMap()
    return bigMap;
}



