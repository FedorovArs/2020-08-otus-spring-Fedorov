package ru.otus.spring.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.entity.Book;
import ru.otus.spring.entity.Comment;

import javax.persistence.Query;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("Repository для работы с комментариями должен:")
public class CommentRepositoryTest {

    private static final String BAD_COMMENT = "Ужасная книга";
    private static final String GOOD_COMMENT = "Хорошая книга";
    private static final int ONE_EXPECT_COMMENTS_COUNT = 1;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private TestEntityManager em;

    @DisplayName(" должен создать комментарий если его нет в БД")
    @Test
    void shouldGetCommentByNameOrCreateIt() {
        Query queryFindByName = em.getEntityManager().createNativeQuery("SELECT * FROM COMMENTS WHERE text = :text");
        queryFindByName.setParameter("text", BAD_COMMENT);
        int countBeforeSave = queryFindByName.getResultList().size();

        List<Comment> allComments = commentRepository.findAll();
        assertThat(allComments).noneMatch(s -> s.getText().equalsIgnoreCase(BAD_COMMENT));

        Book bookForTest = bookRepository.findById(1L).get();
        Comment newComment = new Comment(null, bookForTest, BAD_COMMENT);
        Comment comment = commentRepository.getByTextOrCreate(newComment);

        assertThat(comment.getId()).isNotNull();
        int countAfterSave = queryFindByName.getResultList().size();
        assertThat(countBeforeSave).isLessThan(countAfterSave);
    }

    @DisplayName(" должен получить комментарий по содержимому и не создавать новоый комментарий, если такой уже есть в БД")
    @Test
    void shouldGetCommentByNameAndNotCreateIfIfExists() {
        Query queryFindByName = em.getEntityManager().createNativeQuery("SELECT * FROM COMMENTS WHERE text = :text");
        queryFindByName.setParameter("text", GOOD_COMMENT);
        int commentsCountBeforeSearch = queryFindByName.getResultList().size();
        assertThat(commentsCountBeforeSearch).isEqualTo(ONE_EXPECT_COMMENTS_COUNT);

        Book bookForTest = bookRepository.findById(1L).get();
        Comment newComment = new Comment(null, bookForTest, GOOD_COMMENT);
        Comment comment = commentRepository.getByTextOrCreate(newComment);
        assertThat(comment.getId()).isNotNull();

        int genresCountAfterSearch = queryFindByName.getResultList().size();
        assertThat(genresCountAfterSearch).isEqualTo(ONE_EXPECT_COMMENTS_COUNT);
    }
}
