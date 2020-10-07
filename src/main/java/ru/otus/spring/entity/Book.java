package ru.otus.spring.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Table(name = "books")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@NamedEntityGraph(name = "authors-entity-graph", attributeNodes = {@NamedAttributeNode("author")})
public class Book {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @JoinColumn(name = "author_id", nullable = false, foreignKey = @ForeignKey(name = "fk_book_author_id"))
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Author author;

    @JoinColumn(name = "genre_id", nullable = false, foreignKey = @ForeignKey(name = "fk_book_genre_id"))
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Genre genre;

    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Comment> comments;

    public Book(Long id, String name, Author author, Genre genre) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.genre = genre;
    }

    public Book(Long id, String name, Author author, Genre genre, Comment comment) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.genre = genre;
        this.comments = Collections.singletonList(comment);
    }

    @Override
    public String toString() {
        return '(' +
                "id=" + id +
                ", название='" + name +
                ", автор=" + author.getName() +
                ", жанр=" + genre.getName() +
                ", комментарии=" + (comments == null || comments.isEmpty()
                ? "Комментарии отсутствуют"
                : comments.stream().map(Comment::getText).collect(Collectors.joining(", "))) +
                ')';
    }
}
