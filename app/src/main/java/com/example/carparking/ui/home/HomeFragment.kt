package com.example.carparking.ui.home

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.viewModels
import com.example.carparking.R
import com.example.carparking.data.Resource
import com.example.carparking.data.remote.response.PlacesResponseItem
import com.example.carparking.databinding.FragmentHomeBinding
import com.example.carparking.model.ViewModelFactory
import com.example.carparking.ui.adapter.PlacesAdapter
import com.example.carparking.ui.adapter.RecentAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class HomeFragment : Fragment() {

    // Declaration for placesAdapter
    private lateinit var placesAdapter: PlacesAdapter

    // Declaration for binding view
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    // View Model initialization using delegate by viewModels
    private val homeViewModel: HomeViewModel by viewModels {
        ViewModelFactory.getInstance(
            requireContext(),
            requireContext().dataStore
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        homeViewModel.requestToken()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // HIDE Support Action bar on Home, Favorite and History navigation
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()

        // Declare Adapter for RecyclerView place and recent
        placesAdapter = PlacesAdapter()
        val recentAdapter = RecentAdapter()

        // Load token from local data store
        // This token for call private API in the server
        homeViewModel.loadTokenFromDataStore().observe(viewLifecycleOwner) { token ->

            // Load data from the server to get list of parking places
            // Response will be checking, if not null, data will be execute it based on the state
            homeViewModel.getAllParkingPlaces(token).observe(viewLifecycleOwner) { response ->
                if (response != null) {
                    when (response) {
                        is Resource.Loading -> onLoading()
                        is Resource.Success -> showResult(response.data)
                        is Resource.Error -> onError()
                    }
                }
            }
        }

        // Load data from the local database to get recent history parking place
        // Data will be checking, if empty, show the info and if not, show text info
        homeViewModel.getRecentParkingPlaces().observe(viewLifecycleOwner) { data ->
            if (data.isNotEmpty()) {
                recentAdapter.submitList(data)
                binding.ivEmptyRecent.isVisible = false
                binding.tvEmptyRecent.isVisible = false
            } else { // On empty data
                binding.ivEmptyRecent.isVisible = true
                binding.tvEmptyRecent.isVisible = true
            }
        }

        // Setup the adapter of Recycler View
        binding.rvLocation.adapter = placesAdapter
        binding.rvRecent.adapter = recentAdapter
    }

    /**
     * Handle state when response is Success
     * Hide the text info and put the data to the adapter
     * */
    private fun showResult(data: List<PlacesResponseItem>?) {
        if (data.isNullOrEmpty()) {
            showInfo(isProgressBarShow = false, isImageShow = true, isMessageShow = true)
            return
        }

        showInfo(isProgressBarShow = false, isImageShow = false, isMessageShow = false)
        placesAdapter.submitList(data)
    }

    /**
     * Handle state when response is Loading.
     * Show text info with the message
     * */
    private fun onLoading() {
        binding.tvMessageUnexpected.text = getString(R.string.carkir_home_info_loading)
        showInfo(isProgressBarShow = true, isImageShow = false)
    }

    /**
     * Handle state when response is Error.
     * Show text info with the message
     * */
    private fun onError() {
        binding.tvMessageUnexpected.text = getString(R.string.carkir_home_info_error)
        showInfo(isProgressBarShow = false, isImageShow = true)
    }

    /**
     * Show image, progress bar and text info
     * for state loading or error
     * */
    private fun showInfo(
        isProgressBarShow: Boolean,
        isImageShow: Boolean,
        isMessageShow: Boolean = true
    ) {
        binding.progressBar.isVisible = isProgressBarShow
        binding.ivErrorList.isVisible = isImageShow
        binding.tvMessageUnexpected.isVisible = isMessageShow
    }

    /**
     * Set _binding to null when fragment is close
     * to avoid memory-leak.
     * */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}