package id.bca7.demoSpring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import id.bca7.demoSpring.models.dto.request.BookRequest;
import id.bca7.demoSpring.models.dto.response.ResponseData;
import id.bca7.demoSpring.services.book.BookService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookService bookService;

    private ResponseData responseData;

    @PostMapping
    public ResponseEntity<?> addBook(@RequestBody @Valid BookRequest request) {
        try {
            responseData = bookService.createBook(request);
            return ResponseEntity.status(responseData.getStatus()).body(request);
        } catch (Exception e) {
            e.printStackTrace();
            responseData = new ResponseData(500, e.getMessage(), null);
            return ResponseEntity.status(responseData.getStatus()).body(responseData);
        }
    }

    @GetMapping
    // /books -> findAll
    // /books?status=false -> findAll where isDeleted false
    // /books?status=true -> findAll where isDeleted true
    public ResponseEntity<?> getBooks(@RequestParam(value = "status", defaultValue = "") Boolean status) {
        try {
            responseData = bookService.readBook(status);
            return ResponseEntity.ok().body(responseData);
        } catch (Exception e) {
            e.printStackTrace();
            responseData = new ResponseData(500, e.getMessage(), null);
            return ResponseEntity.status(responseData.getStatus()).body(responseData);
        }
    }

    @GetMapping("/{idBook}")
    public ResponseEntity<?> getBookById(@PathVariable("idBook") Long id) throws Exception {
        // try {
            responseData = bookService.readBook(id);
            return ResponseEntity.ok().body(responseData);
        // } catch (Exception e) {
        //     e.printStackTrace();
        //     responseData = new ResponseData(500, e.getMessage(), null);
        //     return ResponseEntity.status(responseData.getStatus()).body(responseData);
        // }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBookById(@PathVariable Long id, @RequestBody BookRequest request) throws Exception {
        // try {
            responseData = bookService.updateBook(id, request);
            return ResponseEntity.status(responseData.getStatus()).body(responseData);
        // } catch (Exception e) {
        //     e.printStackTrace();
        //     responseData = new ResponseData(500, e.getMessage(), null);
        //     return ResponseEntity.status(responseData.getStatus()).body(responseData);
        // }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) throws Exception {
        // try {
            responseData = bookService.deleteBook(id);
            return ResponseEntity.ok().body(responseData);
        // } catch (Exception e) {
        //     e.printStackTrace();
        //     responseData = new ResponseData(500, e.getMessage(), null);
        //     return ResponseEntity.status(responseData.getStatus()).body(responseData);
        // }
    }
}
