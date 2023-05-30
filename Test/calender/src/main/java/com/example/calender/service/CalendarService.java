package com.example.calender.service;

import java.time.LocalDate;
import java.util.*;

import com.example.calender.dto.HeadDto;
import com.example.calender.entity.HeadEntity;
import com.example.calender.repository.HeadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CalendarService {

    @Autowired
    HeadRepository headRepository;

    public List<Map<String, Object>> getEventList() {
        List<HeadEntity> headEntityList = headRepository.findAll();
        HashMap<String, Object> event = new HashMap<String, Object>();
        List<Map<String, Object>> eventList = new ArrayList<Map<String, Object>>();
        for (HeadEntity headEntity : headEntityList) {
            //이게 event 객체를 다시 리셋 시켜야 먹히더라궁
            event = new HashMap<String, Object>();
            event.put("start", headEntity.getStartDay());
            event.put("title", headEntity.getTitle());
            event.put("end", headEntity.getEndDay());
            eventList.add(event);
        }
        return eventList;
    }

}