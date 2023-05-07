package com.jefisu.menuside

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MenuSide()
        }
    }
}

@Composable
fun MenuSide() {
    var menuExpanded by rememberSaveable { mutableStateOf(false) }
    val updateAnim = updateTransition(targetState = menuExpanded, label = "")

    fun <T> defaultTransitionAnim() = tween<T>(
        durationMillis = 300,
        easing = FastOutSlowInEasing
    )

    val scale by updateAnim.animateFloat(
        label = "",
        transitionSpec = { defaultTransitionAnim() }
    ) {
        if (it) 0.7f else 1f
    }

    val offsetX by updateAnim.animateFloat(
        label = "",
        transitionSpec = { defaultTransitionAnim() }
    ) {
        if (it) 0.7f else 0f
    }

    val cornerShape by updateAnim.animateDp(
        label = "",
        transitionSpec = { defaultTransitionAnim() }
    ) {
        if (it) 16.dp else 0.dp
    }

    val menuOffsetX by updateAnim.animateDp(
        label = "",
        transitionSpec = { defaultTransitionAnim() }
    ) {
        if (it) 0.dp else (-100).dp
    }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Red.copy(0.4f))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.56f)
                .fillMaxHeight()
                .offset(x = menuOffsetX)
                .padding(16.dp)
        ) {
            Button(
                onClick = { menuExpanded = !menuExpanded },
                modifier = Modifier.align(Alignment.Center)
            ) {
                Text(text = "COLLAPSE MENU")
            }
        }
        if (menuExpanded) {
            (1..2).reversed().forEach { i ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .scale(scale - (0.05f * i))
                        .offset(x = maxWidth * (offsetX - (0.05f * i)))
                        .clip(RoundedCornerShape(cornerShape))
                        .background((if (i == 1) Color.Blue else Color.Green).copy(0.7f))
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .scale(scale)
                .offset(x = maxWidth * offsetX)
                .clip(RoundedCornerShape(cornerShape))
                .background(Color.White)
                .padding(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = null,
                    modifier = Modifier.clickable(
                        enabled = !menuExpanded,
                        onClick = { menuExpanded = !menuExpanded }
                    )
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "Menu",
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewMenuSide() {
    MenuSide()
}