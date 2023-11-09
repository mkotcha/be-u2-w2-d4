package org.emmek.beu2w2d4.entities;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "blogposts")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class BlogPost {

    @Id
    @GeneratedValue
    private int id;
    private String category;
    private String title;
    private String picture;
    private String content;
    @Column(name = "read_time")
    private int readTime;
    @ManyToOne
    @JoinColumn(name = "author_id")
    @ToString.Exclude
    private Author author;

}
