package com.example.myviewpager

import android.os.Bundle
import android.provider.AlarmClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class MySecondFragment : Fragment() {


    companion object {
        fun newInstance(message: String): MySecondFragment {

            val f = MySecondFragment()
            val bdl = Bundle(1)
            bdl.putString(AlarmClock.EXTRA_MESSAGE, message)
            f.arguments = bdl
            return f
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.mysecondfragment, container, false)
    }
}