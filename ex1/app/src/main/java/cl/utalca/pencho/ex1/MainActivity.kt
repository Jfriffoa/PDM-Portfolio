package cl.utalca.pencho.ex1

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val tag = "Ex01"

    //Función llamada al crear el activity por primera vez
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        debug("OnCreate")
    }

    //Función llamada cuando el activity empieza, después de ser creado (Create) o de ser reiniciado (Restart)
    override fun onStart() {
        super.onStart()
        debug("OnStart")
    }

    //Función llamada justo después de que el activity inicia (Start)
    override fun onResume() {
        super.onResume()
        debug("OnResume")
    }

    //Función llamada cuando otra activity reemplaza a esta en el foreground
    override fun onPause() {
        super.onPause()
        debug("OnPause")
    }

    //Función llamada cuando la aplicación deja de funcionar en el foreground y se mantiene en espera en background
    override fun onStop() {
        super.onStop()
        debug("OnStop")
    }

    //Función llamada cuando la activity se cierra
    override fun onDestroy() {
        super.onDestroy()
        debug("OnDestroy")
    }

    //Función llamada cuando la activity regresa de haber estado detenida
    override fun onRestart() {
        super.onRestart()
        debug("onRestart")
    }

    //On Click del boton Calcular
    fun oc_bt_calcular(view : View){
        val totalCuenta = et_valor.text.toString().toInt()
        val resultado = totalCuenta * 0.10
        tv_result.text = "La propina a pagar es: $resultado"
    }

    fun debug(msg:String){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        Log.i(tag, msg)
    }
}
