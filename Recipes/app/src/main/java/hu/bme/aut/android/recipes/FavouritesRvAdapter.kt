package hu.bme.aut.android.recipes


import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import hu.bme.aut.android.recipes.Model.Recipe
import hu.bme.aut.android.recipes.databinding.RecipeItemBinding

class FavouritesRvAdapter: RecyclerView.Adapter<FavouritesRvAdapter.FavouriteRecipeViewHolder>() {

    private val favouriteRecipesList = mutableListOf<Recipe?>()
    var itemClickListener: FavouriteRecipeItemClickListener? = null

    inner class FavouriteRecipeViewHolder( binding: RecipeItemBinding) : RecyclerView.ViewHolder(binding.root){
        val titleTextView: TextView = binding.tvRecipeTitle
        val categoryTextView: TextView = binding.tvRecipeCategory
        val favouriteImageView: ImageView = binding.ivFavourite
        val dateTextView: TextView = binding.tvDate

        var recipe : Recipe? = null

        init{
            itemView.setOnClickListener{
                recipe?.let{
                    itemClickListener?.onItemClick(it, adapterPosition)
                }
            }

            favouriteImageView.setOnClickListener{
                when(recipe?.favourite){
                    false-> {
                        favouriteImageView.setImageResource(R.drawable.ic_baseline_star_rate_24)
                        recipe?.favourite = true
                        notifyDataSetChanged()
                        updateFavourite(recipe, true)
                    }
                    true ->{
                        favouriteImageView.setImageResource(R.drawable.ic_baseline_star_border_24)
                        recipe?.favourite = false
                        favouriteRecipesList.removeAt(adapterPosition)
                        notifyDataSetChanged()
                        updateFavourite(recipe, false)
                    }

                }


            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouritesRvAdapter.FavouriteRecipeViewHolder =
            FavouriteRecipeViewHolder(RecipeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))


    override fun onBindViewHolder(holder: FavouritesRvAdapter.FavouriteRecipeViewHolder, position: Int) {
        val recipe = favouriteRecipesList[position]
        holder.recipe = recipe
        holder.titleTextView.text = recipe?.title
        holder.categoryTextView.text = recipe?.category
        holder.dateTextView.text = recipe?.date
        if(recipe?.favourite == true){
            holder.favouriteImageView.setImageResource(R.drawable.ic_baseline_star_rate_24)
        }
        else{
            holder.favouriteImageView.setImageResource(R.drawable.ic_baseline_star_border_24)
        }
    }

    override fun getItemCount() = favouriteRecipesList.size

    interface FavouriteRecipeItemClickListener{
        fun onItemClick(recipe: Recipe, pos: Int)
    }

    fun addAll() {
        favouriteRecipesList.clear()
        for (r in RecipeApplication.fullList) {
            if(r?.favourite == true){
                favouriteRecipesList.add(r)
                notifyItemChanged(favouriteRecipesList.size)
            }
        }
    }

    private fun updateFavourite(recipe: Recipe?, value: Boolean) {
        val db = Firebase.firestore
        db.collection("recipes").document(recipe?.id.toString()).update( "favourite", value)
    }

}