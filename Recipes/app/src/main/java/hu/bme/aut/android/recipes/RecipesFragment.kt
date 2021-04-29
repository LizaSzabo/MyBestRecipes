package hu.bme.aut.android.recipes

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import hu.bme.aut.android.recipes.Model.Recipe
import hu.bme.aut.android.recipes.databinding.FragmentRecipesBinding

class RecipesFragment: Fragment(), RvAdapter.RecipeItemClickListener, EditRecipeDialog.EditRecipeListener, AddNewRecipeDialog.AddRecipeListener, DatePickerDialogFragment.OnDateSelectedListener  {
    private lateinit var fragmentBinding: FragmentRecipesBinding
    private lateinit var adapter : RvAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        fragmentBinding = FragmentRecipesBinding.inflate(inflater)

        fragmentBinding.addRecipe.setOnClickListener{
            val addRecipeDialog = AddNewRecipeDialog()
            addRecipeDialog.addRecipeListener = this
            addRecipeDialog.show(parentFragmentManager, "")
        }

        fragmentBinding.editTextSearch.doOnTextChanged { _, _, _, _ -> adapter.addAll(fragmentBinding.editTextSearch.text.toString())  }

        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecyclerView()
    }

    private fun setupRecyclerView(){
        adapter = RvAdapter(parentFragmentManager, activity)
        adapter.itemClickListener = this
        fragmentBinding.rvRecipes.layoutManager = LinearLayoutManager(context)
        fragmentBinding.rvRecipes.adapter = adapter
        adapter.itemClickListener = this
        adapter.addAll("")
    }

    override fun onItemClick(recipe: Recipe) {
        Log.i("click", "click")
        val action = RecipesFragmentDirections.actionRecipesFragmentToDetailsFragment(recipe.title, recipe.category, recipe.content)
        findNavController().navigate(action)
    }

    override fun onItemLongClick(pos: Int, view: View, recipe: Recipe) {
        val popup = PopupMenu(context, view)
        popup.inflate(R.menu.options_menu)

        popup.setOnDismissListener {
            view.setBackgroundResource(R.drawable.recipe_item_background)
        }
        popup.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.delete -> adapter.deleteRecipe(pos)
                R.id.edit -> {
                    val recipeDialog = EditRecipeDialog(pos, recipe)
                    recipeDialog.listener = this
                    recipeDialog.show(parentFragmentManager, "")
                }
            }

            false
        }
        popup.show()
    }

    override fun onRecipeEdited(recipe: Recipe, pos: Int) {
        adapter.editRecipe(recipe, pos)
    }

    override fun onNewRecipe(recipe: Recipe) {
       adapter.addRecipe(recipe)
    }

    override fun onDateSelected(year: Int, month: Int, day: Int, item: Recipe?) {
        adapter.onDateSelected(year, month, day, item)
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            101 -> {
                if (grantResults.isNotEmpty()
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    Toast.makeText(activity, "Permissions granted", Toast.LENGTH_SHORT)
                            .show()
                } else {
                    Toast.makeText(
                            activity,
                            "Permissions are NOT granted", Toast.LENGTH_SHORT
                    ).show()
                }
                return
            }
        }
    }
}