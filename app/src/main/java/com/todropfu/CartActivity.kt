package com.todropfu

import android.databinding.DataBindingUtil
import android.databinding.ObservableArrayList
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.todropfu.databinding.ItemCartBinding
import kotlinx.android.synthetic.main.activity_cart.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug
import org.jetbrains.anko.info
import org.jetbrains.anko.sdk25.coroutines.onClick
import kotlin.properties.Delegates

class CartActivity : AppCompatActivity(),AnkoLogger {

    class CartData(val title: String, val content: String, val price: String)
    private val mCartDatas = ObservableArrayList<Any>().apply {
        add(CartData("트럭마을산 두부", "트럭마을 아조시가 열심히 기른 두부입니다", "2,000"))
        add(CartData("트럭마을산 두부", "트럭마을 아조시가 열심히 기른 두부입니다", "2,000"))
        add(CartData("트럭마을산 두부", "트럭마을 아조시가 열심히 기른 두부입니다", "2,000"))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        setSupportActionBar(actCartToolbar)
        supportActionBar?.let {
            title = "장바구니"
            it.setDisplayHomeAsUpEnabled(true)
            it.setDefaultDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.ic_home)
        }
        val adapter = MyGridAdapter()
        actCartGrid.adapter = adapter
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId){
            android.R.id.home -> finish()
        }
        return true
    }

    inner class MyGridAdapter: BaseAdapter(){
        var binding by Delegates.notNull<ItemCartBinding>()
        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
            binding = DataBindingUtil.inflate(LayoutInflater.from(this@CartActivity), R.layout.item_cart, p2, false)
            binding.setVariable(BR.cart, mCartDatas[p0])
            binding.itemBtnDel.onClick {
                mCartDatas.removeAt(p0)
                debug{ mCartDatas }
                notifyDataSetChanged()
            }
            return binding.root
        }

        override fun getItem(p0: Int): Any {
            return mCartDatas[p0]
        }

        override fun getItemId(p0: Int): Long {
            return p0.toLong()
        }

        override fun getCount(): Int {
            return mCartDatas.size
        }

        fun addData(data: CartData){
            mCartDatas.add(data)
        }

    }
}
