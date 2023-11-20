package com.maingiexe.socialapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.maingiexe.socialapp.screens.AddThread

import com.maingiexe.socialapp.screens.BottomNav
import com.maingiexe.socialapp.screens.Home
import com.maingiexe.socialapp.screens.Login
import com.maingiexe.socialapp.screens.Notification
import com.maingiexe.socialapp.screens.OtherUsers
import com.maingiexe.socialapp.screens.Profile
import com.maingiexe.socialapp.screens.Register
import com.maingiexe.socialapp.screens.Search
import com.maingiexe.socialapp.screens.Splash

@Composable
fun NavGraph(navController :NavHostController) {

    NavHost(navController = navController,
        startDestination = Routes.Splash.routes ){

        composable(Routes.Splash.routes){
            Splash(navController)
        }
        composable(Routes.Home.routes){
            Home(navController)
        }
        composable(Routes.Notification.routes){
            Notification()
        }
        composable(Routes.Search.routes){
            Search(navController)
        }
        composable(Routes.AddThread.routes){
            AddThread(navController)
        }
        composable(Routes.Profile.routes){
            Profile(navController)
        }
        composable(Routes.BottomNav.routes){
            BottomNav(navController)
        }
        composable(Routes.Login.routes){
            Login(navController)
        }
        composable(Routes.Register.routes){
            Register(navController)
        }
        composable(Routes.OtherUsers.routes){
            val data = it.arguments!!.getString("data")
            OtherUsers(navController,data!!)
        }

    }

}