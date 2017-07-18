package com.todropfu

import android.databinding.DataBindingUtil
import android.databinding.ObservableArrayList
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.widget.LinearLayout
import com.github.nitrico.lastadapter.LastAdapter
import com.github.nitrico.lastadapter.Type
import com.google.gson.Gson
import com.todropfu.databinding.ActivityMarketZoomBinding
import com.todropfu.databinding.ItemMarketZoomBinding
import kotlinx.android.synthetic.main.activity_market_zoom.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import kotlin.properties.Delegates

class MarketZoomActivity : AppCompatActivity(), AnkoLogger {
    data class MarketZoomData(val title: String, val content: String, val price: String)
    private val mMarketZoomDatas = ObservableArrayList<Any>().apply {
        add(MarketZoomData("트럭마을산 두부", "트럭마을 아조시가 열심히 기른 두부입니다", "2,000"))
        add(MarketZoomData("트럭마을산 두부", "트럭마을 아조시가 열심히 기른 두부입니다 이 두부는 신기한 두부", "2,000"))
    }
    var binding: ActivityMarketZoomBinding by Delegates.notNull<ActivityMarketZoomBinding>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@MarketZoomActivity, R.layout.activity_market_zoom)
        setSupportActionBar(binding.marketZoomToolbar)
        supportActionBar?.let{
            title = "자세히 보기"
            it.setDisplayHomeAsUpEnabled(true)
            it.setDefaultDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.ic_home)
        }
        val sData = intent.getStringExtra("data")
        val gson = Gson()
        val data = gson.fromJson(sData, MarketFragment.MarketData::class.java)
        binding.setVariable(BR.mzdata, data)
        info{ sData }

        LinearLayoutManager(this).let {
            it.orientation = LinearLayout.VERTICAL
            marketZoomRecycler.layoutManager = it
        }
        LastAdapter(mMarketZoomDatas, BR.zdata).map<MarketZoomData>(Type<ItemMarketZoomBinding>(R.layout.item_market_zoom)).into(marketZoomRecycler)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId){
            android.R.id.home -> finish()
        }
        return true
    }
}
