@file:Suppress("unused")

package com.angelvictor.movies.buildsrc

object Libs {

    const val androidGradlePlugin = "com.android.tools.build:gradle:7.2.1"
    const val gradleVersionsPlugin = "com.github.ben-manes:gradle-versions-plugin:0.43.0"
    const val playServicesLocation = "com.google.android.gms:play-services-location:21.0.1"

    object Kotlin {
        private const val version = "1.7.21"
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"

        object Coroutines {
            private const val version = "1.6.4"
            const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
            const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$version"
        }
    }

    object AndroidX {

        const val coreKtx = "androidx.core:core-ktx:1.9.0"
        const val appCompat = "androidx.appcompat:appcompat:1.4.1"
        const val recyclerView = "androidx.recyclerview:recyclerview:1.2.1"
        const val material = "com.google.android.material:material:1.5.0"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.1.3"

        object Activity {
            private const val version = "1.4.0"
            const val ktx = "androidx.activity:activity-ktx:1.4.0"
        }

        object Lifecycle {
            private const val version = "2.4.1"
            const val viewmodelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
            const val runtimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:$version"
        }

        object Navigation {
            private const val version = "2.4.1"
            const val fragmentKtx = "androidx.fragment:fragment-ktx:1.5.2"
            const val navigationFragmentKtx = "androidx.navigation:navigation-fragment-ktx:$version"
            const val uiKtx = "androidx.navigation:navigation-ui-ktx:$version"
            const val gradlePlugin = "androidx.navigation:navigation-safe-args-gradle-plugin:$version"
        }

        object Room {
            private const val version = "2.4.2"
            const val runtime = "androidx.room:room-runtime:$version"
            const val ktx = "androidx.room:room-ktx:$version"
            const val compiler = "androidx.room:room-compiler:$version"
        }

        object Test {
            private const val version = "1.5.1"
            const val runner = "androidx.test:runner:$version"

            object Rules {
                private const val version = "1.5.0"
                const val rules = "androidx.test:rules:$version"
            }
            object Ext {
                private const val version = "1.1.4"
                const val junit = "androidx.test.ext:junit-ktx:$version"
            }
            object Espresso{
                private const val version="3.5.0"
                const val contrib = "androidx.test.espresso:espresso-contrib:$version"
            }
            object CoreTesting{
                private const val version="2.1.0"
                const val test = "androidx.arch.core:core-testing:$version"
            }
        }
    }

    object Glide {
        private const val version = "4.14.2"
        const val glide = "com.github.bumptech.glide:glide:$version"
        const val compiler = "com.github.bumptech.glide:compiler:$version"
    }

    object OkHttp3 {
        private const val version = "4.9.3"
        const val loginInterceptor = "com.squareup.okhttp3:logging-interceptor:$version"
        const val mockWebServer = "com.squareup.okhttp3:mockwebserver:$version"
    }

    object Retrofit {
        private const val version = "2.9.0"
        const val retrofit = "com.squareup.retrofit2:retrofit:$version"
        const val converterGson = "com.squareup.retrofit2:converter-gson:$version"
    }

    object Arrow {
        private const val version = "1.0.1"
        const val core = "io.arrow-kt:arrow-core:$version"
    }

    object Hilt {
        private const val version = "2.44"
        const val android = "com.google.dagger:hilt-android:$version"
        const val compiler = "com.google.dagger:hilt-compiler:$version"
        const val gradlePlugin = "com.google.dagger:hilt-android-gradle-plugin:$version"
        const val test = "com.google.dagger:hilt-android-testing:$version"
    }

    object JUnit {
        private const val version = "4.13.2"
        const val junit = "junit:junit:$version"
    }

    object Mockito {
        const val kotlin = "org.mockito.kotlin:mockito-kotlin:4.0.0"
        const val inline = "org.mockito:mockito-inline:4.8.1"
    }

    object JavaX {
        const val inject = "javax.inject:javax.inject:1"
    }

}