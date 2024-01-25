package com.galomzik.spring.service;

import com.galomzik.spring.dto.User;
import org.springframework.stereotype.Service;

import static com.galomzik.spring.domainService.ContainerService.*;


@Service
public class UserService {


    public String getAllUsers() {
        return "List users and passwords:\n" + giveAllUsersFromStorage();
    } // список всех зарегистрированных пользователей


    public String signUpUser(User user) { //регистрация нового пользователя
        String usernamePossible = user.getUsername();
        String passwordPossible = user.getPassword();
        if (isUserIncorrect(usernamePossible)) { // проверка на корректность username
            return "Username is incorrect";
        }
        if (isUserExist(usernamePossible)) { // существует ли уже пользователь?
            return "Oh no! The user has already been added once";
        }
        if (isPasswordIncorrectFormat(passwordPossible)) { // проверка на некорректный пароль
            return "Password is incorrect format:(";
        }
        setPassword(usernamePossible, passwordPossible); // если все проверки пройдены то помещает в хранилище
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

        if (isPasswordMatch(username, password)) { // проверяет на совпадение пароля с хранилищем
            return "Successful login. Congratulations";
        } else return "Wrong password";
    }


    public String changePassword(User user) { //смена пароля
        String username = user.getUsername();
        String oldPassword = user.getPassword();
        String newPassword = user.getNewPassword();


        if (isPasswordMatch(username, oldPassword)) { // если все условия выполнены то пароль сменится
            setPassword(username, newPassword);
            return "Password was changed successfully. Your new login details:\nusername: " + username + "\npassword: " + newPassword;
        }
        return "Error. The password could not be changed. Please repeat";
    }

    public static boolean isPasswordMatch(String username, String passwordPossible) { // метод проверяет совпадают ли пароль в хранилище с переданным паролем
        return getPasswordFromStorage(username).equals(passwordPossible);
    }
}


