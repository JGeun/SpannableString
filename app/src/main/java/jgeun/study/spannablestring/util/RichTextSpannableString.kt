package jgeun.study.spannablestring.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.ImageSpan
import android.text.style.RelativeSizeSpan
import jgeun.study.spannablestring.data.RichText
import java.net.URL

/**
 * Its [Builder] returns a [SpannableString] whose style has changed by [RichText].
 */
class RichTextSpannableString(richText: RichText): SpannableString(richText.text ?: " ") {
    class Builder(private val richText: RichText) {
        private var spannableString = SpannableString(richText.text ?: " ")

        fun setColor(color: String?): Builder {
            color?.let {
                val span = ForegroundColorSpan(Color.parseColor(it))
                setSpanForTextRange(span)
            }
            return this
        }

        fun setStyle(style: String?): Builder {
            style?.let {
                setSpanForTextRange(StyleSpanFactory.createStyleSpan(it))
            }
            return this
        }

        fun setSize(size: Float?): Builder {
            size?.let {
                val span = RelativeSizeSpan(it)
                setSpanForTextRange(span)
            }
            return this
        }

        fun setImage(image: String?, context: Context): Builder {
            image?.let {
                val resource = BitmapFactory.decodeStream(URL(image).openConnection().getInputStream())
                val bitmap = Bitmap.createScaledBitmap(resource, 100, 100, true)
                spannableString.setSpan(
                    ImageSpan(context, bitmap),
                    0,
                    1,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
            return this
        }

        fun build() = spannableString

        private fun setSpanForTextRange(span: Any?) {
            spannableString.setSpan(
                span,
                0, richText.text?.length ?: 0,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
    }
}