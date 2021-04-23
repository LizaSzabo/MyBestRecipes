package hu.bme.aut.android.recipes

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.recipes.Model.Recipe
import hu.bme.aut.android.recipes.databinding.RecipeItemBinding

class RvAdapter() : RecyclerView.Adapter<RvAdapter.RecipeViewHolder>()  {

    private val recipesList = mutableListOf<Recipe>(
        Recipe("recipe1", "category", true, "aaaaaaaaa"),
        Recipe("recipe2", "category1", true, "aaaaaaaaa"),
        Recipe("recipe3", "category1", true, "aaaaaaaaa"))

    inner class RecipeViewHolder(val binding: RecipeItemBinding) : RecyclerView.ViewHolder(binding.root){
        val titleTextView: TextView = binding.tvRecipeTitle
        val categoryTextView: TextView = binding.tvRecipeCategory
        val favouriteImageView: ImageView = binding.ivFavourite

        var recipe : Recipe? = null

    }

    interface RecipeItemClickListener{
       // fun onItemChanged(item: ShoppingItem)
      //  fun onItemDeleted(item: ShoppingItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder =
        RecipeViewHolder(RecipeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))


    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = recipesList[position]
        holder.titleTextView.text = recipe.title
        holder.categoryTextView.text = recipe.category
      //  holder.favouriteImageView = recipe.f
    }

    override fun getItemCount() = recipesList.size
}