package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Tag {

    private int id;
    private String title;

    @Override
    public String toString() {
        return "Tweet {" + '\n' +
                '\t' + " id : " + id +'\n' +
                '\t' + " title : '" + title + '\'' +'\n' +
                '}';
    }
}
