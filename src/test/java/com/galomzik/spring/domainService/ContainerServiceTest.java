package com.galomzik.spring.domainService;

import com.galomzik.spring.storage.Storage;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.HashMap;

import static com.galomzik.spring.storage.Storage.*;
import static org.junit.Assert.*;

public class ContainerServiceTest extends ContainerService {

    @Before
    public void init() {          //меняем hashmap на свою.
        HashMap<String, String> userAccounts = new HashMap<>() {{
            put("Jone", "6664");
            put("Fil", "7374");
            put("Klodt", "93934");
        }};
        Field field;
        try {
            field = Storage.class.getDeclaredField("userAccounts");
            field.setAccessible(true);
            field.set(Storage.class, userAccounts);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test // 2 теста на проверку существует ли пользователь
    public void isExistUser_ShouldTrue_WhenUserExist() {
        assertTrue(isUserExist("Jone"));
    }

    @Test
    public void isExistUser_ShouldFalse_WhenUserNotExist() {
        assertFalse(isUserExist("Pavel"));
    }

    @Test // тест на проверку поменялся ли пароль
    public void setNewPassword_WhenPasswordSetInStorage() {
        setPassword("Klodt", "93934");
        assertEquals("93934", userAccounts.get("Klodt"));
    }

    @Test // тест метода по возвращению пароль по username
    public void getPassword_Test() {
        assertEquals(getPasswordFromStorage("Fil"), userAccounts.get("Fil"));
    }
}