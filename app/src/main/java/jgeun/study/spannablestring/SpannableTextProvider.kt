package jgeun.study.spannablestring

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.*
import android.util.Log
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

object SpannableTextProvider {

    private val sb = SpannableStringBuilder()
    private var pos: Int = 1

    fun provide(textView: TextView, texts: List<RichText>) {

        for (text in texts) {
            sb.append(getSpannableString(textView, text))
        }
    }

    private fun getSpannableString(textView: TextView, richText: RichText): SpannableString {
        val span = SpannableString(richText.text ?: " ")

        if (richText.image != null) {
            val index = pos

            makeGlideBitmap(textView, richText, span, index)
            // makeCoroutineBitmap(textView, richText, span, index)
        } else {
            richText.color?.let {
                span.setSpan(
                    ForegroundColorSpan(Color.parseColor(it)),
                    0,
                    richText.text?.length ?: 0,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }

            richText.style?.let {
                val style = when (it) {
                    "BOLD" -> StyleSpan(Typeface.BOLD)
                    "underline" -> UnderlineSpan()
                    "cancel_line" -> StrikethroughSpan()
                    else -> StyleSpan(Typeface.NORMAL)
                }
                span.setSpan(
                    style, 0, richText.text?.length ?: 0, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }

            richText.size?.let {
                span.setSpan(
                    AbsoluteSizeSpan(it*10) ,
                    0,
                    richText.text?.length ?: 0,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }

        pos += if (richText.text == "\n") 2 else span.length
        return span
    }

    private fun makeGlideBitmap(
        textView: TextView,
        richText: RichText,
        span: SpannableString,
        index: Int
    ) {
        Glide.with(textView.context).asBitmap().load(richText.image)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap>?
                ) {
                    val bitmap = Bitmap.createScaledBitmap(resource, 100, 100, true)
                    span.setSpan(
                        ImageSpan(textView.context, bitmap),
                        0,
                        1,
                        Spannable.SPAN_INCLUSIVE_INCLUSIVE
                    )
                    textView.text = sb.insert(index, span)
                }

                override fun onLoadCleared(placeholder: Drawable?) {}
            })
    }

    private fun makeCoroutineBitmap(
        textView: TextView,
        richText: RichText,
        span: SpannableString,
        index: Int
    ) {
        CoroutineScope(Dispatchers.Main).launch {
            val resource = withContext(Dispatchers.IO) {
                loadImage(richText.image!!)
            }

            resource?.let {
                val bitmap = Bitmap.createScaledBitmap(resource, 100, 100, true)
                span.setSpan(
                    ImageSpan(textView.context, bitmap),
                    0,
                    1,
                    Spannable.SPAN_INCLUSIVE_INCLUSIVE
                )
            }
            textView.text = sb.insert(index, span)
        }
    }

    private fun loadImage(imageUrl: String): Bitmap? {
        var bitmap: Bitmap? = null
        val connection: HttpURLConnection?

        try {
            val url = URL(imageUrl)
            connection = url.openConnection() as HttpURLConnection
            connection.doInput = true // InputStream으로 데이터를 읽겠다는 설정
            connection.connect()

            val input = connection.inputStream
            bitmap =
                BitmapFactory.decodeStream(input) // BitmapFactory의 메소드를 통해 InputStream으로부터 Bitmap을 만들어 준다.
            connection.disconnect()

        } catch (e: IOException) {
            e.printStackTrace()
        }
        return bitmap
    }
}