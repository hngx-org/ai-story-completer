package com.newton.storycompleter.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.newton.storycompleter.R
import com.newton.storycompleter.app.data.local.Story
import com.newton.storycompleter.app.theme.StoryCompleterTheme
import com.newton.storycompleter.ui.stories.StoriesScreen
import com.newton.storycompleter.ui.stories.StoryListState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navigateBack: () -> Unit,
    openAndPopUp: (String, String) -> Unit
) {
    val viewModel: ProfileViewModel = hiltViewModel()
    val profile by viewModel.profile.observeAsState()
    Surface(color = MaterialTheme.colorScheme.background) {
        LazyColumn {
            item {
                TopAppBar(
                    navigationIcon = {
                        Box(
                            modifier = Modifier
                                .clickable {
                                    navigateBack()
                                }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.arrow_back_24),
                                contentDescription = "back nav"
                            )
                        }
                    },
                    title = {
                        Text(
                            text = "Profile",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight(400),
                                color = Color(0xFF000000),
                                textAlign = TextAlign.Center,
                            )
                        )
                    }
                )
            }
            item {
                ProfilePicSection(viewModel)
            }
            item {
                SubscriptionSection()
            }
            item {
                Spacer(modifier = Modifier.height(20.dp))
                Text(text = profile?.id ?: "",)
                Text(text = profile?.name ?: "",)
                Text(text = profile?.credit.toString() ?: "",)
            }
            item {
                LogoutButton(viewModel = viewModel,onClick = openAndPopUp)
            }
        }
    }
}

@Composable
fun ProfilePicSection(viewModel: ProfileViewModel) {
    val profilePic = painterResource(id = R.drawable.account_user_profile_avatar)
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = profilePic,
            contentDescription = "Profile Picture",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .width(200.dp)
                .height(200.dp)
                .clip(CircleShape)
        )
        Surface(
            modifier = Modifier
                .width(340.dp)
                .height(75.dp)
                .align(Alignment.BottomCenter)
                .offset(y = 24.dp)
                .clip(RoundedCornerShape(size = 5.dp)),
            color = Color(0xFFFCFCFC)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IdentitySection(viewModel)
            }
        }
    }
}

@Composable
fun IdentitySection(viewModel: ProfileViewModel) {
    val profile by viewModel.profile.observeAsState()
//should come from the viewmodel
    val slackName = profile?.email

    Text(
        text =  slackName ?:"Username",
        style = TextStyle(
            fontFamily = FontFamily.SansSerif,
            fontSize = 16.sp,
            fontWeight = FontWeight(500),
            color = Color(0xFF000000),
        ),
        modifier = Modifier.padding(16.dp)
    )

}

@Composable
fun SubscriptionSection() {

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Current Subscription Status",
            color = Color(0xFF68725D)
        )
    }
}

@Composable
fun LogoutButton(onClick: (String,String) -> Unit, viewModel: ProfileViewModel) {
    Row(
    ) {
        Button(
            onClick = {
viewModel.SignOut(onClick)

            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = Color(0xFFEA3131),
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.logout_left),
                    contentDescription = "Logout Icon"
                )
                Spacer(modifier = Modifier.size(width = 4.dp, height = 0.dp))
                Text(
                    text = "Logout",
                    style = TextStyle(
                        fontSize = 18.sp,
                        lineHeight = 28.sp,
                        fontWeight = FontWeight(500),
                        color = Color(0xFFEA3131),
                    )
                )
            }
        }
    }
}
