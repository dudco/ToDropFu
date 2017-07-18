package com.todropfu

import android.Manifest
import android.content.pm.PackageManager
import android.databinding.DataBindingUtil
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.todropfu.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_toolbar.view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivity
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity(), AnkoLogger {

    data class Tabs(var isHomeSelected: Boolean, var isMarketSelected: Boolean, var isBillSelected: Boolean, var isProfileSelected: Boolean)

    private val tabs = Tabs(false, true, false, false)

    private var mBainding by Delegates.notNull<ActivityMainBinding>()
    private val mTabs by lazy{
        arrayOf(tabHome, tabMarket, tabBill, tabProfile)
    }

    companion object {
        const val TABSPACE = 0.075
        const val TABSIZE = 0.1
        /**
         * TAB 1 = 0.09
         * TAB 2 = 0.09 + 0.068 + 0.09 + 0.09
         * TAB 3 = 0.09 + 0.068 + 0.09 + 0.09 + 0.068 + 0.09 + 0.09
         * TAB 4 =
         **/
        const val TABSBETWEEN = TABSPACE * 2 + TABSIZE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        debug { Util.getPref(this@MainActivity, "init", false) }

        if (VERSION.SDK_INT >= VERSION_CODES.M) {
            if( checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                    checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION), 200)
            }
        }

        mBainding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setSupportActionBar(mainToolbar)
        supportActionBar?.let{
            it.setDisplayShowCustomEnabled(true)
            val view = LayoutInflater.from(this).inflate(R.layout.item_toolbar, null)
            view.cartBtn.onClick {
                startActivity<CartActivity>()
            }
            it.setCustomView(view, ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT))
        }

        mBainding.setVariable(BR.main, tabs)

        pager.offscreenPageLimit = 4
        pager.adapter = MyPagerAdapter(supportFragmentManager)

        pager.addOnPageChangeListener(object: ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                val params = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, (TABSPACE + TABSBETWEEN * (positionOffset + position)).toFloat())
                main_tab_indi_margin.layoutParams = params

                val currentTab = Math.round(positionOffset + position)

                tabChange(currentTab)

            }

            override fun onPageSelected(position: Int) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
        for(i in mTabs.indices) mTabs[i].onClick{
            pager.currentItem = i
            tabChange(i)
        }
    }

    fun tabChange(cTab: Int){

        tabs.run{
            isHomeSelected = false
            isMarketSelected = false
            isBillSelected = false
            isProfileSelected =false
        }

        when(cTab){
            0 -> tabs.isHomeSelected = true
            1 -> tabs.isMarketSelected = true
            2 -> tabs.isBillSelected = true
            3 -> tabs.isProfileSelected = true
        }
        mBainding.setVariable(BR.main, tabs)
    }

    inner class MyPagerAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
//            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            when(position){
                0 -> return HomeFragment.newInstance()
                1 -> return MarketFragment.newInstance()
                2 -> return BillFragment.newInstance()
                3 -> return ProfileFragment.newInstance()
            }
            return HomeFragment.newInstance()
        }

        override fun getCount(): Int {
//            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            return 4
        }

    }

}
