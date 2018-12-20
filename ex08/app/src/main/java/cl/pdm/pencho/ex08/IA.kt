package connect4

class IA {
    val maxDepth = 2

    fun makeMove(b:Board){
        var move = negaMax(b, -1, 0)
        b.makePlay(-1, move.second)
    }

    //Función usada para ver si la casilla está dentro de los limites del tablero
    private fun valid(b:Board, i:Int, j:Int):Boolean{
        return i > 0 && i < b.Height() && j > 0 && j < b.Width()
    }

    private fun value(b:Board, player: Int):Pair<Int, Int>{
        //Checkear el tablero de abajo hacía arriba, de izquierda a derecha
        var maxH = 0
        var bestMove = 3

        for (i in b.Height() - 1 downTo 0) {
            for (j in 0 until b.Width()) {
                if (b.pos(i, j) != player){ //Si la casilla no marcó el jugador, ignorar la casilla
                    continue
                }

                var h = 0
                var col = -1
                //Check casilla horizontal
                for (k in -3..3){
                    if (valid(b, i, j+k)){
                        var p = b.pos(i,j+k)
                        if (p == -player)       //Si es jugador contrario, volver h a 0 (no está seguido)
                            h = 0
                        else if (p == player)   //Si es mi casilla, sumarle 2
                            h += 2
                        else {                  //Si es una casilla vacia, guardar la heuristica y la mejor columna
                            h += 1
                            col = j+k
                            if (h > maxH) {
                                maxH = h
                                bestMove = col
                            }
                        }
                    }
                }

                //Comprobación final
                if (h > maxH){
                    maxH = h
                    bestMove = col
                }

                //Reiniciar variables
                h = 0
                col = -1

                //Check casilla vertical
                for (k in -3..3){
                    if (valid(b, i+k, j)){
                        var p = b.pos(i+k,j)
                        if (p == -player)       //Si es jugador contrario, volver h a 0 (no está seguido)
                            h = 0
                        else if (p == player)   //Si es mi casilla, sumarle 2
                            h += 2
                        else {                  //Si es una casilla vacia, guardar la heuristica y la mejor columna
                            h += 1
                            col = j
                            if (h > maxH) {
                                maxH = h
                                bestMove = col
                            }
                        }
                    }
                }

                //Comprobación final
                if (h > maxH){
                    maxH = h
                    bestMove = col
                }

                //Reiniciar variables
                h = 0
                col = -1

                //Check Diagonal \
                for (k in -3..3){
                    if (valid(b, i+k, j+k)){
                        var p = b.pos(i+k,j+k)
                        if (p == -player)       //Si es jugador contrario, volver h a 0 (no está seguido)
                            h = 0
                        else if (p == player)   //Si es mi casilla, sumarle 2
                            h += 2
                        else {                  //Si es una casilla vacia, guardar la heuristica y la mejor columna
                            h += 1
                            col = j+k
                            if (h > maxH) {
                                maxH = h
                                bestMove = col
                            }
                        }
                    }
                }

                //Comprobación final
                if (h > maxH){
                    maxH = h
                    bestMove = col
                }

                //Reiniciar variables
                h = 0
                col = -1

                //Check Diagonal /
                for (k in -3..3){
                    if (valid(b, i+k, j-k)){
                        var p = b.pos(i+k,j-k)
                        if (p == -player)       //Si es jugador contrario, volver h a 0 (no está seguido)
                            h = 0
                        else if (p == player)   //Si es mi casilla, sumarle 2
                            h += 2
                        else {                  //Si es una casilla vacia, guardar la heuristica y la mejor columna
                            h += 1
                            col = j-k
                            if (h > maxH) {
                                maxH = h
                                bestMove = col
                            }
                        }
                    }
                }
                //Comprobación final
                if (h > maxH){
                    maxH = h
                    bestMove = col
                }
            }
        }

        return Pair(maxH, bestMove)
    }

    private fun negaMax(b:Board, player:Int, depth:Int):Pair<Int, Int> {
        //Detener la busqueda en caso de llegar aquí
        if (depth > maxDepth){
            return value(b, player)
        }

        val winner = b.checkWinner()

        //Si el juego ya terminó, Empate (0) y mejor columna (-1)
        if (b.isFull() && winner == 0){
            return Pair(0, b.lastMove())
        }

        //Si ya hay ganador, devuelve uno si es que el jugador actual ganó
        if (winner != 0){
            return Pair(if (winner == player) 10 else -10, b.lastMove())
        }

        //Settear variables
        var score = -11
        var bestCol = -1

        //Si nadie a ganado, llamar a NegaMax por cada movimiento
        for (i in 0 until b.Width()){
            var child = b.clone()
            if (child.makePlay(player, i)) {
                var value = negaMax(child, -player, depth + 1)
                if (-value.first > score){
                    score = -value.first
                    bestCol = value.second
                }
            }
        }

        //Devolver mejor jugada
        return Pair(score, bestCol)
    }
}