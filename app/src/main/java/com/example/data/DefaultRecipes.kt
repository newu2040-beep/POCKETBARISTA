package com.example.data

import com.example.R
import com.example.model.RecipeEntity
import com.example.model.RecipeIngredient
import com.example.model.RecipeStep

object DefaultRecipes {
    fun getList(): List<RecipeEntity> = listOf(
        // 1. ESPRESSO
        createRecipe(
            id = "espresso",
            title = "Classic Espresso",
            subtitle = "The gold standard 1:2 extraction with rich hazelnut crema",
            category = "Espresso & Classics",
            brewMethod = "Espresso",
            description = "A concentrated, viscous coffee brewed by forcing hot water through finely ground, tightly compacted specialty beans under 9 bars of pressure.",
            difficulty = "Intermediate",
            grindSize = "Fine",
            recommendedRatio = "1:2",
            waterTemperature = "93°C / 200°F",
            prepTimeMinutes = 3,
            brewTimeSeconds = 28,
            servings = 1,
            caffeineMgApprox = 64,
            isHouseVariation = false,
            houseTip = "Aim for a steady mouse-tail stream starting between 5-7 seconds. If sputtering occurs, check for puck channeling and distribute grounds evenly.",
            equipment = listOf("Espresso Machine", "Portafilter (18g basket)", "Tamper", "WDT Distribution Tool", "Precision Scale"),
            ingredients = listOf(
                RecipeIngredient("Fine Ground Coffee (Specialty Roast)", 18f, "g", "Medium-dark or sweet medium roast"),
                RecipeIngredient("Filtered Hot Water (Brewed Out)", 36f, "ml", "Yield at 9 bars pressure")
            ),
            steps = listOf(
                RecipeStep(1, "Dose & Distribute", "Grind 18g of fresh beans into portafilter. Use WDT needle tool to fluff up and remove clumps.", 15),
                RecipeStep(2, "Level & Tamp", "Tamp firmly and evenly with ~15kg pressure until bed is completely level and horizontal.", 10),
                RecipeStep(3, "Flush & Lock", "Flush grouphead for 2 seconds to purge stale water, lock portafilter firmly, place cup on scale.", 5),
                RecipeStep(4, "Extract Shot", "Start extraction immediately. Watch for rich amber tiger-striping flow aiming for 36g out in 26-30s.", 28)
            ),
            tags = listOf("Espresso", "Foundational", "Pure", "Crema"),
            coverDrawableRes = R.drawable.img_coffee_hero
        ),

        // 2. RISTRETTO
        createRecipe(
            id = "ristretto",
            title = "Ristretto",
            subtitle = "Restricted 1:1.5 extraction emphasizing intense sweetness",
            category = "Espresso & Classics",
            brewMethod = "Espresso",
            description = "A shorter, more concentrated espresso shot pulled with a tighter 1:1 to 1:1.5 ratio, capturing early sugars while eliminating back-end bitterness.",
            difficulty = "Intermediate",
            grindSize = "Fine (Slightly finer)",
            recommendedRatio = "1:1.5",
            waterTemperature = "93°C / 200°F",
            prepTimeMinutes = 3,
            brewTimeSeconds = 22,
            servings = 1,
            caffeineMgApprox = 55,
            isHouseVariation = false,
            houseTip = "Cut extraction right as the stream begins to blond. Pairs magnificently with microfoam milk in a Piccolo.",
            equipment = listOf("Espresso Machine", "Portafilter", "Tamper", "Scale"),
            ingredients = listOf(
                RecipeIngredient("Fine Specialty Coffee", 18f, "g", "Finer grind than standard espresso"),
                RecipeIngredient("Yield Output", 27f, "ml", "Thick syrupy extraction")
            ),
            steps = listOf(
                RecipeStep(1, "Prepare Puck", "Dose 18g, distribute thoroughly, tamp level.", 15),
                RecipeStep(2, "Short Pull", "Extract for 20-22 seconds until scale reads exactly 27g. Cut pump immediately.", 22)
            ),
            tags = listOf("Ristretto", "Sweet", "Syrupy", "Intense")
        ),

        // 3. LUNGO
        createRecipe(
            id = "lungo",
            title = "Lungo",
            subtitle = "Extended 1:3 extraction highlighting subtle fruit & tea notes",
            category = "Espresso & Classics",
            brewMethod = "Espresso",
            description = "An extended shot where more water flows through the puck, yielding a lighter body and pronounced origin acidity.",
            difficulty = "Beginner",
            grindSize = "Fine (Slightly coarser)",
            recommendedRatio = "1:3",
            waterTemperature = "92°C / 198°F",
            prepTimeMinutes = 3,
            brewTimeSeconds = 38,
            servings = 1,
            caffeineMgApprox = 75,
            isHouseVariation = false,
            houseTip = "Grind a notch coarser than your standard espresso setting to prevent over-extracting astringent woody flavors.",
            equipment = listOf("Espresso Machine", "Portafilter", "Scale"),
            ingredients = listOf(
                RecipeIngredient("Specialty Coffee", 18f, "g", "Single origin light-medium roast"),
                RecipeIngredient("Lungo Yield", 54f, "ml", "High volume extraction")
            ),
            steps = listOf(
                RecipeStep(1, "Grind Coarser", "Set grinder 1 step coarser than espresso. Dose 18g and tamp flat.", 15),
                RecipeStep(2, "Long Pull", "Engage brew switch and pull shot until 54g yield is reached at 35-40 seconds.", 38)
            ),
            tags = listOf("Lungo", "Bright", "Floral", "Extended")
        ),

        // 4. DOPPIO
        createRecipe(
            id = "doppio",
            title = "Doppio (Double Shot)",
            subtitle = "Two distinct shots pulled in a double basket",
            category = "Espresso & Classics",
            brewMethod = "Espresso",
            description = "The cornerstone of specialty café menus worldwide, delivering 2 fluid ounces (approx 60ml / 38g) of balanced, aromatic espresso.",
            difficulty = "Beginner",
            grindSize = "Fine",
            recommendedRatio = "1:2",
            waterTemperature = "93°C / 200°F",
            prepTimeMinutes = 3,
            brewTimeSeconds = 28,
            servings = 2,
            caffeineMgApprox = 128,
            isHouseVariation = false,
            houseTip = "Split through a double spout into two pre-heated demitasse cups for traditional serving.",
            equipment = listOf("Espresso Machine", "Double Spouted Portafilter", "Scale"),
            ingredients = listOf(
                RecipeIngredient("Ground Coffee", 20f, "g", "Balanced house blend"),
                RecipeIngredient("Double Espresso Yield", 40f, "ml", "Split evenly")
            ),
            steps = listOf(
                RecipeStep(1, "Double Basket Dose", "Weigh 20g ground coffee into double basket. Tamp with even level pressure.", 15),
                RecipeStep(2, "Split Extraction", "Extract 40g total liquid over 28-30 seconds across dual demitasses.", 28)
            ),
            tags = listOf("Doppio", "Double", "Classic", "Strong")
        ),

        // 5. AMERICANO
        createRecipe(
            id = "americano",
            title = "Caffè Americano",
            subtitle = "Espresso poured gently over hot water preserving crema",
            category = "Espresso & Classics",
            brewMethod = "Espresso",
            description = "Originated during WWII when American soldiers diluted strong Italian espresso with hot water. Retains the aromatics and character of espresso with the drinkability of brewed coffee.",
            difficulty = "Beginner",
            grindSize = "Fine",
            recommendedRatio = "1:5",
            waterTemperature = "88°C / 190°F",
            prepTimeMinutes = 3,
            brewTimeSeconds = 30,
            servings = 1,
            caffeineMgApprox = 128,
            isHouseVariation = true,
            houseTip = "Always pour espresso ON TOP of hot water (the Long Black technique) rather than pouring water onto espresso, keeping the golden crema pristine.",
            equipment = listOf("Espresso Machine", "8oz Ceramic Cup", "Hot Water Kettle"),
            ingredients = listOf(
                RecipeIngredient("Hot Water (88°C)", 150f, "ml", "Filtered café water"),
                RecipeIngredient("Fresh Double Espresso", 38f, "ml", "Pulled directly over water")
            ),
            steps = listOf(
                RecipeStep(1, "Pour Base Water", "Fill serving cup with 150ml of 88°C water first.", 5),
                RecipeStep(2, "Float Espresso", "Extract a 38g double espresso directly floating over the surface water to sustain crema.", 28)
            ),
            tags = listOf("Americano", "Long Black", "Smooth", "Crema")
        ),

        // 6. FLAT WHITE
        createRecipe(
            id = "flat_white",
            title = "Specialty Flat White",
            subtitle = "Double ristretto with glossy, micro-thin velvety milk",
            category = "Milk & Microfoam",
            brewMethod = "Espresso & Steam",
            description = "An Australasian specialty café icon. Blends a double ristretto with silky microfoam milk poured with no stiff head, creating a seamless mouthfeel from top to bottom.",
            difficulty = "Advanced",
            grindSize = "Fine",
            recommendedRatio = "1:3 (Coffee to Milk)",
            waterTemperature = "62°C / 144°F (Milk)",
            prepTimeMinutes = 4,
            brewTimeSeconds = 25,
            servings = 1,
            caffeineMgApprox = 110,
            isHouseVariation = true,
            houseTip = "Keep the steam wand tip just below the surface for only 2 seconds of stretching, then submerge for pure rolling to achieve paint-like microfoam sheen.",
            equipment = listOf("Espresso Machine", "Milk Steaming Pitcher (350ml)", "5oz-6oz Ceramic Cup", "Scale"),
            ingredients = listOf(
                RecipeIngredient("Double Ristretto", 32f, "ml", "Sweet, dense coffee foundation"),
                RecipeIngredient("Fresh Whole Milk or Oat", 130f, "ml", "Steamed to silky microfoam at 60-63°C")
            ),
            steps = listOf(
                RecipeStep(1, "Extract Ristretto Base", "Pull a concentrated 32g double ristretto directly into your pre-warmed 5.5oz cup.", 24),
                RecipeStep(2, "Steam Glossy Milk", "Steam cold whole milk. Stretch for 2-3 seconds, swirl in vortex until pitcher base is warm to touch (62°C).", 25),
                RecipeStep(3, "Incorporate & Pour", "Swirl pitcher to integrate glossy sheen. Tilt cup 45°, pour high into center to mix base, then lower tip to pour flat surface rosetta.", 15)
            ),
            tags = listOf("Flat White", "Microfoam", "Velvety", "Latte Art"),
            coverDrawableRes = R.drawable.img_latte_art_guide
        ),

        // 7. CAPPUCCINO
        createRecipe(
            id = "cappuccino",
            title = "Artisan Cappuccino",
            subtitle = "Harmonious balance of espresso, warm milk, and dense foam",
            category = "Milk & Microfoam",
            brewMethod = "Espresso & Steam",
            description = "The Italian breakfast staple. Features equal third proportions of bold espresso, sweet steamed milk, and a luscious cloud of pillowy microfoam.",
            difficulty = "Intermediate",
            grindSize = "Fine",
            recommendedRatio = "1:1:1",
            waterTemperature = "65°C / 149°F",
            prepTimeMinutes = 4,
            brewTimeSeconds = 28,
            servings = 1,
            caffeineMgApprox = 128,
            isHouseVariation = false,
            houseTip = "Stretch the milk for 5-6 seconds to introduce air before vortex rolling. The foam should be glossy and cushiony, not dry stiff bubbles.",
            equipment = listOf("Espresso Machine", "Steaming Pitcher", "6oz Cup"),
            ingredients = listOf(
                RecipeIngredient("Double Shot Espresso", 36f, "ml", "Bold chocolate/nutty profile"),
                RecipeIngredient("Whole Milk", 120f, "ml", "Steamed with rich aeration")
            ),
            steps = listOf(
                RecipeStep(1, "Pull Espresso", "Extract 36g rich espresso into cappuccino cup.", 28),
                RecipeStep(2, "Aerated Milk Steam", "Place steam tip at surface to hear pleasant 'ch-ch' chirps for 6 seconds, then roll until 65°C.", 30),
                RecipeStep(3, "Pour Centered Island", "Pour steadily into center, allowing creamy foam dome to mound gracefully with a rich crema ring.", 15)
            ),
            tags = listOf("Cappuccino", "Classic", "Creamy", "Foam")
        ),

        // 8. CAFÈ LATTE
        createRecipe(
            id = "caffe_latte",
            title = "Caffè Latte",
            subtitle = "Smooth espresso balanced with generous steamed milk",
            category = "Milk & Microfoam",
            brewMethod = "Espresso & Steam",
            description = "A mellow, comforting milk-forward classic consisting of a double espresso topped with 8-10oz of steamed milk and a delicate centimeter of foam.",
            difficulty = "Intermediate",
            grindSize = "Fine",
            recommendedRatio = "1:5",
            waterTemperature = "65°C / 149°F",
            prepTimeMinutes = 4,
            brewTimeSeconds = 28,
            servings = 1,
            caffeineMgApprox = 128,
            isHouseVariation = false,
            houseTip = "Great canvas for intricate tulip or swan latte art due to the generous liquid surface area.",
            equipment = listOf("Espresso Machine", "Pitcher", "8-10oz Latte Bowl/Glass"),
            ingredients = listOf(
                RecipeIngredient("Double Espresso", 36f, "ml", "Rich baseline"),
                RecipeIngredient("Steamed Milk", 200f, "ml", "Velvety microfoam")
            ),
            steps = listOf(
                RecipeStep(1, "Extract Base", "Pull espresso into serving glass.", 28),
                RecipeStep(2, "Steam Milk", "Introduce subtle air, heat to 65°C with smooth vortex circulation.", 30),
                RecipeStep(3, "Pour Art", "Pour deep from 5cm above surface to integrate, drop nozzle to 1cm to paint decorative art.", 20)
            ),
            tags = listOf("Latte", "Mild", "Smooth", "Art")
        ),

        // 9. CORTADO
        createRecipe(
            id = "cortado",
            title = "Cortado",
            subtitle = "Equal parts 1:1 espresso and lightly textured warm milk",
            category = "Milk & Microfoam",
            brewMethod = "Espresso & Steam",
            description = "Originating in Spain, the Cortado ('cut') cuts the acidity and intensity of espresso with an equal measure of warm, gently textured milk served in a small Gibraltar glass.",
            difficulty = "Beginner",
            grindSize = "Fine",
            recommendedRatio = "1:1",
            waterTemperature = "60°C / 140°F",
            prepTimeMinutes = 3,
            brewTimeSeconds = 26,
            servings = 1,
            caffeineMgApprox = 128,
            isHouseVariation = false,
            houseTip = "Do not introduce heavy air when steaming; you want silky warm milk that preserves the assertive coffee taste without heaviness.",
            equipment = listOf("Espresso Machine", "Gibraltar Glass (4.5oz)", "Milk Pitcher"),
            ingredients = listOf(
                RecipeIngredient("Espresso Doppio", 40f, "ml", "Full bodied"),
                RecipeIngredient("Warm Steamed Milk", 40f, "ml", "Light micro-texture")
            ),
            steps = listOf(
                RecipeStep(1, "Extract into Glass", "Pull double espresso directly into faceted Gibraltar glass.", 26),
                RecipeStep(2, "Steam 1:1 Milk", "Gently steam 50ml milk, aerating minimally.", 20),
                RecipeStep(3, "Cut with Milk", "Pour equal volume of warm milk over espresso, forming a sharp contrast ring.", 10)
            ),
            tags = listOf("Cortado", "Gibraltar", "Balanced", "1:1")
        ),

        // 10. MACCHIATO
        createRecipe(
            id = "caffe_macchiato",
            title = "Caffè Macchiato (Espresso Macchiato)",
            subtitle = "Espresso stained with a dollop of velvety microfoam",
            category = "Milk & Microfoam",
            brewMethod = "Espresso & Steam",
            description = "The authentic Italian 'stained' coffee. A rich double espresso topped with just two spoonfuls of dense milk foam to soften the initial sip without diluting punch.",
            difficulty = "Beginner",
            grindSize = "Fine",
            recommendedRatio = "1:0.25",
            waterTemperature = "65°C / 149°F",
            prepTimeMinutes = 2,
            brewTimeSeconds = 26,
            servings = 1,
            caffeineMgApprox = 128,
            isHouseVariation = false,
            houseTip = "Spoon the white foam directly onto the dark crema for dramatic visual contrast and tactile softness.",
            equipment = listOf("Espresso Machine", "Demitasse Cup", "Small Milk Pitcher"),
            ingredients = listOf(
                RecipeIngredient("Fresh Espresso", 36f, "ml", "Double shot"),
                RecipeIngredient("Dense Milk Foam", 15f, "ml", "Frothed foam cap")
            ),
            steps = listOf(
                RecipeStep(1, "Pull Demitasse Shot", "Extract 36g espresso into heated ceramic cup.", 26),
                RecipeStep(2, "Spoon White Foam", "Spoon or pour a small stain of velvety milk foam right into the center.", 10)
            ),
            tags = listOf("Macchiato", "Stained", "Punchy", "Italian")
        ),

        // 11. PICCOLO
        createRecipe(
            id = "piccolo_latte",
            title = "Piccolo Latte",
            subtitle = "A single shot of ristretto in a 100ml glass with silky milk",
            category = "Milk & Microfoam",
            brewMethod = "Espresso & Steam",
            description = "Beloved by baristas for taste-testing milk integration throughout morning service. Strong, sweet coffee flavor without overfilling with milk.",
            difficulty = "Intermediate",
            grindSize = "Fine",
            recommendedRatio = "1:4",
            waterTemperature = "62°C / 144°F",
            prepTimeMinutes = 3,
            brewTimeSeconds = 22,
            servings = 1,
            caffeineMgApprox = 64,
            isHouseVariation = false,
            houseTip = "Use a sweet, low-acidity origin like Colombia or Brazil for optimal chocolate-honey balance.",
            equipment = listOf("Espresso Machine", "3oz-3.5oz Glass Cup", "Pitcher"),
            ingredients = listOf(
                RecipeIngredient("Single Ristretto Shot", 20f, "ml", "Dense and syrupy"),
                RecipeIngredient("Silky Microfoam Milk", 70f, "ml", "Warm and glossy")
            ),
            steps = listOf(
                RecipeStep(1, "Pull Short Ristretto", "Extract 20ml ristretto into miniature glass.", 22),
                RecipeStep(2, "Steam Small Pitcher", "Steam milk with gentle roll to 62°C.", 20),
                RecipeStep(3, "Miniature Art Pour", "Pour smooth microfoam into glass, creating a delicate miniature heart.", 10)
            ),
            tags = listOf("Piccolo", "Barista Favorite", "Compact", "Silky")
        ),

        // 12. MOCHA
        createRecipe(
            id = "artisan_mocha",
            title = "Dark Chocolate Mocha",
            subtitle = "Rich espresso melted with 70% single-origin cacao ganache",
            category = "Milk & Microfoam",
            brewMethod = "Espresso & Steam",
            description = "A luxurious fusion of rich bittersweet dark chocolate, double espresso, and steamed milk, dusted with Dutch cocoa powder.",
            difficulty = "Beginner",
            grindSize = "Fine",
            recommendedRatio = "Espresso + Chocolate + Milk",
            waterTemperature = "65°C / 149°F",
            prepTimeMinutes = 5,
            brewTimeSeconds = 30,
            servings = 1,
            caffeineMgApprox = 135,
            isHouseVariation = true,
            houseTip = "Whisk the hot espresso directly into chocolate ganache or shavings first until completely emulsified before introducing milk.",
            equipment = listOf("Espresso Machine", "Mini Whisk", "Latte Cup", "Cocoa Shaker"),
            ingredients = listOf(
                RecipeIngredient("Double Espresso", 36f, "ml", "Freshly extracted"),
                RecipeIngredient("Dark Chocolate Shavings (70%)", 25f, "g", "Bittersweet cacao"),
                RecipeIngredient("Whole Milk", 180f, "ml", "Steamed to 65°C"),
                RecipeIngredient("Cocoa Powder", 2f, "g", "For dusting garnish")
            ),
            steps = listOf(
                RecipeStep(1, "Melt Ganache", "Place chocolate shavings into cup. Extract hot espresso over shavings and whisk until silky smooth.", 25),
                RecipeStep(2, "Steam Milk", "Steam milk to 65°C with creamy latte microfoam.", 30),
                RecipeStep(3, "Combine & Dust", "Pour milk through chocolate-espresso base, dusting top lightly with cocoa.", 15)
            ),
            tags = listOf("Mocha", "Chocolate", "Decadent", "Warm")
        ),

        // 13. AFFOGATO
        createRecipe(
            id = "classic_affogato",
            title = "Traditional Affogato al Caffè",
            subtitle = "Hot espresso drowning a scoop of artisan Madagascar vanilla gelato",
            category = "Espresso & Classics",
            brewMethod = "Immersion & Pour",
            description = "Italian for 'drowned'. A stunning contrast of temperatures and textures as scalding hot espresso cascades over frozen, egg-custard vanilla bean gelato.",
            difficulty = "Beginner",
            grindSize = "Fine",
            recommendedRatio = "1 Scoop : 1 Double Shot",
            waterTemperature = "93°C / 200°F",
            prepTimeMinutes = 2,
            brewTimeSeconds = 26,
            servings = 1,
            caffeineMgApprox = 128,
            isHouseVariation = true,
            houseTip = "Keep dessert glass in freezer for 10 minutes prior to serving to delay gelato melt.",
            equipment = listOf("Espresso Machine", "Chilled Dessert Glass", "Ice Cream Scoop"),
            ingredients = listOf(
                RecipeIngredient("Double Espresso Shot", 36f, "ml", "Freshly pulled hot espresso"),
                RecipeIngredient("Vanilla Gelato or Ice Cream", 70f, "g", "High fat Madagascar vanilla scoop")
            ),
            steps = listOf(
                RecipeStep(1, "Scoop Gelato", "Place a solid round scoop of chilled gelato into frozen coupe glass.", 10),
                RecipeStep(2, "Pull Espresso", "Extract a thick double espresso into small pouring pitcher.", 26),
                RecipeStep(3, "Pour Tableside", "Pour hot espresso tableside directly over peak of gelato.", 10)
            ),
            tags = listOf("Affogato", "Dessert", "Gelato", "Indulgent")
        ),

        // 14. V60 POUR-OVER
        createRecipe(
            id = "v60_pourover",
            title = "Hario V60 Single Origin",
            subtitle = "Precision cone dripper extraction showcasing terroir and clarity",
            category = "Manual Brew",
            brewMethod = "Pour-Over",
            description = "The industry standard for clean, nuanced filter coffee. Spiral ridges inside the 60-degree cone allow air escape, resulting in rapid, customizable drawdown.",
            difficulty = "Intermediate",
            grindSize = "Medium-Fine",
            recommendedRatio = "1:16",
            waterTemperature = "94°C / 201°F",
            prepTimeMinutes = 5,
            brewTimeSeconds = 195,
            servings = 1,
            caffeineMgApprox = 145,
            isHouseVariation = false,
            houseTip = "Pour in concentric circles without washing the paper filter walls directly to prevent bypass channel flow.",
            equipment = listOf("Hario V60 Dripper", "V60 Paper Filter", "Gooseneck Kettle", "Digital Timer Scale", "Carafe"),
            ingredients = listOf(
                RecipeIngredient("Specialty Light Roast Coffee", 18f, "g", "Freshly ground medium-fine (table salt texture)"),
                RecipeIngredient("Hot Filtered Water (94°C)", 288f, "ml", "Total brewing water")
            ),
            steps = listOf(
                RecipeStep(1, "Rinse & Pre-heat", "Rinse paper filter with hot water to remove paper taste and preheat carafe. Discard rinse water.", 20),
                RecipeStep(2, "Bloom Stage", "Add 18g coffee. Pour 50g water in spiral. Swirl gently and let bloom release CO2 gas.", 45),
                RecipeStep(3, "First Pour", "At 0:45, pour gently to 150g in slow spirals. Keep flow stream laminar and low.", 40),
                RecipeStep(4, "Second Pour", "At 1:30, pour remaining water to reached 288g target weight. Give a gentle swirl.", 45),
                RecipeStep(5, "Drawdown", "Allow water to drain completely through flat coffee bed. Total time: 3:00 - 3:15.", 45)
            ),
            tags = listOf("V60", "Pour-Over", "Clarity", "Single Origin")
        ),

        // 15. CHEMEX
        createRecipe(
            id = "chemex_brew",
            title = "Classic Chemex Pour-Over",
            subtitle = "Heavy bonded paper filter yielding an ultra-clean, tea-like cup",
            category = "Manual Brew",
            brewMethod = "Pour-Over",
            description = "Invented by chemist Peter Schlumbohm in 1941. Uses 20-30% thicker bonded paper filters that absorb sediment, fines, and bitter oils for unmatched clarity.",
            difficulty = "Intermediate",
            grindSize = "Medium-Coarse",
            recommendedRatio = "1:15",
            waterTemperature = "95°C / 203°F",
            prepTimeMinutes = 6,
            brewTimeSeconds = 270,
            servings = 2,
            caffeineMgApprox = 220,
            isHouseVariation = false,
            houseTip = "Place the 3-ply side of the folded square filter against the pouring spout to prevent suction seal lock.",
            equipment = listOf("Chemex Glass Coffeemaker (6-cup)", "Chemex Bonded Filter", "Gooseneck Kettle", "Scale"),
            ingredients = listOf(
                RecipeIngredient("Medium-Coarse Coffee Grounds", 30f, "g", "Sea salt coarse grind"),
                RecipeIngredient("Water (95°C)", 450f, "ml", "Clean filtered brew water")
            ),
            steps = listOf(
                RecipeStep(1, "Filter Rinse", "Rinse heavy paper thoroughly with hot water. Drain carefully through spout.", 30),
                RecipeStep(2, "Bloom", "Add 30g coffee, flatten bed. Pour 90g water and let bloom for 45 seconds.", 45),
                RecipeStep(3, "Pulse Pours", "Pour in smooth spirals to 250g, wait 20s, then pour to final 450g.", 90),
                RecipeStep(4, "Drain & Serve", "Allow bed to drain flat. Swirl Chemex bowl before pouring into glasses.", 105)
            ),
            tags = listOf("Chemex", "Clean", "Tea-like", "Aesthetic")
        ),

        // 16. AEROPRESS
        createRecipe(
            id = "aeropress_inverted",
            title = "AeroPress (Inverted Method)",
            subtitle = "Immersion and pneumatic pressure delivering syrupy, rich extraction",
            category = "Manual Brew",
            brewMethod = "Immersion & Pressure",
            description = "Championed by world competitors. The inverted method eliminates premature leakage, allowing full controlled immersion before pressing.",
            difficulty = "Beginner",
            grindSize = "Medium-Fine",
            recommendedRatio = "1:13",
            waterTemperature = "90°C / 194°F",
            prepTimeMinutes = 4,
            brewTimeSeconds = 120,
            servings = 1,
            caffeineMgApprox = 130,
            isHouseVariation = true,
            houseTip = "Stop pressing the moment you hear the air hiss sound to avoid extracting unwanted astringency.",
            equipment = listOf("AeroPress Brewer", "Micro-filter Paper", "Stir Paddle", "Sturdy Mug"),
            ingredients = listOf(
                RecipeIngredient("Coffee Grounds", 15f, "g", "Medium-fine grind"),
                RecipeIngredient("Hot Water (90°C)", 200f, "ml", "Soft brew water")
            ),
            steps = listOf(
                RecipeStep(1, "Set Inverted", "Position plunger at marker #4, stand upside down. Add 15g coffee.", 15),
                RecipeStep(2, "Pour & Agitate", "Pour 200g water rapidly, stir 3 times back and forth with paddle.", 25),
                RecipeStep(3, "Steep & Cap", "Screw on rinsed filter cap. Let steep until 1:30.", 60),
                RecipeStep(4, "Flip & Press", "Carefully flip onto mug and press steadily for 30 seconds until soft hiss.", 30)
            ),
            tags = listOf("AeroPress", "Versatile", "Juicy", "Travel")
        ),

        // 17. FRENCH PRESS
        createRecipe(
            id = "french_press",
            title = "Artisan French Press",
            subtitle = "Full immersion brew delivering full body and natural coffee oils",
            category = "Manual Brew",
            brewMethod = "Immersion",
            description = "The definitive full-body immersion method. Using a metallic mesh screen rather than paper allows aromatic cafestol and kahweol coffee oils to enrich every sip.",
            difficulty = "Beginner",
            grindSize = "Coarse",
            recommendedRatio = "1:14",
            waterTemperature = "95°C / 203°F",
            prepTimeMinutes = 7,
            brewTimeSeconds = 360,
            servings = 2,
            caffeineMgApprox = 180,
            isHouseVariation = true,
            houseTip = "James Hoffmann crust-breaking technique: after 4 minutes, break crust with spoons and skim off foam and floating bits for an exceptionally clean cup.",
            equipment = listOf("French Press (Bodum/Similar)", "Two Spoons", "Scale", "Timer"),
            ingredients = listOf(
                RecipeIngredient("Coarse Coffee", 30f, "g", "Coarse breadcrumb grind"),
                RecipeIngredient("Brew Water (95°C)", 420f, "ml", "Just off boil")
            ),
            steps = listOf(
                RecipeStep(1, "Add Grounds & Water", "Add 30g coffee, pour all 420g water vigorously to saturate.", 30),
                RecipeStep(2, "Initial Steep", "Place plunger lightly on top (do not press). Steep undisturbed for 4 minutes.", 240),
                RecipeStep(3, "Break Crust & Skim", "At 4:00, gently stir surface crust. Skim off white foam and floaters.", 30),
                RecipeStep(4, "Settling & Plunge", "Let settle 3 minutes, then push plunger through surface gently without crushing bottom bed.", 60)
            ),
            tags = listOf("French Press", "Immersion", "Heavy Body", "Comfort")
        ),

        // 18. MOKA POT
        createRecipe(
            id = "moka_pot",
            title = "Stovetop Moka Pot",
            subtitle = "Steam-driven Italian home espresso with deep caramel notes",
            category = "Manual Brew",
            brewMethod = "Moka Pot",
            description = "Invented by Alfonso Bialetti in 1933. Steam pressure pushes boiling water up through coffee grounds into the upper chamber, delivering concentrated espresso-style coffee.",
            difficulty = "Intermediate",
            grindSize = "Medium-Fine",
            recommendedRatio = "1:7",
            waterTemperature = "Pre-boiled (100°C) base",
            prepTimeMinutes = 6,
            brewTimeSeconds = 180,
            servings = 2,
            caffeineMgApprox = 140,
            isHouseVariation = true,
            houseTip = "Fill lower chamber with PRE-HEATED boiling water rather than cold water. This prevents baking the coffee bed on the stove while waiting for water to heat.",
            equipment = listOf("Bialetti Moka Pot (3-cup)", "Stovetop / Burner", "Cold Damp Towel"),
            ingredients = listOf(
                RecipeIngredient("Medium-Fine Coffee", 18f, "g", "Do not tamp! Level flush with funnel rim"),
                RecipeIngredient("Pre-boiled Water", 130f, "ml", "Fill to just below safety pressure valve")
            ),
            steps = listOf(
                RecipeStep(1, "Fill Base & Basket", "Pour boiling water to safety valve. Insert funnel, fill loose grounds without pressing.", 30),
                RecipeStep(2, "Assemble with Towel", "Screw top on securely using oven mitt. Set on low-medium stove heat with lid open.", 30),
                RecipeStep(3, "Extract & Quench", "When rich golden coffee streams into column, lower heat. The moment it begins to sputter, quench base under cold tap.", 120)
            ),
            tags = listOf("Moka Pot", "Stovetop", "Rich", "Traditional")
        ),

        // 19. TURKISH COFFEE
        createRecipe(
            id = "turkish_coffee",
            title = "Traditional Turkish Cezve",
            subtitle = "Finely pulverized coffee simmered with cardamom and velvet foam",
            category = "Manual Brew",
            brewMethod = "Decoction",
            description = "UNESCO Intangible Cultural Heritage. Ultra-fine flour-like coffee decocted slowly in a copper cezve/ibrik, generating thick velvety foam.",
            difficulty = "Intermediate",
            grindSize = "Extra Fine (Flour powder)",
            recommendedRatio = "1:10",
            waterTemperature = "Cold water start",
            prepTimeMinutes = 5,
            brewTimeSeconds = 210,
            servings = 1,
            caffeineMgApprox = 115,
            isHouseVariation = false,
            houseTip = "Never allow the mixture to reach a violent boil; lift the cezve off the flame the instant the foam rises to the rim.",
            equipment = listOf("Copper Cezve / Ibrik", "Demitasse Cup", "Stove / Sand Burner"),
            ingredients = listOf(
                RecipeIngredient("Turkish Powder Coffee", 10f, "g", "Pulverized flour grind"),
                RecipeIngredient("Cold Water", 80f, "ml", "Chilled filtered water"),
                RecipeIngredient("Cardamom Pod (Crushed)", 1f, "pinch", "Optional aromatic accent"),
                RecipeIngredient("Raw Sugar", 4f, "g", "Optional (Orta / Medium sweetness)")
            ),
            steps = listOf(
                RecipeStep(1, "Mix Cold", "Combine coffee, water, and sugar in cezve. Stir with spoon once before heating.", 20),
                RecipeStep(2, "Gentle Simmer", "Place on low flame. Let heat slowly over 3 minutes without stirring.", 120),
                RecipeStep(3, "Rise Foam", "As dark microfoam rises to brim, lift off heat, spoon foam into cup, return for second rise, then pour.", 60)
            ),
            tags = listOf("Turkish", "Cezve", "Aromatic", "Cultural")
        ),

        // 20. VIETNAMESE PHIN COFFEE
        createRecipe(
            id = "vietnamese_phin",
            title = "Vietnamese Cà Phê Sữa Đá",
            subtitle = "Slow-drip dark roast through gravity phin over sweet condensed milk",
            category = "Manual Brew",
            brewMethod = "Gravity Phin",
            description = "The unmistakable Vietnamese café experience. Bold Robusta coffee slowly gravity-dripped through a metal phin filter directly onto thick sweetened condensed milk, served over ice.",
            difficulty = "Beginner",
            grindSize = "Medium-Coarse",
            recommendedRatio = "1:6",
            waterTemperature = "96°C / 205°F",
            prepTimeMinutes = 6,
            brewTimeSeconds = 240,
            servings = 1,
            caffeineMgApprox = 160,
            isHouseVariation = false,
            houseTip = "Pour 20ml water first to let grounds expand inside the chamber before placing the gravity press plate and adding remaining water.",
            equipment = listOf("Stainless Steel Vietnamese Phin Filter", "Glass Tumbler", "Ice Cubes"),
            ingredients = listOf(
                RecipeIngredient("Vietnamese Dark Roast Coffee", 20f, "g", "Bold butter/chicory roasted Robusta"),
                RecipeIngredient("Sweetened Condensed Milk", 30f, "g", "2 generous tablespoons in bottom of glass"),
                RecipeIngredient("Hot Water (96°C)", 110f, "ml", "Boiling water for drip"),
                RecipeIngredient("Ice Cubes", 80f, "g", "To chill and serve")
            ),
            steps = listOf(
                RecipeStep(1, "Prep Glass Base", "Spoon sweetened condensed milk into tumbler.", 10),
                RecipeStep(2, "Pack Phin", "Place phin on glass, add coffee, screw down press filter snugly.", 20),
                RecipeStep(3, "Slow Drip", "Add 20ml water to bloom for 30s. Top off with 90ml water. Cover with lid and let drip drop-by-drop.", 210),
                RecipeStep(4, "Stir & Ice", "Stir coffee and milk vigorously until caramel color, pour into tall glass filled with ice.", 20)
            ),
            tags = listOf("Vietnamese", "Phin", "Sweet", "Iced")
        ),

        // 21. COLD BREW
        createRecipe(
            id = "cold_brew_concentrate",
            title = "Signature 16-Hour Cold Brew",
            subtitle = "Slow chilled immersion yielding sweet, low-acid nectar",
            category = "Cold & Iced",
            brewMethod = "Cold Steeping",
            description = "Brewed with time instead of heat. Coarsely ground specialty beans steep in cold water for 16 hours, releasing rich chocolate and berry notes with 70% less acidity than hot coffee.",
            difficulty = "Beginner",
            grindSize = "Coarse (Raw Sugar size)",
            recommendedRatio = "1:8 (Concentrate)",
            waterTemperature = "Chilled (10°C / 50°F)",
            prepTimeMinutes = 10,
            brewTimeSeconds = 57600, // 16 hours in seconds
            servings = 4,
            caffeineMgApprox = 200,
            isHouseVariation = true,
            houseTip = "Dilute 1:1 with ice water, tonic, or oat milk when serving. The concentrate stays fresh in an airtight bottle for up to 14 days.",
            equipment = listOf("Cold Brew Mason Jar / Pitcher", "Fine Mesh Cold Brew Filter Bag", "Paper Filter"),
            ingredients = listOf(
                RecipeIngredient("Coarse Specialty Coffee", 100f, "g", "Fruity natural African or chocolatey Central American"),
                RecipeIngredient("Cold Filtered Water", 800f, "ml", "Refrigerated water")
            ),
            steps = listOf(
                RecipeStep(1, "Submerge Grounds", "Place coffee inside filter bag. Slowly pour cold water to saturate thoroughly.", 60),
                RecipeStep(2, "Refrigerated Steeping", "Seal jar and place in refrigerator (or room temp for 12h) to steep for 16-18 hours.", 57600),
                RecipeStep(3, "Double Filter", "Pull bag out, then pour concentrate through paper filter into storage bottle.", 120)
            ),
            tags = listOf("Cold Brew", "Concentrate", "Low Acid", "Summer"),
            coverDrawableRes = R.drawable.img_coffee_hero
        ),

        // 22. NITRO-STYLE COFFEE
        createRecipe(
            id = "nitro_style_coffee",
            title = "Nitro-Style Velvet Cold Brew",
            subtitle = "Micro-aerated cold brew with a cascading Guinness head",
            category = "Cold & Iced",
            brewMethod = "Chilled Dispense",
            description = "Cold brew infused with micro-bubbles to create a creamy mouthfeel without adding any milk or sugar, culminating in a dense cascading foam head.",
            difficulty = "Intermediate",
            grindSize = "Coarse",
            recommendedRatio = "1:8",
            waterTemperature = "Ice Cold (4°C)",
            prepTimeMinutes = 5,
            brewTimeSeconds = 30,
            servings = 1,
            caffeineMgApprox = 190,
            isHouseVariation = true,
            houseTip = "If using a whipped cream siphon at home: charge chilled cold brew with pure N2 or nitrous oxide, shake vigorously 10 times, and dispense at a 45° angle.",
            equipment = listOf("Cold Brew Dispenser or Nitro Siphon", "Pint Glass"),
            ingredients = listOf(
                RecipeIngredient("Clarified Cold Brew", 250f, "ml", "Ice-cold filtered concentrate"),
                RecipeIngredient("Nitrogen Infusion", 1f, "shot", "Compressed micro-gas charge")
            ),
            steps = listOf(
                RecipeStep(1, "Chill Glass", "Keep pint glass in freezer until frosty.", 10),
                RecipeStep(2, "Cascade Pour", "Hold glass at 45° angle, pull tap steadily. Watch the cascading velvet storm settle into a creamy white head.", 20)
            ),
            tags = listOf("Nitro", "Cascading", "Velvety", "Cold")
        ),

        // 23. DALGONA COFFEE
        createRecipe(
            id = "dalgona_coffee",
            title = "Whipped Dalgona Coffee",
            subtitle = "Golden whipped coffee cloud floating over cold iced milk",
            category = "Cold & Iced",
            brewMethod = "Whipping",
            description = "The viral sensation named after Korean honeycomb toffee. Equal parts coffee, sugar, and hot water whipped into an ultra-dense, glossy golden foam.",
            difficulty = "Beginner",
            grindSize = "Instant Specialty Coffee",
            recommendedRatio = "1:1:1 (Coffee : Sugar : Water)",
            waterTemperature = "Hot Water (Whipping) & Cold Milk",
            prepTimeMinutes = 5,
            brewTimeSeconds = 120,
            servings = 1,
            caffeineMgApprox = 120,
            isHouseVariation = false,
            houseTip = "Must use instant coffee or dehydrated espresso crystals; liquid brewed coffee lacks the proteins needed to stabilize the whipped foam.",
            equipment = listOf("Electric Hand Frother or Whisk", "Mixing Bowl", "Tall Glass"),
            ingredients = listOf(
                RecipeIngredient("Instant Specialty Coffee", 15f, "g", "2 tablespoons"),
                RecipeIngredient("Granulated Sugar", 15f, "g", "2 tablespoons (stabilizes foam)"),
                RecipeIngredient("Hot Water", 15f, "ml", "2 tablespoons hot water"),
                RecipeIngredient("Cold Milk of Choice", 180f, "ml", "Whole, Oat, or Almond"),
                RecipeIngredient("Ice Cubes", 100f, "g", "To fill serving glass")
            ),
            steps = listOf(
                RecipeStep(1, "Whip Golden Foam", "Combine coffee, sugar, and hot water in small bowl. Whip with frother for 2 minutes until stiff peaks form.", 120),
                RecipeStep(2, "Prepare Milk Base", "Fill tall glass with ice and cold milk.", 15),
                RecipeStep(3, "Dollop Cloud", "Spoon velvety whipped coffee foam on top of iced milk.", 15)
            ),
            tags = listOf("Dalgona", "Whipped", "Sweet", "Creamy")
        ),

        // 24. MATCHA LATTE
        createRecipe(
            id = "matcha_latte",
            title = "Ceremonial Matcha Latte",
            subtitle = "Stone-ground Uji matcha whisked with silky steamed oat milk",
            category = "Tea & Café",
            brewMethod = "Whisk & Steam",
            description = "Vibrant emerald green ceremonial grade Japanese matcha whisked into a rich umami liquor, paired with naturally sweet steamed oat milk.",
            difficulty = "Beginner",
            grindSize = "Stone Ground Matcha Powder",
            recommendedRatio = "2g Matcha : 50ml Water : 180ml Milk",
            waterTemperature = "80°C / 176°F",
            prepTimeMinutes = 4,
            brewTimeSeconds = 40,
            servings = 1,
            caffeineMgApprox = 70,
            isHouseVariation = true,
            houseTip = "Never use boiling water for matcha; 80°C preserves the delicate chlorophyll sweetness and prevents bitter scorching.",
            equipment = listOf("Chasen (Bamboo Whisk)", "Chawan (Matcha Bowl)", "Fine Sifter", "Milk Pitcher"),
            ingredients = listOf(
                RecipeIngredient("Ceremonial Matcha (Uji/Yame)", 2.5f, "g", "Sifted to remove clumps"),
                RecipeIngredient("Warm Water (80°C)", 50f, "ml", "For whisking base"),
                RecipeIngredient("Steamed Oat Milk (Barista Edition)", 180f, "ml", "Steamed to 60°C"),
                RecipeIngredient("Agave or Pure Honey", 5f, "g", "Optional natural sweetener")
            ),
            steps = listOf(
                RecipeStep(1, "Sift Matcha", "Sift 2.5g matcha powder into bowl to eliminate all lumps.", 20),
                RecipeStep(2, "W-Motion Whisk", "Add 50ml 80°C water. Whisk briskly in a rapid 'W' pattern until a dense jade micro-froth forms.", 30),
                RecipeStep(3, "Pour Silky Oat Milk", "Steam oat milk to 60°C and pour over matcha base with latte art.", 20)
            ),
            tags = listOf("Matcha", "Ceremonial", "Antioxidants", "Zen")
        ),

        // 25. DIRTY CHAI
        createRecipe(
            id = "dirty_chai",
            title = "Spiced Dirty Chai Latte",
            subtitle = "Stone-ground masala spiced tea topped with a shot of espresso",
            category = "Tea & Café",
            brewMethod = "Infusion & Espresso",
            description = "The ultimate union of spices and coffee. A rich black tea steeped with crushed cinnamon, cardamom, ginger, and cloves, combined with steamed milk and crowned with an espresso shot.",
            difficulty = "Beginner",
            grindSize = "Fine for Espresso",
            recommendedRatio = "1:2 (Chai : Milk) + 1 Shot",
            waterTemperature = "95°C / 203°F",
            prepTimeMinutes = 5,
            brewTimeSeconds = 30,
            servings = 1,
            caffeineMgApprox = 110,
            isHouseVariation = true,
            houseTip = "Steam the chai concentrate together with the milk inside the pitcher for complete harmonic emulsification.",
            equipment = listOf("Espresso Machine", "Steaming Pitcher", "Latte Cup"),
            ingredients = listOf(
                RecipeIngredient("Spiced Masala Chai Concentrate", 60f, "ml", "House-brewed with cinnamon & cloves"),
                RecipeIngredient("Fresh Espresso Shot", 36f, "ml", "Single or double espresso"),
                RecipeIngredient("Whole or Oat Milk", 150f, "ml", "Steamed to 65°C"),
                RecipeIngredient("Cinnamon Dusting", 1f, "pinch", "Garnish")
            ),
            steps = listOf(
                RecipeStep(1, "Steam Chai & Milk", "Combine chai concentrate and milk in pitcher, steam to silky 65°C.", 30),
                RecipeStep(2, "Extract Espresso", "Pull double espresso into your latte cup.", 28),
                RecipeStep(3, "Pour & Dust", "Pour steamed chai milk over espresso, garnish with freshly grated cinnamon.", 15)
            ),
            tags = listOf("Dirty Chai", "Spiced", "Warmth", "Autumn")
        ),

        // 26. LONDON FOG
        createRecipe(
            id = "london_fog",
            title = "London Fog (Earl Grey Latte)",
            subtitle = "Bergamot-infused Earl Grey steeped with lavender and vanilla steam",
            category = "Tea & Café",
            brewMethod = "Steep & Steam",
            description = "A comforting Canadian specialty café staple. Robust black tea scented with Italian bergamot and French culinary lavender, sweetened with vanilla bean and topped with steamed microfoam.",
            difficulty = "Beginner",
            grindSize = "Whole Leaf Tea",
            recommendedRatio = "1:1 (Tea : Steamed Milk)",
            waterTemperature = "96°C / 205°F",
            prepTimeMinutes = 5,
            brewTimeSeconds = 240,
            servings = 1,
            caffeineMgApprox = 40,
            isHouseVariation = false,
            houseTip = "Steep full 4 minutes to ensure bergamot botanicals stand up to the richness of whole milk.",
            equipment = listOf("Tea Infuser / Teapot", "Milk Steaming Pitcher", "Mug"),
            ingredients = listOf(
                RecipeIngredient("Earl Grey Tea Leaves", 4f, "g", "Bergamot scented black tea"),
                RecipeIngredient("Dried Culinary Lavender", 1f, "g", "Subtle floral pinch"),
                RecipeIngredient("Boiling Filtered Water", 120f, "ml", "To steep tea"),
                RecipeIngredient("Vanilla Bean Syrup", 15f, "ml", "Real Madagascar vanilla"),
                RecipeIngredient("Steamed Whole Milk", 120f, "ml", "Silky foam cap")
            ),
            steps = listOf(
                RecipeStep(1, "Steep Tea & Lavender", "Steep Earl Grey and lavender in 120ml hot water for 4 minutes. Strain into mug.", 240),
                RecipeStep(2, "Add Vanilla", "Stir vanilla syrup into hot tea infusion.", 10),
                RecipeStep(3, "Steam & Top", "Steam milk with rich microfoam, pour into mug creating a fluffy cloud.", 20)
            ),
            tags = listOf("London Fog", "Earl Grey", "Bergamot", "Lavender")
        ),

        // 27. HOMEMADE VANILLA BEAN SYRUP
        createRecipe(
            id = "syrup_vanilla_bean",
            title = "Barista Vanilla Bean Simple Syrup",
            subtitle = "Pure cane sugar simmered with split Tahitian vanilla pods",
            category = "Specialties & Syrups",
            brewMethod = "Simmer & Infuse",
            description = "The secret to world-class café drinks. Ditch artificial chemical syrups for this luscious 1:1 real vanilla bean syrup speckled with aromatic vanilla caviar.",
            difficulty = "Beginner",
            grindSize = "N/A",
            recommendedRatio = "1:1 (Sugar to Water)",
            waterTemperature = "Simmer (90°C)",
            prepTimeMinutes = 15,
            brewTimeSeconds = 600,
            servings = 20,
            caffeineMgApprox = 0,
            isHouseVariation = true,
            houseTip = "Keep the scraped vanilla pod submerged in the syrup jar as it chills in the fridge for continuous infusion over weeks.",
            equipment = listOf("Small Saucepan", "Paring Knife", "Sterilized Glass Bottle"),
            ingredients = listOf(
                RecipeIngredient("Pure Cane Sugar", 200f, "g", "Unbleached organic sugar"),
                RecipeIngredient("Filtered Water", 200f, "ml", "Equal parts"),
                RecipeIngredient("Whole Vanilla Bean Pod", 1f, "unit", "Split lengthwise, seeds scraped")
            ),
            steps = listOf(
                RecipeStep(1, "Scrape Pod", "Split pod with knife, scrape inner black caviar seeds into saucepan.", 30),
                RecipeStep(2, "Simmer & Dissolve", "Add water and sugar. Heat gently on medium-low, stirring until sugar fully dissolves without boiling.", 300),
                RecipeStep(3, "Cool & Bottle", "Allow to cool, bottle with pod. Store chilled up to 1 month.", 300)
            ),
            tags = listOf("Syrup", "Vanilla", "Pantry", "Artisan")
        ),

        // 28. BROWN SUGAR CINNAMON SYRUP
        createRecipe(
            id = "syrup_brown_sugar_cinnamon",
            title = "Brown Sugar Cinnamon Syrup",
            subtitle = "Dark muscovado sugar and cracked Ceylon cinnamon sticks",
            category = "Specialties & Syrups",
            brewMethod = "Simmer & Infuse",
            description = "Rich molasses-forward syrup that provides the signature foundation for shaken espressos and autumn café favorites.",
            difficulty = "Beginner",
            grindSize = "N/A",
            recommendedRatio = "1:1",
            waterTemperature = "Simmer (90°C)",
            prepTimeMinutes = 15,
            brewTimeSeconds = 600,
            servings = 20,
            caffeineMgApprox = 0,
            isHouseVariation = false,
            houseTip = "Cracking the cinnamon sticks exposes greater internal surface area for much deeper spice extraction.",
            equipment = listOf("Saucepan", "Glass Bottle", "Fine Sieve"),
            ingredients = listOf(
                RecipeIngredient("Dark Brown Muscovado Sugar", 200f, "g", "Rich in molasses"),
                RecipeIngredient("Water", 200f, "ml", "Filtered water"),
                RecipeIngredient("Ceylon Cinnamon Sticks", 3f, "unit", "Cracked into pieces"),
                RecipeIngredient("Pure Vanilla Extract", 5f, "ml", "1 teaspoon added off heat")
            ),
            steps = listOf(
                RecipeStep(1, "Simmer Spices", "Combine brown sugar, water, and cinnamon sticks in saucepan. Simmer gently for 8 minutes.", 480),
                RecipeStep(2, "Cool & Strain", "Remove from heat, stir in vanilla extract. Strain into clean bottle.", 120)
            ),
            tags = listOf("Syrup", "Cinnamon", "Brown Sugar", "Spiced")
        ),

        // 29. ICED ORANGE BLOSSOM ESPRESSO TONIC
        createRecipe(
            id = "orange_blossom_tonic",
            title = "Orange Blossom Espresso Tonic",
            subtitle = "Effervescent botanical tonic with floating espresso & citrus oils",
            category = "Specialties & Syrups",
            brewMethod = "Layered Cocktail",
            description = "The ultimate summer specialty refresher. Crisp botanical quinine tonic water infused with a drop of orange blossom water, topped with a floating double espresso.",
            difficulty = "Beginner",
            grindSize = "Fine",
            recommendedRatio = "1 Double Shot : 150ml Tonic",
            waterTemperature = "Chilled / Iced",
            prepTimeMinutes = 3,
            brewTimeSeconds = 28,
            servings = 1,
            caffeineMgApprox = 128,
            isHouseVariation = true,
            houseTip = "Pour the espresso gently over an upside-down bar spoon against the ice cube to maintain a clean two-tone visual split layer.",
            equipment = listOf("Collins Highball Glass", "Bar Spoon", "Espresso Machine"),
            ingredients = listOf(
                RecipeIngredient("Fresh Double Espresso", 36f, "ml", "Single origin citrus profile"),
                RecipeIngredient("Artisan Tonic Water (Chilled)", 150f, "ml", "Fever-Tree or Mediterranean"),
                RecipeIngredient("Orange Blossom Water", 2f, "drop", "Subtle floral aroma"),
                RecipeIngredient("Fresh Orange Peel Ribbon", 1f, "unit", "Expressed over rim"),
                RecipeIngredient("Clear Ice Spears", 100f, "g", "To chill")
            ),
            steps = listOf(
                RecipeStep(1, "Build Tonic Glass", "Fill glass with ice spears. Pour 150ml chilled tonic and 2 drops orange blossom water.", 15),
                RecipeStep(2, "Float Espresso", "Pull double espresso. Place bar spoon just touching top ice cube and pour espresso slowly to float.", 28),
                RecipeStep(3, "Express Orange Peel", "Twist fresh orange peel over glass to release fragrant citrus oils, rest on rim.", 10)
            ),
            tags = listOf("Espresso Tonic", "Sparkling", "Citrus", "Summer Specialty")
        ),

        // 30. CARDAMOM ROSE CAPPUCCINO
        createRecipe(
            id = "cardamom_rose_cappuccino",
            title = "Cardamom Rose Cappuccino",
            subtitle = "Crushed green cardamom espresso with rosewater microfoam",
            category = "Specialties & Syrups",
            brewMethod = "Espresso & Steam",
            description = "A mesmerizing Middle-Eastern inspired café specialty. Freshly crushed green cardamom seeds are incorporated directly into the espresso basket, finished with subtle rose microfoam and dried petals.",
            difficulty = "Intermediate",
            grindSize = "Fine",
            recommendedRatio = "1:1:1",
            waterTemperature = "65°C / 149°F",
            prepTimeMinutes = 4,
            brewTimeSeconds = 28,
            servings = 1,
            caffeineMgApprox = 128,
            isHouseVariation = true,
            houseTip = "Gently crush green cardamom seeds with a mortar and pestle and distribute with the coffee grounds for an intensely aromatic extraction.",
            equipment = listOf("Espresso Machine", "Mortar & Pestle", "Steaming Pitcher"),
            ingredients = listOf(
                RecipeIngredient("Specialty Coffee Grounds", 18f, "g", "Medium roast"),
                RecipeIngredient("Crushed Green Cardamom Seeds", 1f, "g", "Mixed in grounds"),
                RecipeIngredient("Whole Milk with 1 Drop Rosewater", 130f, "ml", "Steamed to 65°C"),
                RecipeIngredient("Organic Dried Rose Petals", 1f, "g", "For garnish")
            ),
            steps = listOf(
                RecipeStep(1, "Puck Preparation", "Mix crushed cardamom into coffee grounds, tamp firmly.", 15),
                RecipeStep(2, "Extract Aromatic Shot", "Pull 36g espresso into cup. The cardamom aroma will bloom immediately.", 28),
                RecipeStep(3, "Steam Rose Microfoam", "Add single drop of rosewater to milk, steam to velvety microfoam, pour art, and sprinkle dried petals.", 30)
            ),
            tags = listOf("Cardamom", "Rose", "Specialty", "Floral")
        )
    )

