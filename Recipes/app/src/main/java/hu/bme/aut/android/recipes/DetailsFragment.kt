package hu.bme.aut.android.recipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import hu.bme.aut.android.recipes.Model.Recipe
import hu.bme.aut.android.recipes.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment(){
    private lateinit var fragmentBinding: FragmentDetailsBinding
    val args: DetailsFragmentArgs by navArgs()
    private lateinit var adapter : RvAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        fragmentBinding =  FragmentDetailsBinding.inflate(inflater, container, false)
        adapter = RvAdapter(parentFragmentManager, activity)

        fragmentBinding.tvRecipeTitleDetail.text = args.recipeTitle
        fragmentBinding.tvContent.setText(args.recipeContent)

        fragmentBinding.btnSave.setOnClickListener{
            val recipe = Recipe(args.recipeId, args.recipeTitle, args.recipeCategory, args.recipeFavourite,  fragmentBinding.tvContent.text.toString(), args.recipeDate)
            updateRecipeContent(recipe)

        }

        return fragmentBinding.root
    }

    private fun updateRecipeContent(recipe: Recipe?) {
        val db = Firebase.firestore
        db.collection("recipes").document(recipe?.id.toString()).update( "content", recipe?.content)
    }

}