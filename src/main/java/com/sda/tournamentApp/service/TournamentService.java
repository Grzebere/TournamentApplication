package com.sda.tournamentApp.service;

import com.sda.tournamentApp.exception.InvalidIdAddress;
import com.sda.tournamentApp.model.Category;
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
public class TournamentService {
    private final TournamentRepository tournamentRepository;
    private final TeamRepository teamRepository;
    private final PhaseRepository phaseRepository;
    private final GameRepository gameRepository;

    public void addTournament(String name,
                              String organizer,
                              LocalDate date,
                              Category category,
                              String description,
                              String prize,
                              Integer maximumNumberOfParticipants) {
        tournamentRepository.save(Tournament.builder()
                .name(name)
                .organizer(organizer)
                .date(date)
                .category(category)
                .description(description)
                .prize(prize)
                .maximumNumberOfParticipants(maximumNumberOfParticipants)
                .build());
    }

    public void addTeamToTournament(Long tournamentID, Long teamId) {
        Optional<Team> teamOptional = teamRepository.findById(teamId);
        Optional<Tournament> tournamentOptional = tournamentRepository.findById(tournamentID);
        if (tournamentOptional.isPresent()) {
            if (teamOptional.isPresent()) {
                Tournament tournament = tournamentOptional.get();
                tournament.getTeams().add(teamOptional.get());
                tournamentRepository.save(tournament);
            } else {
                throw new InvalidIdAddress("No team with that Id in database");
            }
        } else {
            throw new InvalidIdAddress("No tournament with that Id in database");
        }
    }

    public List<Tournament> getAllTournaments() {
        return tournamentRepository.findAll();
    }
}
