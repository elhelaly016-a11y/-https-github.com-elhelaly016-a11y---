package com.example.data.ai

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.ai.ai
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withTimeout

private const val TAG = "CateringAiService"

class CateringAiService {
    // Lazily evaluate Firebase.ai generative model on first call
    private val model by lazy {
        Firebase.ai.generativeModel(
            modelName = "gemini-3.8-flash"
        )
    }

    /**
     * Ask Gemini AI for catering advice, menu planning, quality compliance, or nutrition.
     * Streams responses for low TTFT.
     */
    fun askAiStreaming(prompt: String, contextInfo: String = ""): Flow<String> = flow {
        val fullPrompt = if (contextInfo.isNotBlank()) {
            """
            أنت المساعد الذكي وخبير الإعاشة والتشغيل المركزي لمنصة (CATERING EGYPT | كاترينج مصر - الشركة الوطنية اللوجستية).
            سياق العمل الحالي:
            $contextInfo
            
            سؤال أو استفسار المستخدم:
            $prompt
            
            أجب باحترافية عملية وواضحة باللغة العربية (أو الإنجليزية إن طُلب ذلك)، مدعومة بمعايير الجودة وسلامة الغذاء (ISO 22000 / HACCP) والقيمة الغذائية وحساب التكلفة والكفاءة التشغيلية.
            """.trimIndent()
        } else {
            prompt
        }

        val accumulated = StringBuilder()
        try {
            withTimeout(60_000L) {
                model.generateContentStream(fullPrompt).collect { chunk ->
                    val chunkText = chunk.text.orEmpty()
                    accumulated.append(chunkText)
                    emit(accumulated.toString())
                }
            }
        } catch (e: TimeoutCancellationException) {
            Log.e(TAG, "Streaming timed out", e)
            emit(accumulated.toString() + "\n[عذراً، استغرقت الاستجابة وقتاً أطول من المتوقع، يرجى إعادة المحاولة]")
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Log.e(TAG, "Firebase AI generation failed", e)
            emit("حدث خطأ أثناء الاتصال بنموذج Gemini: ${e.localizedMessage ?: "فشل الاتصال"}")
        }
    }

    /**
     * Unary content generation with 60s timeout for quick insights
     */
    suspend fun generateInsight(prompt: String): String {
        return try {
            withTimeout(60_000L) {
                val response = model.generateContent(prompt)
                response.text ?: "لم يتم توليد رد من النموذج."
            }
        } catch (e: TimeoutCancellationException) {
            Log.e(TAG, "Generation timed out", e)
            "انتهت مهلة التوليد بعد 60 ثانية."
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Log.e(TAG, "Firebase AI call failed", e)
            "فشل استدعاء الذكاء الاصطناعي: ${e.localizedMessage}"
        }
    }
}
