package com.todropfu

import android.databinding.ObservableArrayList
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.widget.LinearLayout
import com.github.nitrico.lastadapter.LastAdapter
import com.github.nitrico.lastadapter.Type
import com.todropfu.databinding.ItemCardBinding
import kotlinx.android.synthetic.main.activity_add_card.*

class AddCardActivity : AppCompatActivity() {
    data class CardData(val name: String, val num: String, val date: String)

    private val mCardItems = ObservableArrayList<Any>().apply {
        add(CardData("신한카드", "6233", "05/21"))
        add(CardData("신한카드", "6233", "05/21"))
        add(CardData("신한카드", "6233", "05/21"))
        add(CardData("신한카드", "6233", "05/21"))
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_card)

        setSupportActionBar(addCardToolbar)
        supportActionBar?.let{
            title = "카드 등록"
            it.setDisplayHomeAsUpEnabled(true)
            it.setDefaultDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.ic_home)
        }
        LinearLayoutManager(this@AddCardActivity).let {
            it.orientation = LinearLayout.VERTICAL
            addCardRecycler.layoutManager = it
        }
        LastAdapter(mCardItems, BR.card).map<CardData>(Type<ItemCardBinding>(R.layout.item_card)).into(addCardRecycler)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId){
            android.R.id.home -> finish()
        }
        return true
    }
}
