package hu.bme.aut.android.recipes.network

import hu.bme.aut.android.recipes.networkData.Base
import retrofit2.Call
import retrofit2.http.*

interface RecipeAPI {

    @GET("recipes/{id}/information?apiKey=17bf067da9884f3e9e17e3d41781b1e6")
    fun getRecipe(@Path("id")  id: String ) : Call<Base>
}