package com.example.springapp.controllerTest;

import com.example.springapp.controller.EventsController;
import com.example.springapp.dao.EventsDao;
import com.example.springapp.entity.Booking;
import com.example.springapp.entity.Events;
import com.example.springapp.service.EventsServices;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;

@SpringBootTest
public class EventsControllerTests {

    @Autowired
    private EventsController eventsController;

    @Autowired
    private EventsDao eventsDao;

    @Autowired
    private EventsServices eventsServices;

    @Test
    public void testViewAllEvents(){
        List<Booking> allbookings = new ArrayList<>();
        Events events1 = new Events(1, "Birthday", "http://birthday", 25000, "Best quality", allbookings);
        eventsDao.save(events1);
        List<Events> expected = this.eventsController.viewAllEvents();

    }
}
