package com.myTodoApp.MyFirstTodoApp.ControllerTest;
import static org.hamcrest.Matchers.hasSize;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.myTodoApp.MyFirstTodo.MyFirstTodoAppApplication;
import com.myTodoApp.MyFirstTodo.App.TodoService;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = MyFirstTodoAppApplication.class)
@AutoConfigureMockMvc
public class TodoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TodoService todoService;

    @WithMockUser(username = "testuser")
    @Test
    void listAllTodos_ShouldReturnListTodosView() throws Exception {
        // Arrange
        todoService.addTodo("testuser", "A new valid Todo", LocalDate.now(), false);

        // Act & Assert
        mockMvc.perform(get("/list-todos"))
                .andExpect(status().isOk())
                .andExpect(view().name("listTodos"))
                .andExpect(model().attributeExists("todos"))
                .andExpect(model().attribute("todos", hasSize(1)));
    }

    @WithMockUser(username = "testuser")
    @Test
    void addNewTodo_ShouldRedirectToListTodos() throws Exception {
        // Act & Assert
        mockMvc.perform(post("/add-todo")
                .param("description", "A new valid Todo")
                .param("targetDate", LocalDate.now().toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("list-todos"));
    }

    @WithMockUser(username = "testuser")
    @Test
    void deleteTodo_ShouldRedirectToListTodos() throws Exception {
        // Arrange
        todoService.addTodo("testuser", "Todo to Delete", LocalDate.now(), false);
        int todoId = todoService.findByUsername("testuser").get(0).getId();

        // Act & Assert
        mockMvc.perform(get("/delete-todo")
                .param("id", String.valueOf(todoId)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("list-todos"));
    }
}

