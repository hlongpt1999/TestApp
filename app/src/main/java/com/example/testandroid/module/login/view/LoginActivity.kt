package com.example.testandroid.module.login.view

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
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.testandroid.function.OpenActivity.open
import com.example.testandroid.function.OpenActivity.openHomeActivity
import com.example.testandroid.function.ToastUtils
import com.example.testandroid.function.ToastUtils.showToastLongTime
import com.example.testandroid.model.ScreenEnum
import com.example.testandroid.model.User
import com.example.testandroid.model.UserDao
import com.example.testandroid.model.UserInfoDatabase
import com.example.testandroid.module.login.viewmodel.AuthResultContract
import com.example.testandroid.module.login.viewmodel.LoginViewModel
import com.example.testandroid.module.todo.view.ui.theme.TestAndroidTheme
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private var database: UserInfoDatabase? = null

    //Sign in with fingerprint
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    //viewmodel
    private var viewModel: LoginViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDatabase()
        setUpViewModel()
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

    private fun setUpViewModel() {
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java].apply {
            saveUserToFirebaseLiveData.observe(this@LoginActivity) {
                it?.let {
                    if (it) {
                        this@LoginActivity.open(ScreenEnum.HOME)
                    } else {
                        this@LoginActivity.showToastLongTime("Sign in failed when saving data to Firebase")
                    }
                    saveUserToFirebaseLiveData.value = null
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
            viewModel?.saveToDatabase(user)
            this.openHomeActivity()
        }
    }

    private fun initDatabase() {
        CoroutineScope(Dispatchers.IO).launch {
            database = Room.databaseBuilder(
                applicationContext,
                UserInfoDatabase::class.java, "Info"
            ).build()
            viewModel?.userDao = database?.userDao()
            val users: List<User>? = viewModel?.userDao?.getAll()
            if (!users.isNullOrEmpty()) {
                this@LoginActivity.openHomeActivity()
            }
//            handleAutoLogin()
        }
    }

//    private fun handleAutoLogin() {
//        viewModel?.userDao?.let {
//            val users: List<User> = it.getAll()
//            if (users.isNotEmpty()) {
//                this.openHomeActivity()
//            }
//        }
//    }

    @Composable
    private fun GoogleSignInUI() {
        var text by remember { mutableStateOf<String?>(null) }
        val authResultLauncher = rememberLauncherForActivityResult(
            contract = AuthResultContract(),
            onResult = {
                try {
                    val account = it?.getResult(ApiException::class.java)
                    if (account == null) {
                        text = "Sign in failed with null account"
                    } else {
                        ToastUtils.showToastLongTime(
                            "Sign in success with :" + account.displayName,
                            this
                        )
                        handleLoginWithGoogleSuccess(account)
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

    private fun handleLoginWithGoogleSuccess(account: GoogleSignInAccount) {
        viewModel?.saveUserToFirebaseDatabase(account)
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