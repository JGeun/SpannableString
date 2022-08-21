package jgeun.study.spannablestring

import android.content.Context
import android.text.SpannableStringBuilder
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import jgeun.study.spannablestring.data.RichText
import jgeun.study.spannablestring.util.SpannableTextProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject

class MainViewModel : ViewModel() {
    private val _spannableStringBuilder = MutableStateFlow(SpannableStringBuilder())
    val spannableStringBuilder: StateFlow<SpannableStringBuilder> = _spannableStringBuilder

    private var fetchJob: Job? = null

    fun fetchText(data: String, context: Context) {
        fetchJob?.cancel()
        fetchJob = CoroutineScope(Dispatchers.IO).launch {
            val jsonObject = JSONObject(data)
            val jsonArray = jsonObject.getJSONArray("richText")
            val richTextList = Gson().fromJson(jsonArray.toString(), Array<RichText>::class.java).toList()

            val spannableStringBuilder = SpannableTextProvider.getSpannableStringBuilder(richTextList, context)
            _spannableStringBuilder.value = spannableStringBuilder
        }
    }
}