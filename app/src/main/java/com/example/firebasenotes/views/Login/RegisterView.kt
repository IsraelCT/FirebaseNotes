package com.example.firebasenotes.views.Login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.firebasenotes.components.Alert
import com.example.firebasenotes.viewModels.LoginViewmodel

@Composable
fun RegisterView(navController: NavController, loginVM: LoginViewmodel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var passwordEnable by remember { mutableStateOf(true) }
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            maxLines = 1,
            singleLine = true,
            label = { Text(text = "username") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 30.dp, end = 30.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            maxLines = 1,
            singleLine = true,
            label = { Text(text = "Escribe tu correo") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 30.dp, end = 30.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            maxLines = 1,
            singleLine = true,
            label = { Text(text = "Escribe tu contraseña") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 30.dp, end = 30.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = if (passwordEnable) PasswordVisualTransformation() else VisualTransformation.None

        )
        Spacer(Modifier.height(20.dp))
        Button(onClick = {
            loginVM.creteUser(email,password,username){
                navController.navigate("Home")
            }
        }, modifier = Modifier
            .fillMaxWidth()
            .padding(start = 30.dp, end = 30.dp)) {
            Text("Registrarse")
        }
        if (loginVM.showAlert) {
            Alert(
                title = "Alerta",
                message = "Usuario no creado",
                confirmText = "Aceptar",
                onConfirmClick = { loginVM.closeAlert() }) {

            }
        }

    }
}