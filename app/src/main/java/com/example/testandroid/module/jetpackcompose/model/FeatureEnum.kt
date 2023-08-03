package com.example.testandroid.module.jetpackcompose.model

enum class FeatureEnum(
    val featureName: String,
    val avatarUrl: String? = "",
) {
    TODO(
        "To Do",
        avatarUrl = "https://static.vecteezy.com/system/resources/thumbnails/003/529/153/small/business-to-do-list-flat-icon-modern-style-free-vector.jpg",
    ),

    QUIZ(
        "Quiz",
        avatarUrl = "https://cdn-icons-png.flaticon.com/512/3407/3407038.png",
    ),

    PIP(
        "Pip Screen",
        avatarUrl = "https://cdn-icons-png.flaticon.com/512/3407/3407038.png",
    )
}