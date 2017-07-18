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
import com.todropfu.databinding.ItemBillBinding
import kotlinx.android.synthetic.main.fragment_bill.view.*

class BillFragment : Fragment() {
    data class BillData(val storeName: String, val title: String, val price: String, val year: String, val month: String, val day: String)

    val mBillData = ObservableArrayList<Any>().apply {
        add(BillData("따듯한 트럭", "트럭마을산 두부", "2,000", "2017", "5", "23"))
        add(BillData("따듯한 트럭", "트럭마을산 두부", "2,000", "2017", "5", "23"))
        add(BillData("따듯한 트럭", "트럭마을산 두부", "2,000", "2017", "5", "23"))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_bill, container, false)
        LinearLayoutManager(context).let {
            it.orientation = LinearLayout.VERTICAL
            view.billRecycler.layoutManager = it
        }
        LastAdapter(mBillData, BR.bill).map<BillData>(Type<ItemBillBinding>(R.layout.item_bill)).into(view.billRecycler)
        return view
    }

    companion object {
        fun newInstance(): BillFragment {
            val fragment = BillFragment()
            return fragment
        }
    }

}
