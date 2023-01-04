package id.bca7.demoSpring.services.book;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import id.bca7.demoSpring.exceptions.custom.CustomNotFoundExceptions;
import id.bca7.demoSpring.models.dto.request.BookRequest;
import id.bca7.demoSpring.models.dto.response.ResponseData;
import id.bca7.demoSpring.models.entity.Book;
import id.bca7.demoSpring.models.entity.Category;
import id.bca7.demoSpring.repositories.BookRepository;
import id.bca7.demoSpring.repositories.CategoryRepository;
import id.bca7.demoSpring.validators.AppValidator;

@Service
@Transactional
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private AppValidator appValidator;

    private Book book;
    private ResponseData responseData;
    private List<Book> books;

    @Override
    public ResponseData createBook(BookRequest request) throws Exception {
        if (request.getBookTitle() == null || request.getBookAuthor() == null || request.getBookPublisher() == null) {
            responseData = new ResponseData(400, "Bad Request", null);
        } else {
            // Instance Book
            book = new Book();

            // Convert DTO to Entity
            book.setAuthor(request.getBookAuthor());
            book.setPublisher(request.getBookPublisher());
            book.setTitle(request.getBookTitle());

            // find category
            Category category = categoryRepository.findByName(request.getCategoryName());
            if (Objects.isNull(category)) {
                throw new CustomNotFoundExceptions("Category is not found");
            }

            // set category
            book.setCategory(category);

            // Save to Repository
            bookRepository.save(book);

            // Response Data
            responseData = new ResponseData(HttpStatus.CREATED.value(), "success", book);
        }
        return responseData;
    }

    @Override
    public ResponseData readBook(Boolean status) {
        if (status == null) {
            books = bookRepository.findAll();
        } else {
            books = bookRepository.findByIsDeleted(status);
        }
        responseData = new ResponseData(200, "success", books);
        return responseData;
    }

    @Override
    public ResponseData readBook(Long id) throws Exception {
        // find book
        Optional<Book> bookById = bookRepository.findById(id);

        // validate book is not found
        appValidator.validateBookNotFound(bookById);

        // get book
        book = bookById.get();
        responseData = new ResponseData(200, "success", book);
        return responseData;
    }

    @Override
    public ResponseData updateBook(Long id, BookRequest request) throws Exception {
        // find book
        Optional<Book> bookById = bookRepository.findById(id);

        // validate book is not found
        appValidator.validateBookNotFound(bookById);

        // get book
        book = bookById.get();
        book.setAuthor(request.getBookAuthor());
        book.setPublisher(request.getBookPublisher());
        book.setTitle(request.getBookTitle());

        // find category
        Category category = categoryRepository.findByName(request.getCategoryName());
        if (Objects.isNull(category)) {
            throw new CustomNotFoundExceptions("Category is not found");
        }

        // set category
        book.setCategory(category);

        bookRepository.save(book);
        responseData = new ResponseData(200, "Book successfully updated", book); 
        return responseData;
    }

    @Override
    public ResponseData deleteBook(Long id) throws Exception {
        // find book
        Optional<Book> bookById = bookRepository.findById(id);

        // validate book is not found
        appValidator.validateBookNotFound(bookById);

        // get book
        book = bookById.get();
        appValidator.validateIsAlreadyDeleted(book);

        book.setIsDeleted(true);

        bookRepository.save(book);
        responseData = new ResponseData(200, "Book successfully deleted", null); 
        return responseData;
    }

}
