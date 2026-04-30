# Guía de setup — Herramientas externas (Supabase, Firebase/FCM)

Esta guía define **qué se necesita** y **cómo configurar** las dependencias externas para arrancar Fase 1 sin subir secretos al repo.

## 1) Requisitos previos

- Cuenta en GitHub
- Android Studio (versión estable)
- Proyecto Android ya compilando localmente
- Cuenta en Supabase
- Cuenta en Firebase (Google)
- (Opcional recomendado) Supabase CLI

---

## 2) Supabase — Setup inicial

### 2.1 Crear proyecto

1. Crear proyecto en Supabase.
2. Guardar:
   - `SUPABASE_URL`
   - `SUPABASE_ANON_KEY`
3. **No** usar `service_role` en Android.

### 2.2 Auth

1. Habilitar Email/Password en Authentication > Providers.
2. Crear usuarios de prueba (user/notifier/admin).

### 2.3 Esquema base (Fase 1)

Crear tablas en orden:

1. `profiles`
2. `train_status`
3. `crossings`
4. `train_events`
5. `device_tokens`
6. (adicional sugerido) `alternate_routes`

### 2.4 RLS y políticas

- Activar RLS en todas las tablas sensibles.
- Definir políticas por rol (`user`, `notifier`, `admin`).
- Validar que restricciones funcionen desde backend (no solo UI).

### 2.5 Realtime

- Habilitar Realtime para `train_status`, `crossings`, `train_events`.

---

## 3) Firebase + FCM — Setup inicial

### 3.1 Crear proyecto Firebase

1. Crear proyecto en Firebase Console.
2. Agregar app Android con package name exacto del proyecto (`com.tequit`).
3. Descargar `google-services.json` y colocarlo en `app/google-services.json`.

### 3.2 Configuración Gradle

- Agregar plugin de Google Services.
- Agregar dependencia de Firebase Messaging.
- Sin llaves privadas en código fuente.

### 3.3 Permisos y notificaciones Android

- Mantener permiso `POST_NOTIFICATIONS` (Android 13+).
- Solicitar permiso en runtime (ya iniciado en el proyecto).
- Crear canal de notificación para Android 8+.

### 3.4 Token FCM

- Obtener token al iniciar sesión.
- Guardar/actualizar token en `device_tokens`.
- Implementar refresh de token con `FirebaseMessagingService`.

---

## 4) Secrets y configuración local

Usar variables locales/no versionadas:

- `local.properties` o `gradle.properties` local
- `SUPABASE_URL`
- `SUPABASE_ANON_KEY`

**Nunca** subir:

- `service_role`
- cuentas de servicio Firebase privadas
- llaves privadas

---

## 5) Siguiente paso recomendado (orden práctico)

1. **TQ-F1.0-006**: Crear Supabase + documentar variables locales.
2. **TQ-F1.0-007**: Crear Firebase + integrar `google-services.json` local.
3. **TQ-F1.2-001**: Versionar SQL de `profiles` en carpeta `supabase/`.
4. **TQ-F1.2-002**: Implementar cliente Supabase en Android.
5. **TQ-F1.2-003/004/005/006**: Login real + sesión + roles + protección.
6. **TQ-F1.6-001..006**: Integración FCM completa con token y refresh.
7. **TQ-F1.6-007/008**: Edge Function para push y disparo por evento.

---

## 6) Checklist rápido de validación

- [ ] Android compila localmente.
- [ ] Supabase URL/anon key configuradas localmente.
- [ ] Firebase integrado con `google-services.json` local.
- [ ] Login real funciona con usuario de prueba.
- [ ] Rol redirige a pantalla correcta.
- [ ] Token FCM se guarda en `device_tokens`.
- [ ] Evento creado dispara push al rol correcto.
