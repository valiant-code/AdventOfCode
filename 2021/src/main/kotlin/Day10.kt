private fun main() {
    //pt 1 - 288291
    //pt 2 - 820045242
    TimeUtil.startClock(1, ::partOne)
    TimeUtil.startClock(2, ::partTwo)
}

private val chunkChars = mapOf('(' to ')', '[' to ']', '{' to '}', '<' to '>')

private val errorPointMap = mapOf(')' to 3, ']' to 57, '}' to 1197, '>' to 25137)
private val addedPointMap = mapOf(')' to 1, ']' to 2, '}' to 3, '>' to 4)


private fun incompleteEx(expectedClose: Char) = Exception("incomplete line, expected $expectedClose");

private fun getClosingChar(line: String, pos: Int): Pair<Char, Int> {
    var position = pos;
    val opener = line[position];
    val expectedCloser = chunkChars[opener]
    val closer: Pair<Char, Int>?;
    if (line.length - 1 < position + 1) throw incompleteEx(expectedCloser ?: '8');
    var nextChar = line.getOrNull(position + 1) ?: throw incompleteEx(expectedCloser ?: '8');
    while (nextChar in chunkChars.keys) {
        val childsClose = getClosingChar(line, position + 1)
        position = childsClose.second;
        nextChar = line.getOrNull(position + 1) ?: throw incompleteEx(expectedCloser ?: '8');
    }
    closer = nextChar to position + 1

    if (closer.first != expectedCloser) {
        throw Exception("Expected ${expectedCloser}, but found ${closer.first} instead")
    }

    return closer;
}

private fun partOne(pt: Int = 1) {
    val input = InputUtil.readFileAsStringList("day10/input.txt")
    val exceptions = mutableListOf<String>()

    //A corrupted line is one where a chunk closes with the wrong character
    for (line in input) {
        try {
            var i = 0;
            while (i < line.length) {
                i = getClosingChar(line, i).second + 1;
            }
        } catch (e: Exception) {
            exceptions.add(e.message ?: e.toString())
        }
    }


    val answer = exceptions.filter { it.matches(Regex(".*Expected ., but found . instead")) }
        .map { it[22] }
        .map { errorPointMap.getOrDefault(it, 0) }.sum();
    println("pt $pt answer: ${answer colorize ConsoleColor.CYAN_BOLD}")
}

private fun partTwo(pt: Int = 2) {
    val input = InputUtil.readFileAsStringList("day10/input.txt")
    val addedChars = mutableListOf<List<Char>>()

    //filter to only lines that need to be completed
    val incompleteLines = input.filter { line ->
        var returnVal = false;
        try {
            var i = 0;
            while (i < line.length) {
                i = getClosingChar(line, i).second;
            }
        } catch (e: Exception) {
            if (e.message?.startsWith("incomplete line,") == true) {
                returnVal = true
            };
        }
        returnVal;
    }

    for (l in incompleteLines) {
        val addedList = mutableListOf<Char>()
        var line = l
        while (true) {
            try {
                var i = 0;
                while (i < line.length) {
                    i = getClosingChar(line, i).second + 1;
                }
                break;
            } catch (e: Exception) {
                if (e.message?.startsWith("incomplete line,") == true) {
                    val nextChar = e.message!!.last()
                    line += nextChar;
                    addedList.add(nextChar);
                }
            }
        }
        addedChars.add(addedList);
    }

    val scores = addedChars.map { listOfAddedChars ->
        //Start with a total score of 0. Then, for each character,
        var score = 0L;
        listOfAddedChars.forEach {
            // multiply the total score by 5 and then add the points
            score = score * 5 + addedPointMap.getOrDefault(it, -9999)
        }
        score
    }.sorted()
    //the winner is found by sorting all of the scores and then taking the middle score.
    // (There will always be an odd number of scores to consider.)

    val answer = scores[(scores.size) / 2]
    println("pt $pt answer: ${answer colorize ConsoleColor.CYAN_BOLD}")
}


