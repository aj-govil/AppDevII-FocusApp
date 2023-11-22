package com.example.ajeshgovil_assignment2

import android.graphics.drawable.Icon
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import java.util.Locale

// Main row containing different tabs for user to switch between
@Composable
fun FocusTabRow(
    allScreens: List<FocusDestination>,
    onTabSelected: (FocusDestination) -> Unit,
    currentScreen: FocusDestination
){
    // This was used as a setup from the Bank Application team
    Surface (
        Modifier.fillMaxWidth().height(64.dp)
        ){
        Row(Modifier.selectableGroup()) {
            allScreens.forEach { screen ->
                FocusTab(
                    text = screen.route,
                    onSelected = { onTabSelected(screen) },
                    selected = currentScreen == screen
                )
            }
        }
    }

}

// Initial setup for this FocusTab was copied from
// The Bank Application project by Jeff, Josh and Caden
// see @https://github.com/AppDevOrganization/5A6-group-project

// Individual tabs use can click on
@Composable
private fun FocusTab(
    text: String,
    onSelected: () -> Unit,
    selected: Boolean,
){
    Row(
        modifier = Modifier
            .padding(13.dp)
            .height(64.dp)
            .selectable(
                selected = selected,
                onClick = onSelected,
                role = Role.Tab,
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(
                    bounded = false,
                    radius = Dp.Unspecified,
                    color = Color.Unspecified
                )
            )
            .clearAndSetSemantics { contentDescription = text }
    ) {
        Spacer(Modifier.width(10.dp))
        Text(text.uppercase(Locale.getDefault()))
    }
}

