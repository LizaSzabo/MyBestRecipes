package hu.bme.aut.android.recipes

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import hu.bme.aut.android.recipes.Model.Recipe
import hu.bme.aut.android.recipes.RecipeApplication.Companion.fullList
import hu.bme.aut.android.recipes.databinding.FragmentRecipesBinding

class RecipesFragment: Fragment(), RvAdapter.RecipeItemClickListener, EditRecipeDialog.EditRecipeListener, AddNewRecipeDialog.AddRecipeListener, DatePickerDialogFragment.OnDateSelectedListener {
    private lateinit var fragmentBinding: FragmentRecipesBinding
    lateinit var adapter: RvAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        fragmentBinding = FragmentRecipesBinding.inflate(inflater)

        (activity as AppCompatActivity).setSupportActionBar(fragmentBinding.toolbar)
        setHasOptionsMenu(true)

        fragmentBinding.addRecipe.setOnClickListener {
            val addRecipeDialog = AddNewRecipeDialog()
            addRecipeDialog.addRecipeListener = this
            addRecipeDialog.show(parentFragmentManager, "")
        }

        fragmentBinding.editTextSearch.doOnTextChanged { _, _, _, _ -> adapter.addAll(fragmentBinding.editTextSearch.text.toString()) }
        fullList.clear()
        initRecipesListener()
        return fragmentBinding.root
    }

    private fun initRecipesListener() {
        val db = Firebase.firestore
        db.collection("recipes")
                .addSnapshotListener { snapshots, e ->
                    if (e != null) {
                        Toast.makeText(activity, e.toString(), Toast.LENGTH_SHORT).show()
                        return@addSnapshotListener
                    }


                    for (dc in snapshots!!.documentChanges) {
                        when (dc.type) {
                            DocumentChange.Type.ADDED -> {
                                adapter.addRecipe(dc.document.toObject())
                                RecipeApplication.fullList.add(dc.document.toObject())
                            }
                            DocumentChange.Type.MODIFIED -> {
                            }
                            DocumentChange.Type.REMOVED -> {
                            }
                        }
                    }
                }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        adapter = RvAdapter(parentFragmentManager, activity)
        adapter.itemClickListener = this
        fragmentBinding.rvRecipes.layoutManager = LinearLayoutManager(context)
        fragmentBinding.rvRecipes.adapter = adapter
        adapter.itemClickListener = this
        adapter.addAll("")
    }

    override fun onItemClick(recipe: Recipe, pos: Int) {
        val action = recipe.title?.let { recipe.category?.let { it1 -> recipe.content?.let { it2 -> recipe.id?.let { it3 -> recipe.favourite?.let { it4 -> recipe.date?.let { it5 -> RecipesFragmentDirections.actionRecipesFragmentToDetailsFragment(it, it1, it2, it3, it4, it5, pos) } } } } } }
        if (action != null) {
            findNavController().navigate(action)
        }
    }

    override fun onItemLongClick(pos: Int, view: View, recipe: Recipe) {
        val popup = PopupMenu(context, view)
        popup.inflate(R.menu.options_menu)

        popup.setOnDismissListener {
            view.setBackgroundResource(R.drawable.recipe_item_background)
        }
        popup.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.delete -> {
                    removeRecipe(recipe)
                    adapter.deleteRecipe(pos)
                }
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
        //   adapter.addRecipe(recipe)
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

    private fun removeRecipe(recipe: Recipe) {

        val db = Firebase.firestore

        db.collection("recipes").document(recipe.id.toString()).delete()
                .addOnSuccessListener {
                    // Toast.makeText(activity, "recipe created", LENGTH_LONG).show()
                }
                .addOnFailureListener { e -> Toast.makeText(activity, e.toString(), Toast.LENGTH_SHORT).show() }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.LogOut) {
            val action = RecipesFragmentDirections.actionRecipesFragmentToLoginFragment()
            findNavController().navigate(action)
            FirebaseAuth.getInstance().signOut()
        } else if (item.itemId == R.id.Favourites) {
            val action = RecipesFragmentDirections.actionRecipesFragmentToFavouritesFragment()
            findNavController().navigate(action)

        }
        return super.onOptionsItemSelected(item)


    }
}