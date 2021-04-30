package hu.bme.aut.android.recipes.Model

import java.util.*

data class Recipe (
    var title: String? = null,
    var category: String? = null,
    var favourite: Boolean? = null,
    var content: String? = null,
    var date: String? = null
        )