package usecases.findpeopleusecases

import com.nsa.coroutines.BaseCoroutinesFLowUseCase
import com.nsa.coroutines.BaseCoroutinesUseCase
import com.nsa.domain.model.PeopleProfile
import com.nsa.domain.model.fakePeopleProfileList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow

sealed class FetchPeopleListKey private constructor(val key:String){


    object NearYou:FetchPeopleListKey(FETCH_PEOPLE_LIST_KEY_NEAR_YOU)

    object NewMatches:FetchPeopleListKey(FETCH_PEOPLE_LIST_KEY_NEW_MATCHES)

    object Favorite:FetchPeopleListKey(FETCH_PEOPLE_LIST_KEY_FAVORITE)
}

private const val FETCH_PEOPLE_LIST_KEY_NEAR_YOU = "near_you"
private const val FETCH_PEOPLE_LIST_KEY_NEW_MATCHES = "new_matches"
private const val FETCH_PEOPLE_LIST_KEY_FAVORITE= "favorite"
class FetchPeopleListUseCase: BaseCoroutinesUseCase<List<PeopleProfile>, FetchPeopleListKey>(
    Dispatchers.IO
) {
    override suspend fun buildUseCase(params: FetchPeopleListKey?): List<PeopleProfile> {
        delay(2000)
        return when(params){
            FetchPeopleListKey.Favorite -> fakePeopleProfileList.filter { it.isFavorite }
            FetchPeopleListKey.NearYou -> fakePeopleProfileList.take(5)
            FetchPeopleListKey.NewMatches -> fakePeopleProfileList.takeLast(6)
            null -> listOf()
        }
    }
}