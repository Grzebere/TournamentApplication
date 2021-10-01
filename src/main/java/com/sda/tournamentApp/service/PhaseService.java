package com.sda.tournamentApp.service;

import com.sda.tournamentApp.exception.InvalidAmountOfTeams;
import com.sda.tournamentApp.exception.InvalidIdAddress;
import com.sda.tournamentApp.model.Game;
import com.sda.tournamentApp.model.Phase;
import com.sda.tournamentApp.model.Tournament;
import com.sda.tournamentApp.model.TournamentPhase;
import com.sda.tournamentApp.repository.PhaseRepository;
import com.sda.tournamentApp.repository.TournamentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PhaseService {
    private final PhaseRepository phaseRepository;
    private final TournamentRepository tournamentRepository;

    public Phase addPhase(Long tournamentId) {
        Optional<Tournament> tournamentOptional = tournamentRepository.findById(tournamentId);
        if (tournamentOptional.isPresent()) {
            Phase phase = phaseRepository.save(Phase.builder()
                    .tournamentPhase(determinateTournamentPhase(tournamentOptional.get().getTeams().size()))
                    .tournament(tournamentOptional.get())
                    .games(new HashSet<Game>())
                    .build());
            return phase;
        } else {
            throw new InvalidIdAddress("No tournament with that Id in database");
        }
    }

    public List<Phase> getAllPhases() {
        return phaseRepository.findAll();
    }

    private TournamentPhase determinateTournamentPhase(int teams) {
        if (teams <= 2) {
            return TournamentPhase.FINAL;
        } else if (teams <=4){
            return TournamentPhase.SEMIFINALS;
        } else if (teams <=8){
            return TournamentPhase.QUARTERFINALS;
        } else {
            throw new InvalidAmountOfTeams("Wrong amount of teams");
        }
    }
}

