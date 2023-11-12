package com.example.testandroid.model

import com.example.testandroid.MainActivity
import com.example.testandroid.module.dashboard.view.DashBoardActivity
import com.example.testandroid.module.flash.FlashActivity
import com.example.testandroid.module.jetpackcompose.view.HomeActivity
import com.example.testandroid.module.login.view.LoginActivity
import com.example.testandroid.module.security.view.SecurityActivity
import com.example.testandroid.module.testroom.view.RoomActivity
import com.example.testandroid.module.todo.view.TodoActivity

enum class ScreenEnum(val activity: Class<*>) {
    FLASH(FlashActivity::class.java),
    SECURITY(SecurityActivity::class.java),
    LOGIN(LoginActivity::class.java),
    MAIN(MainActivity::class.java),
    HOME(HomeActivity::class.java),
    TODO(TodoActivity::class.java),
    ROOM(RoomActivity::class.java),
    DASHBOARD(DashBoardActivity::class.java),
}