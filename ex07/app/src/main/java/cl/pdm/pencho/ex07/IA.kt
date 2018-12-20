package cl.pdm.pencho.ex07

class IA {
    private val tt = TT()

    fun makeMove(b:Board){
        val move = negamax(b, -1, 0)
        b.makePlay(-1, move.second.first, move.second.second)
    }

    //Negamax retorna el score (1 si gana, 0 si empata, -1 si pierde) y un par con la mejor jugada que debería jugar
    fun negamax(b:Board, player:Int, depth:Int):Pair<Int, Pair<Int, Int>>{
        //Si ya evalué esta posición, devolver
        val entry = tt.lookup(b)
        if (entry != null) {
            if (entry!!.hash == b.hash()) {
                return Pair(entry.value, entry.bestMove)
            }
        }

        val winner = b.checkWinner()
        //Empate
        if (winner == 0 && b.isFull()){
            return Pair(0, b.lastMove())
        }

        //Ganador
        if (winner != 0){
            if (player == winner)
                return Pair(1, b.lastMove())
            else
                return Pair(-1, b.lastMove())
        }

        var score = -2 //Menos que el menor puntaje valido
        var bestMove = Pair(-1, -1)

        //Llamar a negamax por cada movimiento
        for (i in 0 until b.length()){
            for (j in 0 until b.length()){
                var child = b.clone()
                if (child.makePlay(player, i, j)){
                    var value = negamax(child, -player, depth+1)
                    if (-value.first > score){
                        score = -value.first
                        bestMove = value.second
                    }
                }
            }
        }

        //Guardar en la tabla y devolver la mejor jugada
        tt.store(b, depth, score, bestMove)
        return Pair(score, bestMove)
    }
}