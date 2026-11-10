package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ui.CateringViewModel
import com.example.ui.theme.*

@Composable
fun GeminiAiDialog(
    viewModel: CateringViewModel,
    isArabic: Boolean,
    onDismiss: () -> Unit
) {
    val aiResponse by viewModel.aiResponseText.collectAsState()
    val isLoading by viewModel.isAiLoading.collectAsState()
    var inputQuery by remember { mutableStateOf("") }

    val presetPrompts = listOf(
        "اقتراح وجبة غذائية متوازنة لمرضى السكري والضغط بمستشفى رويال",
        "إجراء تصحيحي فوري وفق ISO 22000 عند انخفاض حرارة تبريد اللحوم",
        "تخطيط منيو إعاشة ساخنة لوردية عمالية بمصانع الأسمنت (1500 عامل)",
        "أهم اشتراطات الهايجين لسيارات النقل الحراري المبرد للمطابخ المركزية"
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(
                                Brush.linearGradient(listOf(BrandOrange, Color(0xFF673AB7)))
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = "Gemini",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = if (isArabic) "مستشار كاترينج مصر الذكي (Gemini)" else "Gemini Catering AI Advisor",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = if (isArabic) "مدعوم بتقنية Gemini 3.8 Flash عبر Firebase AI Logic" else "Powered by Gemini 3.8 Flash & Firebase AI Logic",
                            style = MaterialTheme.typography.labelSmall,
                            color = BrandOrangeDark
                        )
                    }
                }
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 480.dp)
            ) {
                // Preset quick suggestions
                Text(
                    text = if (isArabic) "استفسارات مقترحة سريعة:" else "Quick Suggestions:",
                    style = MaterialTheme.typography.labelSmall,
                    color = MutedGray
                )
                Spacer(modifier = Modifier.height(4.dp))
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(presetPrompts) { prompt ->
                        SuggestionChip(
                            onClick = {
                                inputQuery = prompt
                                viewModel.askGemini(
                                    prompt = prompt,
                                    contextInfo = "المنصة: كاترينج مصر | الشركة الوطنية اللوجستية، المطابخ: هنجر استيشن، مول العين ببدر، الريان ببني سويف."
                                )
                            },
                            label = {
                                Text(
                                    text = prompt,
                                    maxLines = 1,
                                    style = MaterialTheme.typography.labelSmall
                                )
                            },
                            colors = SuggestionChipDefaults.suggestionChipColors(
                                containerColor = LightGraySurface
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Query input field
                OutlinedTextField(
                    value = inputQuery,
                    onValueChange = { inputQuery = it },
                    placeholder = {
                        Text(
                            text = if (isArabic) "اسأل Gemini عن الوجبات، الحميات، الهايجين، التكاليف..." else "Ask Gemini about recipes, diets, HACCP..."
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                if (inputQuery.isNotBlank() && !isLoading) {
                                    viewModel.askGemini(
                                        prompt = inputQuery,
                                        contextInfo = "المنصة: كاترينج مصر | الشركة الوطنية اللوجستية"
                                    )
                                }
                            },
                            enabled = inputQuery.isNotBlank() && !isLoading
                        ) {
                            if (isLoading) {
                                CircularProgressIndicator(modifier = Modifier.size(20.dp), color = BrandOrange, strokeWidth = 2.dp)
                            } else {
                                Icon(imageVector = Icons.Default.Send, contentDescription = "Send", tint = BrandOrange)
                            }
                        }
                    }
                )

                Spacer(modifier = Modifier.height(12.dp))

                // AI Response Area
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f, fill = false)
                        .clip(RoundedCornerShape(10.dp)),
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    border = androidx.compose.foundation.BorderStroke(1.dp, BorderGray)
                ) {
                    val scrollState = rememberScrollState()
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                            .verticalScroll(scrollState)
                    ) {
                        if (aiResponse.isBlank() && !isLoading) {
                            Text(
                                text = if (isArabic)
                                    "مرحباً بك! أنا مستشارك الذكي المعتمد من الشركة الوطنية اللوجستية. يمكنك الاستفسار عن تخطيط الوجبات وحساب السعرات الغذائية ومعايير سلامة الأغذية وتصميم عروض الأسعار."
                                else
                                    "Welcome! Ask Gemini AI about meal planning, hospital diets, HACCP safety compliance, and operational feasibility.",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        } else {
                            Column {
                                if (isLoading) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.padding(bottom = 6.dp)
                                    ) {
                                        CircularProgressIndicator(
                                            modifier = Modifier.size(14.dp),
                                            color = BrandOrange,
                                            strokeWidth = 2.dp
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            text = if (isArabic) "جاري التحليل وتوليد الإجابة بواسطة Gemini..." else "Generating with Gemini...",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = BrandOrangeDark,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                                Text(
                                    text = aiResponse,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            if (aiResponse.isNotBlank()) {
                TextButton(onClick = { viewModel.clearAiResponse() }) {
                    Text(text = if (isArabic) "مسح الرد" else "Clear", color = CharcoalSecondary)
                }
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = BrandOrange)
            ) {
                Text(text = if (isArabic) "إغلاق" else "Close")
            }
        }
    )
}
