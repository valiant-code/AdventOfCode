object TimeUtil {
    private var start = System.currentTimeMillis()
    private var pt = 0
    fun startClock(part: Int) {
        pt = part
        start = System.currentTimeMillis()
    }

    fun time() {
        val end = System.currentTimeMillis()
//        println("Pt $pt Start Time: $start");
//        println("Pt $pt End Time:   $end");
        println("Pt $pt Time Taken: ${(end - start) / 1000f}")
    }
}