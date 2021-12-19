import kotlin.math.ceil
import kotlin.math.floor

private fun main() {
    //pt 1 - 3675
    //pt 2 - 4650
    TimeUtil.startClock(1, ::partOne)
    TimeUtil.startClock(2, ::partTwo)
}

val simplePairRegex = "\\[(-?\\d+),(-?\\d+)]".toRegex()

private class SnailPair(
    var leftChild: SnailPair? = null,
    var rightChild: SnailPair? = null,
    var parent: SnailPair? = null,
    var value: Int? = null
) {
    init {
        leftChild?.parent = this;
        rightChild?.parent = this;
    }

    companion object {
        fun buildFromString(str: String): SnailPair {
            var line = str;
            val snailNodesMap = mutableMapOf<String, SnailPair>()
            var currentPair: SnailPair = SnailPair(value = -12345);
            var counter = 0;
            while (line.contains('[')) {
                val match = simplePairRegex.find(line)!!
                val firstVal = match.groupValues[1]
                val secondVal = match.groupValues[2]
                val firstNode = snailNodesMap.getOrElse(firstVal) { SnailPair(value = firstVal.toInt()) }
                val secondNode = snailNodesMap.getOrElse(secondVal) { SnailPair(value = secondVal.toInt()) }
                currentPair = SnailPair(firstNode, secondNode)
                snailNodesMap[(--counter).toString()] = currentPair
                line = simplePairRegex.replaceFirst(line, counter.toString())
            }
            return currentPair
        }
    }

    operator fun plus(other: SnailPair): SnailPair {
        val thisCopy = this.deepCopy()
        val otherCopy = other.deepCopy()
        val newParent = SnailPair(thisCopy, otherCopy);
        thisCopy.parent = newParent;
        otherCopy.parent = newParent;
        newParent.reduce()
        return newParent;
    }

    fun deepCopy(): SnailPair {
        return buildFromString(this.toString())
    }

    fun reduce() {
        while (true) {
            val seq = getListOfEndValueNodes().asSequence()
            if (seq.any { it.reduceExplosion() })
                continue;
            else if (seq.any { it.reduceSplit() })
                continue
            else break;
        }
    }

    fun getListOfEndValueNodes(): MutableList<SnailPair> {
        if (this.value != null) return mutableListOf(this)
        val list = leftChild!!.getListOfEndValueNodes();
        list.addAll(rightChild!!.getListOfEndValueNodes());
        return list;
    }

    fun reduceExplosion(): Boolean {
        //If ANY pair is nested inside four pairs, the leftmost such pair explodes.
        if (this.parent?.parent?.parent?.parent?.parent != null) {
            this.parent!!.explode();
            return true
        }
        return false
    }

    fun reduceSplit(): Boolean {
        //If any regular number is 10 or greater, the leftmost such regular number splits.
        if (this.value != null && this.value!! >= 10) {
            this.split();
            return true
        }
        return false
    }

    fun explode() {
        //the pair's left value is added to the first regular number to the LEFT of the exploding pair (if any)
        val left = getNodeToTheLeft()?.getRightMostChild();
        // and the pair's right value is added to the first regular number to the RIGHT of the exploding pair (if any)
        val right = getNodeToTheRight()?.getLeftMostChild();
        left?.value = left!!.value!! + this.leftChild!!.value!!
        right?.value = right!!.value!! + this.rightChild!!.value!!

        this.leftChild = null;
        this.rightChild = null;
        this.value = 0;
    }

    fun split() {
        //To split a regular number, replace it with a pair;
        // the left element of the pair should be the regular number divided by two and rounded down,
        val leftVal = floor(value!! / 2.0).toInt()
        // while the right element of the pair should be the regular number divided by two and rounded up.
        val rightVal = ceil(value!! / 2.0).toInt()
        leftChild = SnailPair(value = leftVal, parent = this)
        rightChild = SnailPair(value = rightVal, parent = this)
        this.value = null;
        // For example, 10 becomes [5,5], 11 becomes [5,6], 12 becomes [6,6], and so on.
    }

    fun getNodeToTheRight(): SnailPair? {
        return if (parent == null || parent?.rightChild == null) null
        else if (parent!!.rightChild != this) parent!!.rightChild!!
        else parent!!.getNodeToTheRight()
    }

    fun getNodeToTheLeft(): SnailPair? {
        return if (parent == null || parent?.leftChild == null) null
        else if (parent!!.leftChild != this) parent!!.leftChild!!
        else parent!!.getNodeToTheLeft()
    }

    fun getLeftMostChild(): SnailPair {
        return if (value != null) this
        else if (leftChild!!.value != null) leftChild!!
        else leftChild!!.getLeftMostChild()
    }

    fun getRightMostChild(): SnailPair {
        return if (value != null) this
        else if (rightChild!!.value != null) rightChild!!
        else rightChild!!.getRightMostChild()
    }

    override fun toString() = if (this.value != null) this.value.toString() else "[$leftChild,$rightChild]"

    fun calcMagnitude(): Long {
        //The magnitude of a regular number is just that number
        if (value != null) return value!!.toLong()

        //The magnitude of a pair is 3 times the magnitude of its left element
        // plus 2 times the magnitude of its right
        return (3 * leftChild!!.calcMagnitude()) + (2 * rightChild!!.calcMagnitude())
    }
}

private fun partOne(pt: Int = 1) {
    val input = InputUtil.readFileAsStringList("day18/input.txt")
    val pairList = input.map { SnailPair.buildFromString(it) }

    val finalNumber = pairList.reduce { a, b -> a + b }
    val answer = finalNumber.calcMagnitude()
    println("pt $pt answer: ${answer colorize ConsoleColor.CYAN_BOLD}")
}

private fun partTwo(pt: Int = 2) {
    var max = Long.MIN_VALUE;
    val input = InputUtil.readFileAsStringList("day18/input.txt")
    val permutationSet = mutableSetOf<String>()
    input.forEach { line ->
        input.filterNot { it == line }.forEach {
            permutationSet.add("$line+$it")
            permutationSet.add("$it+$line")
        }
    }

    permutationSet.forEach {
        max = maxOf(
            max,
            (SnailPair.buildFromString(it.split("+")[0]) + SnailPair.buildFromString(it.split("+")[1])).calcMagnitude()
        )
    }

    val answer = max;
    println("pt $pt answer: ${answer colorize ConsoleColor.CYAN_BOLD}")
}
