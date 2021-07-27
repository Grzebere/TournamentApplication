package com.sda.tournamentApp.repository;

import com.sda.tournamentApp.model.Account;
import com.sda.tournamentApp.model.Team;
import com.sda.tournamentApp.model.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByUsername(String username);
    List<Account> findAllByTeams(Team team);
}
