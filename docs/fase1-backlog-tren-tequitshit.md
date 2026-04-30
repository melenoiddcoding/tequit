# Backlog — Fase 1 Tren / Tequitshit Android

## Contexto

Este backlog define el alcance de la Fase 1 del módulo Tren para una app nativa Android.

La app se construirá con:

- Android Studio
- Kotlin
- Jetpack Compose
- MVVM
- GitHub repo
- Codex Cloud trabajando sobre el repo
- Supabase para Auth, base de datos, roles, eventos y realtime
- Firebase Cloud Messaging para notificaciones push

La Fase 1 no busca construir toda la super-app. Busca validar el flujo principal:

> Un notificador confiable crea un evento manual → el evento se guarda → el usuario correcto ve el cambio → el usuario recibe alerta/push.

---

## Reglas generales de Fase 1

### Roles de usuario

#### Usuario básico

Puede:

- iniciar sesión
- ver estado actual del tren
- ver estado de cruces
- recibir alertas internas
- recibir push notifications

No puede:

- crear eventos
- editar estados
- acceder al panel notificador
- acceder al panel admin

#### Notificador

Puede:

- iniciar sesión
- ver estado actual del tren
- crear eventos operativos
- actualizar estado del tren
- actualizar estado de cruces
- agregar nota opcional a eventos

No puede:

- cambiar roles
- borrar eventos
- editar historial
- acceder a funciones administrativas avanzadas

#### Admin

Puede:

- iniciar sesión
- ver usuarios
- ver roles
- ver historial de eventos
- autorizar o cambiar roles de usuarios

No puede en Fase 1:

- borrar eventos
- editar eventos históricos
- acceder a analítica avanzada

### Eventos permitidos en Fase 1

Solo se permiten estos eventos:

- `train_entering`
- `train_leaving`
- `maneuver_started`
- `maneuver_finished`
- `crossing_occupied`
- `crossing_free`

No se deben agregar eventos nuevos sin moverlos a una fase posterior.

### Fuera de alcance en Fase 1

No construir en esta fase:

- mapas
- GPS del tren
- sensores
- pagos
- chat
- perfiles sociales
- reportes ciudadanos
- múltiples ciudades
- iOS
- panel web separado
- publicación en Google Play
- estadísticas avanzadas
- gamificación
- historial público complejo

---

## Estructura recomendada de backlog

Cada item debe tener:

- ID
- Prioridad
- Tipo
- Descripción
- Criterios de aceptación
- Dependencias
- Definition of Done

Prioridades:

- P0: bloqueante para Fase 1
- P1: necesario para cerrar Fase 1 correctamente
- P2: mejora útil, pero no bloqueante

Tipos:

- Setup
- Feature
- Backend
- Android
- Security
- QA
- Docs

---

## Fase 1.0 — Preparación del proyecto

### Objetivo

Dejar listo el entorno base para que Codex Cloud pueda trabajar sobre el repo y Android Studio pueda compilar localmente.

### Must be done

- Repo creado en GitHub
- Proyecto Android nativo creado
- Repo clonado localmente en la computadora
- Android Studio puede abrir el proyecto
- Codex Cloud conectado al repo
- Firebase project creado
- Supabase project creado
- README inicial creado
- AGENTS.md creado
- `.gitignore` correcto
- Sin secretos dentro del repo

### Backlog

- **TQ-F1.0-001 — Crear repo GitHub** (P0, Setup)
- **TQ-F1.0-002 — Crear proyecto Android nativo** (P0, Android)
- **TQ-F1.0-003 — Agregar estructura base de carpetas** (P0, Android)
- **TQ-F1.0-004 — Crear AGENTS.md para Codex** (P0, Docs)
- **TQ-F1.0-005 — Crear README inicial** (P0, Docs)
- **TQ-F1.0-006 — Configurar Supabase project** (P0, Backend)
- **TQ-F1.0-007 — Configurar Firebase project** (P0, Backend)

> Nota: el detalle completo de criterios/DoD para cada item está en el texto fuente compartido en el ticket de trabajo y en el plan operativo de este repositorio.

