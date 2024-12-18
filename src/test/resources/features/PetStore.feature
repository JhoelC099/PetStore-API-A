@petStoreS
Feature: Funcionalidad del API de la tienda

  Scenario: Crear una orden en la tienda
    Given que configuro el endpoint "https://petstore.swagger.io/v2/store/order"
    When envío una solicitud con los siguientes detalles de la orden:
      | key     | value |
      | orderId | 12345 |
      | orderId | 67890 |
    Then el código de respuesta debe ser 200
    And el cuerpo de la respuesta debe contener los detalles de la orden


  Scenario: Consultar una orden por ID
    Given que configuro el endpoint "https://petstore.swagger.io/v2/store/order/1"
    When envío la solicitud
    Then el código de respuesta debe ser 200
    And el cuerpo de la respuesta debe contener los detalles correctos de la orden
