import org.jetbrains.kotlin.gradle.plugin.mpp.pm20.util.archivesName

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    id("kotlin-kapt")
    id("com.google.devtools.ksp")
}

object AppVersion {
    private const val major: Int = 0
    private const val minor: Int = 2
    private const val patch: Int = 0

    val versionCode: Int
        get() = major*10000 + minor * 100 + patch

    fun versionName(useDots: Boolean = true) =
        listOf(major, minor, patch).joinToString(if (useDots) "." else "-")
}

archivesName.set("minimalcountosser-${AppVersion.versionName(false)}")

android {
    namespace = "io.github.lukasprediger.minimalcointosser"
    compileSdk = 34

    defaultConfig {
        applicationId = "io.github.lukasprediger.minimalcointosser"
        minSdk = 26
        targetSdk = 34
        versionCode = AppVersion.versionCode
        versionName = AppVersion.versionName()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                File("proguard-rules.pro")
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.majorVersion
    }

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    applicationVariants.forEach { variant ->
        kotlin.sourceSets.getByName(variant.name) {
            kotlin.srcDir("build/generated/ksp/${variant.name}/kotlin")
        }
    }
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}

dependencies {
    val composeVersion = "1.5.0"
    val lifecycle = "2.6.1"
    val hilt = 2.47

    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.compose.ui:ui-tooling-preview:$composeVersion")
    implementation("androidx.compose.material3:material3:1.1.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:${lifecycle}")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:${lifecycle}")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:${lifecycle}")
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    implementation("com.google.dagger:hilt-android:$hilt")
    kapt("com.google.dagger:hilt-compiler:$hilt")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")

    implementation("io.github.raamcosta.compose-destinations:core:1.9.52")
    ksp("io.github.raamcosta.compose-destinations:ksp:1.9.52")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$composeVersion")
    debugImplementation("androidx.compose.ui:ui-tooling:$composeVersion")
    debugImplementation("androidx.compose.ui:ui-test-manifest:$composeVersion")
}