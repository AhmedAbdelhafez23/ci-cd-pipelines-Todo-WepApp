package com.myTodoApp.MyFirstTodoApp.ServiceTest;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.myTodoApp.MyFirstTodo.App.Todo;
import com.myTodoApp.MyFirstTodo.App.TodoService;

/**
 * Unit tests for the {@link TodoService} class.
 * Ensures that all functionalities of TodoService work as expected.
 */
class TodoServiceTest {

    private TodoService todoService;

    /**
     * Sets up the test environment before each test.
     * This includes resetting the state of the static list and initializing the service.
     */
    @BeforeEach
    void setUp() {
        TodoService.resetState(); // Reset the static state to ensure isolation between tests
        todoService = new TodoService(); // Create a new instance of TodoService
    }

    /**
     * Tests the functionality of adding a new todo item.
     * Ensures the todo is correctly added and retrievable by username.
     */
    @Test
    void testAddTodo() {
        // Act: Add a new todo
        todoService.addTodo("testUser", "Test Description", LocalDate.now(), false);

        // Retrieve todos for the user
        List<Todo> todos = todoService.findByUsername("testUser");

        // Assert: Verify the list is not empty
        assertFalse(todos.isEmpty(), "The todos list should not be empty after adding a todo");

        // Assert: Verify the added todo's properties
        assertEquals("Test Description", todos.get(0).getDescription(),
                "The description of the first todo should match");
        assertEquals("testUser", todos.get(0).getUsername(), "The username of the first todo should match");
    }

    /**
     * Tests the functionality of deleting a todo by its ID.
     * Ensures that the specified todo is removed from the list.
     */
    @Test
    void testDeleteById() {
        // Arrange: Add a new todo to be deleted
        todoService.addTodo("testUser", "To Be Deleted", LocalDate.now(), false);

        // Retrieve todos for the user and fetch the ID of the first todo
        List<Todo> todos = todoService.findByUsername("testUser");
        int id = todos.get(0).getId(); // Get the ID of the added todo

        // Act: Delete the todo by its ID
        todoService.deleteById(id);

        // Retrieve the updated list of todos for the user
        List<Todo> updatedTodos = todoService.findByUsername("testUser");

        // Assert: Verify the deleted todo no longer exists
        assertFalse(updatedTodos.stream().anyMatch(todo -> todo.getId() == id),
                "The deleted Todo should not exist in the list");
    }

    /**
     * Tests the functionality of finding a todo by its ID.
     * Ensures the correct todo is retrieved with all its properties intact.
     */
    @Test
    void testFindById() {
        // Arrange: Add a new todo
        todoService.addTodo("testUser", "Test Description", LocalDate.now(), false);

        // Retrieve todos for the user and fetch the ID of the first todo
        List<Todo> todos = todoService.findByUsername("testUser");
        int id = todos.get(0).getId(); // Get the ID of the added todo

        // Act: Find the todo by its ID
        Todo foundTodo = todoService.findById(id);

        // Assert: Verify the todo is not null and matches the expected properties
        assertNotNull(foundTodo, "The returned Todo should not be null");
        assertEquals("Test Description", foundTodo.getDescription(), "The description should match");
        assertEquals("testUser", foundTodo.getUsername(), "The username should match");
        assertFalse(foundTodo.isDone(), "The done status should match");
    }

    /**
     * Tests the functionality of updating an existing todo.
     * Ensures that changes to the todo's fields are applied correctly.
     */
    @Test
    void testUpdateTodo() {
        // Arrange: Add a new todo to be updated
        todoService.addTodo("testUser", "Original Description", LocalDate.now(), false);

        // Retrieve todos for the user and fetch the first todo
        List<Todo> todos = todoService.findByUsername("testUser");
        Todo todoToUpdate = todos.get(0); // Get the first todo

        // Act: Update the todo's properties
        todoToUpdate.setDescription("Updated Description");
        todoToUpdate.setTargetDate(LocalDate.now().plusDays(5));
        todoToUpdate.setDone(true);

        // Apply the update
        todoService.updateTodo(todoToUpdate);

        // Retrieve the updated todo to verify changes
        Todo updatedTodo = todoService.findById(todoToUpdate.getId());

        // Assert: Verify the changes were applied correctly
        assertNotNull(updatedTodo, "The updated Todo should not be null");
        assertEquals("Updated Description", updatedTodo.getDescription(), "The description should be updated");
        assertEquals(LocalDate.now().plusDays(5), updatedTodo.getTargetDate(), "The target date should be updated");
        assertTrue(updatedTodo.isDone(), "The 'done' status should be true");
    }

    /**
     * Tests the functionality of toggling the "done" status of a todo.
     * Ensures the "done" status is correctly toggled from false to true and back.
     */
    @Test
    void testToggleDone() {
        // Arrange: Add a new todo
        todoService.addTodo("testUser", "Toggle Done Test", LocalDate.now(), false);

        // Retrieve todos for the user and fetch the first todo
        List<Todo> todos = todoService.findByUsername("testUser");
        Todo todoToToggle = todos.get(0); // Get the first todo
        int id = todoToToggle.getId();

        // Act: Toggle the "done" status
        todoService.toggleDone(id);

        // Retrieve the updated todo to verify the toggle
        Todo updatedTodo = todoService.findById(id);

        // Assert: Verify the "done" status was toggled to true
        assertNotNull(updatedTodo, "The updated Todo should not be null");
        assertTrue(updatedTodo.isDone(), "The 'done' status should be toggled to true");

        // Act: Toggle the "done" status back to false
        todoService.toggleDone(id);

        // Retrieve the updated todo again to verify the second toggle
        updatedTodo = todoService.findById(id);

        // Assert: Verify the "done" status was toggled back to false
        assertFalse(updatedTodo.isDone(), "The 'done' status should be toggled back to false");
    }
}

