package com.maingiexe.socialapp.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.maingiexe.socialapp.item_view.Threaditem
import com.maingiexe.socialapp.utils.SharedPref
import com.maingiexe.socialapp.viewmodel.HomeViewModel

@Composable
fun Home(navHostController: NavHostController) {
    val context  = LocalContext.current
    val  homeViewModel:HomeViewModel = viewModel()
    val threadAndUsers by homeViewModel.threadsAndUsers.observeAsState(null)

    LazyColumn{
        items(threadAndUsers?: emptyList()){pairs->
            Threaditem(thread= pairs.first,
                users =  pairs.second,
                navHostController,
                FirebaseAuth.getInstance().currentUser!!.uid)
        }
    }

}

@Preview(showBackground = true)
@Composable
fun ShowHome() {
    Home(rememberNavController())

}