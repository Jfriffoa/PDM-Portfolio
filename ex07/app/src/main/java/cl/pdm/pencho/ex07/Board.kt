package cl.pdm.pencho.ex07

class Board{
    private val length:Int

    private var filled:Int
    private var tablero:Array<Array<Int>>
    private var lastMove = Pair(-1, -1)

    constructor(){
        length = 3
        filled = 0
        tablero = Array(length, {Array<Int>(length){0}})
    }

    private constructor(l:Int, f:Int, t:Array<Array<Int>>){
        this.length = l
        this.filled = f
        this.tablero = t
    }

    //Imprime el tablero con la numeración de las posiciones
    fun print(){
        for (i in 0 until length){
            for (j in 0 until length){
                when(tablero[i-1][j]){
                    -1 -> print("| o |")
                    0 -> print("| " + (i+j+1) + " |")
                    1 -> print("| x |")
                }
            }
        }
        println()
    }

    //Función para ejectuar una jugada
    fun makePlay(player:Int, x:Int, y:Int):Boolean{
        //Jugador no existe, movimiento invalido
        if (player != -1 && player != 1)
            return false

        //Se sale de los limites, movimiento invalido
        if (x < 0 || x > length - 1 || y < 0 || y > length - 1)
            return false

        //Posición ocupada, movimiento invalido
        if (tablero[x][y] != 0)
            return false

        //Caso contrario, jugada valida
        tablero[x][y] = player
        lastMove = Pair(x, y)
        filled++
        return true
    }

    //Revisar si alguien ganó | 0 = Nadie, 1 = Player, -1 = IA
    fun checkWinner():Int{
        //Menos de 5 movimientos, nadie ganó
        if (filled < 5)
            return 0

        //Horizontales
        for (i in 0 until length){
            val move = tablero[i][0]

            //Si no hay movimiento, la salto
            if (move == 0)
                continue

            for (j in 1 until length){
                //Si no son iguales, romper
                if (move != tablero[i][j])
                    break

                //Si llego al final y no he roto, hay un ganador
                if (j == length - 1)
                    return move
            }
        }

        //Verticales
        for (j in 0 until length){
            val move = tablero[0][j]

            //Si no hay movimiento, la salto
            if (move == 0)
                continue

            for (i in 1 until length){
                //Si no son iguales, romper
                if (move != tablero[i][j])
                    break

                //Si llego al final y no he roto, hay un ganador
                if (i == length - 1)
                    return move
            }
        }

        //Diagonal 1
        var move = tablero[0][0]
        if (move != 0) {
            for (i in 1 until length) {
                //Si no son iguales, romper
                if (move != tablero[i][i])
                    break

                //Si llego al final y no he roto, hay un ganador
                if (i == length - 1)
                    return move
            }
        }

        //Diagonal 2
        move = tablero[0][length-1]
        if (move != 0) {
            for (i in 1 until length) {
                //Si no son iguales, romper
                if (move != tablero[i][length - (i + 1)])
                    break

                //Si llego al final y no he roto, hay un ganador
                if (i == length - 1)
                    return move
            }
        }

        //Si llegamos hasta aquí, quiere decir que nunca encontramos ganador
        return 0
    }

    //Funciones utilitarias
    fun isFull():Boolean{
        return filled >= (length * length)
    }

    fun length():Int{
        return length
    }

    fun pos(x:Int, y:Int):Int{
        if (x < 0 || x > length-1 || y < 0 || y > length-1)
            throw Exception()

        return tablero[x][y]
    }

    fun lastMove():Pair<Int, Int>{
        return lastMove
    }

    //Función para clonar el tablero, necesario para negamax
    fun clone():Board{
        var newTablero = ArrayList<Array<Int>>()
        for (i in 0 until length){
            var ar = tablero[i].clone()
            newTablero.add(ar)
        }
        return Board(length, filled, newTablero.toTypedArray())
    }

    fun reset(){
        for (i in 0 until length){
            for (j in 0 until length){
                tablero[i][j] = 0
            }
        }

        filled = 0
    }

    fun hash():Int{
        var result = 0
        for (i in 0 until length){
            for (j in 0 until length){
                result = result * 31 + (tablero[i][j] + 7)
            }
        }

        result = result * 31 + (lastMove.first + lastMove.second + 5)
        if (result < 0)
            result *= -1

        return result
    }
}