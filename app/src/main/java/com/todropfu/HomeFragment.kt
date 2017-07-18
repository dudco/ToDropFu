package com.todropfu

import android.content.Context
import android.databinding.ObservableArrayList
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.github.nitrico.lastadapter.LastAdapter
import com.github.nitrico.lastadapter.Type
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.todropfu.databinding.ItemHomeRecyclerBinding
import kotlinx.android.synthetic.main.fragment_home.view.*
import org.jetbrains.anko.*
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
            addMarker("내 위치", lat, log, false)
        }
    }

    private fun updateCamera(lat: Double, log: Double) {
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(lat, log), 13.0f))
    }

    private fun zoomCamera(zoom: Float) {
        map.animateCamera(CameraUpdateFactory.zoomTo(zoom))
    }

    private fun addMarker(title: String, lat: Double, log: Double, isMarkerRemove: Boolean) {
        val wparam = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
        val mparam = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
        val view = FrameLayout(context)
        val textView = TextView(context)
        wparam.topMargin = 12
        textView.text = title
        textView.layoutParams = wparam
        textView.gravity = Gravity.CENTER_HORIZONTAL
        textView.textSize = 12.0f
        textView.textColor = ContextCompat.getColor(context, R.color.md_white_1000)
        val imageView = ImageView(context)
        imageView.image = ContextCompat.getDrawable(context, R.drawable.ic_home_marker)
        imageView.layoutParams = mparam

        view.addView(imageView)
        view.addView(textView)
        map.addMarker(MarkerOptions().position(LatLng(lat, log)).icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(context, view))))

    }

    private fun createDrawableFromView(context: Context, view: View): Bitmap {

        val displayMetrics = DisplayMetrics()
        context.windowManager.defaultDisplay.getMetrics(displayMetrics)
        view.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels)
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels)
        view.buildDrawingCache()
        val bitmap = Bitmap.createBitmap(view.measuredWidth, view.measuredHeight, Bitmap.Config.ARGB_8888)

        val canvas = Canvas(bitmap)
        view.draw(canvas)

        return bitmap
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


