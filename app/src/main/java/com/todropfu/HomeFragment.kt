package com.todropfu

import android.databinding.ObservableArrayList
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.github.nitrico.lastadapter.LastAdapter
import com.github.nitrico.lastadapter.Type
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.todropfu.databinding.ItemHomeRecyclerBinding
import kotlinx.android.synthetic.main.fragment_home.view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.sdk25.coroutines.onClick
import kotlin.properties.Delegates


class HomeFragment : Fragment(), OnMapReadyCallback, AnkoLogger {
    private var map by Delegates.notNull<GoogleMap>()

    data class CardData(val title: String, val content: String, val distance: String)

    private val mGpsInfo: GpsInfo by lazy {
        GpsInfo(context)
    }

    private val mCardItems = ObservableArrayList<Any>().apply {
        add(CardData("불기둥네 불소세지", "띠용? 불기동네 불떡볶이 이거 완죤 이름부터\n신선한 걸? 먹으러 가자!", "500 M"))
        add(CardData("불기둥네 불소세지", "띠용? 불기동네 불떡볶이 이거 완죤 이름부터\n신선한 걸? 먹으러 가자!", "500 M"))
        add(CardData("불기둥네 불소세지", "띠용? 불기동네 불떡볶이 이거 완죤 이름부터\n신선한 걸? 먹으러 가자!", "500 M"))
        add(CardData("불기둥네 불소세지", "띠용? 불기동네 불떡볶이 이거 완죤 이름부터\n신선한 걸? 먹으러 가자!", "500 M"))
        add(CardData("불기둥네 불소세지", "띠용? 불기동네 불떡볶이 이거 완죤 이름부터\n신선한 걸? 먹으러 가자!", "500 M"))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_home, container, false)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        LinearLayoutManager(context).let {
            it.orientation = LinearLayout.VERTICAL
            view.homeRecycler.layoutManager = it
        }
        LastAdapter(mCardItems, BR.rdata).map<CardData>(Type<ItemHomeRecyclerBinding>(R.layout.item_home_recycler)).into(view.homeRecycler)

        view.mapZoomIn.onClick{
            map.animateCamera(CameraUpdateFactory.zoomIn())
        }
        view.mapZoomOut.onClick {
            map.animateCamera(CameraUpdateFactory.zoomOut())
        }
        return view
    }

    override fun onMapReady(p0: GoogleMap?) {
        map = p0!!
        info { "map ready" }
        if (mGpsInfo.isGetLocation) {
            val lat = mGpsInfo.latitude
            val log = mGpsInfo.longitude

            updateCamera(lat, log)
            addMarker("현재위치", lat, log, false)
        }
    }

    private fun updateCamera(lat: Double, log: Double) {
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(lat, log), 13.0f))
    }

    private fun zoomCamera(zoom: Float) {
        map.animateCamera(CameraUpdateFactory.zoomTo(zoom))
    }

    private fun addMarker(title: String, lat: Double, log: Double, isMarkerRemove: Boolean) {
        map.addMarker(MarkerOptions().title(title).position(LatLng(lat, log)))
    }

    //    private fun addMarker(lat: Double, log: Double) {
//        marker.remove()
//        //        Log.d("dudco", "remove" + ":" + lat + "," + log);
//        addMarker("현재위치: $lat,$log", lat, log, true)
//        addMarker("목적지", end.getLat(), end.getLog(), false)
//    }
    companion object {
        fun newInstance(): HomeFragment {
            val fragment = HomeFragment()
            return fragment
        }
    }
}


