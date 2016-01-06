package com.data;

public interface BookRepository {
    Book getByIsbn(String isbn);
}
