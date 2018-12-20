package cl.pdm.pencho.ex08

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.res.ResourcesCompat
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import connect4.Board
import connect4.IA
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val b = Board()
    private val ia = IA()
    private var tablero : Array<Array<ImageView>>? = null


    private var currentPlayer = 1
    private var solo = false    //Checkear si juega contra la IA o contra otro jugador
    private var done = false
    private var partidasJugadas = 0

    //Debugging
    private val tag = "Ex08-Connect4"
    private fun debug(msg : String){
        //Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        Log.i(tag, msg)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Initialize Array
        tablero = arrayOf(arrayOf(iv_00, iv_01, iv_02, iv_03, iv_04, iv_05, iv_06),
                          arrayOf(iv_10, iv_11, iv_12, iv_13, iv_14, iv_15, iv_16),
                          arrayOf(iv_20, iv_21, iv_22, iv_23, iv_24, iv_25, iv_26),
                          arrayOf(iv_30, iv_31, iv_32, iv_33, iv_34, iv_35, iv_36),
                          arrayOf(iv_40, iv_41, iv_42, iv_43, iv_44, iv_45, iv_46),
                          arrayOf(iv_50, iv_51, iv_52, iv_53, iv_54, iv_55, iv_56))

        reset()

        solo = intent.getBooleanExtra("Oponnent", false)
        if (solo)
            tv_winner.text = "PLAYING AGAINST THE IA"
        else
            tv_winner.text = "PLAYING AGAINST A PLAYER"
    }

    fun bt_reset(view : View){
        reset()
    }

    fun reset() {
        for (i in 0 until tablero!!.size){
            for (j in 0 until tablero!![i].size) {
                tablero!![i][j].setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.gray, null))
            }
        }

        b.reset()
        tv_winner.text = ""
        done = false
        currentPlayer = 1
    }

    fun bt_move(view : View){
        if (done)
            return

        val btn = view as Button
        val idx = resources.getResourceEntryName(btn.id).substring("bt_".length)!!.toInt()

        b.makePlay(currentPlayer, idx-1)
        checkCol(idx-1)

        var winner = b.checkWinner()

        debug("Player Winner? = $winner")

        //Empate
        if (winner == 0 && b.isFull()){
            tv_winner.text = "¡EMPATE!"
            partidasJugadas++
            tv_partidas.text = "Partidas Jugadas: $partidasJugadas"
            done = true
            return
        }

        if (solo) {                                     //Jugando contra la IA
            if (winner == currentPlayer){               //Checkear si ganó
                tv_winner.text = "¡HAS GANADO!"
                partidasJugadas++
                tv_partidas.text = "Partidas Jugadas: $partidasJugadas"
                done = true
                return
            }

            ia.makeMove(b)                              //Si no ganó, juega la IA
            checkCol(b.lastMove())
            debug("IA Winner? = $winner")

            if (b.checkWinner() == -currentPlayer) {    //Ganó la IA
                tv_winner.text = "¡HAS PERDIDO"
                partidasJugadas++
                tv_partidas.text = "Partidas Jugadas: $partidasJugadas"
                done = true
                return
            } else if (b.isFull()) {                    //Si no, checkear si empataron
                tv_winner.text = "¡EMPATE!"
                partidasJugadas++
                tv_partidas.text = "Partidas Jugadas: $partidasJugadas"
                done = true
                return
            }
        } else {                                        //No IA
            if (b.checkWinner() != 0) {                 //Ver si hay ganador
                tv_winner.text = "JUGADOR " + (if (currentPlayer == 1) "ROJO" else "AMARILLO") + " GANÓ"
                partidasJugadas++
                tv_partidas.text = "Partidas Jugadas: $partidasJugadas"
                done = true
            } else if (b.isFull()) {                     //Ver si hay empate
                tv_winner.text = "¡EMPATE!"
                partidasJugadas++
                tv_partidas.text = "Partidas Jugadas: $partidasJugadas"
                done = true
            }

            currentPlayer = -currentPlayer
        }
    }

    fun checkCol(col : Int) {
        debug("Checking Col $col")

        for (i in 0 until tablero!!.size){
            val pos = b.pos(i, col)
            when (pos){
                 0 -> tablero!![i][col].setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.gray, null))
                -1 -> tablero!![i][col].setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.yellow, null))
                 1 -> tablero!![i][col].setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.red, null))

            }
        }
    }
}
