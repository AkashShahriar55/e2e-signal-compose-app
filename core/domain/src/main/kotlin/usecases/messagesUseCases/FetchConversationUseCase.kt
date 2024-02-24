package usecases.messagesUseCases

import com.nsa.coroutines.BaseCoroutinesUseCase
import com.nsa.domain.model.Message
import com.nsa.domain.model.fakeConversation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay

class FetchConversationUseCase: BaseCoroutinesUseCase<List<Message>, Int>(
    Dispatchers.IO
) {
    override suspend fun buildUseCase(params: Int?): List<Message> {
        delay(2000)
        return fakeConversation
    }
}