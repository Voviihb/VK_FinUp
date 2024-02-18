package com.vk_edu.finup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

@Composable
fun ComposeButton(text: String, fn: () -> Unit) {
    Button(
        onClick = fn,
        modifier = Modifier
            .background(Color.White, RoundedCornerShape(CornerSize(15.dp)))
            .border(2.dp, Color.Black, RoundedCornerShape(CornerSize(30.dp))),
        contentPadding = PaddingValues(0.dp)
    ) {
        Surface(
            modifier = Modifier
                .background(Color.White, RoundedCornerShape(CornerSize(15.dp)))
                .padding(16.dp)

        ) {
            // Your button text
            Text(
                text = text,
                color = Color.Black,
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}

@Composable
fun booleanButton(text: String) {
    var checked by remember {
        mutableStateOf(true)
    }
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = text,
            color = Color.Black,
            style = MaterialTheme.typography.titleLarge,
        )
        Spacer(modifier = Modifier.weight(1f))
        Switch(checked = checked,
            onCheckedChange = {
                checked = it
            })
    }
}

@Composable
fun rowTextImageLeft(text: String, pic: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 15.dp),

        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = text,
            color = Color.Black,
            style = MaterialTheme.typography.titleLarge,
        )
        Image(
            modifier = Modifier
                .size(25.dp, 25.dp),
            painter = painterResource(id = pic),
            contentDescription = null
        )
    }

}

@Composable
fun largeContainer(
    text1: String, text2: String,
    color1: Color, color2: Color, BorderColor: Color
) {
    Button(
        onClick = { },
        contentPadding = PaddingValues(0.dp),
        shape = MaterialTheme.shapes.extraSmall,
        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
        modifier = Modifier
            .padding(top = 10.dp)
            .fillMaxWidth()
            .size(width = 300.dp, height = 150.dp)
            .background(Color.Transparent)
            .padding(20.dp)
            .background(Color.White, RoundedCornerShape(CornerSize(0.dp)))
            .border(2.dp, BorderColor, RoundedCornerShape(CornerSize(0.dp)))
    )
    {
        Column(modifier = Modifier) {
            Surface(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = text1,
                    color = color1,
                    fontSize = 25.sp,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge,
                )
            }
            Surface(
                modifier = Modifier.fillMaxWidth()
            ) {

                Text(
                    text = text2,
                    color = color2,
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge,
                )
            }
        }
    }
}


@Composable
fun rowImageText(
    text: String, pic: Int, parentFragmentManager: FragmentManager,
    func: () -> Fragment
) {
    val height = 70.dp
    Button(
        shape = RectangleShape,
        onClick = { addFragmentBackStack(parentFragmentManager) { func() } },
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
            .size(width = 50.dp, height = height),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ),
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.Transparent,
            contentColor = Color.Black
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .background(Color.Transparent)
                    .size(height = height, width = 6000.dp)
            ) {
                Image(
                    painter = painterResource(id = pic),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(start = 0.dp, end = 10.dp, top = 0.dp, bottom = 0.dp)
                        .size(height = height, width = 50.dp)
                )
                Text(
                    text = text,
                    color = Color.Black,
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.titleLarge,
                )
            }
        }
    }
}

@Composable
fun columnImageText(text: String, pic: Int, fn: () -> Unit) {
    val width = 105.dp
    val height = 80.dp
    val textheight = 30.dp
    Button(
        shape = RectangleShape,
        onClick = fn,
        contentPadding = PaddingValues(0.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ),
        modifier = Modifier
            .size(width = width, height = height + textheight)
    ) {
        Column(
            Modifier
                .size(width = width, height = height + textheight)
        ) {
            Image(
                painter = painterResource(id = pic),
                contentDescription = null,
                modifier = Modifier
                    .size(height = height, width = width)
            )
            Text(
                text = text,
                color = Color.Black,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier
                    .size(height = textheight, width = width)
            )
        }
    }
}

@Composable
fun SettingsItem(text1: String, text2: String? = null) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .size(width = 100.dp, height = 70.dp)
    ) {
        Text(
            text = text1,
            fontSize = 23.sp,
            color = Color.Black
        )
        if (!text2.isNullOrBlank()) {
            Text(
                text = text2,
                fontSize = 18.sp,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun DrawLine() {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp)
    ) {
        val height = size.height
        val width = size.width

        drawLine(
            start = Offset(x = 0f, y = 0f),
            end = Offset(x = width, y = height),
            color = Color.Gray,
            strokeWidth = 2.0f
        )
    }
}