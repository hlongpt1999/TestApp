package com.example.testandroid.module.dashboard.model

import android.graphics.drawable.Drawable
import androidx.fragment.app.Fragment

enum class DashBoardNavigation {
    NEWS, SETTINGS,
}

data class Navigation(
    var id: DashBoardNavigation,
    var label: String,
    var icon: Drawable? = null,
    var imageUrl: String? = null,
    var fragment: Fragment? = null,
)
