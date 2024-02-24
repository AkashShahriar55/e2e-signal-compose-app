package usecases.messagesUseCases

import com.nsa.coroutines.BaseCoroutinesFLowUseCase
import com.nsa.coroutines.BaseCoroutinesUseCase
import com.nsa.domain.model.ChatListData
import com.nsa.domain.model.PeopleProfile
import com.nsa.domain.model.fakeChatListData
import com.nsa.domain.model.fakePeopleProfileList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow

class FetchChatListUseCase: BaseCoroutinesUseCase<ChatListData, Unit>(
    Dispatchers.IO
) {
    override suspend fun buildUseCase(params: Unit?): ChatListData {
        delay(2000)
        return fakeChatListData
    }
}