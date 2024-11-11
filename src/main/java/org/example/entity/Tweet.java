package org.example.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Tweet {

    private int id;
    private String text;
    private int likes;
    private int dislikes;

    private User user;
    private Tweet retweetFrom;

    @Override
    public String toString() {
        return "Tweet {" + '\n' +
                '\t' + " id : " + id +'\n' +
                '\t' + " text : '" + text + '\'' +'\n' +
                '\t' + " likes : " + likes +'\n' +
                '\t' + " dislikes : " + dislikes +'\n' +
                '\t' + " userId : " + user.getId() +'\n' +
                '\t' + " retweetFrom : " + retweetFrom +'\n' +
                '}';
    }
}
