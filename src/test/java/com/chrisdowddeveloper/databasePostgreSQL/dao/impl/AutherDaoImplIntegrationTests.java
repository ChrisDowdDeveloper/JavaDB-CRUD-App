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
import com.chrisdowddeveloper.databasePostgreSQL.domain.Author;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AutherDaoImplIntegrationTests {
     
    private AuthorDaoImpl underTest;

    @Autowired
    public void AuthorDaoImplIntegrationTests(AuthorDaoImpl underTest) {
        this.underTest = underTest;
    }

    @Test
    public void testThatAuthorCanBeCreatedAndRecalled() {
        Author author = TestDataUtil.createTestAuthorA();
        underTest.create(author);
        Optional<Author> result = underTest.findOne(author.getId());
        assertTrue(result.isPresent());
        assertEquals(author, result.get());
    }

    @Test
    public void testThatMultipleAuthorsCanBeCreatedAndRecalled() {
        Author authorA = TestDataUtil.createTestAuthorA();
        underTest.create(authorA);
        Author authorB = TestDataUtil.createTestAuthorB();
        underTest.create(authorB);
        Author authorC = TestDataUtil.createTestAuthorC();
        underTest.create(authorC);

        List<Author> result = underTest.find();
        assertTrue(result.size() == 3);
        List<Author> expectedAuthors = Arrays.asList(authorA, authorB, authorC);
        assertEquals(expectedAuthors, result);
    }

    @Test
    public void testThatUpdateChangesAuthors() {
        Author authorA = TestDataUtil.createTestAuthorA();
        underTest.create(authorA);
        authorA.setName("UPDATED");
        underTest.update(authorA.getId(), authorA);
        Optional<Author> result = underTest.findOne(authorA.getId());
        assertTrue(result.isPresent());
        assertEquals(authorA, result.get());
    }

    @Test
    public void testThatDeleteRemovesAuthor() {
        Author authorA = TestDataUtil.createTestAuthorA();
        underTest.create(authorA);
        underTest.delete(authorA.getId());
        Optional<Author> result = underTest.findOne(authorA.getId());
        assertTrue(result.isEmpty());
    }
}
