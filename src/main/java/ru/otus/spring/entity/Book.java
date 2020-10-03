package ru.otus.spring.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    @JoinColumn(name = "comment_id", foreignKey = @ForeignKey(name = "fk_book_comment_id"))
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Comment comment;

    @Override
    public String toString() {
        return '(' +
                "id=" + id +
                ", название='" + name +
                ", автор=" + author.getName() +
                ", жанр=" + genre.getName() +
                ", комментарий=" + (comment == null ? "Комментарий отсутствует" : comment.getText()) +
                ')';
    }
}
