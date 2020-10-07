package ru.otus.spring.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.entity.Author;

import javax.persistence.Query;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.otus.spring.repository.BookRepositoryTest.CLASSIC_AUTHOR;

@DataJpaTest
@Transactional(propagation = Propagation.REQUIRED)
@DisplayName("Repository для работы с авторами должен:")
public class AuthorRepositoryTest {

    public static final String STIVEN_HOKING = "Хокинг Стивен";
    private static final int ONE_EXPECT_AUTHORS_COUNT = 1;
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private TestEntityManager em;

    @DisplayName(" должен создать автора если его нет в БД")
    @Test
    void shouldGetAuthorByNameOrCreateIt() {
        Query queryFindByName = em.getEntityManager().createNativeQuery("SELECT * FROM AUTHORS WHERE name = :name");
        queryFindByName.setParameter("name", CLASSIC_AUTHOR);
        int countBeforeSave = queryFindByName.getResultList().size();

        List<Author> allAuthors = authorRepository.findAll();
        assertThat(allAuthors).noneMatch(s -> s.getName().equalsIgnoreCase(CLASSIC_AUTHOR));

        Author newAuthor = new Author(null, CLASSIC_AUTHOR);
        Author author = authorRepository.getByNameOrCreate(newAuthor);

        assertThat(author.getId()).isNotNull();
        int countAfterSave = queryFindByName.getResultList().size();
        assertThat(countBeforeSave).isLessThan(countAfterSave);
    }

    @DisplayName(" должен получить автора по имени и не создавать нового автора, если такой уже есть в БД")
    @Test
    void shouldGetAuthorByNameAndNotCreateIfIfExists() {
        Query queryFindByName = em.getEntityManager().createNativeQuery("SELECT * FROM AUTHORS WHERE name = :name");
        queryFindByName.setParameter("name", STIVEN_HOKING);
        int authorsCountBeforeSearch = queryFindByName.getResultList().size();
        assertThat(authorsCountBeforeSearch).isEqualTo(ONE_EXPECT_AUTHORS_COUNT);

        Author hokingAuthor = new Author(null, STIVEN_HOKING);
        Author author = authorRepository.getByNameOrCreate(hokingAuthor);
        assertThat(author.getId()).isNotNull();

        int authorsCountAfterSearch = queryFindByName.getResultList().size();
        assertThat(authorsCountAfterSearch).isEqualTo(ONE_EXPECT_AUTHORS_COUNT);
    }
}
