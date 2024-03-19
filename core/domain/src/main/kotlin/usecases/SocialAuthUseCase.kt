package usecases

import android.icu.text.SimpleDateFormat
import android.icu.util.TimeZone
import com.nsa.coroutines.BaseCoroutinesUseCase
import com.nsa.domain.model.params.GoogleAuthParam
import com.nsa.setting.managers.AuthManager
import kotlinx.coroutines.Dispatchers
import java.util.Locale
import javax.inject.Inject

class GoogleAuthUseCase @Inject constructor(
    private val authManager: AuthManager
): BaseCoroutinesUseCase<String, GoogleAuthParam>(
    Dispatchers.Default
) {
    override suspend fun buildUseCase(params: GoogleAuthParam?): String {
        params?.apply {
            authManager.loginWithGoogle(context,registry)
        }
        return ""
    }

}