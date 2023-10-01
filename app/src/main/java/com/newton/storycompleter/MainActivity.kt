package com.newton.storycompleter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.newton.storycompleter.ui.onboarding.SplashScreen
import com.newton.storycompleter.ui.stories.StoriesScreen
import com.newton.storycompleter.ui.theme.StoryCompleterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StoryCompleterTheme {

                val showSplash = remember { mutableStateOf(true) }
                if (showSplash.value) {
                    SplashScreen(onNext = { showSplash.value = false })
                }
                else {
                    StoriesScreen(stories = emptyList(), onStoryClick = { }, onCreateStoryClick = { })
                }
            }
        }
    }
}