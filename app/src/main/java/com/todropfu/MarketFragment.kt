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
import com.google.gson.Gson
import com.todropfu.databinding.ItemMarketBinding
import kotlinx.android.synthetic.main.fragment_market.view.*
import org.jetbrains.anko.support.v4.intentFor
import org.jetbrains.anko.support.v4.toast

class MarketFragment : Fragment() {
    data class MarketData(val title: String, val mainTitle: String, val detail1Name:String, val detail1Desc: String, val detail2Name: String, val detail2Desc: String,
                          val detail3Name:String, val detail3Desc:String, val detail4Name:String, val detail4Desc:String)
    private val mMarketData = ObservableArrayList<Any>().apply {
        add(MarketData("근처 시장", "불광 시장", "취급 품목", "은솔과 두부", "오픈 시기", "매일 오픈", "주차장 유무", "주차장 있음", "화장실 유무", "화장실 있음"))
        add(MarketData("근처 트럭", "따듯한 트럭", "취급 품목", "은솔과 두부", "앱내 결제", "앱 내 결제 가능", "카드 결제", "카드 결제 가능", "거리", "1 KM"))
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_market, container, false)
        LinearLayoutManager(context).let {
            it.orientation = LinearLayout.VERTICAL
            view.marketRecycler.layoutManager = it
        }
        LastAdapter(mMarketData, BR.mdata)
                .map<MarketData>(Type<ItemMarketBinding>(R.layout.item_market)
                        .onClick {
                            toast("click")
                            val gson = Gson()
                            val data = gson.toJson(mMarketData[it.layoutPosition])
                            startActivity(intentFor<MarketZoomActivity>().putExtra("data", data))
                        }
                ).into(view.marketRecycler)
//        view.marketItemBg.setImageBitmap(resourceInSmapleSizeImg(context.resources, R.drawable.bg_login, 16))
        return view
    }

    companion object {

        fun newInstance(): MarketFragment {
            val fragment = MarketFragment()
            return fragment
        }
    }

}// Required empty public constructor
