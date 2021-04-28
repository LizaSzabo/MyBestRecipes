package hu.bme.aut.android.recipes.Model

import java.util.*

data class Recipe (
    var title: String,
    var category: String,
    var favourite: Boolean,
    var content: String,
    var date: String?
        )