package ru.yandex.practicum.scooter.api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.yandex.practicum.scooter.api.model.Order;

import static io.restassured.RestAssured.given;

public class OrderClient extends BaseApiClient {

    @Step("Создание заказа")
    public Response createOrder(Order order) {
        return given()
                .spec(getReqSpec())
                .body(order)
                .when()
                .post(BASE_URL + "/api/v1/orders");
    }

}
