package module.signin.login.activity

import android.content.ClipData
import android.content.ClipboardManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.mobile.travelaja.R

class TokenIdamanActivity : AppCompatActivity() {
    private lateinit var tvCode: TextView
    private lateinit var tvCodeVerifier: TextView
    private lateinit var btnCode: Button
    private lateinit var btnCodeVerifier: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_token_idaman)
        val code = intent.getStringExtra(CODE)
        val codeV = intent.getStringExtra(CODE_VERIFIER)
        tvCode = findViewById(R.id.tvCode)
        tvCodeVerifier = findViewById(R.id.tvCodeVerifier)
        btnCode = findViewById(R.id.btnCode)
        btnCodeVerifier = findViewById(R.id.btnCodeVerifier)
        tvCode.text = code
        tvCodeVerifier.text = codeV

        btnCode.setOnClickListener {
            copyOnHold(code)
        }

        btnCodeVerifier.setOnClickListener {
            copyOnHold(codeV)
        }
    }

    fun copyOnHold(customText: String?) {
        val clipboardManager = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Copied String", customText)
        clipboardManager.setPrimaryClip(clip)
        Toast.makeText(this,"Copied",Toast.LENGTH_SHORT).show()
    }


    companion object {
        val CODE = "CODE"
        val CODE_VERIFIER = "CODE_VERIFIER"
    }
}