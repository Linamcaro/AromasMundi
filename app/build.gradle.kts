import org.apache.tools.ant.util.JavaEnvUtils.VERSION_11

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs.kotlin")
}



android {
    namespace = "com.example.aromasmundi"
    compileSdk = 34


    defaultConfig {
        applicationId = "com.example.aromasmundi"
        minSdk = 23
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        dataBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

}

dependencies {

    val coordinatorlayour_ver = "1.2.0"
    val navigationComponent_ver ="2.5.3"
    val room_ver = "2.5.1"
    val datastore_ver = "1.0.0"
    val recyclerView_ver = "1.3.0"
    val retrofit_ver = "2.9.0"
    val daggerHilt_ver = "2.44.2"
    val coroutines_ver = "1.6.4"
    val LifeCycleExt_ver = "2.2.0"
    val LifeCycle_ver = "2.6.1"
    val coil_ver = "2.2.2"
    val gson_ver = "2.9.0"
    val shimmer_ver = "0.5.0"
    val jsoup_ver = "1.14.3"

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //Coordinator layout
    implementation("androidx.coordinatorlayout:coordinatorlayout:${coordinatorlayour_ver}")

    //Material Components
    implementation("com.google.android.material:material:1.8.0")

    //Navigation Components
    implementation("androidx.navigation:navigation-fragment-ktx:${navigationComponent_ver}")
    implementation("androidx.navigation:navigation-ui-ktx:${navigationComponent_ver}")

    //Room Components
    implementation("androidx.room:room-runtime:${room_ver}")
    kapt("androidx.room:room-compiler:${room_ver}")
    implementation("androidx.room:room-ktx:${room_ver}")
    androidTestImplementation("androidx.room:room-testing:${room_ver}")

    //dataStore
    implementation("androidx.datastore:datastore-preferences:${datastore_ver}")

    //RecyclerView
    implementation("androidx.recyclerview:recyclerview:${recyclerView_ver}")

    //retrofit
    implementation("com.squareup.retrofit2:retrofit:${retrofit_ver}")
    implementation("com.squareup.retrofit2:converter-gson:${retrofit_ver}")

    //dagger-hilt
    implementation("com.google.dagger:hilt-android:${daggerHilt_ver}")
    kapt("com.google.dagger:hilt-android-compiler:${daggerHilt_ver}")
    kapt("androidx.hilt:hilt-compiler:1.0.0")

    //coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:${coroutines_ver}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${coroutines_ver}")

    //LifeCycle
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:${LifeCycle_ver}")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:${LifeCycle_ver}")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:${LifeCycle_ver}")
    implementation("androidx.lifecycle:lifecycle-extensions:${LifeCycleExt_ver}")

    //coil
    implementation("io.coil-kt:coil:${coil_ver}")

    //Gson
    implementation("com.google.code.gson:gson:${gson_ver}")

    //Shimmer
    implementation("com.facebook.shimmer:shimmer:${shimmer_ver}")

    //Jsoup
    implementation("org.jsoup:jsoup:${jsoup_ver}")

}