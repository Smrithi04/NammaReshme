package com.example.nammareshme.ui.screens

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nammareshme.ui.theme.AccentYellow
import com.example.nammareshme.ui.theme.LightText
import com.example.nammareshme.ui.theme.MutedText
import com.example.nammareshme.ui.theme.ProgressBg
import com.example.nammareshme.ui.theme.SplashGreen
import androidx.navigation.NavController
import kotlinx.coroutines.delay


import androidx.hilt.navigation.compose.hiltViewModel
import com.example.nammareshme.viewmodel.AuthViewModel

@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel()
) {

    LaunchedEffect(Unit) {
        delay(2500) // 2.5 seconds
        if (viewModel.currentUser != null) {
            navController.navigate("dashboard") {
                popUpTo("splash") { inclusive = true }
            }
        } else {
            navController.navigate("auth") {
                popUpTo("splash") { inclusive = true }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SplashGreen)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Logo Container (Tilted)
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .rotate(12f)
                    .background(
                        color = LightText,
                        shape = RoundedCornerShape(24.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                androidx.compose.material3.Icon(
                    painter = painterResource(id = com.example.nammareshme.R.drawable.ic_app_logo),
                    contentDescription = "App Logo",
                    tint = Color.Unspecified,
                    modifier = Modifier.size(60.dp)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Title
            Text(
                text = "RESHME-NAMMA",
                color = LightText,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            // Highlight word
            Text(
                text = "PRIDE",
                color = AccentYellow,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Subtitle
            Text(
                text = "DIGITAL SERICULTURE ASSISTANT",
                color = MutedText,
                fontSize = 12.sp,
                letterSpacing = 1.5.sp
            )
        }

        // Bottom Progress Section
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 40.dp, start = 24.dp, end = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Progress bar background
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .background(
                        color = ProgressBg,
                        shape = RoundedCornerShape(50)
                    )
            ) {
                // Progress indicator (small yellow fill)
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(0.08f)
                        .background(
                            color = AccentYellow,
                            shape = RoundedCornerShape(50)
                        )
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "INITIALIZING ENVIRONMENT...",
                color = MutedText,
                fontSize = 11.sp,
                letterSpacing = 1.sp
            )
        }
    }
}