package app.model;


import app.Person;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "hobby")
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@ToString

public class Hobby {

    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "id", nullable = false, unique = true)
    private long Id;

    @Column(name = "name")
    private String Name;

    @Column(name = "wikilink")
    private String WikiLink;

    @Column(name = "category")
    private String Category;

    @Column(name = "type")
    private String Type;

    @ManyToMany(mappedBy = "hobbySet")
    private Set<Person> person = new HashSet<>();

    public Hobby(String name, String wikiLink, String category, String type) {
        Name = name;
        WikiLink = wikiLink;
        Category = category;
        Type = type;
    }



}

