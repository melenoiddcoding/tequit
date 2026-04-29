# Diseño base — Pantalla de inicio TeQuit

## Objetivo
Estandarizar una primera versión visual para que futuros agentes implementen pantallas consistentes en Android (Jetpack Compose).

## Estructura de layout
1. **Contenedor raíz**
   - Fondo: `#F8FAF7`
   - Padding horizontal: `20dp`
   - Padding vertical superior/inferior: `24dp`
2. **Header**
   - Avatar circular `40dp`
   - Separación avatar/título: `12dp`
   - Título: `Inicio`
   - Acción derecha: icono de notificaciones
3. **Tarjeta principal**
   - `Card` blanca
   - Radio: `32dp`
   - Padding interno: `28dp`
   - Elevación suave (`6dp` en Compose)

## Tokens de diseño
### Color
- `background`: `#F8FAF7`
- `primary`: `#00342B`
- `primaryFixed`: `#AFEFDD`
- `onSurface`: `#191C1B`
- `onSurfaceVariant`: `#3F4945`
- `outline`: `#707975`

### Tipografía (v1)
- Título principal: `24sp`, `Bold`
- Título tarjeta: `headlineSmall`
- Cuerpo: `bodyLarge`
- Nota legal/helper: `labelSmall`
- CTA: `17sp`

### Espaciado
- `space-xs`: `12dp`
- `space-sm`: `20dp`
- `space-md`: `24dp`
- `space-lg`: `28dp`

## Componentes
### HomeHeader
- Avatar local placeholder (sin dependencia de red)
- Texto de sección
- `IconButton` para notificaciones

### NotificationActivationCard
- Ícono circular de alerta
- Mensaje principal + descriptivo
- Botón CTA full width de `56dp` de alto
- Texto helper centrado

## Reglas para agentes
1. No usar imágenes remotas para elementos críticos de UI.
2. Reusar tokens de este documento para nuevas pantallas.
3. Si se agrega modo oscuro, definir tokens equivalentes antes de implementar.
4. Mantener componentes en funciones Compose pequeñas y reutilizables.
