package com.evanemran.newsmvvm.presentation

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.evanemran.newsmvvm.presentation.utils.LayoutType

@Composable
fun ShimmerListItem(
    isLoading: Boolean,
    type: LayoutType,
    contentAfterLoading: @Composable  ()-> Unit,
    modifier: Modifier = Modifier
) {
    if(isLoading) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.Top
        )
        {
            when(type) {
                LayoutType.LARGE -> {
                    ShimmerLarge(modifier = modifier)
                }
                LayoutType.LINEAR -> {
                    ShimmerInLine(modifier = modifier)
                }
                LayoutType.OVERLAY -> {
                    ShimmerOverlay(modifier = modifier)
                }
                LayoutType.GRID -> {
                    ShimmerOverlay(modifier = modifier)
                }
            }
        }
    }
//    else {
//        contentAfterLoading()
//    }
}

@Composable
private fun ShimmerInLine(modifier: Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Top
    )
    {
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(
                    RoundedCornerShape(8.dp))
                .shimmerEffect()
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(14.dp)
                .clip(
                    RoundedCornerShape(8.dp))
                .shimmerEffect()
            )
            Spacer(modifier = Modifier.height(4.dp))
            Box(modifier = Modifier
                .fillMaxWidth(0.7f)
                .height(14.dp)
                .clip(
                    RoundedCornerShape(8.dp))
                .shimmerEffect()
            )
            Spacer(modifier = Modifier.height(4.dp))
            Box(modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(12.dp)
                .clip(
                    RoundedCornerShape(8.dp))
                .shimmerEffect()
            )
        }
    }
}

@Composable
private fun ShimmerLarge(modifier: Modifier) {
    Column(
        modifier = modifier,
    )
    {
        Box(modifier = Modifier
            .fillMaxWidth(0.9f)
            .height(18.dp)
            .clip(
                RoundedCornerShape(8.dp))
            .shimmerEffect()
        )
        Spacer(modifier = Modifier.height(4.dp))
        Box(modifier = Modifier
            .fillMaxWidth(0.8f)
            .height(18.dp)
            .clip(
                RoundedCornerShape(8.dp))
            .shimmerEffect()
        )
        Spacer(modifier = Modifier.height(4.dp))
        Box(modifier = Modifier
            .fillMaxWidth(0.7f)
            .height(18.dp)
            .clip(
                RoundedCornerShape(8.dp))
            .shimmerEffect()
        )
        Spacer(modifier = Modifier.height(4.dp))
        Box(modifier = Modifier
            .fillMaxWidth(0.8f)
            .height(14.dp)
            .clip(
                RoundedCornerShape(8.dp))
            .shimmerEffect()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .height(200.dp)
                .clip(
                    RoundedCornerShape(8.dp))
                .fillMaxWidth()
                .shimmerEffect()
        )
        Spacer(modifier = Modifier.height(4.dp))
        Box(modifier = Modifier
            .fillMaxWidth(0.8f)
            .height(14.dp)
            .clip(
                RoundedCornerShape(8.dp))
            .shimmerEffect()
        )
        Spacer(modifier = Modifier.height(4.dp))
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(14.dp)
            .clip(
                RoundedCornerShape(8.dp))
            .shimmerEffect()
        )
        Spacer(modifier = Modifier.height(4.dp))
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(14.dp)
            .clip(
                RoundedCornerShape(8.dp))
            .shimmerEffect()
        )
        Spacer(modifier = Modifier.height(4.dp))
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(14.dp)
            .clip(
                RoundedCornerShape(8.dp))
            .shimmerEffect()
        )
        Spacer(modifier = Modifier.height(4.dp))
        Box(modifier = Modifier
            .fillMaxWidth(0.5f)
            .height(14.dp)
            .clip(
                RoundedCornerShape(8.dp))
            .shimmerEffect()
        )
    }
}

@Composable
private fun ShimmerOverlay(modifier: Modifier) {
    Column(
        modifier = modifier,
    )
    {
        Box(modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f/1f)
            .clip(
                RoundedCornerShape(8.dp))
            .shimmerEffect()
        )
    }
}

fun Modifier.shimmerEffect(): Modifier = composed {
    var size by remember {
        mutableStateOf(IntSize.Zero)
    }
    val transition = rememberInfiniteTransition(label = "")
    val startOffsetX by transition.animateFloat(
        initialValue = -2 * size.width.toFloat(),
        targetValue = 2 * size.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(1000)
        ), label = ""
    )

    background(
        brush = Brush.linearGradient(
            colors = listOf(
                Color(0x80B8B5B5),
                Color(0x338F8B8B),
                Color(0x80B8B5B5),
            ),
            start = Offset(startOffsetX, 0f),
            end = Offset(startOffsetX + size.width.toFloat(), size.height.toFloat())
        )
    )
        .onGloballyPositioned {
            size = it.size
        }
}