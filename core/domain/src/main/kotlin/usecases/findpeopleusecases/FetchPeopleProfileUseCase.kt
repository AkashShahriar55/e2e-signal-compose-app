package usecases.findpeopleusecases

import com.nsa.coroutines.BaseCoroutinesFLowUseCase
import com.nsa.coroutines.BaseCoroutinesUseCase
import com.nsa.domain.model.PeopleProfile
import com.nsa.domain.model.fakePeopleProfileList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow

class FetchPeopleProfileUseCase: BaseCoroutinesUseCase<PeopleProfile?, Int>(
    Dispatchers.IO
) {

    override suspend fun buildUseCase(params: Int?): PeopleProfile? {
        delay(1000)
        return params?.let { fakePeopleProfileList.find { it.id == params } }
    }
}