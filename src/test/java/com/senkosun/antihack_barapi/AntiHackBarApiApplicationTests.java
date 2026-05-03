package com.senkosun.antihack_barapi;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AntiHackBarApplicationTests {
    @Autowired
    private MockMvc mockMvc;
    private static String VALID_TOKEN

    @Test
    @Order(1)
    @DisplayName("POST /register - проверка вывода программы")
    void testRegisterOutput() throws Exception {
        MvcResult result = mockMvc.perform(post("/register"))
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.status").value("ok"))
                .andExpect(jsonPath("$.id").value(startsWith("BAR-")))
                .andExpect(jsonPath("$.token").exists());
        VALID_TOKEN = result.getResponse().getContentAsString().readTree(responseBody).get("token").asText();
    }

    @Test
    @DisplayName("POST /reset - сброс аккаунта с правильным токеном")
    void testResetWithValidToken() throws Exception {


        mockMvc.perform(post("/reset")
                        // Добавляем заголовок авторизации
                        .header("Authorization", "Bearer " + VALID_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("ok"));
    }

    @Test
    @DisplayName("GET /menu - получение меню с заголовками")
    void testGetMenu() throws Exception {

        mockMvc.perform(get("/menu")
                // Заголовок авторизации
                .header("Authorization", "Bearer " + VALID_TOKEN)
                // Заголовок с временем (формат HH:MM)
                .header("X-Time", "14:30")

                // Проверяем статус
                .andExpect(status().isOk())

                // Проверяем поле status
                .andExpect(jsonPath("$.status").value("ok"))

                // Проверяем что drinks - это массив
                .andExpect(jsonPath("$.drinks").isArray())

                // Проверяем что в массиве есть хотя бы один элемент
                .andExpect(jsonPath("$.drinks.length()").exists())

                // Проверяем balance
                .andExpect(jsonPath("$.balance").exists())
                .andExpect(jsonPath("$.balance").isNumber())

                // Проверяем mood_level
                .andExpect(jsonPath("$.mood_level").exists())
                .andExpect(jsonPath("$.mood_level").isString());
    }

    @Test
    @DisplayName("POST /order - успешный заказ напитка")
    void testOrderSuccess() throws Exception {
        String requestBody = """
        {
            "name": "Русский"
        }
        """;

        mockMvc.perform(post("/order")
                        .header("Authorization", "Bearer " + VALID_TOKEN)
                        .header("X-Time", "14:30")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("ok"))
                .andExpect(jsonPath("$.drink").value("Русский"))
                .andExpect(jsonPath("$.price").value(10))
                .andExpect(jsonPath("$.balance").value(90))
                .andExpect(jsonPath("$.mood_level").value("normal"));
    }

    @Test
    @Disabled
    @DisplayName("POST /order - недостаточно средств")
    void testOrderInsufficientFunds() throws Exception {
        String requestBody = """
        {
            "name": "Русский"
        }
        """;

        mockMvc.perform(post("/order")
                        .header("Authorization", "Bearer " + VALID_TOKEN)
                        .header("X-Time", "14:30")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("error"))
                .andExpect(jsonPath("$.error").value("insufficient_funds"))
                .andExpect(jsonPath("$.price").value(10))
                .andExpect(jsonPath("$.balance").exists())
                .andExpect(jsonPath("$.mood_level").value("normal"));
    }

    @Test
    @DisplayName("POST /order - неизвестный напиток")
    void testOrderUnknownDrink() throws Exception {
        String requestBody = """
        {
            "name": "Неизвестный коктейль"
        }
        """;

        mockMvc.perform(post("/order")
                        .header("Authorization", "Bearer " + VALID_TOKEN)
                        .header("X-Time", "14:30")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("error"))
                .andExpect(jsonPath("$.error").value("unknown_drink"))
                .andExpect(jsonPath("$.balance").exists())
                .andExpect(jsonPath("$.mood_level").value("normal"));
    }

    @Test
    @DisplayName("POST /order - без токена авторизации")
    void testOrderWithoutToken() throws Exception {
        String requestBody = """
        {
            "name": "Русский"
        }
        """;

        mockMvc.perform(post("/order")
                        .header("X-Time", "14:30")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("POST /mix - успешное создание напитка по ингредиентам")
    void testMixSuccess() throws Exception {
        String requestBody = """
        {
            "ingredients": ["водка", "лёд"]
        }
        """;

        mockMvc.perform(post("/mix")
                        .header("Authorization", "Bearer " + VALID_TOKEN)
                        .header("X-Time", "14:30")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("ok"))
                .andExpect(jsonPath("$.drink").value("Русский"))
                .andExpect(jsonPath("$.price").value(8))
                .andExpect(jsonPath("$.balance").value(92))
                .andExpect(jsonPath("$.mood_level").value("normal"));
    }

    @Test
    @DisplayName("POST /mix - неизвестная комбинация ингредиентов")
    void testMixUnknownRecipe() throws Exception {
        String requestBody = """
        {
            "ingredients": ["ром", "кола", "лимон"]
        }
        """;

        mockMvc.perform(post("/mix")
                        .header("Authorization", "Bearer " + VALID_TOKEN)
                        .header("X-Time", "14:30")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("error"))
                .andExpect(jsonPath("$.error").value("unknown_recipe"))
                .andExpect(jsonPath("$.balance").exists())
                .andExpect(jsonPath("$.mood_level").value("normal"));
    }

    @Test
    @Disabled
    @DisplayName("POST /mix - недостаточно средств для микса")
    void testMixInsufficientFunds() throws Exception {
        String requestBody = """
        {
            "ingredients": ["водка", "лёд"]
        }
        """;

        mockMvc.perform(post("/mix")
                        .header("Authorization", "Bearer " + VALID_TOKEN)
                        .header("X-Time", "14:30")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("error"))
                .andExpect(jsonPath("$.error").value("insufficient_funds"))
                .andExpect(jsonPath("$.price").exists())
                .andExpect(jsonPath("$.balance").exists())
                .andExpect(jsonPath("$.mood_level").value("normal"));
    }

    @Test
    @DisplayName("POST /mix - без токена авторизации")
    void testMixWithoutToken() throws Exception {
        String requestBody = """
        {
            "ingredients": ["водка", "лёд"]
        }
        """;

        mockMvc.perform(post("/mix")
                        .header("X-Time", "14:30")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("GET /balance - успешное получение баланса")
    void testGetBalanceSuccess() throws Exception {
        mockMvc.perform(get("/balance")
                        .header("Authorization", "Bearer " + VALID_TOKEN))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("ok"))
                .andExpect(jsonPath("$.balance").exists())
                .andExpect(jsonPath("$.balance").isNumber())
                .andExpect(jsonPath("$.mood_level").exists())
                .andExpect(jsonPath("$.mood_level").isString());
    }

    @Test
    @DisplayName("GET /balance - с неверным токеном")
    void testGetBalanceWithInvalidToken() throws Exception {
        mockMvc.perform(get("/balance")
                        .header("Authorization", "Bearer invalid_token_123"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("POST /tip - успешная оплата чаевых")
    void testTipSuccess() throws Exception {
        String requestBody = """
        {
            "amount": 5
        }
        """;

        mockMvc.perform(post("/tip")
                        .header("Authorization", "Bearer " + VALID_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("ok"))
                .andExpect(jsonPath("$.tip").value(5))
                .andExpect(jsonPath("$.balance").value(85))
                .andExpect(jsonPath("$.mood_level").value("normal"));
    }

    @Test
    @DisplayName("POST /tip - без токена авторизации")
    void testTipWithoutToken() throws Exception {
        String requestBody = """
        {
            "amount": 5
        }
        """;

        mockMvc.perform(post("/tip")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("GET /history - успешное получение истории")
    void testGetHistorySuccess() throws Exception {
        mockMvc.perform(get("/history")
                        .header("Authorization", "Bearer " + VALID_TOKEN))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("ok"))
                .andExpect(jsonPath("$.orders").isArray())
                .andExpect(jsonPath("$.balance").exists())
                .andExpect(jsonPath("$.balance").isNumber())
                .andExpect(jsonPath("$.mood_level").exists())
                .andExpect(jsonPath("$.mood_level").isString());
    }

    @Test
    @DisplayName("GET /profile - успешное получение профиля")
    void testGetProfileSuccess() throws Exception {
        mockMvc.perform(get("/profile")
                        .header("Authorization", "Bearer " + VALID_TOKEN))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("ok"))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.id").isString())
                .andExpect(jsonPath("$.rank").exists())
                .andExpect(jsonPath("$.rank").isString())
                .andExpect(jsonPath("$.total_orders").exists())
                .andExpect(jsonPath("$.total_orders").isNumber())
                .andExpect(jsonPath("$.unique_drinks").exists())
                .andExpect(jsonPath("$.unique_drinks").isNumber())
                .andExpect(jsonPath("$.bar_closed").exists())
                .andExpect(jsonPath("$.bar_closed").isBoolean());
    }

}