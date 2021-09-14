package com.sda.tournamentApp.controller;

import com.sda.tournamentApp.model.Account;
import com.sda.tournamentApp.service.AccountService;
import com.sda.tournamentApp.service.TeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Slf4j
@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class TeamController {
    private final TeamService teamService;
    private final AccountService accountService;

    @GetMapping("/team")
    public String getTeamsPage(Model model) {
        model.addAttribute("teams", teamService.getAllTeams());
        return "team-index";
    }

    @GetMapping("/team/add")
    public String getTeamForm() {
        return "add-team";
    }

    @PostMapping("/team/add")
    public String submitTeamForm(String name, Principal principal) {
        if (principal instanceof UsernamePasswordAuthenticationToken) {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) principal;
            if (usernamePasswordAuthenticationToken.getPrincipal() instanceof Account) {
                Account account = (Account) usernamePasswordAuthenticationToken.getPrincipal();
                teamService.addTeam(name, account.getId());
            }
        }
        return "redirect:/team";
    }

    @GetMapping("/team/remove")
    public String removeTeam(@RequestParam Long teamId) {
        teamService.removeTeam(teamId);
        return "redirect:/team";
    }

    @GetMapping("/team/account/add")
    public String addAccountToTeamForm(Model model, @RequestParam Long teamId) {
        model.addAttribute("all_available_accounts", accountService.getAccountList());
        model.addAttribute("team_id", teamId);
        return "add-account-to-team";
    }

    @PostMapping("/team/account/add")
    public String submitAccountToTeamForm(Long team_id, Long account_id) {
        teamService.addAccountToTeam(team_id, account_id);
        return "redirect:/team";
    }

    @GetMapping("/team/{id}")
    public String getTeamDetails(Model model, @PathVariable(name= "id") Long teamId) {
        model.addAttribute("teamDetails", teamService.getTeamById(teamId));
        model.addAttribute("teamAccounts", accountService.getAllTeamAccounts(teamId));
        return "team-details";
    }

    @GetMapping("/team/{id}/remove")
    public String removeTeammate(@RequestParam Long team_id, Long account_id) {
        teamService.removeTeammate(team_id, account_id);
        return "redirect:/team";
    }


}
