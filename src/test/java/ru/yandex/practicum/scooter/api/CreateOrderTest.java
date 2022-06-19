package ru.yandex.practicum.scooter.api;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.practicum.scooter.api.model.Order;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(Parameterized.class)
public class CreateOrderTest {
    private final Order order;
    private final int expectedStatusCode;

    public CreateOrderTest(Order order, int expectedStatusCode) {
        this.order = order;
        this.expectedStatusCode = expectedStatusCode;
    }

    @Parameterized.Parameters(name = "Тестовые данные: {0} {1}")
    public static Object[] getTestData() {
        return new Object[][]{
                {Order.getOrderWithColor(new String[]{"GREY", "BLACK"}), 201},
                {Order.getOrderWithoutColor(), 201},
                {Order.getOrderWithColor(new String[]{"GREY"}), 201},
                {Order.getOrderWithColor(new String[]{"BLACK"}), 201},
        };
    }

    @Test
    @DisplayName("Проверка создания заказа")
    public void orderCreateTest() {
        OrderClient orderClient = new OrderClient();

        //Создаем заказ
        Response responseCreate = orderClient.createOrder(order);
        assertEquals(expectedStatusCode, responseCreate.statusCode());
        assertFalse(responseCreate.body().jsonPath().getString("track").isEmpty());

    }
}
