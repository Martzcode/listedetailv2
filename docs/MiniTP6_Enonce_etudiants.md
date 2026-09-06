ITUniversity — Module M1 · Développement mobile Kotlin — Séance 6

MINI-TP 6 — ARCHITECTURE MVVM

« Compléter la couche manquante »

ViewModel, StateFlow, flux unidirectionnel — travail individuel, sur le projet fourni « ListeDetail v2 »

# 1. Objectifs

- Compléter un ViewModel : porter l'état (StateFlow) et traiter un événement.
- Vérifier que l'état survit à la rotation — la réponse à la question ouverte en séance 3.
- Casser volontairement le flux unidirectionnel, et constater ce qui se passe.
- Trier les remarques d'une critique IA : pertinente ou non pertinente ici.

# 2. Règles du mini-TP

- Les prédictions se remplissent dans le modèle de l'étape 1 AVANT tout lancement de l'application.
- Pendant les étapes 1 à 3, aucune assistance IA — complétion IA de l’IDE désactivée.
- La navigation et les écrans sont COMPLETS : seul ProduitsViewModel.kt est à modifier (deux TODO), plus le bloc « triche » de l'étape 3.

ÉTAPE 1 — LIRE ET PRÉDIRE (SUR PAPIER, AVANT TOUT LANCEMENT)

Téléchargez MiniTP6_ListeDetailV2.zip depuis le dossier Drive de la séance, décompressez-le, ouvrez le projet dans Android Studio (File → Open) et LISEZ les deux fichiers : MainActivity.kt (les écrans observent l'état) et ProduitsViewModel.kt (les deux TODO). Puis remplissez le modèle :

| Prédiction | Votre réponse |
|---|---|
| P1 — AVANT les TODO : que fait le bouton « Ajouter 1 kg au panier » de l’écran de détail, et pourquoi ? (regardez la ligne provisoire du TODO 1) | Le bouton ne fait **rien**. La fonction `ajouterAuPanier()` est vide : aucun corps d'instruction, donc l'état n'est jamais modifié. Le panier affiche toujours **0 kg** (valeur par défaut de `EtatUi`). Par ailleurs, la ligne provisoire `val uiState: StateFlow<EtatUi> = MutableStateFlow(...)` rend le flow en **lecture seule** côté UI (type `StateFlow`) — il n'existe pas de `_uiState` privé mutable pour appeler `_uiState.update { ... }`. Même si le corps de la fonction existait, sans le duo `_uiState` / `uiState` (TODO 1), impossible de modifier l'état de façon immuable. En résumé : l'événement n'est pas traité (TODO 2 absent) ET le mécanisme d'écriture est absent (TODO 1 absent). L'UI observe via `collectAsState()` (séance 4 : l'état change → recomposition), mais ici l'état ne change jamais. |
| P2 — APRÈS les TODO : vous ajoutez 3 kg au panier, puis vous TOURNEZ L’ÉCRAN. Qu’affiche le panier, et pourquoi ? (souvenez-vous des séances 3 et 4) | Le panier affiche **3 kg** et après rotation il affiche **toujours 3 kg**. Après le TODO 1, `_uiState` (privé, `MutableStateFlow`) permet au ViewModel d'émettre de nouveaux états ; après le TODO 2, `ajouterAuPanier()` appelle `_uiState.update { etat.copy(poidsPanierKg = ...) }` — l'état change, le StateFlow émet, les écrans qui observent via `collectAsState()` se recomposent (séance 4 : recomposition automatique). À la rotation, le système DÉTRUIT puis RECRÉE l'Activity (`onPause` → `onStop` → `onDestroy` → `onCreate` — séance 3), mais le **ViewModel survit** : le système le conserve à travers la recréation. L'état `poidsPanierKg = 3` est donc préservé. C'est exactement la réponse à la question ouverte en séance 3. Contrairement à `remember { mutableStateOf(0) }` (séance 4) qui ne survit qu'aux recompositions mais PAS à la rotation, le ViewModel assure la vraie survie de l'état. |

Ne lancez rien avant d'avoir rempli les deux lignes du modèle.

ÉTAPE 2 — LES DEUX TODO DU VIEWMODEL

- TODO 1 — l'état : remplacez la ligne provisoire par le duo _uiState (privé, mutable) / uiState (public, lecture seule). Le modèle exact est en commentaire dans le fichier et sur la diapositive « Le ViewModel en code ».
- TODO 2 — l'événement : faites évoluer l'état dans ajouterAuPanier(), avec update et copy.
- Vérifiez le circuit complet : depuis le détail, ajoutez 3 kg ; revenez à la liste — le panier affiche bien 3 kg (preuve que le ViewModel est PARTAGÉ entre les deux écrans) ; puis TOURNEZ L'ÉCRAN — le panier survit.

Question de contrôle (une phrase sur cette feuille) : pourquoi uiState est-il déclaré StateFlow et non MutableStateFlow ? Que cela empêche-t-il l’interface de faire ?

ÉTAPE 3 — CASSER LE FLUX POUR COMPRENDRE

Dans MainActivity.kt, décommentez la variable poidsTriche (en haut du fichier) ET le bloc « triche » de EcranDetail (le second bouton). Cette variable vit HORS du circuit état → observation → recomposition.

- Cliquez plusieurs fois sur le bouton « Ajouter 1 kg (hors circuit) ». Que fait l'affichage ? Notez-le.
- Maintenant, TOURNEZ L'ÉCRAN. Que se passe-t-il pour la valeur affichée ? Notez-le.
- Expliquez les deux observations en trois lignes sur cette feuille, avec le vocabulaire du cours : émission, observation, recomposition, durée de vie.

VOIE OUVERTE — UNE TÂCHE IA UNIQUE : TRIER UNE CRITIQUE

- Soumettez votre ProduitsViewModel.kt complété à l'IA de votre choix. Prompt suggéré : « Fais une critique de ce ViewModel Kotlin/Compose : liste tes remarques, ne réécris pas tout. »
- Triez CHAQUE remarque en deux colonnes sur la feuille : pertinente ICI / non pertinente ICI — avec un mot de justification. L'IA parlera probablement d'injection de dépendances, de Repository, de tests unitaires : ce sont des sujets réels, mais hors du périmètre de ce module — le dire en le justifiant est exactement l'exercice. Vous recopierez la synthèse de ce tri dans le champ « JOURNAL-IA » du formulaire.

# 3. Livrables (formulaire « S6 · Dépôt des livrables »)

Tout se dépose en fin de séance dans le formulaire unique « S6 · Dépôt des livrables » — le lien est affiché en séance et dans le dossier Drive de la séance :

- cette feuille remplie (prédictions, question de contrôle, observations de l’étape 3, tri des remarques), en photo ou PDF ;
- le projet avec le ViewModel complété, url GIT ;
- la synthèse de votre tri des remarques IA, recopiée dans le champ « JOURNAL-IA » du formulaire.

Ces dépôts servent au suivi de votre progression.

