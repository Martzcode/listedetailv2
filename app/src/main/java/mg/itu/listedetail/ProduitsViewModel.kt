package mg.itu.listedetail

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

/**
 * Mini-TP 6 — « Compléter la couche manquante »
 *
 * Ce ViewModel est la couche manquante de l'application : il PORTE l'état.
 * Contrairement au remember de la séance 4, il SURVIT à la rotation —
 * c'est la réponse promise depuis la séance 3.
 *
 * Deux TODO, à faire dans l'ordre. Rien d'autre n'est à modifier ici.
 */

/** L'état complet de l'interface, en une seule donnée immuable. */
data class EtatUi(
    val produits: List<Produit> = emptyList(),
    val poidsPanierKg: Int = 0,
)

class ProduitsViewModel : ViewModel() {

    // -----------------------------------------------------------------------
    // TODO 1 — L'ÉTAT : remplacez la ligne provisoire ci-dessous par le duo :
    //
    //   private val _uiState = MutableStateFlow(EtatUi(produits = produits))
    //   val uiState: StateFlow<EtatUi> = _uiState
    //
    // Pourquoi un duo ? Le _uiState privé est MUTABLE : seul le ViewModel
    // a le droit d'écrire. Le uiState public est en LECTURE SEULE : l'UI
    // ne fait qu'observer. C'est le flux unidirectionnel du cours.
    // -----------------------------------------------------------------------
    private val _uiState = MutableStateFlow(EtatUi(produits = produits))   // provisoire : figé
    val uiState: StateFlow<EtatUi> = _uiState

    /** Appelée par l'écran de détail quand l'utilisateur ajoute au panier. */
    fun ajouterAuPanier(poidsKg: Int) {
        // -------------------------------------------------------------------
        // TODO 2 — L'ÉVÉNEMENT (à faire APRÈS le TODO 1) :
        // faites évoluer l'état de façon immuable, avec copy (séance 1) :
        //
        //   _uiState.update { etat ->
        //       etat.copy(poidsPanierKg = etat.poidsPanierKg + poidsKg)
        //   }
        //
        // L'état change -> le StateFlow émet -> l'écran qui l'observe
        // se recompose. Personne n'a « mis à jour l'écran ».
        // -------------------------------------------------------------------
        _uiState.update { etat ->
            etat.copy(poidsPanierKg = etat.poidsPanierKg + poidsKg)
        }
    }
}
