plugins {
    id("com.android.application")
}

android {
    namespace = "com.fullana.proyectofinal"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.fullana.proyectofinal"
        minSdk = 33
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"


        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        manifestPlaceholders["appAuthRedirectScheme"] = "com.fullana.proyectofinal"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        viewBinding = true
    }
    packagingOptions {
        exclude("com/itextpdf/io/font/cmap_info.txt")
        exclude("com/itextpdf/io/font/cmap/*")
        exclude("META-INF/INDEX.LIST")
        exclude("META-INF/DEPENDENCIES")
        exclude("META-INF/gradle/incremental.annotation.processors")

    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("androidx.navigation:navigation-fragment:2.7.7")
    implementation("androidx.navigation:navigation-ui:2.7.7")
    implementation("androidx.wear:wear-phone-interactions:1.0.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    //lombok
    compileOnly("org.projectlombok:lombok:1.18.32")
    annotationProcessor("org.projectlombok:lombok:1.18.32")
    testCompileOnly("org.projectlombok:lombok:1.18.32")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.32")

    //google
    implementation("com.google.api-client:google-api-client:2.4.0")
    implementation("com.google.api-client:google-api-client-android:1.30.9")
    implementation("com.google.apis:google-api-services-sheets:v4-rev20230808-2.0.0")
    implementation("com.google.android.gms:play-services-auth:21.0.0")
    implementation("com.google.jacquard:jacquard-sdk:1.0.0")

    //
    implementation("net.openid:appauth:0.11.1")


}
