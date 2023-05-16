package com.example.calenderex.service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CalenderService {
    public List<Map<String, Object>> getEventList() {
        Map<String, Object> event = new HashMap<String, Object>();
        List<Map<String, Object>> eventList = new ArrayList<Map<String, Object>>();
        //하루만 띄워주는 것
        event.put("start", LocalDate.now());
        event.put("title", "test");
        event.put("end", LocalDate.now());


        eventList.add(event);

        event = new HashMap<String, Object>();
        event.put("start", LocalDate.now().plusDays(3));
        event.put("title", "test2");
        event.put("end", LocalDate.now().plusDays(4));
        eventList.add(event);
        return eventList;
    }


}
