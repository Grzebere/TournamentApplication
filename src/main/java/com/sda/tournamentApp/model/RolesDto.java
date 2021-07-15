package com.sda.tournamentApp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RolesDto {
    private boolean admin;
    private boolean supervisor;
    private boolean user;
}
