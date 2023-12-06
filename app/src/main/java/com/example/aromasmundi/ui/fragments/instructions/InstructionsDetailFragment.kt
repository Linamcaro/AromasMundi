package com.example.aromasmundi.ui.fragments.instructions

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import com.example.aromasmundi.R
import com.example.aromasmundi.databinding.FragmentInstructionsDetailBinding
import com.example.aromasmundi.models.Result
import com.example.aromasmundi.util.Constants
import com.example.aromasmundi.util.retrieveParcelable


class InstructionsDetailFragment : Fragment() {

    private lateinit var instructionsBinding: FragmentInstructionsDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

         instructionsBinding = DataBindingUtil.inflate(inflater,
             R.layout.fragment_instructions_detail,
             container,
             false)

        val args = arguments
        val myBundle: Result? = args?.retrieveParcelable(Constants.RECIPE_RESULT_KEY)

        instructionsBinding.instructionsWebView.webViewClient = object: WebViewClient(){}
        //convert the website url to a string
        val websiteUrl: String = myBundle!!.sourceUrl
        //load the website
        instructionsBinding.instructionsWebView.loadUrl(websiteUrl)


        return instructionsBinding.root
    }
}