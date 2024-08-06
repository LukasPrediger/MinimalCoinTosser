package io.github.lukasprediger.minimalcointosser

import io.appium.java_client.AppiumBy
import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.android.options.UiAutomator2Options
import io.appium.java_client.service.local.AppiumDriverLocalService
import org.junit.AfterClass
import org.junit.Assert
import org.junit.BeforeClass
import org.junit.Test
import org.openqa.selenium.By
import java.net.URL


class AppiumTest {
    lateinit var service: AppiumDriverLocalService

    @Test
    fun FrontPageExists() {
        val options = UiAutomator2Options()
            .setUdid("emulator-5554")
            .setApp("/Users/Lukas.Prediger/AndroidStudioProjects/MinimalCoinTosser/app/build/outputs/apk/debug/minimalcountosser-0-1-0-debug.apk")
        val driver = AndroidDriver( // The default URL in Appium 1 is http://127.0.0.1:4723/wd/hub
            URL("http://127.0.0.1:4723"), options
        )
        try {
            var el =
                driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().textContains(\"Tap screen to toss coin\")"))
            el.click()
            el =
                driver.findElement(By.xpath("//*[contains(@text, 'HEADS') or contains(@text, 'TAILS')]"))
            el.text.shouldBeOneOf("HEADS", "TAILS")
            driver.pageSource
        } finally {
            driver.quit()
        }
    }

    companion object {
        private lateinit var service: AppiumDriverLocalService

        @JvmStatic
        @AfterClass
        fun cleanup() {
            service.stop()
        }

        @JvmStatic
        @BeforeClass
        fun setup() {
            service = AppiumDriverLocalService.buildDefaultService()
            service.start()
        }
    }
}

infix fun Any.shouldBe(expected: Any) {
    Assert.assertEquals(expected, this)
}

fun Any.shouldBeOneOf(vararg values: Any) {
    Assert.assertTrue(values.contains(this))
}