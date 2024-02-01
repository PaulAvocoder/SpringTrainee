package com.galomzik.spring.service;

import com.galomzik.spring.dto.User;
import com.galomzik.spring.exception.BadDataException;
import com.galomzik.spring.exception.ConflictException;
import com.galomzik.spring.exception.NotFoundException;
import com.galomzik.spring.exception.UnauthorizedException;
import org.springframework.stereotype.Service;

import static com.galomzik.spring.domainService.ContainerService.*;


@Service
public class UserService {



    public String getAllUsers() {
        return "List of user usernames and passwords:\n" + giveAllUsersFromStorage();
    } // просит вернуть всех юзеров


    public String signUpUser(User user) { //регистрация нового пользователя
        String usernamePossible = user.getUsername();
        String passwordPossible = user.getPassword();
        if (isUserIncorrect(usernamePossible)) { // проверка на корректность username
            throw new BadDataException("Username is incorrect");
        }
        if (isUserExist(usernamePossible)) { // существует ли уже пользователь
            throw new ConflictException("Oh no! The user has already been added once");
        }
        if (isPasswordIncorrectFormat(passwordPossible)) { // проверка на некорректный пароль
            throw new BadDataException("Password is incorrect format:(");
        }
        setPassword(usernamePossible, passwordPossible); // если все проверки пройдены то помещает данные в хранилище
        return "User has been added:\nlogin: " + usernamePossible + "\npassword: " + passwordPossible;
    }

    public static boolean isUserIncorrect(String username) {
        return username.equals("");
    } // проверка пустая ли строка

    public static boolean isPasswordIncorrectFormat(String password) {
        return password.equals("");
    } // проверяет пустой ли пароль


    public String signInUser(User user) { //вход юзера
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
        if (isPasswordMatch(username, password)) { // проверяет на совпадение пароля с хранилищем
            return "Successful login. Congratulations";
        } else throw new UnauthorizedException("Wrong password");
    }



    public String changePassword(User user) { //смена пароля
        String username = user.getUsername();
        String oldPassword = user.getPassword();
        String newPassword = user.getNewPassword();

        if (isUserIncorrect(username)) {
            throw new BadDataException("Username is incorrect");
        }
        if (!isUserExist(username)) {
            throw new NotFoundException("The user was not found");
        }
        if (isPasswordIncorrectFormat(oldPassword) || isPasswordIncorrectFormat(newPassword)) {
            throw new BadDataException("Some of the Passwords in the wrong format :(");
        }
        if (isPasswordMatch(username, oldPassword)) {
            setPassword(username, newPassword);
            return "Password was changed successfully. Your new login details:\nusername: " + username + "\npassword: " + newPassword;
        } else {
            throw new UnauthorizedException("Wrong old password");
        }
    }

    public static boolean isPasswordMatch(String username, String passwordPossible) {
        return getPasswordFromStorage(username).equals(passwordPossible);
    }
}


