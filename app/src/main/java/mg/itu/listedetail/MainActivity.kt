package mg.itu.listedetail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

/**
 * Mini-TP 6 — « Compléter la couche manquante »
 *
 * La navigation (séance 5) est COMPLÈTE. Les écrans sont COMPLETS.
 * Il manque la couche qui porte l'état : le ViewModel — deux TODO
 * dans ProduitsViewModel.kt. Rien à modifier dans ce fichier,
 * sauf le bloc « CASSER LE FLUX » de l'étape 3 (à décommenter).
 */

data class Produit(
    val id: Int,
    val nom: String,
    val origine: String,
    val prixKg: Double?,
)

val produits = listOf(
    Produit(1, "Vanille Bourbon", "Sambava", 250_000.0),
    Produit(2, "Café Arabica", "Itasy", 12_000.0),
    Produit(3, "Girofle", "Analanjirofo", 38_000.0),
    Produit(4, "Litchi", "Toamasina", null),
    Produit(5, "Poivre noir", "Vatovavy", 45_000.0),
)

// ÉTAPE 3 — CASSER LE FLUX : cette variable vit HORS du circuit
// état -> observation -> recomposition. Décommentez-la avec le bloc
// « triche » de EcranDetail, et observez.
var poidsTriche = 0

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(Modifier.fillMaxSize()) {
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    // UN SEUL ViewModel, partagé par les deux écrans : l'état vit ici,
    // au-dessus de la navigation — il survit aux allers-retours ET à la rotation.
    val viewModel: ProduitsViewModel = viewModel()

    NavHost(navController = navController, startDestination = "liste") {

        composable("liste") {
            EcranListe(
                viewModel = viewModel,
                onProduitClick = { produitId ->
                    navController.navigate("detail/$produitId")
                }
            )
        }

        composable("detail/{produitId}") { backStackEntry ->
            val id = backStackEntry.arguments
                ?.getString("produitId")?.toIntOrNull()
            val etat by viewModel.uiState.collectAsState()
            val produit = etat.produits.find { it.id == id }
            if (produit != null) {
                EcranDetail(
                    produit = produit,
                    viewModel = viewModel,
                    onRetour = { navController.popBackStack() }
                )
            }
        }
    }
}

// ---------------------------------------------------------------------------
// LES ÉCRANS — l'état DESCEND (uiState), les événements REMONTENT (fonctions)
// ---------------------------------------------------------------------------

@Composable
fun EcranListe(
    viewModel: ProduitsViewModel,
    onProduitClick: (Int) -> Unit,
) {
    // L'écran OBSERVE l'état : chaque émission du StateFlow le recompose.
    val etat by viewModel.uiState.collectAsState()

    Column(Modifier.padding(16.dp)) {
        Text("Produits de la coopérative", style = MaterialTheme.typography.headlineSmall)
        Text(
            "Panier : ${etat.poidsPanierKg} kg",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
        )
        Spacer(Modifier.height(12.dp))
        LazyColumn {
            items(etat.produits) { p ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                        .clickable { onProduitClick(p.id) },
                ) {
                    Column(Modifier.padding(14.dp)) {
                        Text(p.nom, style = MaterialTheme.typography.titleMedium)
                        Text(
                            p.prixKg?.let { "${formatAriary(it)} / kg" } ?: "prix non fixé",
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun EcranDetail(
    produit: Produit,
    viewModel: ProduitsViewModel,
    onRetour: () -> Unit,
) {
    val etat by viewModel.uiState.collectAsState()

    Column(Modifier.padding(24.dp)) {
        Text(produit.nom, style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(8.dp))
        Text("Origine : ${produit.origine}", style = MaterialTheme.typography.bodyLarge)
        Text(
            produit.prixKg?.let { "Prix : ${formatAriary(it)} / kg" } ?: "Prix non fixé",
            style = MaterialTheme.typography.bodyLarge,
        )
        Spacer(Modifier.height(16.dp))

        Text("Panier : ${etat.poidsPanierKg} kg")
        Button(onClick = { viewModel.ajouterAuPanier(1) }) {
            Text("Ajouter 1 kg au panier")
        }

        // ÉTAPE 3 — CASSER LE FLUX : décommentez ce bloc (et la variable
        // poidsTriche en haut du fichier), puis cliquez sur ce bouton.
        // Qu'affiche le texte ? Pourquoi ? (Voir l'énoncé.)
        Spacer(Modifier.height(16.dp))
        Text("Triche : $poidsTriche kg (hors circuit)")
        Button(onClick = { poidsTriche++ }) {
            Text("Ajouter 1 kg (hors circuit)")
        }

        Spacer(Modifier.height(24.dp))
        Button(onClick = onRetour) { Text("Retour à la liste") }
    }
}

/** Formate un montant en ariary : 1250000.0 -> "1 250 000 Ar". */
fun formatAriary(montant: Double): String {
    val entier = montant.toLong().toString()
    val groupes = entier.reversed().chunked(3).joinToString(" ").reversed()
    return "$groupes Ar"
}
