package ru.yandex.practicum.scooter.api;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.practicum.scooter.api.model.Courier;
import ru.yandex.practicum.scooter.api.model.CourierCredentials;

import static org.apache.http.HttpStatus.*;
import static ru.yandex.practicum.scooter.api.model.Courier.getRandomCourier;

public class CourierLoginTest {
    Integer courierId;
    Courier courier;

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
    @DisplayName("Проверка авторизации курьера")
    public void courierLoginTest() {

        //Создаем курьера
        Response responseCreate = courierClient.createCourier(courier);

        //Авторизация курьера
        CourierCredentials courierCredentials = new CourierCredentials(courier.getLogin(), courier.getPassword());
        Response responseLogin = courierClient.loginCourier(courierCredentials);
        courierId = responseLogin.body().jsonPath().getInt("id");
        Assert.assertEquals(SC_OK, responseLogin.statusCode());
    }

    @Test
    @DisplayName("Проверка авторизации курьера без указания пароля")
    public void courierLoginWithoutPasswordTest() {

        //Создаем курьера
        Response responseCreate = courierClient.createCourier(courier);

        //Получаем Id курьера для последующего удаления
        CourierCredentials courierCredentialsOk = new CourierCredentials(courier.getLogin(), courier.getPassword());
        Response responseLoginOk = courierClient.loginCourier(courierCredentialsOk);
        courierId = responseLoginOk.body().jsonPath().getInt("id");

        //Авторизация курьера
        CourierCredentials courierCredentialsBad = new CourierCredentials(courier.getLogin(), null);
        Response responseLoginBad = courierClient.loginCourier(courierCredentialsBad);
        Assert.assertEquals(SC_BAD_REQUEST, responseLoginBad.statusCode());
        Assert.assertEquals("Недостаточно данных для входа", responseLoginBad.body().jsonPath().getString("message"));

    }

    @Test
    @DisplayName("Проверка авторизации курьера без указания логина")
    public void courierLoginWithoutLoginTest() {

        //Создаем курьера
        Response responseCreate = courierClient.createCourier(courier);

        //Получаем Id курьера для последующего удаления
        CourierCredentials courierCredentialsOk = new CourierCredentials(courier.getLogin(), courier.getPassword());
        Response responseLoginOk = courierClient.loginCourier(courierCredentialsOk);
        courierId = responseLoginOk.body().jsonPath().getInt("id");

        //Авторизация курьера
        CourierCredentials courierCredentialsBad = new CourierCredentials(null, courier.getPassword());
        Response responseLoginBad = courierClient.loginCourier(courierCredentialsBad);
        Assert.assertEquals(SC_BAD_REQUEST, responseLoginBad.statusCode());
        Assert.assertEquals("Недостаточно данных для входа", responseLoginBad.body().jsonPath().getString("message"));

    }

    @Test
    @DisplayName("Проверка авторизации несуществующего курьера")
    public void courierNotExistLoginTest() {

        //Авторизация курьера
        CourierCredentials courierCredentials = new CourierCredentials("test098iopj", "lkdfnwonn");
        Response responseLogin = courierClient.loginCourier(courierCredentials);
        Assert.assertEquals(SC_NOT_FOUND, responseLogin.statusCode());
        Assert.assertEquals("Учетная запись не найдена", responseLogin.body().jsonPath().getString("message"));
    }
}
