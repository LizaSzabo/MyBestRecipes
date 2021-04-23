package hu.bme.aut.android.recipes

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.recipes.Model.Recipe
import hu.bme.aut.android.recipes.databinding.FragmentRecipesBinding

class RecipesFragment: Fragment(), RvAdapter.RecipeItemClickListener {
    private lateinit var fragmentBinding: FragmentRecipesBinding
    private lateinit var adapter : RvAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentBinding = FragmentRecipesBinding.inflate(inflater)
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecyclerView()
    }

    private fun setupRecyclerView(){
        adapter = RvAdapter()
        adapter.itemClickListener = this
        fragmentBinding.rvRecipes.layoutManager = LinearLayoutManager(context)
        fragmentBinding.rvRecipes.adapter = adapter
        adapter.itemClickListener = this

    }

    override fun onItemClick(recipe: Recipe) {
        Log.i("click", "click")
        val action = RecipesFragmentDirections.actionRecipesFragmentToDetailsFragment()
        findNavController().navigate(action)
    }

    override fun onItemLongClick(pos: Int, view: View, recipe: Recipe) {
        val popup = PopupMenu(context, view)
        popup.inflate(R.menu.options_menu)

        popup.setOnDismissListener(){
            view.setBackgroundResource(R.drawable.recipe_item_background)
        }
        popup.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.delete -> adapter.deleteRecipe(pos)
                R.id.edit -> {
                    val recipeDialog = EditRecipeDialog(pos)
                   // recipe.listener = this
                    recipeDialog.show(parentFragmentManager, "")
                }
            }

            false
        }
        popup.show()
    }

}