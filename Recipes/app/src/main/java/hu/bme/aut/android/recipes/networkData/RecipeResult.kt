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
data class Us(val value: Double?, val unit: String?)*/

/*
@JsonClass(generateAdapter = true)
data class Base(val id: Double, val imageType: String, val title: String, val readyInMinutes: Double, val servings: Double, val sourceUrl: String)*/

@JsonClass(generateAdapter = true)
data class AnalyzedInstructions1289973165(val name: String?, val steps: List<Steps389317355>?)

@JsonClass(generateAdapter = true)
data class Base(val vegetarian: Boolean?, val vegan: Boolean?, val glutenFree: Boolean?, val dairyFree: Boolean?, val veryHealthy: Boolean?, val cheap: Boolean?, val veryPopular: Boolean?, val sustainable: Boolean?, val weightWatcherSmartPoints: Double?, val gaps: String?, val lowFodmap: Boolean?, val aggregateLikes: Double?, val spoonacularScore: Double?, val healthScore: Double?, val creditsText: String?, val sourceName: String?, val pricePerServing: Double?, val extendedIngredients: List<ExtendedIngredients1847277050>?, val id: Double?, val title: String?, val author: String?, val readyInMinutes: Double?, val servings: Double?, val sourceUrl: String?, val image: String?, val imageType: String?, val summary: String?, val cuisines: List<Any>?, val dishTypes: List<String>?, val diets: List<String>?, val occasions: List<Any>?,  val instructions: String?, val analyzedInstructions: List<AnalyzedInstructions1289973165>?, val originalId: Any?)

@JsonClass(generateAdapter = true)
data class Equipment1630513721(val id: Double?, val name: String?, val localizedName: String?, val image: String?)

@JsonClass(generateAdapter = true)
data class Equipment63690568(val id: Double?, val name: String?, val localizedName: String?, val image: String?, val temperature: Temperature?)

@JsonClass(generateAdapter = true)
data class Equipment743652616(val id: Double?, val name: String?, val localizedName: String?, val image: String?)

@JsonClass(generateAdapter = true)
data class Equipment926704410(val id: Double?, val name: String?, val localizedName: String?, val image: String?)

@JsonClass(generateAdapter = true)
data class ExtendedIngredients1847277050(val id: Double?, val aisle: String?, val image: String?, val consistency: String?, val name: String?, val nameClean: String?, val original: String?, val originalString: String?, val originalName: String?, val amount: Double?, val unit: String?, val meta: List<String>?, val metaInformation: List<String>?, val measures: Measures?)

@JsonClass(generateAdapter = true)
data class Ingredients2019044944(val id: Double?, val name: String?, val localizedName: String?, val image: String?)

@JsonClass(generateAdapter = true)
data class Ingredients633414983(val id: Double?, val name: String?, val localizedName: String?, val image: String?)

@JsonClass(generateAdapter = true)
data class Length(val number: Double?, val unit: String?)

@JsonClass(generateAdapter = true)
data class Measures(val us: Us?, val metric: Metric?)

@JsonClass(generateAdapter = true)
data class Metric(val amount: Double?, val unitShort: String?, val unitLong: String?)

@JsonClass(generateAdapter = true)
data class Steps389317355(val number: Int?, val step: String?, val ingredients: List<Any>?, val equipment: List<Equipment743652616>?, val length: Length?)

@JsonClass(generateAdapter = true)
data class Temperature(val number: Double?, val unit: String?)

@JsonClass(generateAdapter = true)
data class Us(val amount: Double?, val unitShort: String?, val unitLong: String?)