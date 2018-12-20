package cl.pdm.pencho.menutictactoe

class TT {
    data class Entry(val hash:Int, val depth:Int, val value:Int, val bestMove:Pair<Int,Int>)

    val size = 250
    var entries = Array<Entry?>(size, {null})

    fun store(board:Board, depth:Int, score:Int, bestMove: Pair<Int, Int>){
        entries[board.hash() % size] = Entry(board.hash(), depth, score, bestMove)
    }

    fun lookup(board:Board):Entry? {
        return entries[board.hash() % size]
    }
}