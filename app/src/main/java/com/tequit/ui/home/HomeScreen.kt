package com.tequit.ui.home

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.AddAlert
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.tequit.R
import com.tequit.ui.theme.TeQuitTheme

private val Background = Color(0xFFF8FAF7)
private val Primary = Color(0xFF00342B)
private val PrimaryFixed = Color(0xFFAFEFDD)
private val OnSurface = Color(0xFF191C1B)
private val OnSurfaceVariant = Color(0xFF3F4945)
private val Outline = Color(0xFF707975)
private val Success = Color(0xFF1B6D24)

@Composable
fun HomeScreen() {
    val context = LocalContext.current
    var notificationsEnabled by remember { mutableStateOf(areNotificationsGranted(context)) }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        notificationsEnabled = granted
    }

    fun requestNotifications() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            notificationsEnabled = true
            return
        }
        permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .padding(horizontal = 20.dp, vertical = 24.dp)
    ) {
        HomeHeader()
        Spacer(modifier = Modifier.height(24.dp))
        NotificationActivationCard(
            notificationsEnabled = notificationsEnabled,
            onEnableNotifications = ::requestNotifications
        )
    }
}

private fun areNotificationsGranted(context: Context): Boolean {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return true
    return ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.POST_NOTIFICATIONS
    ) == PackageManager.PERMISSION_GRANTED
}

@Composable
private fun HomeHeader() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.profile_placeholder),
                contentDescription = "Perfil de usuario",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.size(12.dp))
            Text("Hola, Tequit", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Primary)
        }
        IconButton(onClick = { }) {
            Icon(
                imageVector = Icons.Outlined.Notifications,
                contentDescription = "Notificaciones",
                tint = Primary
            )
        }
    }
}

@Composable
private fun NotificationActivationCard(
    notificationsEnabled: Boolean,
    onEnableNotifications: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(32.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (notificationsEnabled) {
                StatusChip(text = "Activo")
                Spacer(modifier = Modifier.height(16.dp))
            }

            Box(contentAlignment = Alignment.BottomEnd) {
                Box(
                    modifier = Modifier
                        .size(96.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .background(PrimaryFixed),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.AddAlert,
                        contentDescription = null,
                        tint = Primary,
                        modifier = Modifier.size(46.dp)
                    )
                }
                if (notificationsEnabled) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(Success),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Filled.Check, contentDescription = null, tint = Color.White)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = if (notificationsEnabled) "Notificaciones activadas" else "Activa las notificaciones",
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center,
                color = OnSurface
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = if (notificationsEnabled) {
                    "Listo. Ahora solo espera los avisos importantes."
                } else {
                    "Te avisaremos cuando haya movimientos relevantes del tren."
                },
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                color = OnSurfaceVariant
            )

            Spacer(modifier = Modifier.height(24.dp))
            if (!notificationsEnabled) {
                Button(
                    onClick = onEnableNotifications,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Primary,
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "Activar notificaciones", fontSize = 17.sp)
                }

                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Puedes cambiar este permiso después desde la configuración de tu teléfono.",
                    style = MaterialTheme.typography.labelSmall,
                    textAlign = TextAlign.Center,
                    color = Outline
                )
            }
        }
    }
}

@Composable
private fun StatusChip(text: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(999.dp))
            .background(Color(0x4DA0F399))
            .padding(horizontal = 16.dp, vertical = 6.dp)
    ) {
        Text(text = text, color = Success, style = MaterialTheme.typography.labelMedium)
    }
}

@Preview(showBackground = true)
@Composable
private fun HomePreview() {
    TeQuitTheme {
        HomeScreen()
    }
}
