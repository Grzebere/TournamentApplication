package com.sda.tournamentApp.service;

import com.sda.tournamentApp.exception.InvalidIdAddress;
import com.sda.tournamentApp.model.Account;
import com.sda.tournamentApp.model.Team;
import com.sda.tournamentApp.model.Tournament;
import com.sda.tournamentApp.repository.AccountRepository;
import com.sda.tournamentApp.repository.TeamRepository;
import com.sda.tournamentApp.repository.TournamentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;
    private final AccountRepository accountRepository;
    private final TournamentRepository tournamentRepository;

    public void addTeam(String name, Long accountId) {
        Optional<Account> accountOptional = accountRepository.findById(accountId);
        if (accountOptional.isPresent()) {
            teamRepository.save(Team.builder()
                    .name(name)
                    // adding user when creating team
                    .users(Set.<Account>of(accountOptional.get()))
                    .build());
        } else {
            throw new InvalidIdAddress("No account with that Id in database");
        }
    }

    public void addAccountToTeam(Long teamId, Long accountId) {
        Optional<Team> teamOptional = teamRepository.findById(teamId);
        Optional<Account> accountOptional = accountRepository.findById(accountId);
        if (teamOptional.isPresent() && accountOptional.isPresent()) {
            Team team = teamOptional.get();
            team.getUsers().add(accountOptional.get());
            teamRepository.save(team);
        }
    }

    public void removeTeammate(Long teamId, Long accountId) {
        Optional<Team> teamOptional = teamRepository.findById(teamId);
        Optional<Account> accountOptional = accountRepository.findById(accountId);
        if (teamOptional.isPresent() && accountOptional.isPresent()) {
            if (teamOptional.get().getUsers().contains(accountOptional.get())) {
                Team team = teamOptional.get();
                team.getUsers().remove(accountOptional.get());

                teamRepository.save(team);
            }
        }
    }

    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    public Team getTeamById(Long teamId) {
        return teamRepository.findById(teamId).get();
    }

    public List<Tournament> getTournamentListForTeam(Long teamId) {
        Optional<Team> teamOptional = teamRepository.findById(teamId);
        if (teamOptional.isPresent()) {
            return List.copyOf(teamOptional.get().getTournaments());
        }
        throw new InvalidIdAddress("No team with that Id in database");
    }

    public void removeTeam(Long teamId) {
        Optional<Team> teamOptional = teamRepository.findById(teamId);
        if (teamOptional.isPresent()) {
            teamRepository.deleteById(teamId);
        } else {
            throw new InvalidIdAddress("No team with that Id in database");
        }
    }

    public List<Team> getAllTournamentTeams(Long tournamentId) {
        Optional<Tournament> tournamentOptional = tournamentRepository.findById(tournamentId);
        if (tournamentOptional.isPresent()) {
            return teamRepository.findAllByTournaments(tournamentOptional.get());
        } else {
            throw new InvalidIdAddress("No tournament with that Id in database");
        }
    }


}
