package com.sda.tournamentApp.repository;

import com.sda.tournamentApp.model.Account;
import com.sda.tournamentApp.model.FavouriteDogName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavouriteDogNameRepository extends JpaRepository<FavouriteDogName, Long> {
    List<FavouriteDogName> findAllByAccount(Account account);
}
