package com.example.testandroid.module.jetpackcompose.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.testandroid.R
import com.example.testandroid.function.GlobalData
import com.example.testandroid.function.OpenActivity.open
import com.example.testandroid.function.OpenActivity.openPipActivity
import com.example.testandroid.function.OpenActivity.openTodoActivity
import com.example.testandroid.function.ToastUtils.showToastLongTime
import com.example.testandroid.model.ScreenEnum
import com.example.testandroid.module.jetpackcompose.model.FeatureEnum
import com.example.testandroid.module.todo.view.ui.theme.TestAndroidTheme
import java.text.SimpleDateFormat
import java.util.*

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestAndroidTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White,
                ) {
                    FeatureList(
                        features = listOf(
                            FeatureEnum.TODO,
                            FeatureEnum.QUIZ,
                            FeatureEnum.ROOM,
                            FeatureEnum.DASHBOARD,
                        )
                    )
                }
            }
        }
    }

    @Composable
    fun FeatureList(features: List<FeatureEnum>) {
        val paddingModifier = Modifier.padding(8.dp)
        val backgroundUrl = GlobalData.appConfig.backgroundUrl
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+07:00"))
        val currentDate = SimpleDateFormat("hh:mm").format(Date())
        Box(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = backgroundUrl,
                contentDescription = backgroundUrl,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Column(modifier = Modifier.padding(horizontal = 32.dp, vertical = 32.dp)) {
                //Header
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(horizontalAlignment = Alignment.Start) {
                        Text("Day")

                        Text(
                            text = currentDate,
                            color = Color.White,
                            modifier = Modifier.height(24.dp)
                        )
                    }

                    Column(horizontalAlignment = Alignment.End) {
                        Text("Temperature")
                        Text("13C")
                    }
                }

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(8.dp),
                ) {
                    items(features.size) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onItemClick(features[it]) },
                            elevation = 8.dp,
                            shape = RoundedCornerShape(8.dp),
                            border = BorderStroke(1.dp, Color.Black)
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                AsyncImage(
                                    model = features[it].avatarUrl,
                                    contentDescription = features[it].featureName,
                                    modifier = Modifier
                                        .aspectRatio(0.3f)
                                        .weight(1f)
                                )
                                Text(
                                    text = features[it].featureName,
                                    modifier = paddingModifier
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    private fun onItemClick(feature: FeatureEnum) {
        when (feature) {
            FeatureEnum.TODO -> {
                this.openTodoActivity()
            }
            FeatureEnum.QUIZ -> {
                this.openPipActivity()
            }
            FeatureEnum.PIP -> {
                this.openPipActivity()
            }
            FeatureEnum.ROOM -> {
                this.open(ScreenEnum.ROOM)
            }
            else -> {
                if (feature.screen == null) {
                    this.showToastLongTime(getString(R.string.msg_default_feature_soon))
                } else {
                    this.open(feature.screen)
                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    private fun PreviewFeatureList() {
        TestAndroidTheme {
            FeatureList(features = listOf(FeatureEnum.TODO, FeatureEnum.QUIZ, FeatureEnum.PIP))
        }
    }
}