import org.junit.jupiter.api.*;
import reactive_mongo_driver.ReactiveMongoDriver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.fail;

public class ReactiveTest {

    private String sendRequest(String requestStr) {
        try {
            URL url = new URL("http://localhost:8080/" + requestStr);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            Assertions.assertEquals(connection.getResponseCode(), HttpURLConnection.HTTP_OK);
            StringBuilder responseStr = new StringBuilder();
            try (BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String line;
                while ((line = input.readLine()) != null) {
                    responseStr.append(line);
                }
            }
            return responseStr.toString();
        } catch (IOException ex) {
            fail("Request isn't sended: " + ex.getMessage());
        }
        return "";
    }

    @Test
    void correctRequests() {
        String respStr;

        respStr = sendRequest("register-user?id=anya&currency=RUR");
        Assertions.assertTrue(respStr.contains("successfully"));
        respStr = sendRequest("register-user?id=usd-user&currency=USD");
        Assertions.assertTrue(respStr.contains("successfully"));
        respStr = sendRequest("register-user?id=eur-user&currency=EUR");
        Assertions.assertTrue(respStr.contains("successfully"));
        ReactiveMongoDriver.getUsers().toList().forEach(list -> Assertions.assertEquals(list.size(), 3));

        respStr = sendRequest("add-product?id=cat&price=4745.0&currency=RUR");
        Assertions.assertTrue(respStr.contains("successfully"));
        respStr = sendRequest("add-product?id=dog&price=73.0&currency=USD");
        Assertions.assertTrue(respStr.contains("successfully"));
        respStr = sendRequest("add-product?id=parrot&price=65.0&currency=EUR");
        Assertions.assertTrue(respStr.contains("successfully"));
        ReactiveMongoDriver.getProducts().toList().forEach(list -> Assertions.assertEquals(list.size(), 3));

        respStr = sendRequest("show-products?id=anya");
        Assertions.assertTrue(respStr.contains("cat") && respStr.contains("dog") && respStr.contains("parrot") &&
                respStr.contains("RUR") && !respStr.contains("USD") && !respStr.contains("EUR"));
        respStr = sendRequest("show-products?id=usd-user");
        Assertions.assertTrue(respStr.contains("cat") && respStr.contains("dog") && respStr.contains("parrot") &&
                !respStr.contains("RUR") && respStr.contains("USD") && !respStr.contains("EUR"));
        respStr = sendRequest("show-products?id=eur-user");
        Assertions.assertTrue(respStr.contains("cat") && respStr.contains("dog") && respStr.contains("parrot") &&
                !respStr.contains("RUR") && !respStr.contains("USD") && respStr.contains("EUR"));
        ReactiveMongoDriver.getUsers().toList().forEach(list -> Assertions.assertEquals(list.size(), 3));
        ReactiveMongoDriver.getProducts().toList().forEach(list -> Assertions.assertEquals(list.size(), 3));

    }

    @Test
    void incorrectRequests() {
        String respStr;

        respStr = sendRequest("register-user?id=anya&currency=USD");
        Assertions.assertTrue(respStr.contains("already exists"));
        respStr = sendRequest("register-user?id=aud-user&currency=AUD");
        Assertions.assertTrue(respStr.contains("incorrect currency"));

        respStr = sendRequest("add-product?id=cat&price=4745.0&currency=RUR");
        Assertions.assertTrue(respStr.contains("already exists"));
        respStr = sendRequest("add-product?id=aud-dog&price=73.0&currency=AUD");
        Assertions.assertTrue(respStr.contains("incorrect currency"));
        respStr = sendRequest("add-product?id=free-parrot&price=-65.0&currency=EUR");
        Assertions.assertTrue(respStr.contains("less than zero"));

        respStr = sendRequest("show-products?id=aud-user");
        Assertions.assertTrue(respStr.contains("doesn't exist"));
    }
}