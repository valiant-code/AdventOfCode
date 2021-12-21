private fun main() {
    //pt 1 - 707784
    //pt 2 -
    TimeUtil.startClock(1, ::partOne)
    TimeUtil.startClock(2, ::partTwo)
}

private class DeterministicDice() {
    private var nextValue = 1;
    var rollCounter = 0;

    fun roll(): Int {
        if (nextValue >= 101) {
            nextValue = 1
        }
        rollCounter++
        return nextValue++;
    }

    fun roll3Times(): Int {
        return roll() + roll() + roll()

    }
}

private class DiracDicePlayer(var position: Int, val goal: Int = 1000) {
    var points = 0;
    fun moveSpaces(num: Int) {
        var newSpace = (position + num) % 10
        if (newSpace == 0) newSpace = 10
        points += newSpace
        position = newSpace
    }

    fun hasWon() = points >= goal
}

private fun partOne(pt: Int = 1) {
    val input = InputUtil.readFileAsStringList("day21/input.txt")
    var player1 = DiracDicePlayer(input[0].substringAfter(": ").toInt())
    var player2 = DiracDicePlayer(input[1].substringAfter(": ").toInt())
    val players = listOf(player1, player2);
    val dice = DeterministicDice();

    var turnPlayer = player1;

    while (players.none { it.hasWon() }) {
        turnPlayer.moveSpaces(dice.roll3Times())
        turnPlayer = if (turnPlayer == player1) player2 else player1
    }

    val loserPoints = players.filterNot { it.hasWon() }.map { it.points }[0]
    val answer = dice.rollCounter * players.filterNot { it.hasWon() }.map { it.points }[0]
    println("pt $pt answer $loserPoints * ${dice.rollCounter}: ${answer colorize ConsoleColor.CYAN_BOLD}")
}

private fun partTwo(pt: Int = 2) {
    val input = InputUtil.readFileAsStringList("day21/input.txt")
    var player1 = DiracDicePlayer(input[0].substringAfter(": ").toInt(), 21)
    var player2 = DiracDicePlayer(input[1].substringAfter(": ").toInt(), 21)
    val players = listOf(player1, player2);
    val dice = DeterministicDice();

    var turnPlayer = player1;

    while (players.none { it.hasWon() }) {
        turnPlayer.moveSpaces(dice.roll3Times())
        turnPlayer = if (turnPlayer == player1) player2 else player1
    }

    val loserPoints = players.filterNot { it.hasWon() }.map { it.points }[0]
    val answer = dice.rollCounter * players.filterNot { it.hasWon() }.map { it.points }[0]
    println("pt $pt answer $loserPoints * ${dice.rollCounter}: ${answer colorize ConsoleColor.CYAN_BOLD}")
}
