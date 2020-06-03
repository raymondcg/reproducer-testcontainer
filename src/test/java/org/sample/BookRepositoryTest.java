package org.sample;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.testcontainers.containers.PostgreSQLContainer;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { Application.class })
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @ClassRule
    public static PostgreSQLContainer<CustomPostgresqlContainer> postgreSQLContainer = CustomPostgresqlContainer
            .getInstance();

    @Test
    public void saveBooks() throws Exception {
        // Arrange
        Book book = new Book();
        book.setTitle( "Moby Dick" );
        book.setAuthor( "Herman Melville" );

        // Act
        bookRepository.save( book );

        // Assert
        Book bookFound = bookRepository.findById( book.getId() ).get();
        assertNotNull( bookFound );
        assertTrue( bookFound.equals( book ) );
    }

}
