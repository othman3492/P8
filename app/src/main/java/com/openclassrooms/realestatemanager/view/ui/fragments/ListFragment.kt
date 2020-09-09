package com.openclassrooms.realestatemanager.view.ui.fragments


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.sqlite.db.SimpleSQLiteQuery
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.model.RealEstate
import com.openclassrooms.realestatemanager.viewmodel.Injection
import com.openclassrooms.realestatemanager.viewmodel.RealEstateViewModel
import com.openclassrooms.realestatemanager.view.ui.adapters.ElementAdapter
import kotlinx.android.synthetic.main.fragment_list.*


class ListFragment : Fragment(R.layout.fragment_list) {


    private lateinit var adapter: ElementAdapter

    private lateinit var realEstateViewModel: RealEstateViewModel


    companion object {
        fun newInstance(query: String?): ListFragment {

            val args = Bundle()
            args.putString("QUERY", query)

            val fragment = ListFragment()
            fragment.arguments = args
            return fragment
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureRecyclerView()
        configureViewModel()
        getElements()
    }


    // Configure RecyclerView and assign the click handler to the Adapter
    private fun configureRecyclerView() {

        adapter = ElementAdapter(requireContext()) { realEstate: RealEstate -> startDetailsOnClick(realEstate) }
        main_recycler_view.adapter = adapter
        main_recycler_view.layoutManager = LinearLayoutManager(activity)
        main_recycler_view.addItemDecoration(DividerItemDecoration(
                main_recycler_view.context, DividerItemDecoration.VERTICAL))
    }

    private fun configureViewModel() {

        val viewModelFactory = Injection.provideViewModelFactory(requireContext())
        realEstateViewModel = ViewModelProviders.of(this,
                viewModelFactory).get(RealEstateViewModel::class.java)
    }


    // Verify if there's a search query stored in bundle, and execute the right database query
    private fun getElements() {

        val query = requireArguments().getString("QUERY")

        if (query != null) {

            realEstateViewModel.getRealEstateFromUserSearch(SimpleSQLiteQuery(query)).observe(viewLifecycleOwner,
                    Observer<List<RealEstate>> { updateList(it) })

        } else {

            realEstateViewModel.getAllRealEstates().observe(viewLifecycleOwner,
                    Observer<List<RealEstate>> { updateList(it) })
        }
    }


    // Send updated data to adapter
    private fun updateList(list: List<RealEstate>) {

        this.adapter.updateData(list)
    }


    // Display DetailsFragment when clicked after verifying that device is tablet
    private fun startDetailsOnClick(realEstate: RealEstate) {

        val isTablet = resources.getBoolean(R.bool.isTablet)
        val fragment = DetailsFragment.newInstance(realEstate)

        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.addToBackStack(null)

        if (isTablet) {
            transaction.replace(R.id.details_fragment_container, fragment).commit()
        } else {
            transaction.replace(R.id.fragment_container, fragment).commit()
        }
    }

}



