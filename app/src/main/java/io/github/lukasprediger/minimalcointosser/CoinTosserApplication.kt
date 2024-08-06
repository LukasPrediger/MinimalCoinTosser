package io.github.lukasprediger.minimalcointosser

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import org.acra.ACRAConstants
import org.acra.config.dialog
import org.acra.config.mailSender
import org.acra.data.StringFormat
import org.acra.ktx.initAcra

@HiltAndroidApp
class CoinTosserApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        initAcra {
            reportFormat = StringFormat.JSON
            mailSender {
                mailTo = "chase@mailbox.org"
            }
            dialog {
                text = "test"
            }
        }
    }
}