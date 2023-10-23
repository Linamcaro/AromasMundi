package com.example.aromasmundi.ui.fragments.feed

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.aromasmundi.R
import com.example.aromasmundi.databinding.FragmentFeedBinding
import com.facebook.shimmer.BuildConfig


class FeedFragment : Fragment() {

    private lateinit var recepiesBinding: FragmentFeedBinding
            override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
                recepiesBinding = DataBindingUtil.inflate(inflater,
                    R.layout.fragment_feed, container, false)



                return recepiesBinding.root
    }
}