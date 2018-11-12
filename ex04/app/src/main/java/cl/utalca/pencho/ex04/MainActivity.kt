package cl.utalca.pencho.ex04

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var calcString = ""
    var calcError = false
    val tag = "Ex04-Calculator"

    private fun debug(msg : String){
        //Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        Log.i(tag, msg)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    //OnClick Dispatcher
    fun bt_onClick(view : View){
        val btn = view as Button
        val btn_id = resources.getResourceEntryName(btn.id)
        val id = btn_id.substring("bt_".length)

        //Ver si es un boton de "control" o si es uno para añadir al calculo
        if (id.startsWith("c_")){
            when (id){
                "c_erase" -> {
                    calcString = ""
                }
                "c_del" -> {
                    if (!calcError)
                        calcString = calcString.substring(0, calcString.length - 1)
                    else
                        calcString = ""
                }
                "c_equal" -> {
                    calculate()
                }
            }
        } else {
            if (calcError){
                calcString = ""
                calcError = false
            }
            calcString += btn.text
        }

        tv_result.text = calcString
    }

    //Calcular el string
    private fun calculate(){
        debug("Calculando: $calcString")

        if (indexOf(calcString, arrayOf('x', '/', '+', '-')) == -1){
            debug("Ningun simbolo encontrado, abortando...")
            return
        }

        debug("Hay simbolos")
        var done = false
        while(!done){
            //String temporal para mantener los numeros entre iteraciones
            var tempString = ""

            val idx = indexOf(calcString, arrayOf('x', '/'))
            //Revisar si hay una multiplicación o una división por hacer
            if (idx != -1){
                debug("División o Multiplicación")
                val leftIdx = lastIndexOf(calcString.substring(0, idx), arrayOf('x', '/', '+', '-'))
                val rightIdx = indexOf(calcString.substring(idx + 1), arrayOf('x', '/', '+', '-'))

                val leftNum : Float
                val rightNum : Float

                //Tratar de transformar los strings a numeros, si falla es porque se escribio mal el calculo
                debug("Transformando los numeros")
                try {
                    if (leftIdx != -1) {
                        leftNum = calcString.substring(leftIdx + 1, idx).toFloat()
                    } else {
                        leftNum = calcString.substring(0, idx).toFloat()
                    }

                    if (rightIdx != -1) {
                        rightNum = calcString.substring(idx + 1, idx + rightIdx + 1).toFloat()
                    } else {
                        rightNum = calcString.substring(idx + 1).toFloat()
                    }
                } catch (e : Exception) {
                    calcString = "CALC ERROR"
                    calcError = true
                    return
                }

                debug("Numeros transformados, $leftNum y $rightNum")
                debug("Calculando resultado...")

                var result = 0f
                //Tratar de hacer el calculo, si falla es porque se realizó una operación matematicamente imposible
                try {
                    when (calcString[idx]) {
                        'x' -> result = leftNum * rightNum
                        '/' -> result = leftNum / rightNum
                    }
                } catch (e : Exception) {
                    calcString = "MATH ERROR"
                    calcError = true
                    return
                }

                debug("Resultado calculado: $result")

                if (leftIdx != -1)
                    tempString = calcString.substring(0, leftIdx)
                else
                    tempString = ""

                tempString += result.toString()

                if (rightIdx != -1)
                    tempString += calcString.substring(idx + rightIdx + 1)

                //Cuando el calculo se termine, guardar el string y continuar el loop para revisar la siguiente operación
                calcString = tempString
                debug("String rehecho: $calcString")
                continue
            }

            //Si no encontro las operaciones de arriba, buscar ahora las sumas y restas
            val idx2 = indexOf(calcString, arrayOf('+', '-'))
            if (idx2 != -1) {
                debug("Suma o Resta")
                val leftIdx = lastIndexOf(calcString.substring(0, idx2), arrayOf('x', '/', '+', '-'))
                debug("leftIDX $leftIdx")
                val rightIdx = indexOf(calcString.substring(idx2 + 1), arrayOf('x', '/', '+', '-'))

                val leftNum : Float
                val rightNum : Float

                //Tratar de transformar los strings a numeros, si falla es porque se escribio mal el calculo
                debug("Transformando los numeros")
                try {
                    if (leftIdx != -1) {
                        leftNum = calcString.substring(leftIdx + 1, idx2).toFloat()
                    } else {
                        leftNum = calcString.substring(0, idx2).toFloat()
                    }

                    if (rightIdx != -1) {
                        rightNum = calcString.substring(idx2 + 1, idx2 + rightIdx + 1).toFloat()
                    } else {
                        rightNum = calcString.substring(idx2 + 1).toFloat()
                    }
                } catch (e : Exception) {
                    calcString = "CALC ERROR"
                    calcError = true
                    return
                }

                debug("Numeros transformados, $leftNum y $rightNum")
                debug("Calculando resultado...")

                var result = 0f
                //Tratar de hacer el calculo, si falla es porque se realizó una operación matematicamente imposible
                try {
                    when (calcString[idx2]) {
                        '+' -> result = leftNum + rightNum
                        '-' -> result = leftNum - rightNum
                    }
                } catch (e : Exception) {
                    calcString = "MATH ERROR"
                    calcError = true
                    return
                }

                debug("Resultado calculado: $result")

                if (leftIdx != -1)
                    tempString = calcString.substring(0, leftIdx)
                else
                    tempString = ""

                tempString += result.toString()

                if (rightIdx != -1)
                    tempString += calcString.substring(idx2 + rightIdx + 1)

                //Cuando el calculo se termine, guardar el string y continuar el loop para revisar la siguiente operación
                calcString = tempString
                debug("String rehecho: $calcString")
                continue
            }

            //Si llegamos a este punto, quiere decir que estamos listos con el calculo
            done = true
        }
    }

    private fun indexOf(str : String, signs : Array<Char>) : Int {
        for (i in 0 until str.length) {
            signs.forEach {
                if (str[i] == it)
                    return i
            }
        }

        return -1
    }

    private fun lastIndexOf(str : String, signs : Array<Char>) : Int {
        for (i in str.length - 1 downTo 0) {
            signs.forEach {
                if (str[i] == it)
                    return i
            }
        }

        return -1
    }
}
