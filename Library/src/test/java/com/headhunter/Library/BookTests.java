package com.headhunter.Library;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "Oguzhan", authorities = "USER")
public class BookTests {

    @Autowired
    private MockMvc mvc;

    @Test
    void shouldReturnABookWhenDataIsSaved() throws Exception{
        this.mvc.perform(get("/api/v1/book/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void shouldReturnAllBooksWhenListIsRequested() throws Exception {
        this.mvc.perform(get("/api/v1/book"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(4));
    }

    @Test
    @DirtiesContext
    @Transactional
    @Rollback
    @WithMockUser(username = "Admin", authorities = "ADMIN")
    void shouldCreateANewBook() throws Exception {
        String location = this.mvc.perform(post("/api/v1/book")
                .contentType("application/json")
                .content("""
                        {
                            "name": "Book F",
                              "author": {
                                "id": 1,
                                "firstName": "Author",
                                "lastName": "A"
                              },
                              "publisher": {
                                "id": 1,
                                "name": "Publisher A"
                              }
                        }
                        """))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andReturn().getResponse().getHeader("Location");

        this.mvc.perform(get(location))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Book F"));
    }
}
