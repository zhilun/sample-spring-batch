package com.example.demo.batchprocessing;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class MockOrderProcessService {

    @PostMapping("/process")
    public Map<String, String> mockService() {
        HashMap<String, String> map = new HashMap<>();
        map.put("status", "true");
        return map;
    }
}
