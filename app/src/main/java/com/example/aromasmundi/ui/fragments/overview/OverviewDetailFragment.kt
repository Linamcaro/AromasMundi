package com.example.aromasmundi.ui.fragments.overview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import coil.load
import com.example.aromasmundi.R
import com.example.aromasmundi.databinding.FragmentOverviewDetailBinding
import com.example.aromasmundi.models.Result
import com.example.aromasmundi.util.retrieveParcelable
import org.jsoup.Jsoup

class OverviewDetailFragment : Fragment() {

    private lateinit var overviewBinding: FragmentOverviewDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        overviewBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_overview_detail, container, false)

        val args = arguments
        val myBundle: Result? = args!!.retrieveParcelable("recipeBundle") as Result?

        overviewBinding.mainImageView.load(myBundle!!.image)
        overviewBinding.titleTextView.text = myBundle?.title
        overviewBinding.timeTextView.text = myBundle?.readyInMinutes.toString()
        overviewBinding.likesTextView.text = myBundle?.aggregateLikes.toString()
        myBundle.summary?.let {
            val summary = Jsoup.parse(it).text()
            overviewBinding.summaryTextView.text = summary
        }


        setColor(myBundle.vegetarian, overviewBinding.vegetarianImageView, overviewBinding.vegetarianTextView)
        setColor(myBundle.vegan, overviewBinding.veganImageView, overviewBinding.veganTextView)
        setColor(myBundle.glutenFree, overviewBinding.glutenFreeImageView, overviewBinding.glutenFreeTextView)
        setColor(myBundle.dairyFree, overviewBinding.dairyFreeImageView, overviewBinding.dairyFreeTextView)
        setColor(myBundle.veryHealthy, overviewBinding.healthyImageView, overviewBinding.healthyTextView)
        setColor(myBundle.cheap, overviewBinding.cheapImageView, overviewBinding.cheapTextView)

        return overviewBinding.root
    }

    fun setColor(state: Boolean, imageView: ImageView, textView: TextView){
        if (state){
            textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
            imageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
        }
    }

}