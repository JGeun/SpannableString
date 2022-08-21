package jgeun.study.spannablestring

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import jgeun.study.spannablestring.data.RichText
import jgeun.study.spannablestring.databinding.ActivityMainBinding
import jgeun.study.spannablestring.util.SpannableTextProvider
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main).also {
            it.lifecycleOwner = this
        }

        lifecycleScope.launch {
            viewModel.spannableStringBuilder.collectLatest { sb ->
                binding.spannableText.text = sb
            }
        }
    }

    override fun onStart() {
        super.onStart()

        val data = assets.open("data.json").reader().readText()
        viewModel.fetchText(data, baseContext)
    }
}