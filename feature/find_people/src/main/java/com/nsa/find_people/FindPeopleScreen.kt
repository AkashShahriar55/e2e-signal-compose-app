package com.nsa.find_people

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nsa.domain.model.UserProfile
import com.nsa.ui.component.CustomTab
import com.nsa.ui.component.ProfilePicture
import com.nsa.ui.component.ProfilePictureSize
import com.nsa.ui.component.TabData
import com.nsa.ui.theme.AppTheme
import com.nsa.ui.theme.BellIcon
import com.nsa.ui.theme.LocatonIcon
import com.nsa.ui.theme.shimmerColor
import com.valentinilk.shimmer.shimmer
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FindPeopleScreen(
    findPeopleViewModel: FindPeopleViewModel,
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

        UserProfileView(findPeopleViewModel)
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
fun UserProfileView(findPeopleViewModel: FindPeopleViewModel) {

    val uiState by findPeopleViewModel.uiState.collectAsStateWithLifecycle()




    Row(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(8.dp, 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {


        when(uiState){
            FindPeopleUiState.Empty -> {


            }
            is FindPeopleUiState.Fail -> {

            }
            FindPeopleUiState.Loading -> {
                UserShimmerView()
            }
            is FindPeopleUiState.Success -> {
                UserView((uiState as FindPeopleUiState.Success).userProfile)
            }
        }


        Spacer(modifier = Modifier.weight(1f))

        BellIcon(
            modifier = Modifier.padding(12.dp),
            hasNotification = true
        )

    }

}


@Composable
private fun UserView(user: UserProfile){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ){
        ProfilePicture(
            photoUri = user.photo,
            onlineStatus = false,
            size = ProfilePictureSize.MEDIUM)
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Text(
                text = user.name,
                style = TextStyle(fontSize = 16.sp,fontWeight = FontWeight.Bold)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                LocatonIcon()
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = user.location,
                    style = TextStyle(fontSize = 12.sp)
                )
            }
        }
    }

}

@Composable
private fun UserShimmerView(){
    Row(
        modifier = Modifier.shimmer(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ){
        Box(
            modifier = Modifier.size(60.dp).background(
                MaterialTheme.colorScheme.shimmerColor,
                RoundedCornerShape(50)
            )
        )
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Box(
                modifier = Modifier.size(
                    AppTheme.dimens.ShimmerTitleWidth,
                    AppTheme.dimens.ShimmerTitleHeight
                ).background(MaterialTheme.colorScheme.shimmerColor)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier.size(
                        AppTheme.dimens.ShimmerSubtitleWidth,
                        AppTheme.dimens.ShimmerSubtitleHeight
                    ).background(MaterialTheme.colorScheme.shimmerColor)
                )
            }
        }
    }

}


private fun getTabList(): List<TabData> {
    return listOf(
        TabData("Near You","near_you"),
        TabData("New Matches","new_matches")
    )
}

@Preview
@Composable
fun PreviewLoginScreen2() {

    AppTheme {
        UserShimmerView()
    }
}
