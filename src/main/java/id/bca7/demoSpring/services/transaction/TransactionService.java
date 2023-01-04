package id.bca7.demoSpring.services.transaction;

import id.bca7.demoSpring.models.dto.request.TransactionRequest;
import id.bca7.demoSpring.models.dto.response.ResponseData;

public interface TransactionService {
    ResponseData createTransaction(TransactionRequest request) throws Exception;
    ResponseData getTransactionList(Boolean status);
    
}
