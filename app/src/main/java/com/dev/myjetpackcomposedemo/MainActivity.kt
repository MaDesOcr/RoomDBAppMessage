package com.dev.myjetpackcomposedemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dev.myjetpackcomposedemo.ui.theme.MyJetpackComposeDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyJetpackComposeDemoTheme {
                Messages()
            }
        }
    }
}

@Composable
fun Messages() {
    val vm: MessageViewModel = viewModel()
    val liste by vm.allMessages.collectAsState()
    var texte by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(40.dp).fillMaxWidth()) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(40.dp).fillMaxWidth()) {
                Text(
                    text = "App de messages",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Use LazyColumn for better performance with lists
                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    items(liste) { message ->
                        Card(message)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }

        Box() {
            Column {
                OutlinedTextField(
                    value = texte,
                    onValueChange = { newText ->
                        texte = newText
                    },
                    placeholder = {
                        Text("Entrer votre message")
                    },
                    label = {
                        Text(text = "Message")
                    }
                )

                Button(
                    onClick = {
                        if (texte.isNotBlank()) {
                            vm.addMessage(texte)
                            texte = ""
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text("Valider")
                }
            }
        }
    }
}

@Composable
fun Card(messageData: MessageData) {
    Box(modifier = Modifier.background(color = Color.Gray)) {
        Row(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
            Image(
                painter = painterResource(messageData.idAvatar),
                contentDescription = "Contact profile picture",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column {
                Text(text = messageData.pseudo, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = messageData.contenuMessage)
            }
        }
    }
}


@Preview
@Composable
fun CardPreview() {
    MyJetpackComposeDemoTheme {
        Card(
            MessageData(
                pseudo = "Test User",
                contenuMessage = "This is a test message",
                idAvatar = android.R.drawable.ic_menu_gallery
            )
        )
    }
}