---

## Fases 1.1 a 1.9 (resumen operativo)

### Fase 1.1 — App base nativa y navegación

- Tema base Material 3
- Navegación entre `LoginScreen`, `UserHomeScreen`, `NotifierPanelScreen`, `AdminScreen`
- Placeholders iniciales
- MVVM base

### Fase 1.2 — Auth y roles con Supabase

- Login real email/password
- Sesión persistente
- Tabla `profiles`
- Redirección por rol
- Bloqueo de pantallas por rol

### Fase 1.3 — Estado del tren y cruces

- Tablas `train_status` y `crossings`
- Lectura en UserHome
- Actualización desde panel notificador
- Usuario responsable y timestamp

### Fase 1.4 — Eventos e historial

- Tabla `train_events`
- Evento por acción operativa
- Historial visible para admin

### Fase 1.5 — Realtime y alertas internas

- Suscripción realtime a estado del tren y cruces
- Alertas internas por evento
- Mapeo de mensajes de evento

### Fase 1.6 — Push notifications con Firebase

- Integración Firebase/FCM Android
- Permiso Android 13+
- Token FCM
- Tabla `device_tokens`
- Edge Function para envío push

### Fase 1.7 — Admin básico

- Ver usuarios y roles
- Cambiar roles
- Ver historial
- Acceso protegido

### Fase 1.8 — Seguridad, RLS y restricciones

- RLS en tablas sensibles
- Políticas por rol
- Revisión de secretos

### Fase 1.9 — APK, pruebas y cierre beta interna

- APK debug
- QA end-to-end por rol
- Push en dispositivo físico
- README final Fase 1

---

## Catálogo operativo inicial

### Cruces ferroviarios iniciales

- Blvd Gobernadores
- Av Principal
- Colosio
- Quevedeño
- Av Guadalajara
- El Koa

### Puntos de entrada/salida iniciales

- La Cantera
- Pantanal

### Regla clave

No mezclar dirección con estado operativo.

- Dirección: Entrando / Saliendo
- Estado operativo: Pasando / Bloqueando / Maniobrando

---

## Rutas alternas sugeridas (Fase 1)

- Sugerencia manual por cruce afectado
- Sin mapa, sin GPS, sin cálculo de tráfico
- Mostrar máximo 2 sugerencias
- Mostrar solo si el cruce no está libre

### Modelo recomendado

```sql
alternate_routes
- id uuid primary key
- crossing_id uuid not null
- title text not null
- description text not null
- priority int default 1
- is_active boolean default true
- created_at timestamp with time zone default now()
```

### Estados de cruce recomendados

- `free`
- `passing`
- `blocked`
- `maneuvering`

### Ajuste de eventos de cruce

- `crossing_passing`
- `crossing_blocked`
- `crossing_maneuvering`
- `crossing_free`

---

## Checklist final de cierre Fase 1

- [ ] Repo conectado a Codex Cloud
- [ ] Proyecto abre en Android Studio
- [ ] App compila sin errores
- [ ] Login real funciona
- [ ] Sesión persistente funciona
- [ ] Roles funcionan
- [ ] Usuario básico no puede crear eventos
- [ ] Notificador puede crear eventos
- [ ] Admin puede ver usuarios
- [ ] Admin puede cambiar roles
- [ ] Admin puede ver eventos
- [ ] Estado del tren se guarda
- [ ] Estado de cruces se guarda
- [ ] Eventos quedan en historial
- [ ] App muestra alerta interna
- [ ] Firebase token se guarda
- [ ] Push llega a celular Android real
- [ ] APK debug se puede instalar
- [ ] README explica cómo correr local
- [ ] README explica cómo generar APK
- [ ] No hay secretos subidos al repo
- [ ] No hay features fuera de alcance

---

## Nota final de prioridad

No avanzar a nuevas funciones hasta que el flujo principal funcione de punta a punta:

> Notificador crea evento → evento se guarda → usuario ve alerta interna → usuario recibe push → admin puede auditar el evento.
