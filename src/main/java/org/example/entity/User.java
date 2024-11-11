package org.example.entity;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private int id;
    private String displayName;
    private String email;
    private String username;
    private String password;
    private String bio;
    private LocalDate accountCreationDate;


}
