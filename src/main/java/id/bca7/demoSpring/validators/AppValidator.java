package id.bca7.demoSpring.validators;

import java.util.Optional;

import org.springframework.stereotype.Service;

import id.bca7.demoSpring.exceptions.custom.CustomNotFoundExceptions;
import id.bca7.demoSpring.models.entity.Book;
import id.bca7.demoSpring.models.entity.BookTransaction;
import id.bca7.demoSpring.models.entity.Category;
import id.bca7.demoSpring.models.entity.User;

@Service
public class AppValidator {
    public void validateBookNotFound(Optional<Book> bookById) throws Exception {
        if (bookById.isEmpty()) {
            throw new CustomNotFoundExceptions("Book is not found");
        }
    }
    
    public void validateIsAlreadyDeleted(Book book) throws Exception {
        if (book.getIsDeleted()) {
            throw new Exception("Book is already deleted");
        }
    }

    public void validateCategoryNotFound(Optional<Category> categoryById) throws Exception {
        if (categoryById.isEmpty()) {
            throw new CustomNotFoundExceptions("Category is not found");
        }
    }

    public void validateCategoryIsAlreadyDeleted(Category category) throws Exception {
        if (category.getIsDeleted()) {
            throw new Exception("Category is already deleted");
        }
    }

    public void validateUserNotFound(Optional<User> userById) throws Exception {
        if (userById.isEmpty()) {
            throw new CustomNotFoundExceptions("User is not found");
        }
    }

    public void validateUserIsAlreadyDeleted(User user) throws Exception {
        if (user.getIsDeleted()) {
            throw new Exception("User is already deleted");
        }
    }

    public void validateTransactionNotFound(Optional<BookTransaction> transactionById) throws Exception {
        if (transactionById.isEmpty()) {
            throw new CustomNotFoundExceptions("Transaction is not found");
        }
    }
}