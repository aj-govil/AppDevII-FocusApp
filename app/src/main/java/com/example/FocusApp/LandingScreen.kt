package com.example.FocusApp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import kotlinx.coroutines.delay

/**
 * Implementation taken from Advanced State and Side Effects in Jetpack Compose Codelab
 * See @https://developer.android.com/codelabs/jetpack-compose-advanced-state-side-effects#4
 */

private const val SplashWaitTime: Long = 2000 // wait 2 seconds
@Composable
fun LandingScreen(onTimeout: () -> Unit, modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        // This will always refer to the latest onTimeout function that
        // LandingScreen was recomposed with
        val currentOnTimeout by rememberUpdatedState(onTimeout)

        // Create an effect that matches the lifecycle of LandingScreen.
        // If LandingScreen recomposes or onTimeout changes,
        // the delay shouldn't start again.
        LaunchedEffect(Unit) {
            delay(SplashWaitTime)
            currentOnTimeout()
        }
        // Coroutine scopes can be added to fetch data if needed

        // Content to be displayed in the middle of the screen
        // Dev Note: This can be changed to an icon or custom logo of our choosing
        // Image(painterResource(id = R.drawable.ic_crane_drawer), contentDescription = null)

        Text(text = "Please wait a moment while gradle gradles :)")

    }
}
