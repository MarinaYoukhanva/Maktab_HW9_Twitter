package org.example.entity;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
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

    @Override
    public String toString() {
        return "User {" +'\n' +
                '\t' + " id : " + id +'\n' +
                '\t' + " displayName : " + displayName + '\'' + '\n' +
                '\t' + " email : " + email + '\'' +'\n' +
                '\t' + " username : " + username + '\'' +'\n' +
                '\t' + " bio : " + bio + '\'' +'\n' +
                '\t' + " accountCreationDate : " + accountCreationDate +'\n' +
                '}';
    }
}
