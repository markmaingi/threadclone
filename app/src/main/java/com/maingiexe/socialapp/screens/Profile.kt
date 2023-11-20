package com.maingiexe.socialapp.screens

import android.content.pm.PackageManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.auth.FirebaseAuth
import com.maingiexe.socialapp.R
import com.maingiexe.socialapp.item_view.Threaditem
import com.maingiexe.socialapp.model.UserModel
import com.maingiexe.socialapp.navigation.Routes
import com.maingiexe.socialapp.utils.SharedPref
import com.maingiexe.socialapp.viewmodel.AuthViewModel
import com.maingiexe.socialapp.viewmodel.UserViewModel

@Composable
fun Profile(navHostController: NavHostController) {
    val authViewModel :AuthViewModel = viewModel()
    val firebaseUser by authViewModel.firebaseUser.observeAsState(null)

    val context = LocalContext.current

    val userViewModel :UserViewModel = viewModel()
    val threads by userViewModel.threads.observeAsState(null)

    val followerList by userViewModel.followerList.observeAsState(null)
    val followingList by userViewModel.followingList.observeAsState(null)

    var currentUserId = ""

    if (FirebaseAuth.getInstance().currentUser != null)
        currentUserId = FirebaseAuth.getInstance().currentUser!!.uid

if  (currentUserId!=""){
    userViewModel.getFollowers(currentUserId)
    userViewModel.getFollowing(currentUserId)
}



    val user = UserModel(
        name = SharedPref.getName(context  ),
        userName = SharedPref.getUserName(context  ),
           imageUrl = SharedPref. getImage(context )
    )
    if(firebaseUser != null)
      userViewModel.fetchThreads(firebaseUser!!.uid)



    LaunchedEffect(firebaseUser){
        if(firebaseUser == null){
            navHostController.navigate(Routes.Login.routes){
                popUpTo(navHostController.graph.startDestinationId)
                launchSingleTop= true
            }


        }
    }


    LazyColumn(){
        item {
            ConstraintLayout (modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)){

                val ( text,logo,userName,bio,followers,following,button
                ) = createRefs()

                Text(text = SharedPref.getName(context), style = TextStyle(
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 24.sp),modifier=Modifier.constrainAs(text){
                    top.linkTo(parent.top)
                    start.linkTo(parent.start )
                })
                Image(painter =

                rememberAsyncImagePainter(model = SharedPref.getImage(context)),
                    contentDescription ="close",
                    modifier = Modifier
                        .constrainAs(logo) {
                            top.linkTo(parent.top)
                            end.linkTo(parent.end)


                        }
                        .size(120.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop


                )
                Text(text =
                SharedPref.getUserName(context)
                    ,style = TextStyle(
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 24.sp),modifier=Modifier.constrainAs(userName){
                        top.linkTo(text.bottom)
                        start.linkTo(parent.start )

                    })
                Text(text =
                SharedPref.getBio(context)
                    ,style = TextStyle(
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 24.sp),modifier=Modifier.constrainAs(bio){
                        top.linkTo(userName.bottom)
                        start.linkTo(parent.start )

                    })
                Text(text =
                "${followerList!!.size} Followers"
                    ,style = TextStyle(
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 24.sp),modifier=Modifier.constrainAs(followers){
                        top.linkTo(bio.bottom)
                        start.linkTo(parent.start )

                    })
                Text(text =
                "${followingList!!.size} Following"
                    ,style = TextStyle(
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 24.sp),modifier=Modifier.constrainAs(following){
                        top.linkTo(followers.bottom)
                        start.linkTo(parent.start )

                    })
                ElevatedButton(onClick = {
                    authViewModel.logout()
                }, modifier = Modifier.constrainAs(button){
                    top.linkTo(following.bottom)
                    start.linkTo(following.start)
                }) {
                    Text(text = "Logout")

                }


            }


        }
        items(threads?: emptyList()){pair ->
            Threaditem(thread = pair,
                users = user,
                navHostController = navHostController,
                userId =SharedPref.getUserName(context) )

        }
    }



}