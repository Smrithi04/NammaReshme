package com.example.nammareshme.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

import com.example.nammareshme.viewmodel.MainViewModel

import androidx.compose.runtime.collectAsState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BatchScreen(
    navController: NavController,
    viewModel: MainViewModel? = null
) {
    // Collect active batch state from ViewModel
    val activeBatch by (viewModel?.activeBatch?.collectAsState() ?: remember { mutableStateOf(null) })

    var expanded by remember { mutableStateOf(false) }
    val silkVarieties = listOf(
        "Bivoltine (High Yield)",
        "Multivoltine",
        "CB Hybrid"
    )
    var selectedVariety by remember { mutableStateOf(silkVarieties[0]) }

    // Input States
    var batchName by remember { mutableStateOf("") }
    var hatchDate by remember { mutableStateOf("06-05-2026") }
    var farmerName by remember { mutableStateOf("Smrithi G") }
    var region by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF3F2EE))
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 82.dp)
        ) {

            Spacer(modifier = Modifier.height(14.dp))

            // HEADER
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFF8F8F6)
                )
            ) {

                Column(
                    modifier = Modifier.padding(
                        horizontal = 18.dp,
                        vertical = 16.dp
                    )
                ) {

                    Row {

                        Text(
                            text = "RESHME-",
                            color = Color(0xFF0F4B35),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold
                        )

                        Text(
                            text = "NAMMA",
                            color = Color(0xFFD0A81E),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = "DIGITAL SERICULTURE ASSISTANT",
                        color = Color(0xFF99A199),
                        fontSize = 10.sp,
                        letterSpacing = 0.7.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(22.dp))

            // MAIN CARD
            Box(
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .background(
                        color = Color(0xFFF8F8F6),
                        shape = RoundedCornerShape(28.dp)
                    )
                    .border(
                        width = 2.dp,
                        color = Color(0xFF0F4B35),
                        shape = RoundedCornerShape(28.dp)
                    )
            ) {

                if (activeBatch != null) {

                    // ACTIVE BATCH EXISTS UI
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = 24.dp,
                                vertical = 38.dp
                            ),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(
                            text = "CURRENT ACTIVE BATCH",
                            color = Color(0xFF0F4B35),
                            fontSize = 22.sp,
                            fontWeight = FontWeight.ExtraBold
                        )

                        Spacer(modifier = Modifier.height(32.dp))

                        Text(
                            text = "Batch: ${activeBatch?.name}",
                            color = Color(0xFF122C34),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "An active batch is currently in progress.",
                            color = Color(0xFF7C7C7C),
                            fontSize = 14.sp
                        )

                        Spacer(modifier = Modifier.height(32.dp))

                        Button(
                            onClick = {
                                activeBatch?.id?.let {
                                    viewModel?.archiveBatch(it)
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(58.dp),
                            shape = RoundedCornerShape(18.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFB91C1C) // Red for archive
                            )
                        ) {
                            Text(
                                text = "END & ARCHIVE BATCH",
                                color = Color.White,
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 16.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(26.dp))
                    }

                } else {

                    // CREATE NEW BATCH UI
                    Column(
                        modifier = Modifier.padding(
                            horizontal = 24.dp,
                            vertical = 34.dp
                        )
                    ) {

                        Text(
                            text = "START NEW BATCH",
                            color = Color(0xFF0F4B35),
                            fontSize = 22.sp,
                            fontWeight = FontWeight.ExtraBold
                        )

                        Spacer(modifier = Modifier.height(34.dp))

                        // BATCH NAME
                        FieldLabel("BATCH NAME")

                        StyledTextField(
                            value = batchName,
                            onValueChange = { batchName = it },
                            placeholder = "e.g. SEC-04-A"
                        )

                        Spacer(modifier = Modifier.height(18.dp))

                        // SILKWORM VARIETY
                        FieldLabel("SILKWORM VARIETY")

                        ExposedDropdownMenuBox(
                            expanded = expanded,
                            onExpandedChange = {
                                expanded = !expanded
                            }
                        ) {

                            OutlinedTextField(
                                value = selectedVariety,
                                onValueChange = {},
                                readOnly = true,
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(
                                        expanded = expanded
                                    )
                                },
                                modifier = Modifier
                                    .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                                    .fillMaxWidth(),
                                shape = RoundedCornerShape(14.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color(0xFFD0D3D6),
                                    unfocusedBorderColor = Color(0xFFD0D3D6),
                                    focusedContainerColor = Color(0xFFF8F8F6),
                                    unfocusedContainerColor = Color(0xFFF8F8F6)
                                ),
                                textStyle = LocalTextStyle.current.copy(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF122C34)
                                )
                            )

                            ExposedDropdownMenu(
                                expanded = expanded,
                                onDismissRequest = {
                                    expanded = false
                                }
                            ) {

                                silkVarieties.forEach {

                                    DropdownMenuItem(
                                        text = {
                                            Text(it)
                                        },
                                        onClick = {
                                            selectedVariety = it
                                            expanded = false
                                        }
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(18.dp))

                        // DATE
                        FieldLabel("HATCHING DATE")

                        StyledTextField(
                            value = hatchDate,
                            onValueChange = { hatchDate = it },
                            placeholder = "DD-MM-YYYY"
                        )

                        Spacer(modifier = Modifier.height(18.dp))

                        // FARMER + REGION
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {

                            Column(
                                modifier = Modifier.weight(1f)
                            ) {

                                FieldLabel("FARMER NAME")

                                StyledTextField(
                                    value = farmerName,
                                    onValueChange = { farmerName = it },
                                    placeholder = ""
                                )
                            }

                            Column(
                                modifier = Modifier.weight(1f)
                            ) {

                                FieldLabel("REGION\n(VILLAGE/TALUK)")
                                StyledTextField(
                                    value = region,
                                    onValueChange = { region = it },
                                    placeholder = "Location"
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(34.dp))

                        // Validation logic
                        val isFormValid = batchName.isNotBlank() && region.isNotBlank() && farmerName.isNotBlank()

                        // BUTTON
                        Button(
                            onClick = {
                                if (isFormValid) {
                                    viewModel?.createBatch(
                                        name = batchName,
                                        breed = selectedVariety,
                                        farmerName = farmerName,
                                        region = region,
                                        hatchDate = hatchDate
                                    )
                                    navController.navigate("dashboard") {
                                        popUpTo("batch") { inclusive = true }
                                    }
                                }
                            },
                            enabled = isFormValid,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(58.dp),
                            shape = RoundedCornerShape(18.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF14532D),
                                disabledContainerColor = Color.Gray
                            )
                        ) {

                            Text(
                                text = "START BATCH",
                                color = Color.White,
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 18.sp
                            )
                        }
                    }
                }
            }
        }
        val currentRoute =
            navController.currentBackStackEntryAsState()
                .value?.destination?.route
        BottomNavBar(
            navController = navController,
            currentRoute = currentRoute,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun FieldLabel(text: String) {

    Text(
        text = text,
        color = Color(0xFF9A9A9A),
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 0.5.sp
    )

    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
fun StyledTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String
) {

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                placeholder,
                color = Color(0xFF9A9A9A),
                fontWeight = FontWeight.SemiBold
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(58.dp),
        shape = RoundedCornerShape(14.dp),
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFFD0D3D6),
            unfocusedBorderColor = Color(0xFFD0D3D6),
            focusedContainerColor = Color(0xFFF8F8F6),
            unfocusedContainerColor = Color(0xFFF8F8F6)
        ),
        textStyle = LocalTextStyle.current.copy(
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF122C34)
        )
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BatchPreview() {
    val navController = rememberNavController()
    BatchScreen(navController = navController)
}