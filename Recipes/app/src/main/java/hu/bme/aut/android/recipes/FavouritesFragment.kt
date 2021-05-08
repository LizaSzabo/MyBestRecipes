package hu.bme.aut.android.recipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import hu.bme.aut.android.recipes.Model.Recipe
import hu.bme.aut.android.recipes.databinding.FragmentFavouriteBinding

class FavouritesFragment: Fragment(), FavouritesRvAdapter.FavouriteRecipeItemClickListener {
    private lateinit var fragmentBinding: FragmentFavouriteBinding
    lateinit var adapter: FavouritesRvAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        fragmentBinding = FragmentFavouriteBinding.inflate(inflater)

        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        adapter = FavouritesRvAdapter()
        adapter.itemClickListener = this
        fragmentBinding.rvRecipes.layoutManager = LinearLayoutManager(context)
        fragmentBinding.rvRecipes.adapter = adapter
        adapter.addAll()
    }

    override fun onItemClick(recipe: Recipe, pos: Int) {
        TODO("Not yet implemented")
    }


}