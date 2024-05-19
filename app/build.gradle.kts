plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.appbanvexemphim"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.appbanvexemphim"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        externalNativeBuild {
            cmake {
                cppFlags += ""
            }
        }
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
    }
    externalNativeBuild {
        cmake {
            path = file("src/main/cpp/CMakeLists.txt")
            version = "3.22.1"
        }
    }
}

dependencies{
    implementation ("de.hdodenhof:circleimageview:3.1.0")
    implementation ("com.github.bumptech.glide:glide:4.12.0")
    implementation ("com.google.android.material:material:<version>")
    implementation("androidx.activity:activity:1.8.0")
    implementation(fileTree(mapOf(
        "dir" to "C:\\Users\\PC ASUS\\OneDrive\\Desktop\\MobileProgram\\AppBanVeXemPhim-master\\app\\libs",
        "include" to listOf("*.aar", "*.jar")
    )))
    annotationProcessor ("com.github.bumptech.glide:compiler:4.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment:2.6.0")
    implementation("androidx.navigation:navigation-ui:2.6.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("androidx.navigation:navigation-ui:2.6.0")


    // VNPAY
    implementation("com.google.code.gson:gson:2.8.5")
    implementation(group = "com.squareup.okhttp3", name = "okhttp", version = "3.14.1")
    // ZALOPAY
    implementation("com.squareup.okhttp3:okhttp:4.6.0")
    implementation("commons-codec:commons-codec:1.14")
    // MOMO
    implementation("com.github.momo-wallet:mobile-sdk:1.0.7")
}