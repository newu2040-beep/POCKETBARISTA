package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.DefaultRecipes
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ExampleRobolectricTest {

    @Test
    fun `read app name from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("RECIPEPOCKET", appName)
    }

    @Test
    fun `verify default recipes list has full 30 barista recipes`() {
        val recipes = DefaultRecipes.getList()
        assertTrue("Recipe library must contain at least 30 recipes", recipes.size >= 30)

        // Check key recipes exist
        val ids = recipes.map { it.id }.toSet()
        assertTrue("Must contain espresso", ids.contains("espresso"))
        assertTrue("Must contain flat white", ids.contains("flat_white"))
        assertTrue("Must contain v60", ids.contains("v60_pourover"))
        assertTrue("Must contain cold brew", ids.contains("cold_brew_concentrate"))
        assertTrue("Must contain matcha latte", ids.contains("matcha_latte"))
    }
}
