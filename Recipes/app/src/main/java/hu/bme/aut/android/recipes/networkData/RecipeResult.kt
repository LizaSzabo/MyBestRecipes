package hu.bme.aut.android.recipes.networkData

import com.squareup.moshi.JsonClass
/*
@JsonClass(generateAdapter = true)
data class Amount(val metric: Metric?, val us: Us?)

@JsonClass(generateAdapter = true)
data class Base(val ingredients: List<Ingredients1182891732>?)

@JsonClass(generateAdapter = true)
data class Ingredients1182891732(val name: String?, val image: String?, val amount: Amount?)

@JsonClass(generateAdapter = true)
data class Metric(val value: Double?, val unit: String?)

@JsonClass(generateAdapter = true)
data class Us(val value: Double?, val unit: String?)
*/

@JsonClass(generateAdapter = true)
data class Base(val id: Double, val imageType: String, val title: String, val readyInMinutes: Double, val servings: Double, val sourceUrl: String)
