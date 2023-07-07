package com.batuhanmercan.kotlincountriesapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.batuhanmercan.kotlincountriesapp.CountryFragmentArgs
import com.batuhanmercan.kotlincountriesapp.databinding.FragmentCountryBinding
import com.batuhanmercan.kotlincountriesapp.viewmodel.CountryViewModel
import kotlinx.android.synthetic.main.fragment_country.countryCapital
import kotlinx.android.synthetic.main.fragment_country.countryCurrency
import kotlinx.android.synthetic.main.fragment_country.countryLanguage
import kotlinx.android.synthetic.main.fragment_country.countryName
import kotlinx.android.synthetic.main.fragment_country.countryRegion


class CountryFragment : Fragment() {
    private var _binding : FragmentCountryBinding? = null
    private val binding get() = _binding!!
    private var countryUuid = 0
    private lateinit var viewModel : CountryViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding =FragmentCountryBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//viewmodel tanımalaması
        viewModel = ViewModelProviders.of(this).get(CountryViewModel::class.java)
        viewModel.getDataFromRoom()


        arguments?.let{
        countryUuid = CountryFragmentArgs.fromBundle(it).countryUuid
        }
        observeLiveData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding =null
    }
    private fun observeLiveData(){
        viewModel.countryLiveData.observe(viewLifecycleOwner, Observer {country->
            country?.let {
                countryName.text = country.countryName
                countryCapital.text = country.countryCapital
                countryCurrency.text = country.countryCurrency
                countryLanguage.text = country.countryLanguage
                countryRegion.text = country.countryRegion
            }

        })
    }

}