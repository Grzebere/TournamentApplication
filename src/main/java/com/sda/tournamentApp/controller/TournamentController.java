package com.sda.tournamentApp.controller;

import com.sda.tournamentApp.model.Account;
import com.sda.tournamentApp.model.Category;
import com.sda.tournamentApp.service.TournamentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.time.LocalDate;

@Slf4j
@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class TournamentController {
    private final TournamentService tournamentService;

    @GetMapping("/tournament")
    public String getTournamentPage(Model model) {
        model.addAttribute("tournaments", tournamentService.getAllTournaments());
        return "tournament-index";
    }

    @GetMapping("/tournament/add")
    public String getTournamentForm() {
        return "add-tournament";
    }


    @PostMapping("/tournament/add")
    public String submitTournamentForm(String name,
                                       String organizer,
                                       @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
                                       Category category,
                                       String description,
                                       String prize,
                                       Integer maximumNumberOfParticipants,
                                       Principal principal) {
        if (principal instanceof UsernamePasswordAuthenticationToken) {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) principal;
            if (usernamePasswordAuthenticationToken.getPrincipal() instanceof Account) {
                Account account = (Account) usernamePasswordAuthenticationToken.getPrincipal();
                tournamentService.addTournament(name, organizer, date, category, description, prize, maximumNumberOfParticipants);
            }
        }
        return "redirect:/tournament";
    }
}
