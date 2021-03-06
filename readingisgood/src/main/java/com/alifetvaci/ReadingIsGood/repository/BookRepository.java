package com.alifetvaci.ReadingIsGood.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.alifetvaci.ReadingIsGood.models.Book;

@Repository
public interface BookRepository extends MongoRepository<Book, String>{

}
