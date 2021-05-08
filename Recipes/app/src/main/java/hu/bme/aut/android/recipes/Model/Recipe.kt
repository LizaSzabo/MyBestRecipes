package hu.bme.aut.android.recipes.Model


data class Recipe (
        var id: String? = null,
    var title: String? = null,
    var category: String? = null,
    var favourite: Boolean? = null,
    var content: String? = null,
    var date: String? = null
        )