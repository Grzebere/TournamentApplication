package com.sda.jz75_security_template.controller;

import com.sda.jz75_security_template.model.Account;
import com.sda.jz75_security_template.service.TeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Slf4j
@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class TeamController {
    private final TeamService teamService;

    @GetMapping("/team")
    public String getTeamsPage(Model model) {
        model.addAttribute("teams", teamService.getAllTeams());
        return "team-index";
    }

    @GetMapping("/team/add")
    public String getTeamForm(){ return "add-team";}

    @PostMapping("/team/add")
    public String submitTeamForm(String name, Principal principal) {
        if(principal instanceof UsernamePasswordAuthenticationToken){
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) principal;
            if(usernamePasswordAuthenticationToken.getPrincipal() instanceof Account) {
                Account account = (Account) usernamePasswordAuthenticationToken.getPrincipal();
                teamService.addTeam(name, account.getId());
            }
        }

        return "redirect:/team";
    }


}
