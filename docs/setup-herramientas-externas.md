# Guía de setup — Herramientas externas (orden real Fase 1)

> Objetivo: documentar lo que **sí podemos preparar en repo** mientras tú ejecutas configuración en plataformas (Supabase/Firebase).

## Orden práctico acordado

1. Supabase project
2. SQL de tablas
3. RLS básico
4. Usuarios de prueba
5. Conectar Android con Supabase
6. Login + roles
7. Firebase/FCM
8. Token FCM
9. Push por evento

---

## Tarea actual: TQ-F1.0-006 (Supabase)

### Paso 1 — Crear proyecto Supabase

- Crear proyecto: `tequitshit-tren`
- Guardar fuera del repo:
  - `SUPABASE_URL`
  - `SUPABASE_ANON_KEY`
- No usar `service_role` en Android.

### Paso 2 — Auth por correo

- Activar `Authentication > Providers > Email`.
- En desarrollo: `Confirm email = OFF` (temporal).

### Paso 3 — SQL base en repo

Crear estructura:

```text
supabase/
  migrations/
    001_initial_schema.sql
    002_seed_initial_data.sql
    003_rls_policies.sql
```

> El contenido SQL recomendado ya quedó definido en la conversación y se aplicará exactamente en esos archivos.

### Paso 4 — Ejecutar SQL en Supabase

En `SQL Editor`, ejecutar en este orden:

1. `001_initial_schema.sql`
2. `002_seed_initial_data.sql`
3. `003_rls_policies.sql`

Validar tablas en `Table Editor`:

- profiles
- train_status
- crossings
- train_events
- device_tokens
- alternate_routes

### Paso 5 — Usuarios de prueba

Crear en `Authentication > Users`:

- `user@test.com`
- `notifier@test.com`
- `admin@test.com`

Insertar/actualizar `profiles` con rol correspondiente.

### Paso 6 — Variables locales Android

En `local.properties`:

```properties
SUPABASE_URL=https://TU-PROYECTO.supabase.co
SUPABASE_ANON_KEY=TU_ANON_KEY
```

Asegurar `.gitignore` con:

```gitignore
local.properties
app/google-services.json
```

---

## Siguiente después de Supabase

### TQ-F1.2-002

Implementar cliente Supabase en Android (sin `service_role`, con RLS como control principal).

### Luego Firebase/FCM

Solo después de login+roles:

- Registro app Android en Firebase.
- `google-services.json` local en `app/`.
- Integrar FCM token y refresh.
- Push por evento desde backend.

---

## Checklist operativo (para marcar)

### Bloque A — Supabase

- [ ] Crear proyecto Supabase
- [ ] Copiar `SUPABASE_URL`
- [ ] Copiar `SUPABASE_ANON_KEY`
- [ ] Activar Email/Password
- [ ] Desactivar confirm email temporalmente
- [ ] Ejecutar `001_initial_schema.sql`
- [ ] Ejecutar `002_seed_initial_data.sql`
- [ ] Ejecutar `003_rls_policies.sql`
- [ ] Crear `user@test.com`
- [ ] Crear `notifier@test.com`
- [ ] Crear `admin@test.com`
- [ ] Insertar `profiles` con roles
- [ ] Confirmar 6 cruces seed en `crossings`

### Bloque B — Android + Auth

- [ ] Configurar `SUPABASE_URL` en local
- [ ] Configurar `SUPABASE_ANON_KEY` en local
- [ ] Implementar cliente Supabase Android
- [ ] Login email/password funcionando
- [ ] Redirección por rol funcionando
- [ ] Restricción de pantallas por rol funcionando

### Bloque C — Firebase/FCM (después)

- [ ] Crear proyecto Firebase
- [ ] Registrar app Android con package correcto
- [ ] Agregar `app/google-services.json` local
- [ ] Obtener token FCM
- [ ] Guardar token en `device_tokens`
- [ ] Implementar refresh de token
- [ ] Envío de push por evento

---

## Lo que necesito de ti antes de seguir (sin inventar)

Comparte estos datos cuando los tengas para avanzar con código real:

1. `SUPABASE_URL` (puedes ocultar parte si prefieres).
2. Confirmación de que `SUPABASE_ANON_KEY` ya está en tu `local.properties`.
3. Confirmación de que ejecutaste migraciones `001/002/003` en Supabase.
4. Confirmación de creación de usuarios de prueba (`user`, `notifier`, `admin`).
5. Package final Android a usar en Firebase (`com.tequit` u otro exacto).

Con eso, el siguiente ticket implementable en repo es: **TQ-F1.2-002 (cliente Supabase Android)**.
