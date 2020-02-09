package netty_http_server;

import io.netty.handler.codec.http.QueryStringDecoder;
import io.reactivex.netty.protocol.http.server.HttpServer;
import reactive_mongo_driver.Currency;
import reactive_mongo_driver.Product;
import reactive_mongo_driver.ReactiveMongoDriver;
import reactive_mongo_driver.User;
import rx.Observable;

import java.util.List;
import java.util.Map;

public class RxNettyHttpServer {

    public static void main(final String[] args) {
        HttpServer
                .newServer(8080)
                .start((req, resp) -> {

                    String name = req.getDecodedPath().substring(1);
                    Map<String, List<String>> parameters = new QueryStringDecoder(req.getUri()).parameters();

                    Observable<String> response;
                    switch (name) {
                        case "register-user":
                            response = ReactiveMongoDriver
                                    .getUsers()
                                    .exists(user -> user.getId().equals(parameters.get("id").get(0)))
                                    .flatMap(exist -> Observable
                                            .just(new User(
                                                    parameters.get("id").get(0),
                                                    parameters.get("currency").get(0)))
                                            .flatMap(user -> {
                                                if (exist) {
                                                    return Observable
                                                            .just(user.toString() +
                                                                    " isn't registered because it already exists.");
                                                }
                                                String currency = parameters.get("currency").get(0);
                                                if (!Currency.correct(currency)) {
                                                    return Observable
                                                            .just(user.toString() +
                                                                    " isn't registered because it has an incorrect currency: " +
                                                                    currency + ".");
                                                }
                                                return ReactiveMongoDriver
                                                        .addUser(user)
                                                        .map(success -> user.toString() + " successfully registered.");
                                            }));
                            break;

                        case "add-product":
                            response = ReactiveMongoDriver
                                    .getProducts()
                                    .exists(product -> product.getId().equals(parameters.get("id").get(0)))
                                    .flatMap(exist -> Observable
                                            .just(new Product(
                                                    parameters.get("id").get(0),
                                                    parameters.get("price").get(0),
                                                    parameters.get("currency").get(0)))
                                            .flatMap(product -> {
                                                if (exist) {
                                                    return Observable
                                                            .just(product.toString() +
                                                                    " isn't added because it already exists.");
                                                }
                                                String currency = parameters.get("currency").get(0);
                                                if (!Currency.correct(currency)) {
                                                    return Observable.just(product.toString() +
                                                            " isn't added because its price has incorrect currency: " +
                                                            currency + ".");
                                                }
                                                if (Double.parseDouble(parameters.get("price").get(0)) < 0) {
                                                    return Observable.just(product.toString() +
                                                            " isn't added because its price is less than zero.");
                                                }
                                                return ReactiveMongoDriver
                                                        .addProduct(product)
                                                        .map(success -> product.toString() + " successfully added.");
                                            })
                                    );
                            break;

                        case "show-products":
                            response = ReactiveMongoDriver
                                    .getUsers()
                                    .filter(user -> user.getId().equals(parameters.get("id").get(0)))
                                    .lastOrDefault(new User(parameters.get("id").get(0), "UNDEF"))
                                    .flatMap(maybeUser -> {
                                        if (!Currency.correct(maybeUser.getCurrency())) {
                                            return Observable
                                                    .just(maybeUser)
                                                    .map(defaultUser ->
                                                            defaultUser.toString() + " doesn't exist.");
                                        } else {
                                            return ReactiveMongoDriver
                                                    .getProducts()
                                                    .map(product ->
                                                            product.getId() + ": " +
                                                                    product.getPrice(maybeUser.getCurrency()) + " " +
                                                                    maybeUser.getCurrency() + "\n");
                                        }
                                    });

                            break;

                        default:
                            response = null;

                    }
                    return resp.writeString(response);
                })
                .awaitShutdown();
    }
}
