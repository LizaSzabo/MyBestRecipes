package hu.bme.aut.android.recipes

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.datatransport.runtime.dagger.Component
import hu.bme.aut.android.recipes.network.RecipeAPI
import hu.bme.aut.android.recipes.databinding.FragmentNetworkSearchBinding
import hu.bme.aut.android.recipes.networkData.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


class NetworkSearch : Fragment() {
    private lateinit var fragmentBinding : FragmentNetworkSearchBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        fragmentBinding = FragmentNetworkSearchBinding.inflate(inflater, container, false)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.spoonacular.com/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        val recipeAPI = retrofit.create(RecipeAPI::class.java)

        fragmentBinding.btnSearch.setOnClickListener{
            fragmentBinding.searchedRecipeContent.setText("Recipe loading..")
            getData(recipeAPI)
        }

        return fragmentBinding.root
    }


    private fun getData( recipeAPI: RecipeAPI ){

        val recipeCall = recipeAPI.getRecipe()

        recipeCall.enqueue(object : Callback<Base> {


            override fun onFailure(call: Call<Base>, t: Throwable) {
                fragmentBinding.searchedRecipeContent.setText(t.message)
            }


            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<Base>, response: Response<Base>) {
                val recipeResult = response.body()
                fragmentBinding.searchedRecipeTitle.text = "${recipeResult?.title}"
                fragmentBinding.searchedRecipeContent.setText("Ingredients:")
                fragmentBinding.searchedRecipeContent.append("\n")

                val ingredientsList: List<ExtendedIngredients1847277050>? = recipeResult?.extendedIngredients
                if (ingredientsList != null) {
                    for(index in 0 until ingredientsList.size){
                        fragmentBinding.searchedRecipeContent.append(ingredientsList[index].name)
                        fragmentBinding.searchedRecipeContent.append("  ")
                        fragmentBinding.searchedRecipeContent.append(ingredientsList[index].amount.toString())
                        fragmentBinding.searchedRecipeContent.append("  ")
                        fragmentBinding.searchedRecipeContent.append(ingredientsList[index].unit)
                        fragmentBinding.searchedRecipeContent.append("\n")
                    }
                }

                fragmentBinding.searchedRecipeContent.append("\n")
                fragmentBinding.searchedRecipeContent.append("Steps:")
                fragmentBinding.searchedRecipeContent.append("\n")
                val instructions: List<AnalyzedInstructions1289973165>? = recipeResult?.analyzedInstructions
                val steps :  List<Steps389317355>? = instructions?.get(0)?.steps
                if (steps != null) {
                    for(index in 0 until steps.size){
                        fragmentBinding.searchedRecipeContent.append(steps[index].number.toString())
                        fragmentBinding.searchedRecipeContent.append(". ")
                        fragmentBinding.searchedRecipeContent.append(steps[index].step)
                        fragmentBinding.searchedRecipeContent.append("\n")
                    }
                }

            }
        })
    }

}