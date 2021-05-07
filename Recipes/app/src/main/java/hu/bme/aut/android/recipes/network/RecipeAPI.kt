package hu.bme.aut.android.recipes.network

import hu.bme.aut.android.recipes.networkData.Base
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface RecipeAPI {

    @GET("recipes/715538/similar?apiKey=17bf067da9884f3e9e17e3d41781b1e6")
    fun getRecipe( ) : Call<List<Base>>
}