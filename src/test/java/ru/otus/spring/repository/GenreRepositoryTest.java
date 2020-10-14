package ru.otus.spring.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.spring.entity.Genre;

import javax.persistence.Query;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.otus.spring.repository.BookRepositoryTest.CLASSIC_GENRE;

@DataJpaTest
@DisplayName("Repository для работы с жанрами должен:")
public class GenreRepositoryTest {

    private static final String IT_GENRE = "ИТ";
    private static final int ONE_EXPECT_GENRES_COUNT = 1;
    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private CustomGenreRepository customGenreRepository;

    @Autowired
    private TestEntityManager em;

    @DisplayName(" должен создать жанр если его нет в БД")
    @Test
    void shouldGetGenreByNameOrCreateIt() {
        Query queryFindByName = em.getEntityManager().createNativeQuery("SELECT * FROM GENRES WHERE name = :name");
        queryFindByName.setParameter("name", CLASSIC_GENRE);
        int countBeforeSave = queryFindByName.getResultList().size();

        List<Genre> allGenres = genreRepository.findAll();
        assertThat(allGenres).noneMatch(s -> s.getName().equalsIgnoreCase(CLASSIC_GENRE));

        Genre newGenre = new Genre(null, CLASSIC_GENRE);
        Genre genre = customGenreRepository.getByNameOrCreate(newGenre);

        assertThat(genre.getId()).isNotNull();
        int countAfterSave = queryFindByName.getResultList().size();
        assertThat(countBeforeSave).isLessThan(countAfterSave);
    }

    @DisplayName(" должен получить жанр по имени и не создавать новоый жанр, если такой уже есть в БД")
    @Test
    void shouldGetGenreByNameAndNotCreateIfIfExists() {
        Query queryFindByName = em.getEntityManager().createNativeQuery("SELECT * FROM GENRES WHERE name = :name");
        queryFindByName.setParameter("name", IT_GENRE);
        int genresCountBeforeSearch = queryFindByName.getResultList().size();
        assertThat(genresCountBeforeSearch).isEqualTo(ONE_EXPECT_GENRES_COUNT);

        Genre existsGenre = new Genre(null, IT_GENRE);
        Genre genre = customGenreRepository.getByNameOrCreate(existsGenre);
        assertThat(genre.getId()).isNotNull();

        int genresCountAfterSearch = queryFindByName.getResultList().size();
        assertThat(genresCountAfterSearch).isEqualTo(ONE_EXPECT_GENRES_COUNT);
    }
}
