plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    `maven-publish`

}

android {
    namespace = "com.rehman.view"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
}


afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"])

                groupId = "com.github.AbdulRehman-Pro" 
                artifactId = "AlertBanner"
                version = "1.0.0" // Update for new versions

                pom {
                    name.set("AlertBanner")
                    description.set("An Android library for showing alert banners easily.")
                    url.set("https://github.com/AbdulRehman-Pro/AlertBanner")

                    licenses {
                        license {
                            name.set("MIT License")
                            url.set("https://opensource.org/licenses/MIT")
                        }
                    }

                    developers {
                        developer {
                            id.set("AbdulRehman-Pro")
                            name.set("Abdul Rehman")
                            email.set("rehmankhan8360@gmail.com")
                        }
                    }

                    scm {
                        connection.set("scm:git:github.com/AbdulRehman-Pro/AlertBanner.git")
                        developerConnection.set("scm:git:ssh://github.com/AbdulRehman-Pro/AlertBanner.git")
                        url.set("https://github.com/AbdulRehman-Pro/AlertBanner")
                    }
                }
            }
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}