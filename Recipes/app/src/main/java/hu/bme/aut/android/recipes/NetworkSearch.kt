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
import hu.bme.aut.android.recipes.networkData.Base
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
            getData(recipeAPI)
        }

        return fragmentBinding.root
    }


    private fun getData( recipeAPI: RecipeAPI ){

        val recipeCall = recipeAPI.getRecipe()

        recipeCall.enqueue(object : Callback<List<Base>> {


            override fun onFailure(call: Call<List<Base>>, t: Throwable) {
                fragmentBinding.searchedRecipeContent.text = t.message
            }


            override fun onResponse(call: Call<List<Base>>, response: Response<List<Base>>) {
                val recipeResult = response.body()

                fragmentBinding.searchedRecipeContent.text = "${recipeResult?.get(0)?.title}"
            }
        })
    }

}