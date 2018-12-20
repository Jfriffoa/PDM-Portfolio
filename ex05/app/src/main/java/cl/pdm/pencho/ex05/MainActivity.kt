package cl.pdm.pencho.ex05

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
    private val player1 = ArrayList<String>()
    private val player2 = ArrayList<String>()


    //Debugging
    private val tag = "Ex05-TicTacToe"
    private fun debug(msg : String){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        Log.i(tag, msg)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tv_partidas.text = "Partidas Jugadas: $partidasJugadas"
    }

    fun bt_clicked(view : View) {
        val btn = view as Button
        make_move(btn)
    }

    fun reset(view : View){
        tv_winner.text = ""
        done = false

        val btns = arrayOf(b1, b2, b3, b4, b5, b6, b7, b8, b9)
        for (b in btns) {
            b.text = ""
            b.setBackgroundResource(R.drawable.abc_btn_default_mtrl_shape)
            b.isClickable = true
        }

        player1.clear()
        player2.clear()
    }

    fun make_move(btn : Button) {
        if (done)
            return

        btn.textSize = 36f

        if (currentPlayer == 1) {
            btn.text = "X"
            btn.setBackgroundColor(Color.RED)
            player1.add(resources.getResourceEntryName(btn.id))
        } else {
            btn.text = "O"
            btn.setBackgroundColor(Color.MAGENTA)
            player2.add(resources.getResourceEntryName(btn.id))
        }

        btn.isClickable = false

        if(checkWinner()){
            tv_winner.text = "JUGADOR " + (if (currentPlayer == 1) "X" else "O") + " GANÓ"
            partidasJugadas++
            tv_partidas.text = "Partidas Jugadas: $partidasJugadas"
            done = true
        } else if (isFull()){
            tv_winner.text = "EMPATE"
            partidasJugadas++
            tv_partidas.text = "Partidas Jugadas: $partidasJugadas"
            done = true
        }

        currentPlayer = -currentPlayer
    }

    fun isFull() : Boolean {
        return player1.size + player2.size >= 9
    }

    fun checkWinner() : Boolean {
        val arr = if (currentPlayer == 1) player1 else player2

        //Horizontal
        if (arr.contains("b1") && arr.contains("b2") && arr.contains(("b3"))){
            return true
        }
        if (arr.contains("b4") && arr.contains("b5") && arr.contains(("b6"))){
            return true
        }
        if (arr.contains("b7") && arr.contains("b8") && arr.contains(("b9"))){
            return true
        }

        //Vertical
        if (arr.contains("b1") && arr.contains("b4") && arr.contains(("b7"))){
            return true
        }
        if (arr.contains("b2") && arr.contains("b5") && arr.contains(("b8"))){
            return true
        }
        if (arr.contains("b3") && arr.contains("b6") && arr.contains(("b9"))){
            return true
        }

        //Diagonal
        if (arr.contains("b1") && arr.contains("b5") && arr.contains(("b9"))){
            return true
        }
        if (arr.contains("b3") && arr.contains("b5") && arr.contains(("b7"))){
            return true
        }



        return false
    }
}
