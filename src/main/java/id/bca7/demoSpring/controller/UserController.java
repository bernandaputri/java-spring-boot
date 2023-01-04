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

import id.bca7.demoSpring.models.dto.request.UserRequest;
import id.bca7.demoSpring.models.dto.response.ResponseData;
import id.bca7.demoSpring.services.user.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    private ResponseData responseData;

    @PostMapping
    public ResponseEntity<?> addUser(@RequestBody @Valid UserRequest request) {
        try {
            responseData = userService.createUser(request);
            return ResponseEntity.status(responseData.getStatus()).body(request);
        } catch (Exception e) {
            e.printStackTrace();
            responseData = new ResponseData(500, e.getMessage(), null);
            return ResponseEntity.status(responseData.getStatus()).body(responseData);
        }
    }

    @GetMapping
    public ResponseEntity<?> getUser(@RequestParam(value = "status", defaultValue = "") Boolean status) {
        try {
            responseData = userService.readUser(status);
            return ResponseEntity.ok().body(responseData);
        } catch (Exception e) {
            e.printStackTrace();
            responseData = new ResponseData(500, e.getMessage(), null);
            return ResponseEntity.status(responseData.getStatus()).body(responseData);
        }
    }

    @GetMapping("/{idBook}")
    public ResponseEntity<?> getBookById(@PathVariable("idBook") Integer id) throws Exception {
        // try {
            responseData = userService.readUser(id);
            return ResponseEntity.ok().body(responseData);
        // } catch (Exception e) {
        //     e.printStackTrace();
        //     responseData = new ResponseData(500, e.getMessage(), null);
        //     return ResponseEntity.status(responseData.getStatus()).body(responseData);
        // }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBookById(@PathVariable Integer id, @RequestBody UserRequest request) throws Exception {
        // try {
            responseData = userService.updateUser(id, request);
            return ResponseEntity.status(responseData.getStatus()).body(responseData);
        // } catch (Exception e) {
        //     e.printStackTrace();
        //     responseData = new ResponseData(500, e.getMessage(), null);
        //     return ResponseEntity.status(responseData.getStatus()).body(responseData);
        // }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Integer id) throws Exception {
        // try {
            responseData = userService.deleteUser(id);
            return ResponseEntity.ok().body(responseData);
        // } catch (Exception e) {
        //     e.printStackTrace();
        //     responseData = new ResponseData(500, e.getMessage(), null);
        //     return ResponseEntity.status(responseData.getStatus()).body(responseData);
        // }
    }
}
