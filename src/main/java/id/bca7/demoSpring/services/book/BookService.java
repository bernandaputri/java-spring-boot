package id.bca7.demoSpring.services.book;

import id.bca7.demoSpring.models.dto.request.BookRequest;
import id.bca7.demoSpring.models.dto.response.ResponseData;

public interface BookService {
    // kerangka method untuk CRUD Book

    // Create
    ResponseData createBook(BookRequest request) throws Exception;

    // Read
    // all
    ResponseData readBook(Boolean status);
    
    // by ID
    ResponseData readBook(Long id) throws Exception;

    // Update
    ResponseData updateBook(Long id, BookRequest request) throws Exception;

    // Delete
    ResponseData deleteBook(Long id) throws Exception;
}
