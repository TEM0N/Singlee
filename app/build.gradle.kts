import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id ("kotlin-kapt")
    id("com.google.devtools.ksp")
    id ("kotlin-parcelize")
}

android {
    namespace = "an.imation.singlee"
    compileSdk = 35

    defaultConfig {
        applicationId = "an.imation.singlee"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        javaCompileOptions {
            annotationProcessorOptions {
                arguments += mapOf(
                    "room.schemaLocation" to "$projectDir/schemas",
                    "room.incremental" to "true",
                    "room.expandProjection" to "true"
                )
            }
        }
    }
    signingConfigs{
        getByName("debug"){
            val keystorePropertiesFile = rootProject.file("app/myFirstKeyStore.properties")
            val keystoreProperties = Properties()
            keystoreProperties.load(FileInputStream(keystorePropertiesFile))
            keyAlias = keystoreProperties["keyAlias"].toString()
            keyPassword = keystoreProperties["keyPassword"].toString()
            storeFile = file(keystoreProperties["storeFile"].toString())
            storePassword = keystoreProperties["storePassword"].toString()
        }
    }
    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            signingConfig = signingConfigs.getByName("debug")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            initWith(getByName("release"))
            isDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false
        }
        /*create("localTest"){
            buildConfigField("String","string","\"string\"")
            buildConfigField("Boolean","boolean","true")
            buildConfigField("Int","int","3")
        }*/
        create("localTest") {
            initWith(getByName("debug"))

            buildConfigField("String", "string", "\"string\"")
            buildConfigField("boolean", "LOGGING_ENABLED", "true")
            buildConfigField("int", "TIMEOUT_SECONDS", "30")

            isDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    flavorDimensions += listOf("appFlavors")
    productFlavors {
        create("flavOne") {
            dimension = "appFlavors"
        }
        create("flavTwo") {
            dimension = "appFlavors"
        }
    }
}

dependencies {
    //raamcosta
    implementation("io.github.raamcosta.compose-destinations:animations-core:1.9.54")
    implementation("com.google.accompanist:accompanist-navigation-animation:0.34.0")
    implementation("io.github.raamcosta.compose-destinations:core:1.9.54")
    ksp("io.github.raamcosta.compose-destinations:ksp:1.9.54")
    //room
    implementation ("androidx.room:room-runtime:2.6.1")
    implementation ("androidx.room:room-ktx:2.6.1")
    kapt ("androidx.room:room-compiler:2.6.1")
    //leakcanary
    debugImplementation ("com.squareup.leakcanary:leakcanary-android:2.14")
    //Retrofit
    implementation (libs.retrofit)
    implementation (libs.converter.gson)
    implementation ("com.andretietz.retrofit:cache-extension:1.0.0")

    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    //Koin
    implementation (libs.insert.koin.koin.androidx.compose)
    //Navigation
    implementation (libs.androidx.navigation.compose)
    //Jetpack Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation (libs.ui)
    //Material Design
    implementation(libs.androidx.material3)
    //Для тестов
    testImplementation ("org.mockito:mockito-core:5.8.0")
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.1")
    testImplementation("io.insert-koin:koin-test:3.3.0")
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    //Для отладки
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    //Default
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
}
/*ksp {
    arg("compose-destinations.mode", "destinations")
    arg("compose-destinations.generateNavGraphs", "true")
}*/