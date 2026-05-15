package com.example.nammareshme.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.nammareshme.viewmodel.AuthState
import com.example.nammareshme.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthScreen(
    onAuthSuccess: (isNewUser: Boolean) -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    var isLogin by remember { mutableStateOf(true) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }

    val authState by viewModel.authState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(authState) {
        if (authState is AuthState.Success) {
            onAuthSuccess(!isLogin) // isNewUser if we just signed up
            viewModel.resetState()
        } else if (authState is AuthState.Error) {
            Toast.makeText(context, (authState as AuthState.Error).message, Toast.LENGTH_LONG).show()
            viewModel.resetState()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF1B5E3B), Color(0xFFF3F3F3)),
                    startY = 0f,
                    endY = 1000f
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Namma Reshme",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF1B5E3B)
                )
                Text(
                    text = if (viewModel.currentUser != null) "Welcome Back" else if (isLogin) "Welcome Back" else "Join the Community",
                    fontSize = 16.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 32.dp)
                )

                if (viewModel.currentUser != null) {
                    val user = viewModel.currentUser
                    Text(
                        text = user?.displayName?.ifBlank { user.email } ?: user?.email ?: "",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    
                    Button(
                        onClick = { onAuthSuccess(false) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD4A017)),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text("Continue as User", fontWeight = FontWeight.Bold, color = Color.Black)
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    TextButton(onClick = { viewModel.signOut() }) {
                        Text("Switch Account", color = Color(0xFF1B5E3B))
                    }
                } else {

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text(androidx.compose.ui.res.stringResource(com.example.nammareshme.R.string.email)) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text(androidx.compose.ui.res.stringResource(com.example.nammareshme.R.string.password)) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        visualTransformation = PasswordVisualTransformation(),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                }

                if (authState is AuthState.Loading) {
                    CircularProgressIndicator(color = Color(0xFF1B5E3B))
                } else {
                    Button(
                        onClick = {
                            if (isLogin) {
                                viewModel.signIn(email, password)
                            } else {
                                viewModel.signUp(email, password)
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD4A017)),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text(
                            text = if (isLogin) androidx.compose.ui.res.stringResource(com.example.nammareshme.R.string.sign_in) else androidx.compose.ui.res.stringResource(com.example.nammareshme.R.string.create_account),
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                
                TextButton(onClick = { isLogin = !isLogin }) {
                    Text(
                        text = if (isLogin) "Need an account? Sign up" else "Already have an account? Sign in",
                        color = Color(0xFF1B5E3B)
                    )
                }
            }
        }
    }
}
