package hu.bme.aut.android.recipes.networkData

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Amount(val metric: Metric?, val us: Us?)



@JsonClass(generateAdapter = true)
data class Base(val name: String?, val image: String?, val amount: Amount?)

@JsonClass(generateAdapter = true)
data class Metric(val value: Double?, val unit: String?)

@JsonClass(generateAdapter = true)
data class Us(val value: Double?, val unit: String?)