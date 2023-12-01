package com.example.FocusApp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.example.FocusApp.R
import kotlinx.coroutines.delay

/**
 * Implementation taken from Advanced State and Side Effects in Jetpack Compose Codelab
 * See @https://developer.android.com/codelabs/jetpack-compose-advanced-state-side-effects#4
 */

private const val SplashWaitTime: Long = 2000 // wait 2 seconds
@Composable
fun LandingScreen(onTimeout: () -> Unit, modifier: Modifier = Modifier) {
    Box(modifier = modifier
        .fillMaxSize()
        .background(Color.Black), contentAlignment = Alignment.Center) {

        val currentOnTimeout by rememberUpdatedState(onTimeout)
        LaunchedEffect(Unit) {
            delay(SplashWaitTime)
            currentOnTimeout()
        }

        Image(
            painter = painterResource(id = R.drawable.focuslogo),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )

    }
}
