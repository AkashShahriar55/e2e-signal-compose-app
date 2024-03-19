package mm.com.atom.eagle.data.remote.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

//@Singleton
//class AppStateInterceptor @Inject constructor() : BaseInterceptor() {
//    override fun intercept(chain: Interceptor.Chain): Response {
//        val initialResponse = chain.proceed(chain.request())
//
//        return when (initialResponse.code) {
//            EagleHttpCodes.FORCE_LOGOUT -> {
//                eventUtils.postForceLogoutEvent()
//                initialResponse.createEmptyResponse()
//            }
//
//            EagleHttpCodes.FORCE_UPDATE -> {
//
//                val bodyLength = initialResponse.body?.contentLength() ?: -1
//                val body = initialResponse.peekBody(bodyLength).string()
//
//                eventUtils.postForceUpdateEvent(data = body)
//
//                initialResponse.createEmptyResponse()
//            }
//
//            EagleHttpCodes.MAINTENANCE_MODE -> {
//
//                val bodyLength = initialResponse.body?.contentLength() ?: -1
//                val body = initialResponse.peekBody(bodyLength).string()
//
//                eventUtils.postMaintenanceModeEnableEvent(data = body)
//
//                initialResponse.createEmptyResponse()
//            }
//
//            else -> initialResponse
//        }
//    }
//}