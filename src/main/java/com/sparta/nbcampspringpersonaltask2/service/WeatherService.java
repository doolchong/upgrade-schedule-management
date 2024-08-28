package com.sparta.nbcampspringpersonaltask2.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    private final RestTemplate restTemplate;

    public WeatherService(RestTemplateBuilder builder) {
        restTemplate = builder.build();
    }

    public String getCallWeather(String date) {
        String url = "https://f-api.github.io/f-api/weather.json"; // JSON 데이터를 제공하는 API URL

        // API로부터 JSON 데이터 가져오기
        String jsonResponse = restTemplate.getForObject(url, String.class);

        String weather = getWeatherByDate(jsonResponse, date);
        System.out.println(weather);
        return weather;
    }

    public static String getWeatherByDate(String jsonData, String date) {
        JSONArray jsonArray = new JSONArray(jsonData);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            if (jsonObject.getString("date").equals(date)) {
                return jsonObject.getString("weather");
            }
        }
        return "날씨 정보가 없습니다.";
    }
}
