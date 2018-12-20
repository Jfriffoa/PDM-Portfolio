package cl.pdm.pencho.menutictactoe

//import android.graphics.Color
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var currentPlayer = 1
    private var partidasJugadas = 0
    private var done = false
    private var solo = false //Boolean para ver si juega contra otro o contra la IA


    //Preparación del juego
    private val b = Board()
    private val ia = IA()
    private var tablero : Array<Array<Button>>? = null

    //Debugging
    private val tag = "Ex07-TicTacToe"
    private fun debug(msg : String){
        //Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        Log.i(tag, msg)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tv_partidas.text = "Partidas Jugadas: $partidasJugadas"

        solo = intent.getBooleanExtra("Oponnent", false)
        if (solo)
            tv_winner.text = "PLAYING AGAINST THE IA"
        else
            tv_winner.text = "PLAYING AGAINST A PLAYER"

        //Inicializar Array
        tablero = arrayOf(arrayOf(b1, b2, b3),
                          arrayOf(b4, b5, b6),
                          arrayOf(b7, b8, b9))
    }

    fun bt_clicked(view : View) {
        val btn = view as Button
        make_move(btn)
    }

    fun reset(view : View){
        debug("RESET")

        tv_winner.text = ""
        done = false

        for (col in tablero!!) {
            for (btn in col) {
                btn.text = ""
                btn.setBackgroundResource(R.drawable.abc_btn_default_mtrl_shape)
                btn.isClickable = true
            }
        }

        b.reset()
        currentPlayer = 1
    }

    fun make_move(btn : Button) {
        if (done)
            return

        val idx = resources.getResourceEntryName(btn.id).substring("b".length)!!.toInt() - 1
        val x = idx % b.length()
        val y = idx / b.length()

        debug ("Pair: X $x Y $y | Player by $currentPlayer")

        b.makePlay(currentPlayer, x, y)
        btn.textSize = 36f
        if (currentPlayer == 1) {
            btn.text = "X"
            btn.setBackgroundColor(Color.RED)
        } else {
            btn.text = "O"
            btn.setBackgroundColor(Color.MAGENTA)
        }
        btn.isClickable = false

        if (solo){                                          //Jugando contra la IA
            if (b.checkWinner() == currentPlayer){          //Checkear si ganó
                tv_winner.text = "¡HAS GANADO!"
                partidasJugadas++
                tv_partidas.text = "Partidas Jugadas: $partidasJugadas"
                done = true
            } else if (b.isFull()){                         //Si no ganó, checkear si empató
                tv_winner.text = "¡EMPATE!"
                partidasJugadas++
                tv_partidas.text = "Partidas Jugadas: $partidasJugadas"
                done = true
            } else {                                        //Si no, juega IA
                ia.makeMove(b)

                debug ("Pair: X " + b.lastMove().first + " Y " + b.lastMove().second + " | Player by IA")

                refreshPos(b.lastMove())

                if (b.checkWinner() == -currentPlayer) {    //Ganó IA
                    tv_winner.text = "¡HAS PERDIDO!"
                    partidasJugadas++
                    tv_partidas.text = "Partidas Jugadas: $partidasJugadas"
                    done = true
                } else if (b.isFull()) {                    //Si no ganó, checkear si empató
                    tv_winner.text = "¡EMPATE!"
                    partidasJugadas++
                    tv_partidas.text = "Partidas Jugadas: $partidasJugadas"
                    done = true
                }
            }
        } else {                                            //Jugando contra otro jugador
            if(b.checkWinner() != 0){                       //Ver si es que ganó
                tv_winner.text = "JUGADOR " + (if (currentPlayer == 1) "X" else "O") + " GANÓ"
                partidasJugadas++
                tv_partidas.text = "Partidas Jugadas: $partidasJugadas"
                done = true
            } else if (b.isFull()){                         //Ver si es que empataron
                tv_winner.text = "¡EMPATE!"
                partidasJugadas++
                tv_partidas.text = "Partidas Jugadas: $partidasJugadas"
                done = true
            }

            currentPlayer = -currentPlayer
        }
    }

    fun refreshPos(pos:Pair<Int, Int>){
        val p = b.pos(pos.first, pos.second)
        val btn = tablero!![pos.second][pos.first]

        when (p){
            0 -> {
                btn.text = ""
                btn.setBackgroundResource(R.drawable.abc_btn_default_mtrl_shape)
                btn.isClickable = true
            }
            -1 -> {
                btn.textSize = 36f
                btn.text = "O"
                btn.setBackgroundColor(Color.MAGENTA)
                btn.isClickable = false
            }
            1 -> {
                btn.textSize = 36f
                btn.text = "X"
                btn.setBackgroundColor(Color.RED)
                btn.isClickable = false
            }
        }
    }
}
