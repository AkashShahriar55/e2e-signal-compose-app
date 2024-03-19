package mm.com.atom.eagle.data.remote.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

//@Singleton
//class ResponseCacheInterceptor @Inject constructor(): BaseInterceptor() {
//    private val responseCaches = LinkedList<ResponseCache>()
//
//
//    override fun intercept(chain: Interceptor.Chain): Response {
//        val url = chain.request().url.toString()
//
//        val initialResponse = chain.proceed(chain.request())
//        try{
//            val bodyLength = initialResponse.body?.contentLength() ?: -1
//            val body = initialResponse.peekBody(bodyLength).string()
//
//            Timber.d("ResponseCache : $url")
//
//            Timber.d("ResponseCache : $body")
//
//            val responseCache = ResponseCache(url, body, initialResponse.code)
//
//            responseCaches.removeIf {
//                it.url == url
//            }
//
//            if (responseCaches.size == MAX_CACHE_SIZE) {
//                responseCaches.remove()
//            }
//            responseCaches.add(responseCache)
//        }catch (ex:Throwable){
//            ex.printStackTrace()
//        }
//
//        return  initialResponse
//    }
//
//    fun getEntryByUrl(url:String): ResponseCache?{
//        return responseCaches.firstOrNull { responseCache ->
//            responseCache.url.contains(url.split("{")[0])
//        }?.let {
//            responseCaches.remove(it)
//            it
//        }
//    }
//    companion object{
//        private const val MAX_CACHE_SIZE = 10
//    }
//}