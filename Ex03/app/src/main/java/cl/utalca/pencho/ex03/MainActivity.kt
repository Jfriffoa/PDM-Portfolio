package cl.utalca.pencho.ex03

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.res.ResourcesCompat
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    //Función para ser llamada al apretar el botón
    fun oc_bt_calcular(view : View) {
        try {
            val edad = et_edad.text.toString().toInt()
            val peso = et_peso.text.toString().toFloat()
            var altura = et_altura.text.toString().toFloat()

            //Cambiar la altura de cm a M
            altura = altura / 100f
            //Calcular bmi

            val bmi = peso / (altura * altura)

            // Rango de BMI para la categorización
            var range = FloatArray(4)

            // Tabla para Bajo Peso - Saludable - Sobrepeso - Obesidad - Obesidad Extrema según sexo
            // Niños y Jovenes (0 - 20) solo llegan hasta Obesidad, no Obesidad Extrema
            // Adultos bajo los 65 usan el espectro completo
            // Personas sobre 65 solo se categorizan en Bajo Peso, Saludable, y Sobrepeso
            if (rb_hombre.isChecked){
                when (edad) {
                    in    0..2 -> { range[0] = 14.7f; range[1] = 18.2f; range[2] = 19.3f; range[3] = 99.9f }
                             3 -> { range[0] = 14.3f; range[1] = 17.3f; range[2] = 18.2f; range[3] = 99.9f }
                             4 -> { range[0] = 14.0f; range[1] = 16.9f; range[2] = 17.8f; range[3] = 99.9f }
                             5 -> { range[0] = 13.8f; range[1] = 16.8f; range[2] = 17.9f; range[3] = 99.9f }
                             6 -> { range[0] = 13.7f; range[1] = 17.0f; range[2] = 18.4f; range[3] = 99.9f }
                             7 -> { range[0] = 13.7f; range[1] = 17.4f; range[2] = 19.2f; range[3] = 99.9f }
                             8 -> { range[0] = 13.8f; range[1] = 18.0f; range[2] = 20.0f; range[3] = 99.9f }
                             9 -> { range[0] = 14.0f; range[1] = 18.6f; range[2] = 21.1f; range[3] = 99.9f }
                            10 -> { range[0] = 14.2f; range[1] = 19.4f; range[2] = 22.2f; range[3] = 99.9f }
                            11 -> { range[0] = 14.6f; range[1] = 20.2f; range[2] = 23.2f; range[3] = 99.9f }
                            12 -> { range[0] = 15.0f; range[1] = 21.0f; range[2] = 24.2f; range[3] = 99.9f }
                            13 -> { range[0] = 15.5f; range[1] = 21.9f; range[2] = 25.2f; range[3] = 99.9f }
                            14 -> { range[0] = 16.0f; range[1] = 22.7f; range[2] = 26.0f; range[3] = 99.9f }
                            15 -> { range[0] = 16.6f; range[1] = 23.5f; range[2] = 26.8f; range[3] = 99.9f }
                            16 -> { range[0] = 17.1f; range[1] = 24.2f; range[2] = 27.6f; range[3] = 99.9f }
                            17 -> { range[0] = 17.7f; range[1] = 24.9f; range[2] = 28.3f; range[3] = 99.9f }
                            18 -> { range[0] = 18.2f; range[1] = 25.7f; range[2] = 29.0f; range[3] = 99.9f }
                            19 -> { range[0] = 18.7f; range[1] = 26.4f; range[2] = 29.7f; range[3] = 99.9f }
                            20 -> { range[0] = 19.1f; range[1] = 27.0f; range[2] = 30.6f; range[3] = 99.9f }
                    in  21..65 -> { range[0] = 20.0f; range[1] = 25.0f; range[2] = 30.0f; range[3] = 40.0f }
                          else -> { range[0] = 22.0f; range[1] = 27.0f; range[2] = 99.9f; range[3] = 99.9f }

                }
            }

            if (rb_mujer.isChecked){
                when (edad) {
                    in    0..2 -> { range[0] = 14.4f; range[1] = 18.0f; range[2] = 19.1f; range[3] = 99.9f }
                             3 -> { range[0] = 14.0f; range[1] = 17.2f; range[2] = 18.3f; range[3] = 99.9f }
                             4 -> { range[0] = 13.7f; range[1] = 16.8f; range[2] = 18.0f; range[3] = 99.9f }
                             5 -> { range[0] = 13.5f; range[1] = 16.8f; range[2] = 18.3f; range[3] = 99.9f }
                             6 -> { range[0] = 13.4f; range[1] = 17.1f; range[2] = 18.8f; range[3] = 99.9f }
                             7 -> { range[0] = 13.4f; range[1] = 17.6f; range[2] = 19.7f; range[3] = 99.9f }
                             8 -> { range[0] = 13.5f; range[1] = 18.3f; range[2] = 20.7f; range[3] = 99.9f }
                             9 -> { range[0] = 13.7f; range[1] = 19.1f; range[2] = 21.8f; range[3] = 99.9f }
                            10 -> { range[0] = 14.0f; range[1] = 20.0f; range[2] = 23.0f; range[3] = 99.9f }
                            11 -> { range[0] = 14.4f; range[1] = 20.9f; range[2] = 24.1f; range[3] = 99.9f }
                            12 -> { range[0] = 14.8f; range[1] = 21.7f; range[2] = 25.3f; range[3] = 99.9f }
                            13 -> { range[0] = 15.3f; range[1] = 22.6f; range[2] = 26.3f; range[3] = 99.9f }
                            14 -> { range[0] = 15.8f; range[1] = 23.3f; range[2] = 27.3f; range[3] = 99.9f }
                            15 -> { range[0] = 16.3f; range[1] = 24.0f; range[2] = 28.1f; range[3] = 99.9f }
                            16 -> { range[0] = 16.8f; range[1] = 24.7f; range[2] = 28.9f; range[3] = 99.9f }
                            17 -> { range[0] = 17.2f; range[1] = 25.2f; range[2] = 29.6f; range[3] = 99.9f }
                            18 -> { range[0] = 17.6f; range[1] = 25.7f; range[2] = 30.3f; range[3] = 99.9f }
                            19 -> { range[0] = 17.8f; range[1] = 26.1f; range[2] = 31.0f; range[3] = 99.9f }
                            20 -> { range[0] = 17.8f; range[1] = 26.5f; range[2] = 31.8f; range[3] = 99.9f }
                    in  21..65 -> { range[0] = 20.0f; range[1] = 24.0f; range[2] = 29.0f; range[3] = 37.0f }
                          else -> { range[0] = 22.0f; range[1] = 27.0f; range[2] = 99.9f; range[3] = 99.9f }

                }
            }

            when {
                bmi < range[0] -> report(BMIndex.BAJO_PESO, bmi)
                bmi < range[1] -> report(BMIndex.NORMAL, bmi)
                bmi < range[2] -> report(BMIndex.OBESIDAD_LEVE, bmi)
                bmi < range[3] -> report(BMIndex.OBESIDAD_SEVERA, bmi)
                          else -> report(BMIndex.OBESIDAD_MUY_SEVERA, bmi)
            }
        } catch (e : Exception){
            Log.w("BMI-Calculator", "Datos incompletos al momento de calcular BMI")
            Toast.makeText(this, "Complete todos los valores antes de calcular", Toast.LENGTH_SHORT).show()
        }
    }

    fun report(idx : BMIndex, bmi : Float){

        when(idx){
            BMIndex.BAJO_PESO -> {
                tv_resultado.text = "Su indice de masa corporal es de " + "%.2f".format(bmi) + ", eso quiere decir que está BAJO PESO"
                iv_image.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.underweight, null))
            }

            BMIndex.NORMAL -> {
                tv_resultado.text = "Su indice de masa corporal es de " + "%.2f".format(bmi) + ", eso quiere decir que está en un peso NORMAL"
                iv_image.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.normalweight, null))
            }

            BMIndex.OBESIDAD_LEVE -> {
                tv_resultado.text = "Su indice de masa corporal es de " + "%.2f".format(bmi) + ", eso quiere decir que está con una OBESIDAD LEVE"
                iv_image.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.overweight, null))
            }

            BMIndex.OBESIDAD_SEVERA -> {
                tv_resultado.text = "Su indice de masa corporal es de " + "%.2f".format(bmi) + ", eso quiere decir que está con una OBESIDAD SEVERA"
                iv_image.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.obese, null))
            }

            BMIndex.OBESIDAD_MUY_SEVERA -> {
                tv_resultado.text = "Su indice de masa corporal es de " + "%.2f".format(bmi) + ", eso quiere decir que está con una OBESIDAD MUY SEEVRA"
                iv_image.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.extremelyobese, null))
            }
        }
    }
}
