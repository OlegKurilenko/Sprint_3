package ru.yandex.practicum.scooter.api;

import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;


public class GetOrderListTest extends BaseApiClient {

    @Test
    @DisplayName("Проверка получения списка заказов")
    public void checkGetListOrders() {
        given()
                .spec(getReqSpec())
                .when()
                .get(BASE_URL + "/api/v1/orders")
                .then().log().all()
                .assertThat().statusCode(200).and().body("orders", notNullValue());
    }
}