    private fun createRecipe(
        id: String,
        title: String,
        subtitle: String,
        category: String,
        brewMethod: String,
        description: String,
        difficulty: String,
        grindSize: String,
        recommendedRatio: String,
        waterTemperature: String,
        prepTimeMinutes: Int,
        brewTimeSeconds: Int,
        servings: Int,
        caffeineMgApprox: Int,
        isHouseVariation: Boolean,
        houseTip: String,
        equipment: List<String>,
        ingredients: List<RecipeIngredient>,
        steps: List<RecipeStep>,
        tags: List<String>,
        coverDrawableRes: Int? = null
    ): RecipeEntity {
        return RecipeEntity(
            id = id,
            title = title,
            subtitle = subtitle,
            category = category,
            brewMethod = brewMethod,
            description = description,
            difficulty = difficulty,
            grindSize = grindSize,
            recommendedRatio = recommendedRatio,
            waterTemperature = waterTemperature,
            prepTimeMinutes = prepTimeMinutes,
            brewTimeSeconds = brewTimeSeconds,
            servings = servings,
            caffeineMgApprox = caffeineMgApprox,
            isHouseVariation = isHouseVariation,
            houseTip = houseTip,
            equipmentJson = RecipeEntity.buildListJson(equipment),
            ingredientsJson = RecipeEntity.buildIngredientsJson(ingredients),
            instructionsJson = RecipeEntity.buildInstructionsJson(steps),
            tagsJson = RecipeEntity.buildListJson(tags),
            coverDrawableRes = coverDrawableRes,
            isFavorite = (id == "espresso" || id == "flat_white" || id == "v60_pourover" || id == "cold_brew_concentrate"),
            isSavedForLater = false,
            isCustom = false,
            viewCount = if (id == "espresso") 14 else if (id == "flat_white") 22 else 5
        )
    }
}
