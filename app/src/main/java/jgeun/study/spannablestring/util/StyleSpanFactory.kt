package jgeun.study.spannablestring.util

import android.graphics.Typeface
import android.text.style.StrikethroughSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan

/* 설계에서 굵게, 아래선, 취소선, 노말만 있다고 설계하였습니다.
    추가적인 기능들은 추가하시면 됩니다.
*/
object StyleSpanFactory {
    fun createStyleSpan(style: String): Any {
        return when(style) {
            TextStyle.BOLD.name -> StyleSpan(Typeface.BOLD)
            TextStyle.UNDERLINE.name -> UnderlineSpan()
            TextStyle.STRIKE_THROUGH.name -> StrikethroughSpan()
            else -> StyleSpan(Typeface.NORMAL)
        }
    }
}