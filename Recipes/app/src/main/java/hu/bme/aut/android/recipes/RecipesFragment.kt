package hu.bme.aut.android.recipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import hu.bme.aut.android.recipes.databinding.FragmentRecipesBinding

class RecipesFragment: Fragment() {
    private lateinit var fragmentBinding: FragmentRecipesBinding
    private lateinit var adapter : RvAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        fragmentBinding = FragmentRecipesBinding.inflate(inflater, container, false)

        initRecyclerView()
        return fragmentBinding.root
    }

    private fun initRecyclerView(){
        adapter = RvAdapter()
        fragmentBinding.rvRecipes.layoutManager = LinearLayoutManager( context)
        fragmentBinding.rvRecipes.adapter = adapter

    }

}