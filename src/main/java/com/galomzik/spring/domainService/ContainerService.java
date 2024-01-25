package com.galomzik.spring.domainService;


import java.util.Map;
import java.util.Set;

import static com.galomzik.spring.storage.Storage.userAccounts;

public class ContainerService {
    public static void setPassword(String username, String newPassword) {
        userAccounts.put(username, newPassword);
    } // метод меняет пароль на новый пароль

    public static Set<Map.Entry<String, String>> giveAllUsersFromStorage() {
        return userAccounts.entrySet();
    } // достает из хранилища все имена

    public static boolean isUserExist(String username) {
        return userAccounts.containsKey(username);
    } // существует ли пользователь

    public static String getPasswordFromStorage(String username) { //получает пароль по username
        return userAccounts.get(username);
    }
}

