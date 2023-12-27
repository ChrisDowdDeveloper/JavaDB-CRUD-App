package com.chrisdowddeveloper.databasePostgreSQL.dao;

import java.util.List;
import java.util.Optional;

import com.chrisdowddeveloper.databasePostgreSQL.domain.Author;

public interface AuthorDao {
    
    void create(Author author);

    Optional<Author> findOne(long l);

    List<Author> find();

    void update(long id, Author author);

    void delete(long id);
}