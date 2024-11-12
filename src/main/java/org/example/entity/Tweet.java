package org.example.entity;

import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    private List<Tag> tags;

    public Tweet(int id, String text, int likes, int dislikes, User user, Tweet retweetFrom) {
        this.id = id;
        this.retweetFrom = retweetFrom;
        this.user = user;
        this.dislikes = dislikes;
        this.likes = likes;
        this.text = text;
    }

    @Override
    public String toString() {
        Integer retweetFromId;
        if (retweetFrom != null) {
            retweetFromId = retweetFrom.getId();
        } else retweetFromId = null;
        List<String> tagsTitles = new ArrayList<>();
        if (tags != null) {
            for (Tag tag : tags) {
                tagsTitles = new ArrayList<>();
                tagsTitles.add(tag.getTitle());
            }
        } else tagsTitles = null;

        return "Tweet {" + '\n' +
                '\t' + " id : " + id + '\n' +
                '\t' + " text : '" + text + '\'' + '\n' +
                '\t' + " likes : " + likes + '\n' +
                '\t' + " dislikes : " + dislikes + '\n' +
                '\t' + " userId : " + user.getId() + '\n' +
                '\t' + " retweetFrom : " + retweetFromId + '\n' +
                '\t' + " tags : " + tagsTitles + '\n' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tweet tweet = (Tweet) o;
        return id == tweet.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
