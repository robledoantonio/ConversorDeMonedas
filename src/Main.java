import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.google.gson.JsonObject;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        HttpClient client = HttpClient.newHttpClient();
        Gson gson = new Gson();

        while (true) {
            // Mostrar el menú
            System.out.println("*************************************************");
            System.out.println("Sea bienvenido/a al Conversor de Moneda =)");
            System.out.println();
            System.out.println(" 1) Dólar --> Peso argentino");
            System.out.println(" 2) Peso argentino --> Dólar");
            System.out.println(" 3) Dolar --> Real brasileño");
            System.out.println(" 4) Real brasileño --> Dólar");
            System.out.println(" 5) Dólar --> Peso colombiano");
            System.out.println(" 6) Peso colombiano --> Dólar");
            System.out.println(" 7) Salir");
            System.out.println();
            System.out.print("Elija una opción válida: ");
            System.out.println("*************************************************");

            // Leer la opción del usuario
            int option = scanner.nextInt();
            if (option == 7) {
                System.out.println("Gracias por usar el conversor de moneda. ¡Hasta luego!");
                break;
            }

            // Definir las monedas base y target según la opción seleccionada
            String baseCurrency = "";
            String targetCurrency = "";
            switch (option) {
                case 1:
                    baseCurrency = "USD";
                    targetCurrency = "ARS";
                    break;
                case 2:
                    baseCurrency = "ARS";
                    targetCurrency = "USD";
                    break;
                case 3:
                    baseCurrency = "USD";
                    targetCurrency = "BRL";
                    break;
                case 4:
                    baseCurrency = "BRL";
                    targetCurrency = "USD";
                    break;
                case 5:
                    baseCurrency = "USD";
                    targetCurrency = "COP";
                    break;
                case 6:
                    baseCurrency = "COP";
                    targetCurrency = "USD";
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, intente de nuevo.");
                    continue;
            }

            // Solicitar la cantidad a convertir
            System.out.print("Ingrese el valor que desea convertir: ");
            double amount = scanner.nextDouble();

            // URL de la API con parámetros dinámicos
            String apiUrl = String.format(
                    "https://exchange-rates.abstractapi.com/v1/live/?api_key=17a857dc418448778bd03db51e4c31ff&base=%s&target=%s",
                    baseCurrency, targetCurrency);

            try {
                // Crear la solicitud HTTP
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(new URI(apiUrl))
                        .header("Accept", "application/json")
                        .GET()
                        .build();

                // Enviar la solicitud y obtener la respuesta
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                // Convertir la respuesta JSON a un objeto Gson
                JsonObject jsonResponse = gson.fromJson(response.body(), JsonObject.class);

                // Obtener el tipo de cambio
                JsonObject exchangeRates = jsonResponse.getAsJsonObject("exchange_rates");
                double exchangeRate = exchangeRates.get(targetCurrency).getAsDouble();

                // Calcular el valor convertido
                double convertedValue = amount * exchangeRate;

                // Mostrar el resultado en pantalla
                System.out.printf("El valor %.2f [%s] corresponde al valor final de --> %.2f [%s]%n",
                        amount, baseCurrency, convertedValue, targetCurrency);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Cerrar el escáner
        scanner.close();
    }
}
