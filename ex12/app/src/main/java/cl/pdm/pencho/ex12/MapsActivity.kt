package cl.pdm.pencho.ex12

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.media.RingtoneManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_maps.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private var mlocation : Location? = null
    private var auxmLocation : Location? = null
    private var mNotificationManager : NotificationManager? = null

    private val code = 1
    private var mPersonajes = ArrayList<Personaje>()

    //"Static" variables
    companion object {
        var selected : Personaje? = null
        var forceRefresh = false
    }

    //Debugging
    private val tag = "Ex12-MapsActivity"
    private fun debug(msg : String){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        Log.i(tag, msg)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    //Notify method
    private fun soundAndVibrate() {
        val id = "cl.pdm.pencho.ex12"
        val name = "GPS Game"
        val description = "Example Notification GPS Game"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //Mostrar notificación y vibrar
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(id, name, importance)
            channel.description = description
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
            mNotificationManager!!.createNotificationChannel(channel)

            //Sonar
            val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val ringtone = RingtoneManager.getRingtone(this, uri)
            ringtone.play()
        }
    }

    private fun loadCharacters(){
        mPersonajes.add(Personaje("Doge", 100, LatLng(-35.4, -71.64), R.drawable.ttdoge))
        mPersonajes.add(Personaje("Doge", 100, LatLng(-35.410887, -71.641964), R.drawable.ttdoge))
        mPersonajes.add(Personaje("TT", 200, LatLng(-35.404134, -71.635843), R.drawable.tt))
        mPersonajes.add(Personaje("Cloud", 300, LatLng(-35.403796, -71.634420), R.drawable.cloud))
        mPersonajes.add(Personaje("Cloud", 300, LatLng(-35.403583, -71.634028), R.drawable.cloud))
        mPersonajes.add(Personaje("Link", 400, LatLng(-35.403312, -71.634159), R.drawable.link))
        mPersonajes.add(Personaje("Link", 400, LatLng(-35.404046, -71.636189), R.drawable.link))
        mPersonajes.add(Personaje("Ryu", 250, LatLng(-35.404876, -71.637410), R.drawable.ryu))
        mPersonajes.add(Personaje("Ryu", 250, LatLng(-35.405185, -71.636072), R.drawable.ryu))
        mPersonajes.add(Personaje("Sonic", 150, LatLng(-35.405169, -71.635497), R.drawable.sonic))
        mPersonajes.add(Personaje("Sonic", 150, LatLng(-35.405488, -71.636333), R.drawable.sonic))
        mPersonajes.add(Personaje("TT", 200, LatLng(-35.405786, -71.636131), R.drawable.tt))
        mPersonajes.add(Personaje("Boss", 500, LatLng(-35.404530, -71.631939), R.drawable.squirtle))
    }

    fun btn_fight(view : View) {
        debug("FIGHT")

        val intent = Intent(this, FightActivity::class.java)
        startActivity(intent)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val personaje = LatLng(-33.45, -70.666667)
        mMap.addMarker(MarkerOptions().position(personaje).title("Squirtle").snippet("Personaje de Pokemon").icon(BitmapDescriptorFactory.fromResource(R.drawable.squirtle)))

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(personaje, 16f))

        loadCharacters()
        checkPermiso()
    }

    //*************************************************

    fun checkPermiso() {
        if (Build.VERSION.SDK_INT >= 23) {

            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), code)
                return
            }

        }
        getUserLocation()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode){
            code -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    getUserLocation()
                }
            }
        }
    }


    private fun getUserLocation() {
        var ml = mLocation()
        var lm = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3, 5f, ml)

        var th = mThread()

        th.start()

        Toast.makeText(this, "Localizando", Toast.LENGTH_SHORT)
    }

    //*************************************************

    inner class mLocation : LocationListener {

        constructor(){
            mlocation = Location("POI")
            mlocation!!.latitude = 0.0
            mlocation!!.longitude = 0.0
            auxmLocation = Location("AUX")
            auxmLocation!!.latitude = 0.0
            auxmLocation!!.longitude = 0.0
        }

        /**
         * onStatusChanged
         * Llamado cuando la prioridad de la aplicación cambia
         * Se puede ver mejor ejemplificado al mover la app a
         * segundo plano ya que el gps deja de darle la máxima
         * prioridad a la app.
         */
        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
            //debug("onStatusChanged")
        }

        override fun onLocationChanged(location: Location?){
            mlocation = location
            Log.i("LOCATION", "LOCATION CHANGED")
        }

        /**
         * OnProviderEnabled
         * Llamado cuando el sensor gps empieza a detectar la
         * localización y envía las coordenadas al teléfono
         */
        override fun onProviderEnabled(provider: String?) {
            //debug("onProviderEnabled")
        }

        /**
         * OnProviderDisabled
         * Llamado cuando el sensor gps se apaga y deja de
         * enviar señales y coordenadas al teléfono
         */
        override fun onProviderDisabled(provider: String?) {
            //debug("onProviderDisabled")
        }
    }
    inner class mThread() : Thread() {

        override fun run() {
            super.run()

            while(true){
                try {

                    if (!forceRefresh) {
                        if (auxmLocation!!.distanceTo(mlocation) == 0f) {
                            //No me movi
                            continue
                        }
                    }

                    runOnUiThread {
                        mMap.clear()

                        //Calcular cuál es el personaje más cercano y asignarlo a nuestra variable estatica
                        var minDistance = 100f
                        for (pj in mPersonajes){
                            //pj.isSeen = true

                            var loc = Location("PJ")
                            loc.latitude = pj.location.latitude
                            loc.longitude = pj.location.longitude
                            val dist = loc.distanceTo(mlocation)

                            if (dist <= 10){
                                minDistance = dist
                                selected = pj

                                if (!pj.isSeen){
                                    pj.isSeen = true
                                    soundAndVibrate()
                                }
                            } else {
                                pj.isSeen = false
                            }

                            if (minDistance < 100f){
                                btn_fight.visibility = Button.VISIBLE
                                btn_fight.text = "FIGHT " + selected!!.nombre + "!"
                            } else {
                                btn_fight.visibility = Button.INVISIBLE
                            }
                        }

                        for (pj in mPersonajes){
                            if (!pj.isDead && pj.isSeen)
                                mMap.addMarker(MarkerOptions().position(pj.location).title(pj.nombre).snippet(pj.status).icon(BitmapDescriptorFactory.fromResource(pj.icon)))
                        }

                        val personaje = LatLng(mlocation!!.latitude, mlocation!!.longitude)
                        mMap.addMarker(MarkerOptions().position(personaje).title("Squirtle").snippet("Personaje de Pokemon").icon(BitmapDescriptorFactory.fromResource(R.drawable.squirtle)))

                        auxmLocation = mlocation

                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(personaje, 15f))

                    }
                } catch (e : Exception) {

                }

                sleep(1000)
            }
        }
    }
}
