package demo

class BlockStatementSampleInKt {

    fun main() {
        for (j in 0..2) {
            println(j)
        }
        var i = 2
        ++i
        if (i == 2) {
            println(i)
        }
    }
//
//    private fun test(targetScreen: String) {
//        targetScreen?.let { targetScreen ->
//            if (targetScreen !is String) {
//                print("")
//            }
//            print("")
//        }
//    }
}

