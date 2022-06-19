package ru.yandex.practicum.scooter.api;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.practicum.scooter.api.model.Courier;
import ru.yandex.practicum.scooter.api.model.CourierCredentials;
import ru.yandex.practicum.scooter.api.model.CreateCourierResponse;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static ru.yandex.practicum.scooter.api.model.Courier.*;

public class CourierCreateTest {
    Integer courierId;
    Courier courier;
    Courier courierWithoutLogin;
    Courier courierWithoutPassword;

    CourierClient courierClient = new CourierClient();

    @Before
    public void setup() {
        courier = getRandomCourier();
    }

    @After
    public void delete() {
        if (courierId != null) {
            courierClient.deleteCourier(courierId);
        }
    }

    @Test
    @DisplayName("Проверка создания курьера")
    public void courierCreateTest() {
        Response responseCreate = courierClient.createCourier(courier);

        //Создаем курьера
        CreateCourierResponse createCourierResponse = responseCreate.as(CreateCourierResponse.class);
        assertEquals(SC_CREATED, responseCreate.statusCode());
        assertTrue(createCourierResponse.ok);

        //Получаем Id курьера для последующего удаления
        CourierCredentials courierCredentials = new CourierCredentials(courier.getLogin(), courier.getPassword());
        Response responseLogin = courierClient.loginCourier(courierCredentials);
        courierId = responseLogin.body().jsonPath().getInt("id");
    }

    @Test
    @DisplayName("Проверка создания курьера с существующим логином")
    public void courierDoubleCreateTest() {
        Response responseCreate = courierClient.createCourier(courier);

        //Создаем курьера
        CreateCourierResponse createFirstCourierResponse = responseCreate.as(CreateCourierResponse.class);
        assertEquals(SC_CREATED, responseCreate.statusCode());
        assertTrue(responseCreate.body().jsonPath().getBoolean("ok"));
        assertTrue(createFirstCourierResponse.ok);

        //Получаем Id курьера для последующего удаления
        CourierCredentials courierCredentials = new CourierCredentials(courier.getLogin(), courier.getPassword());
        Response responseLogin = courierClient.loginCourier(courierCredentials);
        courierId = responseLogin.body().jsonPath().getInt("id");

        //Пытаемся создать курьера еще раз с теми же данными
        Response responseCreate2 = courierClient.createCourier(courier);
        assertEquals(SC_CONFLICT, responseCreate2.statusCode());
        assertEquals("Этот логин уже используется. Попробуйте другой.", responseCreate2.body().jsonPath().getString("message"));

    }

    @Test
    @DisplayName("Проверка создания курьера без логина")
    public void courierCreateTestWithoutLogin() {
        courierWithoutLogin = getRandomCourierWithoutLogin();

        //Создаем курьера
        Response responseCreate = courierClient.createCourier(courierWithoutLogin);
        assertEquals(SC_BAD_REQUEST, responseCreate.statusCode());
        assertEquals("Недостаточно данных для создания учетной записи", responseCreate.body().jsonPath().getString("message"));
    }

    @Test
    @DisplayName("Проверка создания курьера без задания пароля")
    public void courierCreateTestWithoutPassword() {
        courierWithoutPassword = getRandomCourierWithoutPassword();

        //Создаем курьера
        Response responseCreate = courierClient.createCourier(courierWithoutPassword);
        assertEquals(SC_BAD_REQUEST, responseCreate.statusCode());
        assertEquals("Недостаточно данных для создания учетной записи", responseCreate.body().jsonPath().getString("message"));
    }
}
