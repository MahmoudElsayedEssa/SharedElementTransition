@file:Suppress("UNREACHABLE_CODE")

package com.binissa.sharedelementtransitionexample.screens.home

import com.binissa.sharedelementtransitionexample.R

// Enhanced FoodItem data class with more details
data class FoodItem(
    val id: Int,
    val name: String,
    val price: String,
    val rating: Float,
    val imageRes: Int,
    val description: String,
    val ingredients: List<String>,
    val calories: Int,
    val preparationTime: Int, // in minutes
    val isVegetarian: Boolean,
    val reviews: List<ReviewItem>,
    val isSelected: Boolean = false,
    val isInCart: Boolean = false,
    val quantity: Int = 0
)

// Review data class for food items
data class ReviewItem(
    val reviewerName: String,
    val rating: Float,
    val comment: String,
    val date: String
)

// Expanded sample list of food items with rich data
val sampleFoodItems = listOf(
    FoodItem(
        id = 0,
        name = "French Fries",
        price = "$7.25",
        rating = 4.5f,
        imageRes = R.drawable.french_fries,
        description = "Crispy golden fries made from premium russet potatoes, perfectly seasoned with sea salt.",
        ingredients = listOf("Russet potatoes", "Vegetable oil", "Sea salt"),
        calories = 312,
        preparationTime = 15,
        isVegetarian = true,
        reviews = listOf(
            ReviewItem(
                "Emma Wilson",
                4.5f,
                "These fries are perfectly crispy! Great portion size too.",
                "Feb 12, 2025"
            ),
            ReviewItem(
                "James Smith",
                4.0f,
                "Good flavor but could be a bit crispier. Still very tasty.",
                "Jan 30, 2025"
            )
        )
    ),
    FoodItem(
        id = 1,
        name = "Doughnut",
        price = "$10.00",
        rating = 3.8f,
        imageRes = R.drawable.doughnut,
        description = "Soft and fluffy glazed doughnut with a melt-in-your-mouth texture.",
        ingredients = listOf("Flour", "Sugar", "Yeast", "Eggs", "Milk", "Butter", "Vanilla glaze"),
        calories = 289,
        preparationTime = 30,
        isVegetarian = true,
        reviews = listOf(
            ReviewItem(
                "Sophia Chen",
                3.5f,
                "Good sweetness but a bit too dense for my taste.",
                "Mar 5, 2025"
            ),
            ReviewItem(
                "Noah Johnson",
                4.0f,
                "Classic glazed goodness! My kids love them.",
                "Feb 22, 2025"
            )
        )
    ),
    FoodItem(
        id = 2,
        name = "Hamburger",
        price = "$12.50",
        rating = 4.7f,
        imageRes = R.drawable.hamburger,
        description = "Premium beef patty with fresh lettuce, tomato, pickles, and our signature sauce on a toasted brioche bun.",
        ingredients = listOf(
            "Beef patty",
            "Brioche bun",
            "Lettuce",
            "Tomato",
            "Onion",
            "Pickles",
            "Signature sauce"
        ),
        calories = 520,
        preparationTime = 18,
        isVegetarian = false,
        reviews = listOf(
            ReviewItem(
                "Liam Taylor",
                5.0f,
                "Best burger I've had in a long time! Juicy and perfectly cooked.",
                "Mar 1, 2025"
            ),
            ReviewItem(
                "Olivia Brown",
                4.5f,
                "Great flavor profile. The sauce really makes it stand out.",
                "Feb 15, 2025"
            )
        )
    ),
    FoodItem(
        id = 3,
        name = "Hotdog",
        price = "$9.15",
        rating = 3.7f,
        imageRes = R.drawable.hotdog,
        description = "All-beef frankfurter served on a warm bun with your choice of ketchup, mustard, relish, and onions.",
        ingredients = listOf(
            "Beef frankfurter",
            "Hot dog bun",
            "Ketchup",
            "Mustard",
            "Relish",
            "Diced onions"
        ),
        calories = 290,
        preparationTime = 10,
        isVegetarian = false,
        reviews = listOf(
            ReviewItem(
                "Ethan Davis",
                3.5f,
                "Standard hot dog, nothing extraordinary but satisfying.",
                "Feb 28, 2025"
            ),
            ReviewItem(
                "Ava Martinez",
                4.0f,
                "Good quality hot dog. The bun was fresh which is important!",
                "Feb 10, 2025"
            )
        )
    ),
    FoodItem(
        id = 4,
        name = "Sandwich",
        price = "$8.00",
        rating = 4.3f,
        imageRes = R.drawable.sandwich,
        description = "Freshly baked multi-grain bread filled with turkey, avocado, bacon, lettuce, and tomato. Served with a side of chips.",
        ingredients = listOf(
            "Multi-grain bread",
            "Turkey",
            "Avocado",
            "Bacon",
            "Lettuce",
            "Tomato",
            "Mayo"
        ),
        calories = 425,
        preparationTime = 12,
        isVegetarian = false,
        reviews = listOf(
            ReviewItem(
                "Isabella Kim",
                4.5f,
                "Fresh ingredients and good portion! I loved the avocado.",
                "Mar 3, 2025"
            ),
            ReviewItem(
                "Lucas Garcia",
                4.0f,
                "Solid sandwich. The bread is really good quality.",
                "Feb 18, 2025"
            )
        )
    ),
    FoodItem(
        id = 5,
        name = "Sushi",
        price = "$15.50",
        rating = 4.6f,
        imageRes = R.drawable.sushi,
        description = "Freshly prepared sushi rolls with premium salmon, cucumber, and avocado. Served with wasabi, ginger, and soy sauce.",
        ingredients = listOf(
            "Sushi rice",
            "Nori seaweed",
            "Salmon",
            "Cucumber",
            "Avocado",
            "Wasabi",
            "Pickled ginger",
            "Soy sauce"
        ),
        calories = 350,
        preparationTime = 25,
        isVegetarian = false,
        reviews = listOf(
            ReviewItem(
                "Mia Wong",
                5.0f,
                "Fresh fish and perfectly seasoned rice. Exceptional quality!",
                "Mar 2, 2025"
            ),
            ReviewItem(
                "William Lee",
                4.5f,
                "Very fresh ingredients. The salmon practically melts in your mouth.",
                "Feb 20, 2025"
            )
        )
    ),
    FoodItem(
        id = 6,
        name = "Pizza",
        price = "$12.50",
        rating = 4.4f,
        imageRes = R.drawable.pizza,
        description = "Hand-tossed thin crust pizza with fresh mozzarella, basil, and our signature tomato sauce.",
        ingredients = listOf(
            "Pizza dough",
            "Tomato sauce",
            "Fresh mozzarella",
            "Basil",
            "Olive oil",
            "Italian herbs"
        ),
        calories = 285,
        preparationTime = 20,
        isVegetarian = true,
        reviews = listOf(
            ReviewItem(
                "Charlotte Romano",
                4.5f,
                "Authentic Italian style with a great balance of flavors.",
                "Mar 5, 2025"
            ),
            ReviewItem(
                "Benjamin Scott",
                4.0f,
                "Good pizza! Crust was perfectly crisp and chewy.",
                "Feb 25, 2025"
            )
        )
    ),
    FoodItem(
        id = 7,
        name = "Burrito",
        price = "$18.22",
        rating = 4.2f,
        imageRes = R.drawable.burrito,
        description = "Large flour tortilla filled with seasoned rice, black beans, grilled chicken, cheese, guacamole, and sour cream.",
        ingredients = listOf(
            "Flour tortilla",
            "Rice",
            "Black beans",
            "Grilled chicken",
            "Cheddar cheese",
            "Guacamole",
            "Sour cream",
            "Pico de gallo"
        ),
        calories = 680,
        preparationTime = 15,
        isVegetarian = false,
        reviews = listOf(
            ReviewItem(
                "Amelia Rodriguez",
                4.0f,
                "Hefty portion and great flavor. The guacamole was fresh!",
                "Feb 28, 2025"
            ),
            ReviewItem(
                "Henry Thompson",
                4.5f,
                "Perfect mix of ingredients. Very filling!",
                "Feb 15, 2025"
            )
        )
    ),
    FoodItem(
        id = 8,
        name = "Cake",
        price = "$5.99",
        rating = 4.5f,
        imageRes = R.drawable.bakery_cake_dessert,
        description = "Rich chocolate cake with layers of buttercream frosting. Perfect for dessert or celebrations.",
        ingredients = listOf(
            "Flour",
            "Sugar",
            "Cocoa powder",
            "Eggs",
            "Butter",
            "Milk",
            "Vanilla extract",
            "Buttercream frosting"
        ),
        calories = 450,
        preparationTime = 45,
        isVegetarian = true,
        reviews = listOf(
            ReviewItem(
                "Evelyn Baker",
                5.0f,
                "Decadent and moist! The frosting is just perfect.",
                "Mar 6, 2025"
            ),
            ReviewItem(
                "Alexander White",
                4.0f,
                "Delicious cake. Not too sweet, which I appreciate.",
                "Feb 23, 2025"
            )
        )
    )
)







