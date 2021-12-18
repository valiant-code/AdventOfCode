import kotlin.math.ceil
import kotlin.math.floor

private fun main() {
    //pt 1 -
    //pt 2 -
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
        val parent = SnailPair(this, other);
        this.parent = parent;
        other.parent = parent;
        parent.reduce()
        return parent;
    }

    fun reduce() {
        while (true) {
            val seq = getListOfEndNodes().asSequence()
            if (seq.any { it.reduceExplosion() })
                continue;
            else if (seq.any { it.reduceSplit() })
                continue
            else break;
        }
    }

    fun getListOfEndNodes(): MutableList<SnailPair> {
        if (this.value != null) return mutableListOf(this)
        val list = leftChild!!.getListOfEndNodes();
        list.addAll(rightChild!!.getListOfEndNodes());
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
        //TODO
        return -1;
    }
}

private fun partOne(pt: Int = 1) {
    val input = InputUtil.readFileAsStringList("day18/input.txt")
    //[1,2]
    //[[3,4],5]
    //[9,[8,7]]
    //[[1,9],[8,5]]
    val pairList = input.map { SnailPair.buildFromString(it) }

    val finalNumber = pairList.reduce {a, b -> a + b}
    val answer = finalNumber.calcMagnitude()
    println("pt $pt answer: ${answer colorize ConsoleColor.CYAN_BOLD}")
}

private fun partTwo(pt: Int = 2) {

}
