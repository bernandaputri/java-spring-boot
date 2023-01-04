package id.bca7.demoSpring.services.user;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import id.bca7.demoSpring.models.dto.request.UserRequest;
import id.bca7.demoSpring.models.dto.response.ResponseData;
import id.bca7.demoSpring.models.entity.User;
import id.bca7.demoSpring.repositories.UserRepository;
import id.bca7.demoSpring.validators.AppValidator;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AppValidator appValidator;

    private User user;
    private ResponseData responseData;
    private List<User> users;

    @Override
    public ResponseData createUser(UserRequest request) throws Exception {
        if (request.getFieldEmail() == null || request.getFieldFullname() == null || request.getFieldPassword() == null
                || request.getFieldUsername() == null) {
            responseData = new ResponseData(400, "Bad Request", null);
        } else {
            // Instance User
            user = new User();

            // Convert DTO to Entity
            user.setEmail(request.getFieldEmail());
            user.setFullname(request.getFieldFullname());
            user.setUsername(request.getFieldUsername());
            user.setPassword(request.getFieldPassword());

            // Save to Repository
            userRepository.save(user);

            // Response Data
            responseData = new ResponseData(HttpStatus.CREATED.value(), "success", user);
        }
        return responseData;
    }

    @Override
    public ResponseData readUser(Boolean status) {
        if (status == null) {
            users = userRepository.findAll();
        } else {
            users = userRepository.findByIsDeleted(status);
        }
        responseData = new ResponseData(200, "success", users);
        return responseData;
    }

    @Override
    public ResponseData readUser(Integer id) throws Exception {
        // find user
        Optional<User> userById = userRepository.findById(id);

        // validate user is not found
        appValidator.validateUserNotFound(userById);

        // get user
        user = userById.get();
        responseData = new ResponseData(200, "success", user);
        return responseData;
    }

    @Override
    public ResponseData updateUser(Integer id, UserRequest request) throws Exception {
        // find user
        Optional<User> userById = userRepository.findById(id);

        // validate user is not found
        appValidator.validateUserNotFound(userById);

        // get user
        user = userById.get();

        // Convert DTO to Entity
        user.setEmail(request.getFieldEmail());
        user.setFullname(request.getFieldFullname());
        user.setUsername(request.getFieldUsername());
        user.setPassword(request.getFieldPassword());

        // Save to Repository
        userRepository.save(user);
        responseData = new ResponseData(200, "User data successfully updated", user);
        return responseData;
    }

    @Override
    public ResponseData deleteUser(Integer id) throws Exception {
        // find user
        Optional<User> userById = userRepository.findById(id);

        // validate user is not found
        appValidator.validateUserNotFound(userById);

        // get user
        user = userById.get();
        appValidator.validateUserIsAlreadyDeleted(user);

        user.setIsDeleted(true);

        userRepository.save(user);
        responseData = new ResponseData(200, "User successfully deleted", null); 
        return responseData;
    }

}
