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
import hu.bme.aut.android.recipes.Model.Recipe
import hu.bme.aut.android.recipes.RecipeApplication.Companion.fullList
import hu.bme.aut.android.recipes.databinding.RecipeItemBinding
import java.util.*

class RvAdapter(private val fragmentManager: FragmentManager, private  val activity: FragmentActivity?) : RecyclerView.Adapter<RvAdapter.RecipeViewHolder>(), DatePickerDialogFragment.OnDateSelectedListener {

     var itemClickListener: RecipeItemClickListener? = null
     val dateListener : DatePickerDialogFragment.OnDateSelectedListener = this

    private val recipesList = mutableListOf<Recipe?>()

    inner class RecipeViewHolder( binding: RecipeItemBinding) : RecyclerView.ViewHolder(binding.root){
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
               dateDialog.onDateSelectedListener = dateListener
               dateDialog.show(fragmentManager, "")
           }
        }

    }

    interface RecipeItemClickListener{
        fun onItemClick(recipe: Recipe)
        fun onItemLongClick(pos: Int, view: View, recipe: Recipe)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder =
        RecipeViewHolder(RecipeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))


    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = recipesList[position]
        holder.recipe = recipe
        holder.titleTextView.text = recipe?.title
        holder.categoryTextView.text = recipe?.category
        holder.dateTextView.text = recipe?.date
      //  holder.favouriteImageView = recipe.f
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
        recipesList.removeAt(pos)
        notifyDataSetChanged()
    }

    fun editRecipe(recipe: Recipe, pos: Int){
        recipesList[pos] = recipe
        notifyDataSetChanged()
    }

    fun addRecipe(newRecipe: Recipe?){
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

       val recipe = item?.let { Recipe(item.title, item.category, item.favourite, it.content, dataAsString) }

       if (recipe != null) {
           recipesList[pos] = recipe
       }
        notifyDataSetChanged()

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

}