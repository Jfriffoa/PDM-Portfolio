package cl.utalca.pencho.ex02

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    //Función llamada al apretar el boton calcular
    fun oc_bt_calcular(view : View){
        try {
            val monto = et_valor.text.toString().toInt()
            val amigos = et_amigos.text.toString().toInt()

            val montoTotal = monto * 1.1f
            val precioPorAmigo = (montoTotal / amigos).toInt()
            tv_result.text = "El total a pagar es de $" + montoTotal.toInt() + " con propina incluida.\n\nEl precio por persona es de $$precioPorAmigo"

        } catch (e : Exception) {
            Toast.makeText(this, "Debe rellenar los campos antes de presionar el boton", Toast.LENGTH_SHORT)
        }
    }
}
