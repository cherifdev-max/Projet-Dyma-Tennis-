package com.dyma.tennis.service;

import com.dyma.tennis.Player;
import com.dyma.tennis.PlayerList;
import com.dyma.tennis.PlayerTosave;
import com.dyma.tennis.Rank;
import com.dyma.tennis.data.PlayeRepository;
import com.dyma.tennis.data.PlayerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlayerService {

    @Autowired
    private PlayeRepository playerRepository;

    public PlayerService(PlayeRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public List<Player> getAllPlayers() {

        return playerRepository.findAll().stream()
                .map(playerEntity -> new Player(playerEntity.getFirstName(), playerEntity.getLastName(), playerEntity.getBirthDate(),
                        new Rank(playerEntity.getRank(), playerEntity.getPoints()))).
                sorted(Comparator.comparing(player -> player.rank().position())).
                collect(Collectors.toList());
    }

    public Player getPlayerByLastName(String lastName) {

        Optional<PlayerEntity> player = playerRepository.findOneByLastNameIgnoreCase(lastName);
        if (player.isEmpty()) {
            throw new PlayerNotFoundException(lastName);
        }
        PlayerEntity playerEntity = player.get();


        return new Player(playerEntity.getFirstName(), playerEntity.getLastName(), playerEntity.getBirthDate(),
                new Rank(playerEntity.getRank(), playerEntity.getPoints()));

    }

    public Player createPlayer(PlayerTosave playerTosave) {

        Optional<PlayerEntity> playerTocreate = playerRepository.findOneByLastNameIgnoreCase(playerTosave.lastName());
        if (playerTocreate.isPresent()) {
            throw new PlayerAreadyExiste(playerTosave.lastName());

        }

        PlayerEntity playerEntity = new PlayerEntity(
                playerTosave.lastName(),
                playerTosave.firstName(),
                playerTosave.birthDate(),
                playerTosave.points(),
                9999999);
        playerRepository.save(playerEntity);
        RankingCalculator rankingCalculator = new RankingCalculator(playerRepository.findAll());
        List<PlayerEntity> updatedplayers = rankingCalculator.getNewPlayersRanking();
        playerRepository.saveAll(updatedplayers);
        return getPlayerByLastName(playerTosave.lastName());


    }

    public Player update(PlayerTosave playerToSave) {
        Optional<PlayerEntity> player = playerRepository.findOneByLastNameIgnoreCase(playerToSave.lastName());
        if (player.isEmpty()) {
            throw new PlayerNotFoundException(playerToSave.lastName());

        }
        player.get().setFirstName(playerToSave.firstName());
        player.get().setBirthDate(playerToSave.birthDate());
        player.get().setPoints(playerToSave.points());
        playerRepository.save(player.get());

        RankingCalculator rankingCalculator = new RankingCalculator(playerRepository.findAll());
        List<PlayerEntity> updatedplayers = rankingCalculator.getNewPlayersRanking();
        playerRepository.saveAll(updatedplayers);

        return getPlayerByLastName(playerToSave.lastName());
    }

    public void delete(String lastName) {
        Optional<PlayerEntity> playerToDelete = playerRepository.findOneByLastNameIgnoreCase(lastName);
        if (playerToDelete.isEmpty()) {
            throw new PlayerNotFoundException(lastName);

        }
        playerRepository.delete(playerToDelete.get());
        RankingCalculator rankingCalculator = new RankingCalculator(playerRepository.findAll());
        List<PlayerEntity> updatedplayers = rankingCalculator.getNewPlayersRanking();
        playerRepository.saveAll(updatedplayers);

    }

//    private Player getPlayersNewRanking(List<PlayerEntity> existangPlayers) {
//        RankingCalculator rankingCalculator = new RankingCalculator(existangPlayers, playerTosave);
//        List<Player> players =rankingCalculator.getNewPlayersRanking();
//        return players.stream().filter(player -> playerTosave.lastName().equals(player.lastName())).findFirst().get();
//
//    }
}
