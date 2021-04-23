package hu.bme.aut.android.recipes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.recipes.Model.Recipe
import hu.bme.aut.android.recipes.databinding.RecipeItemBinding

class RvAdapter : RecyclerView.Adapter<RvAdapter.RecipeViewHolder>()  {

     var itemClickListener: RecipeItemClickListener? = null

    private val recipesList = mutableListOf<Recipe>(
        Recipe("recipe1", "category", true, "aaaaaaaaa"),
        Recipe("recipe2", "category1", true, "aaaaaaaaa"),
        Recipe("recipe3", "category1", true, "aaaaaaaaa"))

    inner class RecipeViewHolder(val binding: RecipeItemBinding) : RecyclerView.ViewHolder(binding.root){
        val titleTextView: TextView = binding.tvRecipeTitle
        val categoryTextView: TextView = binding.tvRecipeCategory
        val favouriteImageView: ImageView = binding.ivFavourite

        var recipe : Recipe? = null

        init{
            itemView.setOnClickListener{
                recipe?.let{
                    itemClickListener?.onItemClick(it)
                }
            }

            itemView.setOnLongClickListener { view ->
                view.setBackgroundResource(R.drawable.clicked_recipe_item_background)
                recipe?.let { itemClickListener?.onItemLongClick(adapterPosition, view, it) }
                true
            }
        }

    }

    interface RecipeItemClickListener{
       // fun onItemChanged(item: ShoppingItem)
      //  fun onItemDeleted(item: ShoppingItem)
        fun onItemClick(recipe: Recipe)
        fun onItemLongClick(pos: Int, view: View, recipe: Recipe)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder =
        RecipeViewHolder(RecipeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))


    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = recipesList[position]
        holder.recipe = recipe
        holder.titleTextView.text = recipe.title
        holder.categoryTextView.text = recipe.category
      //  holder.favouriteImageView = recipe.f
    }

    override fun getItemCount() = recipesList.size

    fun addAll(){
        val rec = mutableListOf<Recipe>(
            Recipe("recipe1", "category", true, "aaaaaaaaa"),
            Recipe("recipe2", "category1", true, "aaaaaaaaa"),
            Recipe("recipe3", "category1", true, "aaaaaaaaa"))
        recipesList.addAll(0, rec)
        notifyDataSetChanged()
    }

    fun deleteRecipe(pos: Int){
        recipesList.removeAt(pos)
        notifyDataSetChanged()
    }

    fun editRecipe(recipe: Recipe){

    }
}