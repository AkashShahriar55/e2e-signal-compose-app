package com.nsa.chat

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.PlayCircleFilled
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Timelapse
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.linc.audiowaveform.AudioWaveform
import com.nsa.domain.model.AudioMessage
import com.nsa.domain.model.Message
import com.nsa.domain.model.MessageStatus
import com.nsa.domain.model.MessageStatus.*
import com.nsa.domain.model.PhotoMessage
import com.nsa.domain.model.TextMessage
import com.nsa.domain.model.UserProfile
import com.nsa.domain.model.fakeConversation
import com.nsa.domain.model.fakeUserProfile
import com.nsa.ui.component.ProfilePicture
import com.nsa.ui.component.ProfilePictureSize
import com.nsa.ui.ext.getFormatedText
import com.nsa.ui.theme.AppTheme
import com.nsa.ui.theme.red
import com.nsa.ui.theme.shimmerColor
import com.valentinilk.shimmer.shimmer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


@Composable
fun ChatScreen(
    stateFlow: StateFlow<ChatScreenUIState>,
    userStateFlow: StateFlow<UserProfile?>,
) {


//    val data = navHostController.previousBackStackEntry?.savedStateHandle?.get<Person>("data") ?: Person()





    val uiState by stateFlow.collectAsStateWithLifecycle()


    when(uiState){
        ChatScreenUIState.Empty -> {}
        is ChatScreenUIState.Fail -> TODO()
        ChatScreenUIState.Loading -> ChatView(userStateFlow = userStateFlow, listOf(),true)
        is ChatScreenUIState.Success -> ChatView(userStateFlow = userStateFlow, chatList = (uiState as ChatScreenUIState.Success).conversation)
    }






}


@Composable
fun ChatView(
    userStateFlow: StateFlow<UserProfile?>,
    chatList:List<Message>,
    isLoading:Boolean = false
){
    val scrollableState = rememberScrollState()
    var message by remember { mutableStateOf("") }

    Scaffold(
        modifier = Modifier
            .statusBarsPadding()
            .imePadding(),
        topBar = { ChatTopBar(userStateFlow) },
        containerColor = MaterialTheme.colorScheme.tertiaryContainer,
        contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
    )
    {
        Surface(
            modifier = Modifier
                .padding(it),
            color = MaterialTheme.colorScheme.background,
            shape = RoundedCornerShape(30.dp, 30.dp, 0.dp, 0.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(30.dp)
                    .verticalScroll(scrollableState),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if(isLoading){
                    CircularProgressIndicator()
                }
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ){
                    itemsIndexed(
                        chatList,
                        key = { index,item-> item.chatId }
                    ){index,item->
                        ChatRow(
                            message = item
                        )
                    }
                }


                CustomTextField(
                    text = message,
                    onValueChange = { message = it },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }



    }
}



@Composable
fun ChatRow(
    message: Message
) {

    var alignment = Alignment.CenterEnd
    var columnAlignment = Alignment.End
    var tonalElevation = 50.dp
    var statusView : @Composable () -> Unit = {
        MessageSending()
    }

    var hasError = false

    when(message.messageStatus){
        Sending -> {
            statusView = { MessageSending() }
        }
        Sent -> {
            statusView = { MessageSent() }
        }
        Seen -> {
            statusView = { MessageSeen() }
        }
        Error -> {
            hasError = true
        }
        Recieved -> {
            tonalElevation = 1.dp
            alignment = Alignment.CenterStart
            columnAlignment = Alignment.Start
        }
    }


    Box(modifier = Modifier
        .fillMaxWidth(),
        contentAlignment = alignment){
        Column(
            horizontalAlignment = columnAlignment
        ) {
            when(message.message){
                is AudioMessage -> AudioMessageUi((message.message as AudioMessage).link)
                is PhotoMessage -> ImageMessageUi((message.message as PhotoMessage).links)
                is TextMessage -> TextMessageUi((message.message as TextMessage).message,tonalElevation)
            }
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {

                if(message.isLastMessage){
                    statusView()
                }

                if(hasError){
                    MessageFailed()
                }

                if(message.shouldShowTime){
                    Text(
                        modifier = Modifier.padding(5.dp),
                        text =  message.time.getFormatedText("hh:mm a"),
                        style = MaterialTheme.typography.labelMedium,
                        textAlign = TextAlign.End
                    )
                }




            }

        }
    }


}

@Composable
fun MessageSent(){
    Icon(
        modifier = Modifier.size(13.dp),
        imageVector = Icons.Default.Done,
        contentDescription = "",
        tint = MaterialTheme.colorScheme.primary
    )
}

@Composable
fun MessageSeen(){

    Icon(
        modifier = Modifier.size(13.dp),
        imageVector = Icons.Default.DoneAll,
        contentDescription = "",
        tint = MaterialTheme.colorScheme.primary
    )
}

@Composable
fun MessageFailed(){
    Row(
        modifier = Modifier.padding(bottom = 5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(

            text = "Couldn't send this message",
            style = MaterialTheme.typography.labelMedium,
            textAlign = TextAlign.End,
            color = MaterialTheme.colorScheme.red
        )
        Icon(
            modifier = Modifier.padding(start = 5.dp).size(14.dp),
            imageVector = Icons.Default.Error,
            contentDescription = "",
            tint = MaterialTheme.colorScheme.red
        )


    }

}

@Composable
fun MessageSending(){

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(12.dp),
            imageVector = Icons.Default.Timelapse,
            contentDescription = "",
            tint = Color.LightGray
        )
        Text(
            modifier = Modifier.padding(5.dp),
            text = "Sending",
            style = MaterialTheme.typography.labelMedium,
            textAlign = TextAlign.End
        )

    }

}



