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
        minSdk = 21
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    val coordinatorlayour_ver = "1.2.0"
    val navigationComponent_ver ="2.7.4"
    val room_ver = "2.5.0"
    val datastore_ver = "1.0.0"
    val dataBindingCommon_ver= "8.1.2"
    val dataBindingCompiler_ver = "3.2.0-alpha10"
    val recyclerView_ver = "1.2.1"
    val retrofit_ver = "2.9.0"
    val daggerHilt_ver = "2.44.2"
    val hilt_ver = "1.0.0-alpha03"
    val coroutines_ver = "1.6.4"
    val LifeCycle_ver = "2.2.0"
    val coil_ver = "2.2.2"
    val gson_ver = "2.10"
    val shimmer_ver = "0.5.0"
    val shimmer_recyclerView_ver = "0.4.0"
    val jsoup_ver = "1.15.3"


    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //Coordinator layout
    implementation("androidx.coordinatorlayout:coordinatorlayout:${coordinatorlayour_ver}")

    //Material Components
    implementation("com.google.android.material:material:1.10.0")

    //Navigation Components
    implementation("androidx.navigation:navigation-fragment-ktx:${navigationComponent_ver}")
    implementation("androidx.navigation:navigation-ui-ktx:${navigationComponent_ver}")

    //Room Components
    implementation("androidx.room:room-runtime:${room_ver}")
    kapt("androidx.room:room-compiler:${room_ver}")
    implementation("androidx.room:room-ktx:${room_ver}")
    androidTestImplementation("androidx.room:room-testing:${room_ver}")

    //data binding
    kapt("androidx.databinding:databinding-common:${dataBindingCommon_ver}")
    kapt("com.android.databinding:compiler:${dataBindingCompiler_ver}")

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

    implementation("androidx.hilt:hilt-lifecycle-viewmodel:${hilt_ver}")
    kapt("androidx.hilt:hilt-compiler:${hilt_ver}")

    //coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:${coroutines_ver}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${coroutines_ver}")

    //LifeCycle
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:${LifeCycle_ver}")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:${LifeCycle_ver}")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:${LifeCycle_ver}")
    implementation("androidx.lifecycle:lifecycle-extensions:${LifeCycle_ver}")

    //coil
    implementation("io.coil-kt:coil:${coil_ver}")

    //Gson
    implementation("com.google.code.gson:gson:${gson_ver}")

    //Shimmer
    implementation("com.facebook.shimmer:shimmer:${shimmer_ver}")
    //implementation("com.todkars:shimmer-recyclerview:${shimmer_recyclerView_ver}")

    //Jsoup
    implementation("org.jsoup:jsoup:${jsoup_ver}")

}