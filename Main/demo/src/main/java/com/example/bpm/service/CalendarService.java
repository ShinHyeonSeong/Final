package com.example.bpm.service;

import com.example.bpm.entity.HeadEntity;
import com.example.bpm.repository.HeadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CalendarService {
    @Autowired
    HeadRepository headRepository;

    public List<Map<String, Object>> getEventList() {
        List<HeadEntity> headEntityList = headRepository.findAll();
        HashMap<String, Object> event = new HashMap<String, Object>();
        List<Map<String, Object>> eventList = new ArrayList<Map<String, Object>>();
        for (HeadEntity headEntity : headEntityList) {
            event = new HashMap<String, Object>();
            event.put("start", headEntity.getStartDay());
            event.put("title", headEntity.getTitle());
            event.put("end", headEntity.getEndDay());
            eventList.add(event);
        }
        return eventList;
    }

}
