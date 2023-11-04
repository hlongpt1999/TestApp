package com.example.testandroid.function

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.example.testandroid.MainActivity
import com.example.testandroid.PipActivity
import com.example.testandroid.function.OpenActivity.openLoginActivity
import com.example.testandroid.model.ScreenEnum
import com.example.testandroid.module.flash.FlashActivity
import com.example.testandroid.module.jetpackcompose.view.HomeActivity
import com.example.testandroid.module.login.view.LoginActivity
import com.example.testandroid.module.retrofit.view.RetrofitActivity
import com.example.testandroid.module.security.view.SecurityActivity
import com.example.testandroid.module.todo.view.TodoActivity

object OpenActivity {

    fun Context.openSecurityActivity() {
        this.startActivity(Intent(this, SecurityActivity::class.java))
    }

    fun Context.openFlashActivity() {
        this.startActivity(Intent(this, FlashActivity::class.java))
    }

    fun Context.openMainActivity() {
        this.startActivity(Intent(this, MainActivity::class.java))
    }

    fun Context.openPipActivity() {
        this.startActivity(Intent(this, PipActivity::class.java))
    }

    fun Context.openRetrofitActivity() {
        this.startActivity(Intent(this, RetrofitActivity::class.java))
    }

    fun Context.openTodoActivity() {
        this.startActivity(Intent(this, TodoActivity::class.java))
    }

    fun Context.openQuizActivity() {
        //todo: change destination activity
        this.startActivity(Intent(this, TodoActivity::class.java))
    }

    fun Activity.openHomeActivity(finish: Boolean = true) {
        this.startActivity(Intent(this, HomeActivity::class.java))
        if (finish) {
            this.finish()
        }
    }

    fun Activity.openLoginActivity(finish: Boolean = true) {
        this.startActivity(Intent(this, LoginActivity::class.java))
        if (finish) {
            this.finish()
        }
    }

    fun Activity.open(screen: ScreenEnum, finishActivity: Boolean = false, bundle: Bundle? = null) {
        val intent = when (screen) {
            ScreenEnum.FLASH -> {
                Intent(this, FlashActivity::class.java)
            }
            ScreenEnum.SECURITY -> {
                Intent(this, SecurityActivity::class.java)
            }
            ScreenEnum.LOGIN -> {
                Intent(this, LoginActivity::class.java)
            }
            ScreenEnum.MAIN -> {
                Intent(this, MainActivity::class.java)
            }
            ScreenEnum.HOME -> {
                Intent(this, HomeActivity::class.java)
            }
            ScreenEnum.TODO -> {
                Intent(this, TodoActivity::class.java)
            }
        }
        this.startActivity(intent, bundle)

        if (finishActivity) {
            this.finish()
        }
    }
}