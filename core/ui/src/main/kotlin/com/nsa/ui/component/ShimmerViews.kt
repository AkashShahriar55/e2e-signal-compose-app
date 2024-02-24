package com.nsa.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nsa.ui.theme.AppTheme
import com.nsa.ui.theme.shimmerColor
import com.valentinilk.shimmer.shimmer


@Composable
fun TitleMediumShimmer(
    modifier: Modifier = Modifier
){
    Text(modifier = modifier
        .background(
            MaterialTheme.colorScheme.shimmerColor,
            RoundedCornerShape(5.dp)
        )
        .shimmer(),
        text = "Lily James, 29",
        color = Color.Transparent,
        style = MaterialTheme.typography.bodyMedium
    )
}


@Composable
fun TitleLargeShimmer(
    modifier: Modifier = Modifier
){
    Text(modifier = modifier
        .background(
            MaterialTheme.colorScheme.shimmerColor,
            RoundedCornerShape(5.dp)
        )
        .shimmer(),
        text = "Interest",
        color = Color.Transparent,
        style = MaterialTheme.typography.titleLarge
    )
}

@Composable
fun BodyMediumShimmer(
    modifier: Modifier = Modifier
){
    Text(modifier = modifier
        .background(
            MaterialTheme.colorScheme.shimmerColor,
            RoundedCornerShape(5.dp)
        )
        .shimmer(),
        text = "Fun and adventurous. I'm not afraid to try new things and I love to be spontaneous. I want someone who is always up for an adventure, whether it's trying a new restaurant, going on a hike, or traveling to a new country.\n",
        color = Color.Transparent,
        style = MaterialTheme.typography.bodyMedium
    )
}


@Composable
fun ChipsShimmer(
    modifier: Modifier = Modifier
){
    Box(
        modifier = modifier
            .height(35.dp)
            .width(100.dp)
            .background(
                MaterialTheme.colorScheme.shimmerColor,
                RoundedCornerShape(50)
            )
    )
}


@Preview
@Composable
fun previewShimmerView(){
    AppTheme {
        Column(
            modifier = Modifier.padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            BodyMediumShimmer()
            TitleLargeShimmer()
            TitleMediumShimmer()
            ChipsShimmer()
        }
    }


}