package com.sda.tournamentApp.service;

import com.sda.tournamentApp.model.Account;
import com.sda.tournamentApp.model.FavouriteDogName;
import com.sda.tournamentApp.repository.FavouriteDogNameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavouriteDogNameService {
    private final FavouriteDogNameRepository favouriteDogNameRepository;

    public List<FavouriteDogName> favouriteDogNameList(Account account){
        return favouriteDogNameRepository.findAllByAccount(account);
    }
}
