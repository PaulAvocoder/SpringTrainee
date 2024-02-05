package com.galomzik.spring.service;

import com.galomzik.spring.dto.ChangePswdDTO;
import com.galomzik.spring.dto.UserDTO;
import com.galomzik.spring.entity.UserEntity;
import com.galomzik.spring.exception.BadDataException;
import com.galomzik.spring.exception.ConflictException;
import com.galomzik.spring.exception.NotFoundException;
import com.galomzik.spring.exception.UnauthorizedException;
import com.galomzik.spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserService{
    public UserService(@Autowired UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    private final UserRepository userRepository;

    public String getAllUsers() {
        List<UserEntity> userEntityList = userRepository.findAll();
        List<UserDTO> userDTOList = new ArrayList<>();
        for(UserEntity userEntity: userEntityList){
            UserDTO userDTO = new UserDTO();
            userDTO.setUsername(userEntity.getUsername());
            userDTO.setPassword(userEntity.getPassword());
            userDTOList.add(userDTO);
        }
        return "List of user usernames and passwords:\n" + userDTOList;
    } // просит вернуть всех юзеров класс, работающий с хранилищем

    public String signUpUser(UserDTO user) { //регистрация нового пользователя
        String usernamePossible = user.getUsername();
        String passwordPossible = user.getPassword();
        if (isUserIncorrect(usernamePossible)) { // проверка на корректность username
            throw new BadDataException("Username is incorrect");
        }
        if (isUserExist(usernamePossible)) { // существует ли уже пользователь в контейнере?
            throw new ConflictException("Oh no! The user has already been added once");
        }
        if (isPasswordIncorrectFormat(passwordPossible)) { // проверка на некорректный пароль
            throw new BadDataException("Password is incorrect format:(");
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(usernamePossible);
        userEntity.setPassword(passwordPossible);
        userRepository.save(userEntity);// если все проверки пройдены то помещает данные в хранилище
        return "User has been added:\nlogin: " + usernamePossible + "\npassword: " + passwordPossible;
    }


    public static boolean isUserIncorrect(String username) {
        return username.equals("");
    } // проверка пустая ли строка

    public boolean isUserExist(String username) { // проверяет существование пользователя в бд по юзернейму.
        return userRepository.findByUsername(username) != null;
    } // ищет пользователя по username. возвращает true если находит

    public static boolean isPasswordIncorrectFormat(String password) {
        return password.equals("");
    } // проверяет пустой ли пароль и возвращает правду если пустой

    public String signInUser(UserDTO user) { //вход юзера
        String username = user.getUsername();
        String password = user.getPassword();
        if (isUserIncorrect(username)) {
            throw new BadDataException("Username is incorrect");
        }
        if (!isUserExist(username)) {
            throw new NotFoundException("The user was not found");
        }
        if (isPasswordIncorrectFormat(password)) {
            throw new BadDataException("Password is incorrect format:(");
        }
        if (isPasswordMatch(username, password)) { // проверяет на совпадение пароля с хранилищем если всё ок то говорит что успешный вход
            return "Successful login. Congratulations";
        } else throw new UnauthorizedException("Wrong password");
    }

    public boolean isPasswordMatch(String username, String passwordPossible) { // проверка на совпадение указанного пароля в параметрах
        UserEntity userEntity = userRepository.findByUsername(username);
        return userEntity.getPassword().equals(passwordPossible);
    }

    public String changePassword(ChangePswdDTO changePasswordDTO) { //смена пароля
        String username = changePasswordDTO.getUsername();
        String oldPassword = changePasswordDTO.getOldPassword();
        String newPassword = changePasswordDTO.getNewPassword();

        if (isUserIncorrect(username)) {
            throw new BadDataException("Username is incorrect");
        }
        if (!isUserExist(username)) {
            throw new NotFoundException("The user was not found");
        }
        if (isPasswordIncorrectFormat(oldPassword) || isPasswordIncorrectFormat(newPassword)) {
            throw new BadDataException("Some of the Passwords in the wrong format :(");
        }
        if (isPasswordMatch(username, oldPassword)) { // если все условия выполнены то пароль сменится
            UserEntity userEntity = userRepository.findByUsername(username);
            userEntity.setPassword(newPassword);
            userRepository.save(userEntity);
            return "Password was changed successfully. Your new login details:\nusername: " + username + "\npassword: " + newPassword;
        } else {
            throw new UnauthorizedException("Wrong old password");
        }
    }
    }




