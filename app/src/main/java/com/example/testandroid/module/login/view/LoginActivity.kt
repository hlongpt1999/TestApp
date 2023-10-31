package com.example.testandroid.module.login.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.room.Room
import com.example.testandroid.function.OpenActivity.open
import com.example.testandroid.function.OpenActivity.openHomeActivity
import com.example.testandroid.function.ToastUtils
import com.example.testandroid.function.Utils
import com.example.testandroid.model.ScreenEnum
import com.example.testandroid.model.User
import com.example.testandroid.model.UserDao
import com.example.testandroid.model.UserInfoDatabase
import com.example.testandroid.module.login.viewmodel.AuthResultContract
import com.example.testandroid.module.todo.view.ui.theme.TestAndroidTheme
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task


class LoginActivity : AppCompatActivity() {

    private var database: UserInfoDatabase? = null
    private var userDao: UserDao? = null

    //Sign in with fingerprint
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        initDatabase()
//        handleAutoLogin()
        setContent {
            TestAndroidTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    BodyContent()
                }
            }
        }
    }

    @Composable
    fun BodyContent() {
        var userNameText by remember { mutableStateOf(TextFieldValue("")) }
        var passwordText by remember { mutableStateOf(TextFieldValue("")) }
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
                .padding(top = 100.dp)
                .wrapContentSize(Alignment.TopCenter)
                .background(color = Color.Gray),
            verticalArrangement = Arrangement.Center,
            contentPadding = PaddingValues(8.dp)
        ) {
            item {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(0.7f),
                    value = userNameText,
                    onValueChange = {
                        userNameText = it
                    },
                    label = { Text(text = "User Name") },
                    placeholder = { Text(text = "Enter your name") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    maxLines = 1,
                    singleLine = true,
                )
            }

            item {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(0.7f),
                    value = passwordText,
                    onValueChange = {
                        passwordText = it
                    },
                    label = { Text(text = "Password") },
                    placeholder = { Text(text = "Enter password") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    maxLines = 1,
                    singleLine = true,
                )
            }

            item {
                Button(
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .height(56.dp)
                        .padding(top = 16.dp),
                    onClick = { signIn(userNameText.text, passwordText.text) },
                    elevation = ButtonDefaults.elevation(
                        defaultElevation = 16.dp,
                        pressedElevation = 32.dp,
                        disabledElevation = 0.dp
                    )
                ) {
                    Text(text = "Login")
                }
            }

            item {
                GoogleSignInUI()
            }

            item {
                Button(
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .height(56.dp)
                        .padding(top = 16.dp),
                    onClick = {
                        handleSignInWithGoogle()
                    },
                    elevation = ButtonDefaults.elevation(
                        defaultElevation = 16.dp,
                        pressedElevation = 32.dp,
                        disabledElevation = 0.dp
                    )
                ) {
                    Text(text = "Sign In With Google copy - paste")
                }
            }

            initBiometricPrompt("Sign in")
            handleBiometricPromptResult()
            item {
                Button(
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .height(56.dp)
                        .padding(top = 16.dp),
                    onClick = {
                        handleSignInWithFingerprint()
                    },
                    elevation = ButtonDefaults.elevation(
                        defaultElevation = 16.dp,
                        pressedElevation = 32.dp,
                        disabledElevation = 0.dp
                    )
                ) {
                    Text(text = "Login with fingerprint")
                }
            }
        }
    }

    @Preview
    @Composable
    fun ContentPreview() {
        BodyContent()
    }

    private fun signIn(userName: String, password: String) {
        if (userName == "0" && password == "0") {
            val user = User(
                firstName = "Long",
                lastName = "Phan",
                userName = userName,
                password = password,
                phone = "12345",
                gender = 1
            )
            saveToDatabase(user)
            this.openHomeActivity()
        }
    }

    private fun initDatabase() {
        database = Room.databaseBuilder(
            applicationContext,
            UserInfoDatabase::class.java, "Info"
        ).build()
        userDao = database?.userDao()
    }

    private fun handleAutoLogin() {
        userDao?.let {
            val users: List<User> = it.getAll()
            if (users.isNotEmpty()) {
                this.openHomeActivity()
            }
        }
    }

    private fun saveToDatabase(user: User) {
        userDao?.let {
            it.insertAll(user)
        }
    }

    @Composable
    private fun GoogleSignInUI() {
        var text by remember { mutableStateOf<String?>(null) }
        val authResultLauncher = rememberLauncherForActivityResult(
            contract = AuthResultContract(),
            onResult = {
                try {
                    val account = it?.getResult(ApiException::class.java)
                    if (account == null) {
                        text = "Sign in failed"
                    } else {
                        ToastUtils.showToastLongTime(
                            "Sign in success with : account.displayName",
                            this
                        )
                    }
                } catch (e: ApiException) {
                    text = "Sign in failed because: " + e.message
                }
            }
        )

        AuthGoogleView(error = text, onCLick = {
            text = null
            authResultLauncher.launch(1)
        })
    }

    @Composable
    private fun AuthGoogleView(error: String?, onCLick: () -> Unit) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Button(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(56.dp)
                    .padding(top = 16.dp),
                onClick = {
                    onCLick()
                },
                elevation = ButtonDefaults.elevation(
                    defaultElevation = 16.dp,
                    pressedElevation = 32.dp,
                    disabledElevation = 0.dp
                )
            ) {
                Text(
                    text = error ?: "Login with Google"
                )
            }
        }
    }

    private fun handleSignInWithGoogle() {
        val signInIntent: Intent = Utils.getGoogleSignInClient(this).signInIntent
        startActivityForResult(signInIntent, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount = completedTask.getResult(ApiException::class.java)
            ToastUtils.showToastLongTime("Success: " + account.displayName, this)
        } catch (e: ApiException) {
            ToastUtils.showToastLongTime("Failed with: " + e.message, this)
        }
    }

    private fun handleSignInWithFingerprint() {
        biometricPrompt.authenticate(promptInfo)
    }

    private fun initBiometricPrompt(title: String) {
        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(title)
            .setDeviceCredentialAllowed(true).build()
    }

    private fun handleBiometricPromptResult() {
        biometricPrompt = BiometricPrompt(this, ContextCompat.getMainExecutor(this),
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(
                    errorCode: Int,
                    errString: CharSequence
                ) {
                    super.onAuthenticationError(errorCode, errString)
                }

                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult
                ) {
                    super.onAuthenticationSucceeded(result)
                    this@LoginActivity.open(ScreenEnum.HOME, true)
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                }
            })
    }
}