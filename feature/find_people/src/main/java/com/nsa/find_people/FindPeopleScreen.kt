package com.nsa.find_people

import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab

import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nsa.ui.component.CustomTab
import com.nsa.ui.component.ProfilePicture
import com.nsa.ui.component.ProfilePictureSize
import com.nsa.ui.component.TabData
import com.nsa.ui.theme.AppTheme
import com.nsa.ui.theme.BellIcon
import com.nsa.ui.theme.LocatonIcon
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FindPeopleScreen(
    NearYouListScreen: @Composable () -> Unit,
    NewMatchesListScreen: @Composable () -> Unit,
) {


    val scope = rememberCoroutineScope()

    val tabData = getTabList()
    val pagerState = rememberPagerState(pageCount = { tabData.size })

    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .padding(20.dp, 20.dp, 20.dp, 0.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        UserProfileView()
        CustomTab(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            items = tabData,
            pagerState = pagerState,
            onClick = {
                scope.launch{
                    pagerState.animateScrollToPage(it)
                }

            },
        )



        HorizontalPager(state = pagerState) {index ->
            when(index){
                0 ->{
                    NearYouListScreen()
                }
                1->{
                    NewMatchesListScreen()
                }
            }

        }




    }

}





@Composable
fun UserProfileView(){
    Row(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(8.dp, 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        ProfilePicture(
            photoUri = Uri.parse("https://images.unsplash.com/photo-1542178243-bc20204b769f?ixid=MXwxMjA3fDB8MHxzZWFyY2h8MTB8fHBvcnRyYWl0fGVufDB8MnwwfA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"),
            onlineStatus = false,
            size = ProfilePictureSize.MEDIUM)
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Text(
                text = "Hello, Jason",
                style = TextStyle(fontSize = 16.sp,fontWeight = FontWeight.Bold)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                LocatonIcon(color = Color(0xffFF0F66))
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    color = Color(0xff8C8C8C),
                    text = "Bali,Indonasia",
                    style = TextStyle(fontSize = 12.sp)
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))

        BellIcon(
            modifier = Modifier.padding(12.dp),
            hasNotification = true
        )

    }

}


private fun getTabList(): List<TabData> {
    return listOf(
        TabData("Near You","near_you"),
        TabData("New Matches","new_matches")
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen2() {


    AppTheme {
        FindPeopleScreen({},{})
    }
}
