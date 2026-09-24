package com.example.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.model.RecipeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {
    @Query("SELECT * FROM recipes ORDER BY isFavorite DESC, createdAt DESC")
    fun getAllRecipes(): Flow<List<RecipeEntity>>

    @Query("SELECT * FROM recipes WHERE id = :id LIMIT 1")
    fun getRecipeById(id: String): Flow<RecipeEntity?>

    @Query("SELECT * FROM recipes WHERE isFavorite = 1 ORDER BY createdAt DESC")
    fun getFavoriteRecipes(): Flow<List<RecipeEntity>>

    @Query("SELECT * FROM recipes WHERE isSavedForLater = 1 ORDER BY createdAt DESC")
    fun getSavedForLaterRecipes(): Flow<List<RecipeEntity>>

    @Query("SELECT * FROM recipes WHERE isCustom = 1 ORDER BY createdAt DESC")
    fun getCustomRecipes(): Flow<List<RecipeEntity>>

    @Query("SELECT * FROM recipes WHERE category = :category ORDER BY isFavorite DESC, title ASC")
    fun getRecipesByCategory(category: String): Flow<List<RecipeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipe: RecipeEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(recipes: List<RecipeEntity>)

    @Update
    suspend fun updateRecipe(recipe: RecipeEntity)

    @Query("UPDATE recipes SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun setFavorite(id: String, isFavorite: Boolean)

    @Query("UPDATE recipes SET isSavedForLater = :isSaved WHERE id = :id")
    suspend fun setSavedForLater(id: String, isSaved: Boolean)

    @Query("UPDATE recipes SET viewCount = viewCount + 1 WHERE id = :id")
    suspend fun incrementViewCount(id: String)

    @Delete
    suspend fun deleteRecipe(recipe: RecipeEntity)

    @Query("DELETE FROM recipes WHERE id = :id")
    suspend fun deleteRecipeById(id: String)

    @Query("SELECT COUNT(*) FROM recipes")
    suspend fun getRecipeCount(): Int
}
