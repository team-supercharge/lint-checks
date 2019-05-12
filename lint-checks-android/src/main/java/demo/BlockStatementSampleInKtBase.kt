package demo

class BlockStatementSampleInKtBase {

    fun main() {
        for (j in 0..2) {
            println(j)
        }
        var i = 2
        println(++i)
        if (i == 2) {
            println("2")
        } else {
            ++i
        }
    }

    fun main2() {

        print(2)
        for (o in 0..2) {
            print(o)
        }

        print(2)
        var k = 0
        k++

        print(2)
        if (k == 2) {
            val l = 2
            print(l)
        }

        val x = 0
        when (x) {
            1 -> print("x == 1")
            2 -> print("x == 2")
            else -> { // Note the block
                print("x is neither 1 nor 2")
            }
        }
        val xa = 0
        /* Hello */
        val ka = when (xa) {
            0 -> println("Foo")
            else -> println("Bar")
        }
        print(ka)

        if (xa == 2) { println("") }
    }

}
