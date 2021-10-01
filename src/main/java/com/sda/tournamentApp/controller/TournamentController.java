package com.sda.tournamentApp.controller;

import com.sda.tournamentApp.model.Account;
import com.sda.tournamentApp.model.Category;
import com.sda.tournamentApp.service.PhaseService;
import com.sda.tournamentApp.service.TeamService;
import com.sda.tournamentApp.service.TournamentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;

@Slf4j
@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class TournamentController {
    private final TournamentService tournamentService;
    private final PhaseService phaseService;
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
    public String submitTeamToTournamentForm(Long tournament_id, Long team_id) {
        tournamentService.addTeamToTournament(tournament_id, team_id);
        return "redirect:/tournament";
    }


    // Wychodzi zapytanie ze /?{id}
    @GetMapping("/tournament/{id}")
    public String getTournamentDetails(Model model, @PathVariable(name = "id") Long tournamentId) {
        model.addAttribute("tournamentDetails", tournamentService.getTournamentById(tournamentId));
        model.addAttribute("tournamentTeams", teamService.getAllTournamentTeams(tournamentId));
        return "tournament-details";
    }

    @GetMapping("/tournament/{id}/start")
    public String startTournament(Model model, @PathVariable(name = "id") Long tournamentId) {
        tournamentService.addPhaseToTournament(tournamentId, phaseService.addPhase(tournamentId).getId());
        return "redirect:/tournament";
    }



}