@Composable
fun TextMessageUi(
    text: String,
    tonalElevation: Dp
){
    Surface(
        modifier = Modifier
            .padding(0.dp, 2.dp)
            .widthIn(0.dp, 250.dp)
        ,
        shape = RoundedCornerShape(15.dp),
        tonalElevation = tonalElevation,
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 15.dp),
                textAlign = TextAlign.End
            )
    }
}

@Composable
fun AudioMessageUi(
    link: String
) {
    var waveformProgress by remember { mutableStateOf(0.2f) }
    Surface(
        modifier = Modifier
            .padding(0.dp, 2.dp)
            .widthIn(0.dp, 250.dp)
        ,
        shape = RoundedCornerShape(50),
        color = MaterialTheme.colorScheme.primary
    ) {
        Row(
            modifier = Modifier
                .height(50.dp)
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(30.dp),
                imageVector = Icons.Default.PlayCircleFilled,
                contentDescription = "",
            )

            AudioWaveform(
                modifier = Modifier.requiredHeight(20.dp),
                amplitudes = listOf(0,20,5,50,60,55,30,12,30,40,90,95,23),
                progress = waveformProgress,
                onProgressChange = { waveformProgress = it },
                waveformBrush = SolidColor( Color(0xFFFFB2BB)),
                progressBrush = SolidColor(Color.White),
                spikePadding = 2.dp
            )
        }
    }
}

@Composable
fun ImageMessageUi(links: List<String>) {

}

@Composable
fun CustomTextField(
    text: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit
) {

    OutlinedTextField(
        modifier = modifier,
        value = text, onValueChange = { onValueChange(it) },
        placeholder = {
            Text(
                text = "Type Message",
                style = MaterialTheme.typography.bodySmall
            )
        },
        leadingIcon = { CommonIconButton(imageVector = Icons.Default.Add) },
        trailingIcon = {
            if(text.isNotBlank()){
                CommonIconButton(Icons.AutoMirrored.Filled.Send)
            }else{
                CommonIconButton(Icons.Default.Mic)
            }

        },
        shape = RoundedCornerShape(50)

    )

}

@Composable
fun CommonIconButton(
    imageVector: ImageVector
) {
    FilledIconButton(
        modifier = Modifier
            .padding(10.dp)
            .size(33.dp),
        onClick = {},
    ){
        Icon(
            modifier = Modifier.size(18.dp),
            imageVector = imageVector,
            contentDescription = "",

        )
    }

}

@Composable
fun CommonIconButtonDrawable(
    @DrawableRes icon: Int
) {

    Box(
        modifier = Modifier
            .size(33.dp)
            .clip(CircleShape)
            .background(Color.Yellow), contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = icon), contentDescription = "",
            tint = Color.Black,
            modifier = Modifier.size(15.dp)
        )
    }

}

@Composable
fun UserNameRow(
    modifier: Modifier = Modifier,
    person: UserProfile
) {

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            ProfilePicture(
                person.photo,
                person.status,
                ProfilePictureSize.MEDIUM
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = person.name, style = TextStyle(
                        color = Color.White,
                        fontSize = 16.sp
                    )
                )
                Text(
                    text = "Online", style = TextStyle(
                        color = Color.White,
                        fontSize = 14.sp
                    )
                )
            }
        }
        IconButton(
            onClick = {}, modifier = Modifier
                .size(24.dp)
                .align(Alignment.CenterVertically)
        ) {
            Icon(Icons.Default.MoreVert, contentDescription = "", tint = Color.White)
        }
    }

}



@Composable
fun ChatTopBar(userStateFlow: StateFlow<UserProfile?>){
    val user by userStateFlow.collectAsStateWithLifecycle()

    val shimmerEnable = user == null

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ){
        ProfilePicture(
            modifier = Modifier.then(if(shimmerEnable) Modifier.shimmer() else Modifier),
            photoUri = user?.photo,
            onlineStatus = false,
            size = ProfilePictureSize.SMALL)
        Column(
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Text(
                modifier = Modifier.then(if(shimmerEnable) Modifier
                    .shimmer()
                    .background(MaterialTheme.colorScheme.shimmerColor, RoundedCornerShape(5.dp)) else Modifier),
                text = user?.name ?: "Fake Name",
                style = MaterialTheme.typography.titleSmall,
                color = if(shimmerEnable) Color.Transparent else Color.Unspecified,
            )

            Text(
                modifier = Modifier.then(if(shimmerEnable) Modifier
                    .shimmer()
                    .background(MaterialTheme.colorScheme.shimmerColor, RoundedCornerShape(5.dp)) else Modifier),
                text = if(user?.status == true) "Online" else "Offline",
                style = MaterialTheme.typography.bodySmall,
                color = if(shimmerEnable) Color.Transparent else Color.Unspecified
            )
        }

        Spacer(modifier = Modifier.weight(1f))
        FilledIconButton(
            onClick = {},
            shape = RoundedCornerShape(12.dp),
            colors = IconButtonDefaults.filledTonalIconButtonColors()
        ){
            Icon(
                Icons.Default.MoreVert,
                contentDescription = "",
            )
        }
    }
}


@Preview
@Composable
fun PreviewChatScreen(){
    AppTheme {
        ChatScreen(
            MutableStateFlow(ChatScreenUIState.Success(fakeConversation)),
            MutableStateFlow(fakeUserProfile)
        )
    }
}
