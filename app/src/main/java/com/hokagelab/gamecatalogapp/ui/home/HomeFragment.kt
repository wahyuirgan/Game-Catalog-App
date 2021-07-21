package com.hokagelab.gamecatalogapp.ui.home

import com.hokagelab.gamecatalogapp.core.data.Resource
import com.hokagelab.gamecatalogapp.core.ui.GameAdapter
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.hokagelab.gamecatalogapp.databinding.FragmentHomeBinding
import com.hokagelab.gamecatalogapp.ui.detail.DetailGameActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModel()
    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding

    private val gameAdapter = GameAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            gameAdapter.onItemClick = { selectedData ->
                val intent = Intent(activity, DetailGameActivity::class.java)
                intent.putExtra(DetailGameActivity.EXTRA_DATA, selectedData)
                startActivity(intent)
            }

            with(binding?.rvGame) {
                this?.layoutManager = GridLayoutManager(context, 1)
                this?.setHasFixedSize(true)
                this?.adapter = gameAdapter
            }
        }

        binding?.fabBackTop?.setOnClickListener {
            binding?.rvGame?.smoothScrollToPosition(0)
        }

        homeViewModel.games.observe(viewLifecycleOwner, { games ->
            if (games != null) {
                when (games) {
                    is Resource.Error -> {
                        binding?.mainProgressBar?.visibility = View.GONE
                        Toast.makeText(activity, "Something went wrong", Toast.LENGTH_SHORT).show()
                    }
                    is Resource.Loading -> binding?.mainProgressBar?.visibility = View.VISIBLE
                    is Resource.Success -> {
                        binding?.mainProgressBar?.visibility = View.GONE
                        gameAdapter.setData(games.data)
                    }
                    else -> binding?.mainProgressBar?.visibility = View.GONE
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}