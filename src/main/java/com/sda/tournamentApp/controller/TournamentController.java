package com.sda.tournamentApp.controller;

import com.sda.tournamentApp.model.Account;
import com.sda.tournamentApp.model.Category;
import com.sda.tournamentApp.service.TeamService;
import com.sda.tournamentApp.service.TournamentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.time.LocalDate;

@Slf4j
@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class TournamentController {
    private final TournamentService tournamentService;
    private final TeamService teamService;

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

    @GetMapping("/tournament/remove")
    public String removeTournament(@RequestParam Long tournamentId) {
        tournamentService.removeTournament(tournamentId);
        return "redirect:/tournament";
    }

    @GetMapping("/tournament/team/add")
    public String addTeamToTournamentForm(Model model, @RequestParam Long tournamentId) {
        model.addAttribute("all_available_teams", teamService.getAllTeams());
        model.addAttribute("tournament_id", tournamentId);
        return "add-team-to-tournament";
    }

    @PostMapping("/tournament/team/add")
    public String submitTeamToTournamentForm(Long tournamentId, Long teamId) {
        tournamentService.addTeamToTournament(tournamentId, teamId);
        return "redirect:/tournament";
    }
}
