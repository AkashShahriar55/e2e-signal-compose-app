package usecases.findpeopleusecases

import android.util.Log
import com.nsa.coroutines.BaseCoroutinesFLowUseCase
import com.nsa.coroutines.BaseCoroutinesUseCase
import com.nsa.domain.model.PeopleProfile
import com.nsa.domain.model.fakePeopleProfileList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow




class MakeFavoriteUseCase: BaseCoroutinesUseCase<Boolean, Int>(
    Dispatchers.IO
) {

    override suspend fun buildUseCase(params: Int?): Boolean {
        var result:Boolean? = null
        fakePeopleProfileList = fakePeopleProfileList.toMutableList().map {
            Log.d("check_data", "user match  ${it.id == params}")
            if(it.id == params){
                result = !it.isFavorite
                it.copy(isFavorite = result!!)
            }else{
                it
            }
        }
        Log.d("check_data", "buildUseCase: $result")
        delay(1000)
        return result!!
    }
}