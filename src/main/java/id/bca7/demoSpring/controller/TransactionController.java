package id.bca7.demoSpring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import id.bca7.demoSpring.models.dto.request.TransactionRequest;
import id.bca7.demoSpring.models.dto.response.ResponseData;
import id.bca7.demoSpring.services.transaction.TransactionService;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    private ResponseData responseData;

    @PostMapping
    public ResponseEntity<?> addTransaction(@RequestBody TransactionRequest request) throws Exception {
        try {
            responseData = transactionService.createTransaction(request);
            return ResponseEntity.status(responseData.getStatus()).body(request);
        } catch (Exception e) {
            e.printStackTrace();
            responseData = new ResponseData(500, e.getMessage(), null);
            return ResponseEntity.status(responseData.getStatus()).body(responseData);
        }
    }

    @GetMapping
    public ResponseEntity<?> getTransactions(@RequestParam(value = "status", defaultValue = "") Boolean status) {
        try {
            responseData = transactionService.getTransactionList(status);
            return ResponseEntity.ok().body(responseData);
        } catch (Exception e) {
            e.printStackTrace();
            responseData = new ResponseData(500, e.getMessage(), null);
            return ResponseEntity.status(responseData.getStatus()).body(responseData);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> returnBook(@PathVariable Integer id) throws Exception {
        // try {
            responseData = transactionService.returnTransaction(id);
            return ResponseEntity.ok().body(responseData);
        // } catch (Exception e) {
        //     e.printStackTrace();
        //     responseData = new ResponseData(500, e.getMessage(), null);
        //     return ResponseEntity.status(responseData.getStatus()).body(responseData);
        // }
    }
}
