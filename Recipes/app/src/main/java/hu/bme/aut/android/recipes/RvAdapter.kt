package hu.bme.aut.android.recipes

import android.Manifest
import android.content.ContentValues
import android.content.pm.PackageManager
import android.provider.CalendarContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import hu.bme.aut.android.recipes.Model.Recipe
import hu.bme.aut.android.recipes.RecipeApplication.Companion.fullList
import hu.bme.aut.android.recipes.databinding.RecipeItemBinding
import java.util.*

class RvAdapter(private val fragmentManager: FragmentManager, private  val activity: FragmentActivity?) : RecyclerView.Adapter<RvAdapter.RecipeViewHolder>(), DatePickerDialogFragment.OnDateSelectedListener {

     var itemClickListener: RecipeItemClickListener? = null
     val dateListener : DatePickerDialogFragment.OnDateSelectedListener = this

     val recipesList = mutableListOf<Recipe?>()

    inner class RecipeViewHolder( binding: RecipeItemBinding) : RecyclerView.ViewHolder(binding.root){
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

            itemView.setOnLongClickListener { view ->
                view.setBackgroundResource(R.drawable.clicked_recipe_item_background)
                recipe?.let { itemClickListener?.onItemLongClick(adapterPosition, view, it) }
                true
            }

            favouriteImageView.setOnClickListener{
                val oldRecipe = recipe
                when(recipe?.favourite){
                    false-> {
                        favouriteImageView.setImageResource(R.drawable.ic_baseline_star_rate_24)
                        recipe?.favourite = true
                        updateFavourite(recipe, true)
                    }
                    true ->{
                        favouriteImageView.setImageResource(R.drawable.ic_baseline_star_border_24)
                        recipe?.favourite = false
                        updateFavourite(recipe, false)
                    }

                }
                recipe?.let { it1 -> oldRecipe?.let { it2 -> itemClickListener?.onStarClicked(adapterPosition, it1, it2) } }


            }

           dateTextView.setOnClickListener{
               val dateDialog = DatePickerDialogFragment(recipe)
               dateDialog.onDateSelectedListener = dateListener
               dateDialog.show(fragmentManager, "")
           }
        }

    }

    interface RecipeItemClickListener{
        fun onItemClick(recipe: Recipe, pos: Int)
        fun onItemLongClick(pos: Int, view: View, recipe: Recipe)
        fun onStarClicked(pos: Int, recipe: Recipe, oldRecipe: Recipe)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder =
        RecipeViewHolder(RecipeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))


    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = recipesList[position]
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

    override fun getItemCount() = recipesList.size

    fun addAll(category: String){
        Log.i("number", fullList.size.toString())
        if(category.isEmpty()) {
            recipesList.clear()
            notifyDataSetChanged()
            for(r in fullList){
                    addRecipe(r)
            }
        }
        else{
            recipesList.clear()
            notifyDataSetChanged()
            for(r in fullList){
                if(r?.category?.contains(category, ignoreCase = true)!!)
                  addRecipe(r)
            }
        }
    }


    fun deleteRecipe(pos: Int){
        fullList.remove(recipesList[pos])
        recipesList.removeAt(pos)
        notifyDataSetChanged()
    }

    fun editRecipe(recipe: Recipe, pos: Int){
        val oldrecipe =  recipesList[pos]
        val position = fullList.indexOf(oldrecipe)
        fullList[position] = recipe
        recipesList[pos] = recipe
        notifyDataSetChanged()
    }

    fun addRecipe(newRecipe: Recipe?){
        if(recipesList.contains(newRecipe)) return
        recipesList.add(newRecipe)
        newRecipe?.title?.let { Log.i("add", it) }
        notifyItemChanged(recipesList.size)
    }

   override fun onDateSelected(year: Int, month: Int, day: Int, item: Recipe?) {
        val pos = recipesList.indexOf(item)
        val date = Calendar.getInstance()
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
        val dataAsString = "$year.$monthnulla$monthString.$nulla$day"

       val recipe = item?.let { Recipe(item.id, item.title, item.category, item.favourite, it.content, dataAsString) }

       if (recipe != null) {
           val oldrecipe =  recipesList[pos]
           val position = fullList.indexOf(oldrecipe)
           fullList[position] = recipe
           recipesList[pos] = recipe
       }
        notifyDataSetChanged()
       updateRecipeDate(recipe)
       saveInCalendar(recipe, date)
    }


    private fun saveInCalendar(item: Recipe?, date: Calendar){
        requestNeededPermission()
        try {
            val values = ContentValues()
            values.put(CalendarContract.Events.DTSTART, date.timeInMillis)
            values.put(CalendarContract.Events.DTEND, date.timeInMillis)
            values.put(CalendarContract.Events.TITLE, item?.title)
            values.put(CalendarContract.Events.DESCRIPTION, "Tárgy: ${item?.title}")
            values.put(CalendarContract.Events.CALENDAR_ID, 1)
            values.put(
                    CalendarContract.Events.EVENT_TIMEZONE,
                    TimeZone.getDefault().id
            )
            val uri = activity?.contentResolver?.insert(CalendarContract.Events.CONTENT_URI, values)
            Log.d("LOG", uri.toString())

        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }

    private fun requestNeededPermission() {
        if ( activity?.let {
                    ContextCompat.checkSelfPermission(it,
                            Manifest.permission.WRITE_CALENDAR)
                } != PackageManager.PERMISSION_GRANTED) {

            activity?.let {
                ActivityCompat.requestPermissions(it,
                        arrayOf( Manifest.permission.WRITE_CALENDAR),
                        101)
            }
        }
    }

    private fun updateRecipeDate(recipe: Recipe?) {
        val db = Firebase.firestore
        db.collection("recipes").document(recipe?.id.toString()).update( "date", recipe?.date)
    }


    private fun updateFavourite(recipe: Recipe?, value: Boolean) {
        val db = Firebase.firestore
        db.collection("recipes").document(recipe?.id.toString()).update( "favourite", value)
    }

}