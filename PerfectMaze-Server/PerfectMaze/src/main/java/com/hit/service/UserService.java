package com.hit.service;

public class UserService {


    public UserService(String filePath) {

    }
    // should support operations such as: register, login, update user details and delete user details

    // SUGGESTION: Maybe implementing basic user authentication system rather than just saving raw password? Like the following:
    // On register:
    // 1) register should implement hashing and salting using BCrypt library
    // 2) salt result should be saved inside User class with the hashed password
    // On login:
    // 1) login should compare the password of the client's (after hashing with the salt in the User class instance) with the hashed password in the User class instance

}
