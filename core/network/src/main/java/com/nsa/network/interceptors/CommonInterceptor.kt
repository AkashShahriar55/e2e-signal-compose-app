package mm.com.atom.eagle.data.remote.interceptor

import android.content.Context
import android.location.Location
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import javax.inject.Inject

//class CommonInterceptor @Inject constructor(
//    private val darkModeUtils: DarkModeUtils,
//    @ApplicationContext private val context: Context,
//    private val locationUtils: LocationUtils,
//) : BaseInterceptor() {
//
//    override fun intercept(chain: Interceptor.Chain): Response {
//        val newRequest = chain.request().updateCredentials()
//        return chain.proceed(newRequest)
//    }
//
//    private fun Request.updateCredentials(): Request {
//        val deviceLocation = locationUtils.deviceLocation
//
//        val newRequestBuilder = this.newBuilder()
//        val acceptLanguage = LocaleHelper.getLanguage(context)
//        // Identifier for new deeplink/domain/atom
//        newRequestBuilder.header("Has-New-Package", "true")
//
//        if (isPreprodBuild()) {
//            newRequestBuilder.header("X-Server-Select", "preprod")
//        }
//
//        if (deviceLocation != null) {
//            //we will try to add device location with the request
//            addLocation(newRequestBuilder, deviceLocation)
//        }
//        val isDarkModeEnabled = runBlocking { darkModeUtils.getIsDarkModeEnabled() }
//
//        addUiModeHeader(newRequestBuilder, isDarkModeEnabled)
//
//        return newRequestBuilder
//            .header("User-Agent", getUserAgent())
//            .header("Accept-Language", acceptLanguage)
//            .build()
//    }
//
//    private fun addLocation(builder: Request.Builder, location: Location?) {
//        val json = JSONObject()
//
//        json.put("device_lat", location?.latitude ?: 0.0)
//        json.put("device_long", location?.longitude ?: 0.0)
//
//        builder.header("Device-Location", json.toString())
//    }
//
//
//    private fun addUiModeHeader(
//        builder: Request.Builder,
//        isDarkMode: Boolean,
//    ) {
//        builder.header("Ui-Mode", if (isDarkMode) "Dark" else "Light")
//    }
//
//}