package hu.bme.aut.android.recipes

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.recipes.Model.Recipe
import hu.bme.aut.android.recipes.databinding.RecipeItemBinding
import java.util.*

class RvAdapter(val fragmentManager: FragmentManager) : RecyclerView.Adapter<RvAdapter.RecipeViewHolder>(), DatePickerDialogFragment.OnDateSelectedListener {

     var itemClickListener: RecipeItemClickListener? = null
        val dateListener : DatePickerDialogFragment.OnDateSelectedListener? = this

    private val recipesList = mutableListOf<Recipe>(
        Recipe("recipe1", "category", true, "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "2010-12-13"),
        Recipe("recipe2", "category1", true, "aaaaaaaaa", "2010-12-13"),
        Recipe("recipe3", "category1", true, "aaaaaaaaa", "2010-12-13"))

    inner class RecipeViewHolder(val binding: RecipeItemBinding) : RecyclerView.ViewHolder(binding.root){
        val titleTextView: TextView = binding.tvRecipeTitle
        val categoryTextView: TextView = binding.tvRecipeCategory
        val favouriteImageView: ImageView = binding.ivFavourite
        val dateTextView: TextView = binding.tvDate

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

            favouriteImageView.setOnClickListener{
                when(recipe?.favourite){
                    false-> {
                        favouriteImageView.setImageResource(R.drawable.ic_baseline_star_rate_24)
                        recipe?.favourite = true
                    }
                    true ->{
                        favouriteImageView.setImageResource(R.drawable.ic_baseline_star_border_24)
                        recipe?.favourite = false
                    }

                }
            }

           dateTextView.setOnClickListener{
               val dateDialog = DatePickerDialogFragment(recipe)
               if (dateListener != null) {
                   dateDialog.onDateSelectedListener = dateListener
               }
               dateDialog.show(fragmentManager, "")
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
        holder.dateTextView.text = recipe.date
      //  holder.favouriteImageView = recipe.f
    }

    override fun getItemCount() = recipesList.size

    fun addAll(){
        val rec = mutableListOf<Recipe>(
            Recipe("recipe1", "category", true, "aaaaaaaaa", "2010-12-13"),
            Recipe("recipe2", "category1", true, "aaaaaaaaa", "2010-12-13"),
            Recipe("recipe3", "category1", true, "aaaaaaaaa", "2010-12-13"))
        recipesList.addAll(0, rec)
        notifyDataSetChanged()
    }

    fun deleteRecipe(pos: Int){
        recipesList.removeAt(pos)
        notifyDataSetChanged()
    }

    fun editRecipe(recipe: Recipe, pos: Int){
        recipesList[pos] = recipe
        notifyDataSetChanged()
    }

    fun addRecipe(newRecipe: Recipe){
        recipesList.add(newRecipe)
        notifyItemChanged(recipesList.size)
    }

   override fun onDateSelected(year: Int, month: Int, day: Int, item: Recipe?) {
        val pos = recipesList.indexOf(item)
        var date = Calendar.getInstance()
        date.set(year, month, day)
        val monthString = month +1
        var nulla = ""
        var monthnulla = ""
        if(day < 10) {
            nulla = "0"
        }
        if(month < 9){
            monthnulla = "0"
        }
        val dataAsString = year.toString()+ "."+monthnulla+ monthString.toString() + "."+nulla+ day.toString()

       val recipe = item?.let { Recipe(item?.title, item?.category, item?.favourite, it?.content, dataAsString) }

       if (recipe != null) {
           recipesList[pos] = recipe
       }
        notifyDataSetChanged()
    }
}