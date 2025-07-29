# 📱 SocialMovie

SocialMovie es una aplicación Android para descubrir, guardar y compartir información sobre películas.

---

## ✅ Requisitos previos

Este proyecto usa el **plugin de Gradle para Android**, por lo que necesitas:

- **Android Studio**: Electric Eel (2022.1.1) o superior
- **Java**: JDK 11
- **Kotlin**: Asegúrate de que el complemento de Kotlin esté habilitado en Android Studio
- **Android SDK** con nivel de API **21 o superior**
- Un **emulador** o **dispositivo físico** compatible
- Configura la variable de entorno `ANDROID_HOME` apuntando al directorio del SDK

Para compilar en modo debug:

```bash
./gradlew assembleDebug
```

---

## 🔑 Configuración de claves y APIs

Esta app utiliza servicios externos como **Firebase**, **TMDB (The Movie Database)** y **Facebook Login**, por lo que requiere claves privadas.

### 1️⃣ Firebase

1. Crea un proyecto en [Firebase Console](https://console.firebase.google.com/)
2. Habilita los servicios requeridos (Auth, Firestore, etc.)
3. Descarga el archivo `google-services.json`
4. Colócalo en el directorio `app/` del proyecto

### 2️⃣ TMDB y otras claves API

Debes proveer las claves en un archivo de configuración o como variables de entorno. Hay dos opciones:

#### ✅ Opción 1: Usar `local.properties`

Agrega estas líneas:

```properties
TMDB_API_KEY=TU_CLAVE_DE_TMDB
FACEBOOK_APP_ID=TU_FACEBOOK_APP_ID
FACEBOOK_CLIENT_TOKEN=TU_FACEBOOK_CLIENT_TOKEN
```

#### ✅ Opción 2: Usar variables de entorno

```bash
export TMDB_API_KEY=TU_CLAVE_DE_TMDB
export FACEBOOK_APP_ID=TU_FACEBOOK_APP_ID
export FACEBOOK_CLIENT_TOKEN=TU_FACEBOOK_CLIENT_TOKEN
```

El script de Gradle leerá estos valores y los expondrá a la app mediante `BuildConfig` y recursos generados automáticamente.

---

## 🚀 Ejecución

Una vez configurado todo:

1. Sincroniza el proyecto en Android Studio
2. Conecta un dispositivo o inicia un emulador
3. Compila y ejecuta con:

```bash
./gradlew installDebug
```

---

## 🛠 Tecnologías utilizadas

- **Kotlin** como lenguaje principal
- **Android Jetpack** (ViewModel, LiveData, Navigation)
- **Firebase** (Auth, Firestore, Analytics)
- **Retrofit** para llamadas HTTP
- **Glide** para carga de imágenes
- **TMDB API** como proveedor de datos de películas

---

## 📂 Estructura del proyecto (opcional)

```
socialmovie/
 ├── app/
 │   ├── src/
 │   │   ├── main/
 │   │   │   ├── java/... (código fuente)
 │   │   │   ├── res/... (recursos)
 │   │   │   └── AndroidManifest.xml
 │   └── build.gradle
 ├── gradle.properties
 ├── local.properties (opcional para claves API)
 ├── settings.gradle
 └── README.md
```

---

## 📄 Licencia

Este proyecto está bajo la **licencia MIT**.
