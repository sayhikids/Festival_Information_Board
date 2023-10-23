package com.whiteboard.whiteboard.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class WeatherApiController {
    
    @GetMapping("/weather")
    public String restApiGetWeather() throws Exception{
        
        return null;
        
    }
}
