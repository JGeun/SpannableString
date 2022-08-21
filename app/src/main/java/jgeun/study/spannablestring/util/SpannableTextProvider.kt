package jgeun.study.spannablestring.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.*
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import jgeun.study.spannablestring.data.RichText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

object SpannableTextProvider {
    fun getSpannableStringBuilder(richTextList: List<RichText>, context: Context): SpannableStringBuilder {
        val spannableStringBuilder = SpannableStringBuilder()

        richTextList.forEach { richText ->
            val spannableString = RichTextSpannableString.Builder(richText)
                .setColor(richText.color)
                .setStyle(richText.style)
                .setSize(richText.size)
                .setImage(richText.image, context)
                .build()
            spannableStringBuilder.append(spannableString)
        }

        return spannableStringBuilder
    }
}