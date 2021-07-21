package com.sda.tournamentApp.service;

import com.sda.tournamentApp.exception.InvalidIdAddress;
import com.sda.tournamentApp.model.Game;
import com.sda.tournamentApp.model.Phase;
import com.sda.tournamentApp.model.Team;
import com.sda.tournamentApp.model.Tournament;
import com.sda.tournamentApp.repository.GameRepository;
import com.sda.tournamentApp.repository.PhaseRepository;
import com.sda.tournamentApp.repository.TeamRepository;
import com.sda.tournamentApp.repository.TournamentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GameService {
    private final GameRepository gameRepository;
    private final TeamRepository teamRepository;
    private final TournamentRepository tournamentRepository;
    private final PhaseRepository phaseRepository;

    public void addGame(LocalDate date,
                        Long tournamentId,
                        Long teamOneId,
                        Long teamTwoId,
                        Long phaseId) {
        Optional<Tournament> tournamentOptional = tournamentRepository.findById(tournamentId);
        Optional<Team> teamOneOptional = teamRepository.findById(teamOneId);
        Optional<Team> teamTwoOptional = teamRepository.findById(teamTwoId);
        Optional<Phase> phaseOptional = phaseRepository.findById(phaseId);
        if (tournamentOptional.isPresent()) {
            if (teamOneOptional.isPresent()) {
                if (teamTwoOptional.isPresent()) {
                    if (phaseOptional.isPresent()) {
                        gameRepository.save(Game.builder()
                                .isFinished(false)
                                .teamOnePoints(0d)
                                .teamTwoPoints(0d)
                                .date(date)
                                .teamOne(teamOneOptional.get())
                                .teamTwo(teamTwoOptional.get())
                                .phase(phaseOptional.get())
                                .tournament(tournamentOptional.get())
                                .build());
                    } else {
                        throw new InvalidIdAddress("No phase with that Id in database");
                    }
                } else {
                    throw new InvalidIdAddress("No team with that Id in database");
                }
            } else {
                throw new InvalidIdAddress("No team with that Id in database");
            }
        } else {
            throw new InvalidIdAddress("No tournament with that Id in database");
        }
    }

    public List<Game> getAllGames() {return gameRepository.findAll();}

}
