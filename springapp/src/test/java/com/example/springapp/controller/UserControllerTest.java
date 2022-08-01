package com.example.springapp.controller;

import com.example.springapp.entity.Booking;
import com.example.springapp.entity.Payment;
import com.example.springapp.entity.Users;
import com.example.springapp.service.UserServiceimpl;
import com.example.springapp.service.UserServices;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@WebMvcTest(MyController.class)
public class UserControllerTest {

    @MockBean
    private UserServiceimpl userService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

   @Test
   @WithMockUser(username = "maha",password = "test1234")
    public void shouldReturnListOfUsers() throws Exception {

       Payment pay1 = new Payment(1,false,false,2500);
       Booking book1 = new Booking(1,2,1,"maha","8678999537", LocalDate.of(2022,8,11),"Chennai","http://fhgshf","19.08",250,"pending",pay1);
       List<Booking> allbookings = new ArrayList<>();
       allbookings.add(book1);
       when(userService.getUser()).thenReturn(List.of(new Users(1,"user","maha","maha","8678999537","maha@gmail.com","test1234",allbookings)));

       this.mockMvc.perform(MockMvcRequestBuilders.get("/getuser")).andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.is(1)));
   }

}
