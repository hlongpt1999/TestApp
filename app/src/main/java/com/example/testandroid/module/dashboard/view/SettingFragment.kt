package com.example.testandroid.module.dashboard.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.testandroid.databinding.FragmentSettingBinding
import com.example.testandroid.module.dashboard.viewmodel.DashBoardViewModel
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import java.util.*


class SettingFragment() : Fragment() {

    private lateinit var binding: FragmentSettingBinding
    private val viewModel: DashBoardViewModel by activityViewModels()
    private var edittextController = ""
    private var restoreText = ""
    private var editTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(p0: Editable?) {
            edittextController = p0.toString()
        }
    }

    companion object {
        private const val KEY_DATA_EDITTEXT = "KEY_DATA_EDITTEXT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        savedInstanceState?.let {
            restoreText = it.getString(KEY_DATA_EDITTEXT, "fail")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        initEvent()
        restoreData()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY_DATA_EDITTEXT, edittextController)
    }

    override fun onDestroy() {
        binding.edtField.removeTextChangedListener(editTextWatcher)
        super.onDestroy()
    }

    private fun initUI() {
        binding.tvTextView.apply {
            text = HtmlCompat.fromHtml(
                "<a href = \"http://www.google.com\">http://www.google.com</a>",
                HtmlCompat.FROM_HTML_MODE_LEGACY
            )
            movementMethod = LinkMovementMethod.getInstance()
        }
    }

    private fun initEvent() {
        binding.edtField.addTextChangedListener(editTextWatcher)
        binding.tvGenerate.setOnClickListener {
            if (binding.edtField.text.isNotEmpty()) {
                binding.imvQRCode.setImageBitmap(generateQRCode(binding.edtField.text.toString()))
            }
            hideKeyBoard(binding.edtField)
        }
    }

    private fun restoreData() {
        binding.edtField.setText(restoreText)
    }

    private fun generateQRCode(text: String): Bitmap? {
        //initializing MultiFormatWriter for QR code
        val mWriter = MultiFormatWriter()
        try {
            var hints: Map<EncodeHintType, Any>? = null
            hints = EnumMap<EncodeHintType, Any>(EncodeHintType::class.java)
            hints.put(EncodeHintType.MARGIN, 0) /* default = 4 */
            //BitMatrix class to encode entered text and set Width & Height
            val mMatrix: BitMatrix = mWriter.encode(text, BarcodeFormat.CODE_128, 1600, 500, hints)
//            val mEncoder = BarcodeEncoder()
//            return mEncoder.createBitmap(mMatrix)
    //todo another way
            return createBitmap(mMatrix)
        } catch (e: WriterException) {
            e.printStackTrace()
        }
        return null
    }

    private fun createBitmap(matrix: BitMatrix): Bitmap? {
        val width = matrix.width
        val height = matrix.height
        val pixels = IntArray(width * height)
        for (y in 0 until height) {
            val offset = y * width
            for (x in 0 until width) {
                pixels[offset + x] =
                    if (matrix[x, y]) Color.BLACK else Color.TRANSPARENT
            }
        }
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height)
        return bitmap
    }


    private fun hideKeyBoard(view: View) {
        val inputMethodManager =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}