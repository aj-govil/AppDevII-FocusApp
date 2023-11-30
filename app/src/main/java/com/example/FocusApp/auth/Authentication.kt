package com.example.FocusApp.auth

import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth

fun signIn(auth: FirebaseAuth, email: String, password: String, navController: NavController){
    auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {

                val user = auth.currentUser?.email?.let { User(it) }

                navController.navigate("MainScreenRoute")
            } else {
                navController.navigate("SignUpScreenRoute")
            }
        }
}

fun signUp(auth: FirebaseAuth, email: String, password: String, navController: NavController){
//    auth.createUserWithEmailAndPassword(email, password)
//        .addOnCompleteListener { task ->
//            if (task.isSuccessful) {
//                val user = auth.currentUser?.email?.let { User(it) }
//
//                navController.navigate("MainScreenRoute")
//            } else {
//                navController.navigate("SignUpScreenRoute")
//            }
//        }
}

fun signOut(auth: FirebaseAuth, navController: NavController){
    auth.signOut()

//    navController.navigate("SignInScreenRoute")
}