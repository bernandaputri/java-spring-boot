package id.bca7.demoSpring.services.transaction;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import id.bca7.demoSpring.models.dto.request.TransactionRequest;
import id.bca7.demoSpring.models.dto.response.ResponseData;
import id.bca7.demoSpring.models.entity.Book;
import id.bca7.demoSpring.models.entity.BookTransaction;
import id.bca7.demoSpring.models.entity.User;
import id.bca7.demoSpring.repositories.BookRepository;
import id.bca7.demoSpring.repositories.TransactionRepository;
import id.bca7.demoSpring.repositories.UserRepository;
import id.bca7.demoSpring.validators.AppValidator;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AppValidator appValidator;

    private ResponseData responseData;
    private BookTransaction transaction;
    private User user;
    private Book book;
    private List<BookTransaction> transactions;

    @Override
    public ResponseData createTransaction(TransactionRequest request) throws Exception {
        if (request.getBookId() == null || request.getUserId() == null) {
            responseData = new ResponseData(400, "Bad Request", null);
        } else {
            // Instance Book
            transaction = new BookTransaction();

            // find user
            Optional<User> userById = userRepository.findByIdAndIsDeleted(request.getUserId(), false);

            // validate user is not found
            appValidator.validateUserNotFound(userById);

            // get user
            user = userById.get();

            // find available book
            Optional<Book> books = bookRepository.findByIdAndIsDeleted(request.getBookId(), false);

            // validate book is not found
            appValidator.validateBookNotFound(books);

            // get book
            book = books.get();

            Optional<Book> availableToBorrow = bookRepository
                    .findByIdAndIsBorrowed(request.getBookId(), true);

            if (availableToBorrow.isEmpty()) {
                // borrow book
                transaction.setUser(user);
                transaction.setBook(book);
                transaction.setBorrowDateTime(LocalDateTime.now());
                transactionRepository.save(transaction);

                // set isBorrowed book
                book.setIsBorrowed(true);
                bookRepository.save(book);

                // Response Data
                responseData = new ResponseData(HttpStatus.CREATED.value(), "success", book);
            } else {
                responseData = new ResponseData(HttpStatus.BAD_REQUEST.value(), "failed, book is already borrowed",
                        null);
            }

        }
        return responseData;
    }

    @Override
    public ResponseData getTransactionList(Boolean status) {
        if (status == null) {
            transactions = transactionRepository.findAll();
        } else {
            transactions = transactionRepository.findByIsReturned(status);
        }
        responseData = new ResponseData(200, "success", transactions);
        return responseData;
    }

    @Override
    public ResponseData returnTransaction(Integer id) throws Exception {
        Optional<BookTransaction> transactionById = transactionRepository.findById(id);
        appValidator.validateTransactionNotFound(transactionById);

        transaction = transactionById.get();
        appValidator.validateTransactionIsAlreadyReturned(transaction);
        transaction.setIsReturned(true);
        transaction.setReturnDateTime(LocalDateTime.now());
        transactionRepository.save(transaction);
        book.setIsBorrowed(false);
        bookRepository.save(book);
        responseData = new ResponseData(200, "Book successfully returned.", transaction);

        return responseData;
    }

}
