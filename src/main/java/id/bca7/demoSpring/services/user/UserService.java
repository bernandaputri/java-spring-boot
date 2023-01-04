package id.bca7.demoSpring.services.user;

import id.bca7.demoSpring.models.dto.request.UserRequest;
import id.bca7.demoSpring.models.dto.response.ResponseData;

public interface UserService {
    // kerangka method untuk CRUD Book

    // Create
    ResponseData createUser(UserRequest request) throws Exception;

    // Read
    // all
    ResponseData readUser(Boolean status);
    
    // by ID
    ResponseData readUser(Integer id) throws Exception;

    // Update
    ResponseData updateUser(Integer id, UserRequest request) throws Exception;

    // Delete
    ResponseData deleteUser(Integer id) throws Exception;
}
