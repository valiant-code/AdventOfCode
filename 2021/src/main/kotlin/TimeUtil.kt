object TimeUtil {
    private var start = System.currentTimeMillis()
    private var pt = 0
    fun startClock(part: Int, function: () -> Unit) {
        pt = part
        start = System.currentTimeMillis()
        function.invoke();
        time();
    }

    fun time() {
        val end = System.currentTimeMillis()
//        println("Pt $pt Start Time: $start");
//        println("Pt $pt End Time:   $end");
        println("Pt $pt Time Taken: ${(end - start) / 1000f}")
    }
}