package cl.pdm.pencho.ex12

import com.google.android.gms.maps.model.LatLng

/**
 * Created by Pencho on 23-11-2018
 */

class Personaje {
    var nombre:String
    var location:LatLng
    var icon:Int
    var isDead:Boolean
    var isSeen:Boolean
    var hp:Int = 0

    constructor(nombre:String, vida: Int, location: LatLng, icon:Int) {
        this.nombre = nombre
        this.location = location
        this.icon = icon
        this.isDead = false
        this.isSeen = false
        this.hp = vida
    }

    val status get() = "HP Actual: $hp"

}