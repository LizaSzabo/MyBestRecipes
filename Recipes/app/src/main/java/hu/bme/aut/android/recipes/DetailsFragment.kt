package hu.bme.aut.android.recipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
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
            Toast.makeText(context, "Recipe content saved!", LENGTH_LONG).show()
        }

        fragmentBinding.btnNetwork.setOnClickListener{
            navigateToNetworkSearch()
        }

        return fragmentBinding.root
    }

    private fun updateRecipeContent(recipe: Recipe?) {
        val db = Firebase.firestore
        db.collection("recipes").document(recipe?.id.toString()).update( "content", recipe?.content)
    }

    private fun navigateToNetworkSearch(){
        val action = DetailsFragmentDirections.actionDetailsFragmentToNetworkSearch()
        findNavController().navigate(action)
    }

}