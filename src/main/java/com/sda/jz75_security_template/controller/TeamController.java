package com.sda.jz75_security_template.controller;

import com.sda.jz75_security_template.service.TeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;

    @GetMapping("/team")
    public String getRecipeIndexPage(Model model) {
        model.addAttribute("teams", teamService.getAllTeams());
        return "team-index";


    }
}
