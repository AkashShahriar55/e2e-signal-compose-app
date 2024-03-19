package usecases.userprofileusecases

import com.nsa.coroutines.BaseCoroutinesUseCase
import com.nsa.domain.model.UserProfile
import com.nsa.domain.model.fakeUserProfile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay

class FetchUserProfileUseCase(

): BaseCoroutinesUseCase<UserProfile, Any>(Dispatchers.IO) {
    override suspend fun buildUseCase(params: Any?): UserProfile {
        delay(2000)
        return fakeUserProfile
    }
}