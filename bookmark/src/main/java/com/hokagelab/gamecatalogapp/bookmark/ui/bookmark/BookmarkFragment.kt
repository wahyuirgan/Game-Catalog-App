package com.hokagelab.gamecatalogapp.bookmark.ui.bookmark

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.hokagelab.gamecatalogapp.bookmark.databinding.FragmentBookmarkBinding
import com.hokagelab.gamecatalogapp.bookmark.di.BookmarkModule.bookmarkModule
import com.hokagelab.gamecatalogapp.core.ui.GameAdapter
import com.hokagelab.gamecatalogapp.ui.detail.DetailGameActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules

class BookmarkFragment : Fragment() {

    private val bookmarkViewModel: BookmarkViewModel by viewModel()

    private var _binding: FragmentBookmarkBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBookmarkBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadKoinModules(bookmarkModule)

        if (activity != null) {

            binding?.mainProgressBar?.visibility = View.VISIBLE

            val gameAdapter = GameAdapter()
            gameAdapter.onItemClick = { bookmarkData ->
                val intent = Intent(activity, DetailGameActivity::class.java)
                intent.putExtra(DetailGameActivity.EXTRA_DATA, bookmarkData)
                startActivity(intent)
            }

            bookmarkViewModel.bookmarkGames.observe(viewLifecycleOwner, { game ->
                binding?.mainProgressBar?.visibility = View.GONE
                gameAdapter.setData(game)
                binding?.baseNoBookmarkGame?.root?.visibility = if (game.isNotEmpty()) View.GONE else View.VISIBLE
            })

            with(binding?.rvBookmarkGame) {
                this?.layoutManager = GridLayoutManager(context, 1)
                this?.setHasFixedSize(true)
                this?.adapter = gameAdapter
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding?.rvBookmarkGame?.adapter = null
        _binding = null
        unloadKoinModules(bookmarkModule)
    }
}