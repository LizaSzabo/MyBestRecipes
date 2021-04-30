package hu.bme.aut.android.recipes

import android.app.Application
import hu.bme.aut.android.recipes.Model.Recipe

class RecipeApplication: Application() {

    companion object {
        var fullList: MutableList<Recipe?> = mutableListOf()

    }
}