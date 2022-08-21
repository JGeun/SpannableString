package jgeun.study.spannablestring

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.Gson
import jgeun.study.spannablestring.data.RichText
import jgeun.study.spannablestring.databinding.ActivityMainBinding
import jgeun.study.spannablestring.util.SpannableTextProvider
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val gson = Gson()
        val json = assets.open("data.json").reader().readText()
        val jsonObject = JSONObject(json)
        val jsonArray = jsonObject.getJSONArray("richText")
        val response = gson.fromJson(jsonArray.toString(), Array<RichText>::class.java).toList()


        SpannableTextProvider.getSpannableStringBuilder(response, binding.spannableText.context)
    }
}