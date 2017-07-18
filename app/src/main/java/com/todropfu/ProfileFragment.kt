package com.todropfu

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.todropfu.databinding.FragmentProfileBinding
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.startActivity
import kotlin.properties.Delegates


class ProfileFragment : Fragment() {
    data class UserData(val name: String, val Home: String)

    var binding: FragmentProfileBinding by Delegates.notNull<FragmentProfileBinding>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate<FragmentProfileBinding>(inflater, R.layout.fragment_profile, container, false)
        binding.setVariable(BR.udata, UserData("두부넘버원", "서울특별시 용산구 청파동3가 원효로97길 33-4"))

        binding.btnAddCard.onClick {
            startActivity<AddCardActivity>()
        }
        return binding.root
    }

    companion object {

        fun newInstance(): ProfileFragment {
            val fragment = ProfileFragment()
            return fragment
        }
    }

}
