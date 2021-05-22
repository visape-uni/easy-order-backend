package uoc.edu.easyorderbackend.constants;

public final class UrlEasyOrderConstants {

    public static final String databaseUrl = "https://easy-order-ab33a.firebaseio.com";

    // Endpoints
    public static final String apiUrl = "/api";
    public static final String userUrl = "/user";
    public static final String restaurantUrl = apiUrl + "/restaurant";
    public static final String tableUrl = apiUrl + "/table";
    public static final String orderUrl = apiUrl + "/order";
    public static final String menuUrl = apiUrl + "/menu";
    public static final String paymentUrl = apiUrl + "/payment";

    public static final String getFromRestaurant = "/getFromRestaurant/{restaurantId}";
    public static final String getLastOrder = "/getLast/{restaurantId}/{tableId}";
    public static final String getAllOrders = "/getAll/{restaurantId}/{tableId}";
    public static final String getAllTables = "/getAll/{restaurantId}";

    public static final String saveOrder = "/saveOrder/{restaurantId}/{tableId}";

    public static final String changeOrderState = "/changeOrderState/{restaurantId}/{tableId}/{orderId}";

    public static final String changeTableState = "/changeState/{restaurantId}/{tableId}";

    public static final String getTable = "/get/{restaurantId}/{tableId}";

    public static final String createCategory = "/createCategory/{restaurantId}";
    public static final String createDish = "/createDish/{restaurantId}/{categoryId}";

    public static final String deleteDish = "/deleteDish/{restaurantId}/{categoryId}/{dishId}";

    public static final String checkoutPayment = "/checkout";

    public static final String createUrl = "/create";
    public static final String getUrl = "/get/{uid}";

    public static final String addWorkerUrl = "/addWorker/{restaurantId}/{workerId}";
}
