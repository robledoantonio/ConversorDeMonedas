package com.alura.conversordemonedas.operations;
import com.alura.conversordemonedas.principal.Main;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class RequestConversion {
    private static final String API_KEY = "17a857dc418448778bd03db51e4c31ff";
    private HttpClient client;
    private Gson gson;

    public RequestConversion() {
        this.client = HttpClient.newHttpClient();
        this.gson = new Gson();
    }

    public double getExchangeRate(String baseCurrency, String targetCurrency) throws Exception {
        String apiUrl = String.format(
                "https://exchange-rates.abstractapi.com/v1/live/?api_key=%s&base=%s&target=%s",
                API_KEY, baseCurrency, targetCurrency);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(apiUrl))
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JsonObject jsonResponse = gson.fromJson(response.body(), JsonObject.class);
        JsonObject exchangeRates = jsonResponse.getAsJsonObject("exchange_rates");
        return exchangeRates.get(targetCurrency.toUpperCase()).getAsDouble();
    }

    public double convertCurrency(double amount, double exchangeRate) {
        return amount * exchangeRate;
    }
}
