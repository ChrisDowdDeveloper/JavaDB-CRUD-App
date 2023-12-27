package com.chrisdowddeveloper.databasePostgreSQL.dao.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import com.chrisdowddeveloper.databasePostgreSQL.TestDataUtil;
import com.chrisdowddeveloper.databasePostgreSQL.dao.AuthorDao;
import com.chrisdowddeveloper.databasePostgreSQL.domain.Author;
import com.chrisdowddeveloper.databasePostgreSQL.domain.Book;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookDaoImplIntegrationTests {
     

    private AuthorDao authorDao;

    private BookDaoImpl underTest;

    @Autowired
    public void BookDaoImplIntegrationTests(BookDaoImpl underTest, AuthorDao authorDao) {
        this.underTest = underTest;
        this.authorDao = authorDao;
    }

    @Test
    public void testThatBookCanBeCreatedAndRecalled() {
        Author author = TestDataUtil.createTestAuthorA();
        authorDao.create(author);
        Book book = TestDataUtil.createTestBookA();
        book.setAuthorId(author.getId());
        underTest.create(book);
        Optional<Book> result = underTest.findOne(book.getIsbn());
        assertTrue(result.isPresent());
        assertEquals(book, result.get());
    }

    @Test
    public void testThatManyBooksCanBeCreatedAndRecalled() {

        Author authorA = TestDataUtil.createTestAuthorA();
        authorDao.create(authorA);

        Book bookA = TestDataUtil.createTestBookA();
        bookA.setAuthorId(authorA.getId());
        underTest.create(bookA);

        Author authorB = TestDataUtil.createTestAuthorB();
        authorDao.create(authorB);

        Book bookB = TestDataUtil.createTestBookB();
        bookB.setAuthorId(authorB.getId());
        underTest.create(bookB);

        Author authorC = TestDataUtil.createTestAuthorC();
        authorDao.create(authorC);

        Book bookC = TestDataUtil.createTestBookC();
        bookC.setAuthorId(authorC.getId());
        underTest.create(bookC);

        List<Book> result = underTest.find();
        assertTrue(result.size() == 3);
        List<Book> expectedBooks = Arrays.asList(bookA, bookB, bookC);
        assertEquals(expectedBooks, result);
    }

    @Test
    public void testThatBookCanBeUpdated() {
        Author authorA = TestDataUtil.createTestAuthorA();
        authorDao.create(authorA);

        Book bookA = TestDataUtil.createTestBookA();
        bookA.setAuthorId(authorA.getId());
        underTest.create(bookA);

        bookA.setTitle("UPDATED");
        underTest.update(bookA.getIsbn(), bookA);
        Optional<Book> result = underTest.findOne(bookA.getIsbn());
        assertTrue(result.isPresent());
        assertEquals(bookA, result.get());
    }

    @Test
    public void testThatBookCanBeDeleted() {
        Author authorA = TestDataUtil.createTestAuthorA();
        authorDao.create(authorA);

        Book bookA = TestDataUtil.createTestBookA();
        bookA.setAuthorId(authorA.getId());
        underTest.create(bookA);

        underTest.delete(bookA.getIsbn());
        Optional<Book> result = underTest.findOne(bookA.getIsbn());
        assertTrue(result.isEmpty());
    }
}
