package com.dyma.tennis.service;

import com.dyma.tennis.Player;
import com.dyma.tennis.data.PlayeRepository;
import com.dyma.tennis.data.PlayerEntityList;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Ceci est une classe de test unitaire pour le `PlayerService`.
 * L'objectif est de tester la logique du service de manière ISOLÉE,
 * sans dépendre d'une vraie base de données.
 */
public class PlayerServiceTest {

    // @Mock est une annotation de la bibliothèque Mockito.
    // Elle crée un "simulacre" (un faux objet) du PlayerRepository.
    // Ce faux repository ne se connectera jamais à la base de données.
    // Nous allons lui dicter son comportement dans chaque test.
    @Mock
    private PlayeRepository playerRepository;

    // C'est la VRAIE instance de la classe que nous voulons tester.
    private PlayerService playerService;

    // @BeforeEach est une annotation de JUnit.
    // La méthode setUp() sera exécutée AVANT CHAQUE test de cette classe.
    @BeforeEach
    public void setUp() throws Exception {
        // Initialise les objets annotés avec @Mock dans cette classe.
        MockitoAnnotations.openMocks(this);
        // On crée une nouvelle instance du PlayerService en lui injectant
        // notre FAUX repository. Le service pensera qu'il parle à un vrai repository.
        playerService = new PlayerService(playerRepository);
    }




    @Test
    public void shouldReturnPlayersRanking() {
        // On suit la structure "Given / When / Then" (Étant donné / Quand / Alors)

        // GIVEN (Étant donné) : On prépare le contexte du test.
        // On dit à notre FAUX repository : "Quand la méthode findAll() sera appelée,
        // alors retourne la liste de joueurs PlayerEntityList.ALL".
        Mockito.when(playerRepository.findAll()).thenReturn(PlayerEntityList.ALL);

        // WHEN (Quand) : On exécute la méthode que l'on veut tester.
        List<Player> allPlayers = playerService.getAllPlayers();

        // THEN (Alors) : On vérifie que le résultat est celui attendu.
        // On s'attend à ce que la liste retournée contienne bien les noms de famille dans un certain ordre.
        Assertions.assertThat(allPlayers)
                .extracting("lastName")
                .containsExactly("Nadal", "Djokovic", "Federer", "Murray");
    }


    @Test
    public void shouldFailToRetrievePlayer_WhenPlayerDoesNotExist() {
        // GIVEN : On prépare le scénario d'échec.
        String unknownPlayer = "doe";
        // On dit à notre FAUX repository : "Quand on te demandera de trouver un joueur
        // avec le nom 'doe', alors retourne un résultat VIDE (Optional.empty())".
        Mockito.when(playerRepository.findOneByLastNameIgnoreCase(unknownPlayer)).thenReturn(Optional.empty());

        // WHEN / THEN : On exécute l'action et on vérifie le résultat en même temps.
        // On s'attend à ce que l'appel à getPlayerByLastName lève une exception de type PlayerNotFoundException.
        Exception exception = assertThrows(PlayerNotFoundException.class, () -> {
            playerService.getPlayerByLastName(unknownPlayer);
        });

        // On vérifie aussi que le message de l'exception est bien celui que l'on attend.
        Assertions.assertThat(exception.getMessage()).isEqualTo("Player with last name doe could not be found.");
    }
}
