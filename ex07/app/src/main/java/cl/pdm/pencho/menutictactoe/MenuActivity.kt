package cl.pdm.pencho.menutictactoe

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MenuActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
    }

    fun launchIA(view : View) {
        launchGame(true)
    }

    fun launchPlayer(view : View) {
        launchGame(false)
    }

    fun launchGame(solo : Boolean){
        val intent = Intent(this, MainActivity::class.java)

        //TODO
        //Put the IA or Player on the putExtra
        intent.putExtra("Oponnent", solo)
        startActivity(intent)
    }
}
