package akhmedoff.usman.videoforvk

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.captcha_dialog.*

class CaptchaDialog(context: Context, private val listener: CaptchaListener) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.captcha_dialog)
        window.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        enter_captcha_button.setOnClickListener { listener.enterCaptcha(captcha_input.text.toString()) }
    }

    fun loadCaptcha(captchaUrl: String) =
        Picasso.with(context).load(captchaUrl).into(captcha_image_view)

    interface CaptchaListener {
        fun enterCaptcha(captchaKey: String)
    }
}

