import java.util.*

private fun main() {
    //pt 1 - 3118
    //pt 2 - 4332887448171
    TimeUtil.startClock(1, ::partOne)
    TimeUtil.startClock(2, ::partTwo)
}

fun <K, V> Map<K, V>.getKey(value: V) = entries.firstOrNull { it.value == value }?.key

private val hexMap = mapOf(
    "0" to "0000",
    "1" to "0001",
    "2" to "0010",
    "3" to "0011",
    "4" to "0100",
    "5" to "0101",
    "6" to "0110",
    "7" to "0111",
    "8" to "1000",
    "9" to "1001",
    "A" to "1010",
    "B" to "1011",
    "C" to "1100",
    "D" to "1101",
    "E" to "1110",
    "F" to "1111",
)


private fun partOne(pt: Int = 1) {
    val input = InputUtil.readFileAsStringList("day16/input.txt", "").filterNot { it.isEmpty() }
    val bits = input.flatMap { hexMap[it]!!.toList() }

    val packet = processPacket(bits);

    val answer = 12345
    println("pt $pt answer: ${answer colorize ConsoleColor.CYAN_BOLD}")
}

private class Packet(
    val version: Int,
    val typeId: Int,
    val value: Int,
    var subPackets: MutableList<Packet> = mutableListOf(),


) {
    override fun toString(): String {
        return "Packet(version=$version, typeId=$typeId, value=$value, subPackets=${subPackets.size})"
    }
}


private fun processPacket(bits: List<Char>, startIndex: Int = 0): Pair<Packet, Int> {
    var index = startIndex;
    //the first three bits encode the packet version
    //the next three bits encode the packet type ID
    val version = hexMap.getKey("0" + bits.subList(index, index + 3).joinToString(""))!!.toInt();
    val typeId = hexMap.getKey("0" + bits.subList(index + 3, index + 6).joinToString(""))!!.toInt();
    val value: Int
    index += 6;

    when (typeId) {
        //Packets with type ID 4 represent a literal value
        4 -> {
            //Literal value packets encode a single binary number.
            // To do this, the binary number is padded with leading zeroes until its length is a multiple of four bits,
            // and then it is broken into groups of four bits. Each group is prefixed by a 1 bit except the last group,
            // which is prefixed by a 0 bit
            val uuid = Random().nextFloat().times(1000).toString()
            var endReached = false;
            var valueBlocks = mutableListOf<List<Char>>()
            for (block in bits.subList(index, bits.size).windowed(5, 5)) {
                valueBlocks.add(block)
                if (block[0] == '0') break;
            }
            index += valueBlocks.flatten().size;
            value = valueBlocks.flatMap {
                it.subList(1, it.size)
            }.joinToString("").toInt(2)
            return Packet(version, typeId, value) to index
        }
        else -> {
            //Every other type of packet (any packet with a type ID other than 4) represent an operator
            val packet = Packet(version, typeId, -2)
            val lengthTypeId = bits[index++]
            if (lengthTypeId == '0') {
                //If the length type ID is 0, then the next 15 bits are a number that represents
                // the total length in bits of the sub-packets contained by this packet.
                val length = bits.subList(index, index + 15).joinToString("").replaceBefore("1", "").toInt(2)
                index += 15;
                val subPacketsEnd = index + length;
                while (index <  subPacketsEnd) {
                    val res = processPacket(bits.subList(index, bits.size))
                    packet.subPackets.add(res.first)
                    index += res.second
                }

            } else {
                //If the length type ID is 1, then the next 11 bits are a number that represents
                // the number of sub-packets immediately contained by this packet.

            }
            return packet to index;
        }
    }

}


private fun partTwo(pt: Int = 2) {

}
