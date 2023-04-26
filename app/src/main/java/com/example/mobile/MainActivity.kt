package com.example.mobile

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.mobile.ui.theme.MobileTheme
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.material3.Button
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.mobile.ui.theme.Black00
import com.example.mobile.ui.theme.White00

class StartScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MobileTheme {
                Surface(modifier = Modifier.fillMaxSize(),
                    color = Black00) {
                    StartContent()
                }
            }
        }
    }
}

@Composable
fun StartContent() {
    val context = LocalContext.current

    Column (verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            bitmap = ImageBitmap.imageResource(R.drawable.logo),
            contentDescription = "Project logo"
        )
        Button(onClick = { context.startActivity(Intent(context, BackScreen::class.java)) },
            modifier = Modifier.padding(50.dp)) {
            Text(
                text = "Let`s code!",
                color = White00,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)
            )
        }
    }
}
