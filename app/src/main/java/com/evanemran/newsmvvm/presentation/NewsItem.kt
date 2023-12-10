package com.evanemran.newsmvvm.presentation

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.evanemran.newsmvvm.R
import com.evanemran.newsmvvm.domain.news.Articles
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun NewsItem(
    context: Context,
    article: Articles
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(article.url))
                context.startActivity(intent)
            }
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp,
        ),
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .background(Color.White)
        ) {
            Text(
                modifier = Modifier
                    .padding(vertical = 0.dp),
                text = article.title,
                color = Color.Black,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row() {
                FooterIcon(Icons.Outlined.DateRange)
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    modifier = Modifier
                        .padding(0.dp),
                    text = "Published  ${article.publishedAt}",
                    color = Color.DarkGray,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            ImageFromUrl(article.urlToImage.toString())
            BlogFooter(author = article.author ?: "N/A", source = article.source ?: "N/A")
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = article.description ?: "N/A", color = Color.Black, fontSize = 14.sp)
        }
    }
}

@Composable
fun BlogFooter(author: String, source: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
        ) {
            Row() {
                FooterIcon(Icons.Outlined.Person)
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    modifier = Modifier
                        .padding(0.dp),
                    text = "Author: $author",
                    color = Color.DarkGray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal
                )
            }
        }
        
        Spacer(modifier = Modifier.width(8.dp))

        Row(
            modifier = Modifier
                .weight(1f)
        ) {
            Row() {
                FooterIcon(Icons.Outlined.Info)
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    modifier = Modifier
                        .padding(0.dp),
                    text = "Source: $source",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.DarkGray,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal
                )
            }
        }
    }

}

@Composable
fun FooterIcon(icon: ImageVector) {
    Icon(
        icon,
        tint = Color.DarkGray,
        modifier = Modifier.size(20.dp),
        contentDescription = null
    )
}

@Composable
fun ImageFromUrl(imageUrl: String) {
    AsyncImage(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        model = imageUrl,
        contentScale = ContentScale.Crop,
        placeholder = painterResource(id = R.drawable.ic_launcher_background),
        error = painterResource(id = R.drawable.ic_launcher_background),
        contentDescription = "URL Image",
    )
}