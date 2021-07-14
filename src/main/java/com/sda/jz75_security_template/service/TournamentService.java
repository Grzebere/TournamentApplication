package com.sda.jz75_security_template.service;

import com.sda.jz75_security_template.exception.InvalidIdAddress;
import com.sda.jz75_security_template.model.Category;
import com.sda.jz75_security_template.model.Team;
import com.sda.jz75_security_template.model.Tournament;
import com.sda.jz75_security_template.repository.GameRepository;
import com.sda.jz75_security_template.repository.PhaseRepository;
import com.sda.jz75_security_template.repository.TeamRepository;
import com.sda.jz75_security_template.repository.TournamentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TournamentService {
    TournamentRepository tournamentRepository;
    TeamRepository teamRepository;
    PhaseRepository phaseRepository;
    GameRepository gameRepository;

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


}
