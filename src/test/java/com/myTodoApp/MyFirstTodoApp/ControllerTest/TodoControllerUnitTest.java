package com.myTodoApp.MyFirstTodoApp.ControllerTest;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import com.myTodoApp.MyFirstTodo.App.Todo;
import com.myTodoApp.MyFirstTodo.App.TodoController;
import com.myTodoApp.MyFirstTodo.App.TodoService;

class TodoControllerUnitTest {

    private TodoController todoController;

    @Mock
    private TodoService todoService;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        todoController = new TodoController(todoService);

        // Mock SecurityContextHolder to return a mocked Authentication object
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("testUser");
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void listAllTodos_ShouldReturnListTodosView() {
        // Arrange
        List<Todo> mockTodos = new ArrayList<>();
        mockTodos.add(new Todo(1, "testUser", "Test Description", LocalDate.now(), false));
        when(todoService.findByUsername("testUser")).thenReturn(mockTodos);
        ModelMap model = new ModelMap();

        // Act
        String viewName = todoController.listAllTodos(model);

        // Assert
        assertEquals("listTodos", viewName, "The view name should be 'listTodos'");
        assertNotNull(model.get("todos"), "The model should contain the todos attribute");
        assertEquals(mockTodos, model.get("todos"), "The todos in the model should match the mocked todos");
    }

    @Test
    void addNewTodo_ShouldRedirectToListTodos() {
        // Arrange
        Todo newTodo = new Todo(0, "testUser", "Test Description", LocalDate.now().plusYears(1), false);
        ModelMap model = new ModelMap();

        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false); // Simulate no validation errors

        // Act
        String viewName = todoController.addNewTodo(model, newTodo, bindingResult);

        // Assert
        assertEquals("redirect:list-todos", viewName, "The view name should be 'redirect:list-todos'");
        verify(todoService).addTodo(eq("testUser"), eq(newTodo.getDescription()), any(LocalDate.class), eq(false));
    }


    @Test
    void deleteTodo_ShouldRedirectToListTodos() {
        // Act
        String viewName = todoController.deleteTodo(1);

        // Assert
        assertEquals("redirect:list-todos", viewName, "The view name should be 'redirect:list-todos'");
        verify(todoService).deleteById(1);
    }
}
