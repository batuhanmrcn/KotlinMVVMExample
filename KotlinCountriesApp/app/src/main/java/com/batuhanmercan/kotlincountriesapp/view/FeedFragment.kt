package com.batuhanmercan.kotlincountriesapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.batuhanmercan.kotlincountriesapp.adapter.CountryAdapter
import com.batuhanmercan.kotlincountriesapp.databinding.FragmentCountryBinding
import com.batuhanmercan.kotlincountriesapp.databinding.FragmentFeedBinding
import com.batuhanmercan.kotlincountriesapp.model.Country
import com.batuhanmercan.kotlincountriesapp.viewmodel.FeedViewModel
import kotlinx.android.synthetic.main.fragment_feed.countryError
import kotlinx.android.synthetic.main.fragment_feed.countryList
import kotlinx.android.synthetic.main.fragment_feed.countryLoading


class FeedFragment : Fragment() {
    private var _binding : FragmentFeedBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel : FeedViewModel
    private val countryAdapter : CountryAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? { // Inflate the layout for this fragment
        _binding = FragmentFeedBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this.get(FeedViewModel::class.java))
        viewModel.refreshData()

        countryList.layoutManager = LinearLayoutManager(context)
        countryList.adapter = countryAdapter


    }

    fun observeLiveData(){
        viewModel.countries.observe(viewLifecycleOwner, Observer {countries ->
            countries?.let {
                countryList.visibility = View.VISIBLE
                countryAdapter.updateCountryList(countries)
            }
         })
        viewModel.countryError.observe(viewLifecycleOwner, Observer {error ->
            error?.let {
                if (it){
                    //Eğer doğru ise demek
                    countryError.visibility = View.VISIBLE
                } else {
                    countryError.visibility = View.GONE
                }
            }
        })
        viewModel.countryLoading.observe(viewLifecycleOwner, Observer { loading ->
            loading?.let {
            if (it){
                countryLoading.visibility = View.VISIBLE
            }
            }

        })
    }
}