package hu.bme.aut.android.recipes

import android.app.Application
import hu.bme.aut.android.recipes.Model.Recipe
import java.util.*

class RecipeApplication: Application() {

    companion object {
        var fullList: MutableList<Recipe?> = mutableListOf()
        var random: String = Random().nextInt(1199999).toString()
    }
}