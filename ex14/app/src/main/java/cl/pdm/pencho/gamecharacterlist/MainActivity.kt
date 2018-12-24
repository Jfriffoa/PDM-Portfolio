package cl.pdm.pencho.gamecharacterlist

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fila.view.*

class MainActivity : AppCompatActivity() {


    var listPersonaje = ArrayList<Personaje>()
    var ml: myLista? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listPersonaje.add( Personaje("Cloud", "Este es un personaje de Final Fantasy VII", 1000, R.drawable.cloud))
        listPersonaje.add( Personaje("Ryu", "Este es un personaje de Street Fighter", 1000, R.drawable.ryu))
        listPersonaje.add( Personaje("Sonic", "Este es un personaje de Sonic The Hedgehog", 1000, R.drawable.sonic))
        listPersonaje.add( Personaje ("Link", "Este es un personaje de The Legend of Zelda", 1000, R.drawable.link))
        listPersonaje.add( Personaje("Cloud", "Este es un personaje de Final Fantasy VII", 1000, R.drawable.cloud))
        listPersonaje.add( Personaje("Ryu", "Este es un personaje de Street Fighter", 1000, R.drawable.ryu))
        listPersonaje.add( Personaje("Sonic", "Este es un personaje de Sonic The Hedgehog", 1000, R.drawable.sonic))
        listPersonaje.add( Personaje ("Link", "Este es un personaje de The Legend of Zelda", 1000, R.drawable.link))
        ml = myLista(this, listPersonaje)
        listViewXML.adapter = ml

        listPersonaje[3].hp = 200
        listPersonaje[5].hp = 250
        listPersonaje[6].hp = 100
    }

    fun eliminar(pos:Int){
        Toast.makeText(this, "Personaje ${listPersonaje[pos].name} ha sido eliminado", Toast.LENGTH_SHORT).show()
        listPersonaje.removeAt(pos)
        ml!!.notifyDataSetChanged()
    }

    fun clone(pos:Int){
        Toast.makeText(this, "Personaje ${listPersonaje[pos].name} ha sido clonado", Toast.LENGTH_SHORT).show()
        listPersonaje.add(listPersonaje[pos])
        ml!!.notifyDataSetChanged()
    }

    inner class myLista : BaseAdapter {

        var listPersonaje = ArrayList<Personaje>()
        var context: Context? = null

        constructor(context: Context, listPersonaje: ArrayList<Personaje>) : super() {

            this.listPersonaje = listPersonaje
            this.context = context

        }

        //Principal View
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

            var myView: View? = null
            val pj = this.listPersonaje[position]
            var inflater = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater



            if (pj.inDanger()) {

                //Añadirlo con Marco Rojo
                myView = inflater.inflate(R.layout.fila_danger, null)


                myView.nombre.text = pj.name
                myView.imageView.setImageResource(pj.image)
                myView.descripcion.text = pj.description

                myView.imageView.setOnClickListener {
                    /*
                    val intent = Intent(context, PersonajeInfo::class.java)
                    intent.putExtra("name", "Nombre: " + pj.name)
                    intent.putExtra("description", "Descripción: " + pj.description)
                    intent.putExtra("image", pj.image)
                    context!!.startActivity(intent)
                    */

                    eliminar(position)
                }

            } else {
                //Añadirlo con marco normal
                myView = inflater.inflate(R.layout.fila, null)


                myView.nombre.text = pj.name
                myView.imageView.setImageResource(pj.image)
                myView.descripcion.text = pj.description

                myView.imageView.setOnClickListener {
                    /*
                    val intent = Intent(context, PersonajeInfo::class.java)
                    intent.putExtra("name", "Nombre: " + pj.name)
                    intent.putExtra("description", "Descripción: " + pj.description)
                    intent.putExtra("image", pj.image)
                    context!!.startActivity(intent)
                    */

                    clone(position)
                }

            }
            return myView


        }

        override fun getItem(position: Int): Any {
            // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            return this.listPersonaje[position]
        }

        override fun getItemId(position: Int): Long {
            // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

            return position.toLong()
        }

        override fun getCount(): Int {
            // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            return this.listPersonaje.size
        }


    }

}
