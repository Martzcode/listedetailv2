# ListeDetail v2

Application Android d'exercice - Mini-TP 6 « Architecture MVVM » du module **M1 · Développement mobile Kotlin** (ITUniversity).

Android exercise app - Mini-TP 6 "MVVM Architecture" from the **M1 · Kotlin Mobile Development** module (ITUniversity).

---

## 🇫🇷 Français

### À propos

Liste **→** Détail en **Kotlin + Jetpack Compose**. La navigation et les écrans sont complexes, mais ce qui manque est la couche qui porte l'état : le **ViewModel**, complété dans le cadre du TP.

L'app liste des produits d'une coopérative malgache (vanille, café, girofle, litchi, poivre) et permet d'ajouter une quantité au panier depuis l'écran de détail. Le panier **survit** à la rotation de l'écran : l'état vit dans le ViewModel, pas dans l'écran.

### Objectifs pédagogiques

- Compléter un ViewModel : porter l'état (StateFlow) et traiter un événement.
- Vérifier que l'état survit à la rotation (cycle de vie de l'Activity, séance 3).
- Comprendre le flux unidirectionnel : l'état descend, les événements remontent.
- Casser volontairement le flux pour constater la différence.

### Technologies

- Kotlin 2.0 · Jetpack Compose (Material 3)
- Navigation Compose
- ViewModel + StateFlow (MVVM)
- Gradle 9 · AGP 8.5 · Android SDK 35 (minSdk 24)

### Structure

```
app/src/main/java/mg/itu/listedetail/
├── MainActivity.kt          # navigation + écrans Compose (liste, détail)
└── ProduitsViewModel.kt     # ViewModel partagé : état (_uiState/uiState) + événement
```

### Lancer le projet

1. Ouvrir le projet dans Android Studio (File → Open).
2. Laisser Gradle synchroniser.
3. Lancer `app` sur un émulateur ou un appareil (dans les options développeur : « Installer via USB » activé).

> Java 25 n'est pas supporté par ce build : utilisez un JDK 21 (ex. le JBR d'Android Studio/IntelliJ).

### Documentation

Les supports de cours convertis en Markdown sont dans [`docs/`](docs/) : séances 3, 4 et 6, plus l'énoncé du TP.

---

## 🇬🇧 English

### About

A List **→** Detail app in **Kotlin + Jetpack Compose**. The navigation and screens are complete, but the missing layer is the one that holds the state: the **ViewModel**, completed as part of the exercise.

The app lists products from a Malagasy cooperative (vanilla, coffee, clove, lychee, pepper) and lets you add weight to the cart from the detail screen. The cart **survives** screen rotation: the state lives in the ViewModel, not in the screen.

### Learning objectives

- Complete a ViewModel: hold state (StateFlow) and handle an event.
- Verify that state survives rotation (Activity lifecycle, session 3).
- Understand the unidirectional flow: state flows down, events flow up.
- Deliberately break the flow to see the difference.

### Tech stack

- Kotlin 2.0 · Jetpack Compose (Material 3)
- Navigation Compose
- ViewModel + StateFlow (MVVM)
- Gradle 9 · AGP 8.5 · Android SDK 35 (minSdk 24)

### Structure

```
app/src/main/java/mg/itu/listedetail/
├── MainActivity.kt          # navigation + Compose screens (list, detail)
└── ProduitsViewModel.kt     # shared ViewModel: state (_uiState/uiState) + event
```

### Run the project

1. Open the project in Android Studio (File → Open).
2. Let Gradle sync.
3. Run `app` on an emulator or device (Developer options: "Install via USB" enabled).

> Java 25 is not supported by this build: use a JDK 21 (e.g. the Android Studio/IntelliJ JBR).

### Documentation

Course materials converted to Markdown live in [`docs/`](docs/): sessions 3, 4 and 6, plus the exercise statement.

---

## Licence / License

Ce projet est sous licence **GNU AGPL v3** - voir [`LICENSE`](LICENSE).