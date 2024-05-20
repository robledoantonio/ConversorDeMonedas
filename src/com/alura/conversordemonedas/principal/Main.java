package com.alura.conversordemonedas.principal;

import com.alura.conversordemonedas.operations.RequestConversion;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

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

            try {
                // Obtener el tipo de cambio
                RequestConversion operation = new RequestConversion();

                double exchangeRate = operation.getExchangeRate(baseCurrency, targetCurrency);

                // Calcular el valor convertido
                double convertedValue = operation.convertCurrency(amount, exchangeRate);

                // Mostrar el resultado en pantalla
                System.out.printf("El valor %.2f [%s] corresponde al valor final de %.2f [%s]%n",
                        amount, baseCurrency, convertedValue, targetCurrency);

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Ocurrió un error al intentar convertir la moneda. Por favor, intente de nuevo.");
            }
        }

        // Cerrar el escáner
        scanner.close();
    }
}
