package cl.pdm.pencho.ex09

import com.google.android.gms.maps.model.LatLng

/**
 * Created by Pencho on 23-11-2018
 */

class Personaje {
    var nombre:String
    var status:String
    var location:LatLng
    var icon:Int
    var isDead:Boolean

    constructor(nombre:String, status:String, location: LatLng, icon:Int) {
        this.nombre = nombre
        this.status = status
        this.location = location
        this.icon = icon
        this.isDead = false
    }
}