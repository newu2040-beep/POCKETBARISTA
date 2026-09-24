package com.example.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.json.JSONArray
import org.json.JSONObject

data class RecipeIngredient(
    val name: String,
    val amount: Float,
    val unit: String, // g, ml, oz, shot, tbsp, tsp, pinch, leaves
    val notes: String = ""
) {
    fun formatDisplay(servingsMultiplier: Float, isImperial: Boolean = false): String {
        val scaledAmount = amount * servingsMultiplier
        return if (isImperial) {
            when (unit.lowercase()) {
                "g" -> {
                    val oz = scaledAmount / 28.3495f
                    String.format("%.1f oz %s", oz, name)
                }
                "ml" -> {
                    val flOz = scaledAmount / 29.5735f
                    String.format("%.1f fl oz %s", flOz, name)
                }
                else -> {
                    if (scaledAmount % 1f == 0f) {
                        String.format("%.0f %s %s", scaledAmount, unit, name)
                    } else {
                        String.format("%.1f %s %s", scaledAmount, unit, name)
                    }
                }
            }
        } else {
            if (scaledAmount % 1f == 0f) {
                String.format("%.0f %s %s", scaledAmount, unit, name)
            } else {
                String.format("%.1f %s %s", scaledAmount, unit, name)
            }
        }
    }
}

data class RecipeStep(
    val stepNumber: Int,
    val title: String,
    val instruction: String,
    val durationSeconds: Int = 0 // 0 means no timer needed
)

data class StickerItem(
    val id: String,
    val type: String, // "LATTE_ART", "COFFEE_BEAN", "PORTAFILTER", "STEAM", "ROASTER_STAMP", "GOLD_STAR"
    val xPercent: Float, // 0.0 to 1.0
    val yPercent: Float, // 0.0 to 1.0
    val scale: Float = 1.0f,
    val rotationDeg: Float = 0f
)

@Entity(tableName = "recipes")
data class RecipeEntity(
    @PrimaryKey val id: String,
    val title: String,
    val subtitle: String,
    val category: String, // "Espresso & Classics", "Milk & Microfoam", "Manual Brew", "Cold & Iced", "Tea & Café", "Specialties & Syrups"
    val brewMethod: String, // "Espresso", "Pour-Over", "Immersion", "Cold Steeping", "Moka Pot", "Whisk & Steam", "Infusion"
    val description: String,
    val difficulty: String, // "Beginner", "Intermediate", "Advanced"
    val grindSize: String, // "Extra Fine", "Fine", "Medium-Fine", "Medium", "Coarse", "N/A"
    val recommendedRatio: String, // e.g. "1:2", "1:16", "1:8"
    val waterTemperature: String, // "93°C / 200°F"
    val prepTimeMinutes: Int,
    val brewTimeSeconds: Int,
    val servings: Int = 1,
    val caffeineMgApprox: Int = 64,
    val isHouseVariation: Boolean = false,
    val houseTip: String = "",
    val equipmentJson: String,
    val ingredientsJson: String,
    val instructionsJson: String,
    val coverImageUri: String? = null,
    val coverDrawableRes: Int? = null,
    val stickersJson: String? = null,
    val tagsJson: String = "[]",
    val isFavorite: Boolean = false,
    val isSavedForLater: Boolean = false,
    val isCustom: Boolean = false,
    val viewCount: Int = 0,
    val createdAt: Long = System.currentTimeMillis()
) {
    fun parseEquipment(): List<String> {
        val list = mutableListOf<String>()
        try {
            val arr = JSONArray(equipmentJson)
            for (i in 0 until arr.length()) {
                list.add(arr.getString(i))
            }
        } catch (_: Exception) {}
        return list
    }

    fun parseIngredients(): List<RecipeIngredient> {
        val list = mutableListOf<RecipeIngredient>()
        try {
            val arr = JSONArray(ingredientsJson)
            for (i in 0 until arr.length()) {
                val obj = arr.getJSONObject(i)
                list.add(
                    RecipeIngredient(
                        name = obj.optString("name", ""),
                        amount = obj.optDouble("amount", 0.0).toFloat(),
                        unit = obj.optString("unit", "g"),
                        notes = obj.optString("notes", "")
                    )
                )
            }
        } catch (_: Exception) {}
        return list
    }

    fun parseInstructions(): List<RecipeStep> {
        val list = mutableListOf<RecipeStep>()
        try {
            val arr = JSONArray(instructionsJson)
            for (i in 0 until arr.length()) {
                val obj = arr.getJSONObject(i)
                list.add(
                    RecipeStep(
                        stepNumber = obj.optInt("stepNumber", i + 1),
                        title = obj.optString("title", "Step ${i + 1}"),
                        instruction = obj.optString("instruction", ""),
                        durationSeconds = obj.optInt("durationSeconds", 0)
                    )
                )
            }
        } catch (_: Exception) {}
        return list
    }

    fun parseStickers(): List<StickerItem> {
        if (stickersJson.isNullOrBlank()) return emptyList()
        val list = mutableListOf<StickerItem>()
        try {
            val arr = JSONArray(stickersJson)
            for (i in 0 until arr.length()) {
                val obj = arr.getJSONObject(i)
                list.add(
                    StickerItem(
                        id = obj.optString("id", "$i"),
                        type = obj.optString("type", "LATTE_ART"),
                        xPercent = obj.optDouble("xPercent", 0.5).toFloat(),
                        yPercent = obj.optDouble("yPercent", 0.5).toFloat(),
                        scale = obj.optDouble("scale", 1.0).toFloat(),
                        rotationDeg = obj.optDouble("rotationDeg", 0.0).toFloat()
                    )
                )
            }
        } catch (_: Exception) {}
        return list
    }

    fun parseTags(): List<String> {
        val list = mutableListOf<String>()
        try {
            val arr = JSONArray(tagsJson)
            for (i in 0 until arr.length()) {
                list.add(arr.getString(i))
            }
        } catch (_: Exception) {}
        return list
    }

    companion object {
        fun buildIngredientsJson(ingredients: List<RecipeIngredient>): String {
            val arr = JSONArray()
            for (ing in ingredients) {
                val obj = JSONObject()
                obj.put("name", ing.name)
                obj.put("amount", ing.amount.toDouble())
                obj.put("unit", ing.unit)
                obj.put("notes", ing.notes)
                arr.put(obj)
            }
            return arr.toString()
        }

        fun buildInstructionsJson(steps: List<RecipeStep>): String {
            val arr = JSONArray()
            for (step in steps) {
                val obj = JSONObject()
                obj.put("stepNumber", step.stepNumber)
                obj.put("title", step.title)
                obj.put("instruction", step.instruction)
                obj.put("durationSeconds", step.durationSeconds)
                arr.put(obj)
            }
            return arr.toString()
        }

        fun buildStickersJson(stickers: List<StickerItem>): String {
            val arr = JSONArray()
            for (st in stickers) {
                val obj = JSONObject()
                obj.put("id", st.id)
                obj.put("type", st.type)
                obj.put("xPercent", st.xPercent.toDouble())
                obj.put("yPercent", st.yPercent.toDouble())
                obj.put("scale", st.scale.toDouble())
                obj.put("rotationDeg", st.rotationDeg.toDouble())
                arr.put(obj)
            }
            return arr.toString()
        }

        fun buildListJson(items: List<String>): String {
            val arr = JSONArray()
            for (it in items) {
                arr.put(it)
            }
            return arr.toString()
        }
    }
}
