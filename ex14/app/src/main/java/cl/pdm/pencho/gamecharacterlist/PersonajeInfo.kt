package cl.pdm.pencho.gamecharacterlist

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_personaje_info.*
import kotlinx.android.synthetic.main.fila.*

class PersonajeInfo : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personaje_info)

        val bundle = intent.extras
        val name = bundle.getString("name")
        val des = bundle.getString("description")
        val img = bundle.getInt("image")

        tv_name.text = name
        tv_description.text = des
        iv_image.setImageResource(img)
    }
}
