package connect4

class Board{
    private val width:Int
    private val height:Int

    private var filled:Int
    private var tablero:Array<Array<Int>>
    private var lastMove = -1

    constructor(){
        width = 7
        height = 6
        filled = 0
        tablero = Array(height, {Array<Int>(width){0}})
    }

    private constructor(w:Int, h:Int, f:Int, t:Array<Array<Int>>){
        this.width = w
        this.height = h
        this.filled = f
        this.tablero = t
    }

    //Imprime el tablero con la numeración de las columnas para hacer las jugadas
    fun print(){
        for (i in 0 until height + 2){
            for (j in 0 until width){
                if (i == 0) {
                    print("| " + (j+1) + " |")
                } else if (i == height + 1) {
                    print("|---|")
                } else {
                    when(tablero[i-1][j]){
                        -1 -> print("| o |")
                        0 -> print("|   |")
                        1 -> print("| x |")
                    }
                }
            }
            println()
        }
    }

    //Función para ejectuar una jugada
    fun makePlay(player:Int, column:Int):Boolean{
        //Jugador no existe, movimiento invalido
        if (player != -1 && player != 1)
            return false

        //Columna no existe, movimiento invalido
        if (column < 0 || column > width - 1)
            return false

        //Columna llena, movimiento invalido
        if (tablero[0][column] != 0)
            return false

        //Ir bajando hasta que la pieza toque fondo
        var putted = false
        for (i in 1 until height){
            if (tablero[i][column] != 0) {
                tablero[i - 1][column] = player
                putted = true
                break
            }
        }

        //Si no detectamos que la pieza fue colocada, colocarla al fondo
        if (!putted){
            tablero[height-1][column] = player
        }

        lastMove = column
        filled++
        return true
    }

    //Revisar si alguien ganó | 0 = Nadie, 1 = Player, -1 = IA
    fun checkWinner():Int{
        //Menos de 7 movimientos, nadie ganó
        if (filled < 4)
            return 0

        //Revisar cada casilla de arriba-abajo, izquierda-derecha, y su diagonal
        for (i in 0 until height - 3){
            for (j in 0 until width){
                //Revision de casilla
                var move = tablero[i][j]

                //Si no hay moviimiento, me la salto
                if (move == 0)
                    continue

                //Izq -> Der
                for (k in 1..3){
                    //Si se sale del margen, romper
                    if (j + k >= width)
                        break

                    //Si no son iguales, romper el loop
                    if (move != tablero[i][j+k])
                        break

                    //Si llegue al final de la revisión y no he salido del loop, hay un ganador
                    if (k == 3)
                        return move
                }

                //Arriba - Abajo
                for (k in 1..3){
                    if (move != tablero[i+k][j])
                        break

                    if (k == 3)
                        return move
                }

                //Diagonal
                for (k in 1..3){
                    //Si se sale del margen, romper
                    if (j + k >= width)
                        break

                    if (move != tablero[i+k][j+k])
                        break

                    if (k == 3)
                        return move
                }
            }
        }

        //Revisar las casillas restantes de abajo-arriba, izquierda-derecha, y su diagonal
        for (i in height - 1 downTo height - 3) {
            for (j in 0 until width) {
                //Revision de casilla
                var move = tablero[i][j]

                //Si no hay moviimiento, me la salto
                if (move == 0)
                    continue

                //Izq -> Der
                for (k in 1..3){
                    //Si se sale del margen, romper
                    if (j + k >= width)
                        break

                    //Si no son iguales, romper el loop
                    if (move != tablero[i][j+k])
                        break

                    //Si llegue al final de la revisión y no he salido del loop, hay un ganador
                    if (k == 3)
                        return move
                }

                //Abajo - Arriba
                for (k in 1..3){
                    if (move != tablero[i-k][j])
                        break

                    if (k == 3)
                        return move
                }

                //Diagonal
                for (k in 1..3){
                    //Si se sale del margen, romper
                    if (j + k >= width)
                        break

                    if (move != tablero[i-k][j+k])
                        break

                    if (k == 3)
                        return move
                }
            }
        }

        //Si llegamos hasta aquí, quiere decir que nunca encontramos ganador
        return 0
    }

    //Funciones utilitarias
    fun isFull():Boolean{
        return filled >= (width * height)
    }

    fun Width():Int{
        return width
    }

    fun Height():Int{
        return height
    }

    fun pos(x:Int, y:Int):Int{
        if (x < 0 || x > height || y < 0 || y > width)
            throw Exception()

        return tablero[x][y]
    }

    fun lastMove():Int{
        return lastMove
    }

    //Función para clonar el tablero, necesario para negamax
    fun clone():Board{
        var newTablero = ArrayList<Array<Int>>()
        for (i in 0 until height){
            var ar = tablero[i].clone()
            newTablero.add(ar)
        }
        return Board(width, height, filled, newTablero.toTypedArray())
    }

    fun reset(){
        for (i in 0 until height){
            for (j in 0 until width){
                tablero[i][j] = 0
            }
        }

        filled = 0
    }
}