package com.myTodoApp.MyFirstTodoApp.ServiceTest;



import static org.assertj.core.api.Assertions.assertThat;

import com.myTodoApp.MyFirstTodo.MyFirstTodoAppApplication;
import com.myTodoApp.MyFirstTodo.App.Todo;
import com.myTodoApp.MyFirstTodo.App.TodoService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest(classes = MyFirstTodoAppApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD) // Reset the database after each test
class TodoServiceIntegrationTest {

    @Autowired
    private TodoService todoService; // Inject the service

    @BeforeEach
    void setUp() {
        TodoService.resetState(); // Reset static todos list
    }

    @Test
    void testAddAndFindTodo() {
        // Add a new todo
        todoService.addTodo("integrationUser", "Test Integration Todo", LocalDate.now(), false);

        // Retrieve todos for the user
        List<Todo> todos = todoService.findByUsername("integrationUser");

        // Assertions
        assertThat(todos).isNotEmpty();
        assertThat(todos.get(0).getDescription()).isEqualTo("Test Integration Todo");
    }

    @Test
    void testUpdateTodo() {
        // Add a new todo
        todoService.addTodo("integrationUser", "Original Description", LocalDate.now(), false);

        // Get the added todo
        Todo todo = todoService.findByUsername("integrationUser").get(0);

        // Update the todo
        todo.setDescription("Updated Description");
        todo.setDone(true);
        todoService.updateTodo(todo);

        // Retrieve the updated todo
        Todo updatedTodo = todoService.findById(todo.getId());

        // Assertions
        assertThat(updatedTodo).isNotNull();
        assertThat(updatedTodo.getDescription()).isEqualTo("Updated Description");
        assertThat(updatedTodo.isDone()).isTrue();
    }

    @Test
    void testDeleteTodo() {
        // Add a new todo
        todoService.addTodo("integrationUser", "Todo to be Deleted", LocalDate.now(), false);

        // Get the added todo
        Todo todo = todoService.findByUsername("integrationUser").get(0);

        // Delete the todo
        todoService.deleteById(todo.getId());

        // Assertions
        List<Todo> todos = todoService.findByUsername("integrationUser");
        assertThat(todos).isEmpty();
    }

    @Test
    void testToggleDone() {
        // Add a new todo
        todoService.addTodo("integrationUser", "Todo to be Toggled", LocalDate.now(), false);

        // Get the added todo
        Todo todo = todoService.findByUsername("integrationUser").get(0);

        // Toggle the 'done' status
        todoService.toggleDone(todo.getId());

        // Verify the 'done' status is toggled
        Todo toggledTodo = todoService.findById(todo.getId());
        assertThat(toggledTodo.isDone()).isTrue();

        // Toggle again
        todoService.toggleDone(todo.getId());

        // Verify the 'done' status is toggled back
        Todo toggledBackTodo = todoService.findById(todo.getId());
        assertThat(toggledBackTodo.isDone()).isFalse();
    }
}
