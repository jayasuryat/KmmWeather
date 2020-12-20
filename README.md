# KmmWeather - Kotlin Multiplatform Mobile Sample
This is a **Kotlin Multiplatform Mobile (KMM) Project**. It includes Android and iOS applications with a native UI and a `common module` which is shared between   Android and iOS written in Kotlin Native.

## Features
This is a simple, single-page weather app, this app gets its weather data from [OpenWeatherMap](https://openweathermap.org/current).
* The host Android / iOS application retrieves current location coordinates and request weather data of those coordinates from the common module.
* The common module uses [Ktor](https://kotlinlang.org/docs/mobile/use-ktor-for-networking.html) to make API calls, which is a powerful multiplatform HTTP client.
* The common module also has support for caching the retrieved weather data into persistent storage using [Multiplatform Settings](https://github.com/russhwolf/multiplatform-settings), which is a multiplatform key-value storing library, which utilizes `SharedPreferences` / `NSUserDefaults` under the hood for Android / iOS respectively.
* API response and locally cached data is serialized / deserialized using [kotlinx.serialization](https://github.com/Kotlin/kotlinx.serialization) which is a multiplatform reflectionless serialization library.


## Related links
* Visit [Kotlin Multiplatform Mobile Developer Portal](https://kotlinlang.org/lp/mobile/) to learn more about the technology
* Check out [all the companies](https://kotlinlang.org/lp/mobile/case-studies/) that are already using KMM.
* List of all [KMM libaries](https://libs.kmp.icerock.dev/) available as maven artifacts.
