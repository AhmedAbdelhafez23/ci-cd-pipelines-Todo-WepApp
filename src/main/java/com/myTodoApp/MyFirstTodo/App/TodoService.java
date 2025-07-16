package com.myTodoApp.MyFirstTodo.App;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import jakarta.validation.Valid;

@Service
public class TodoService {

    // Static list to simulate a database for storing todo items
    private static List<Todo> todos = new ArrayList<>();

    // Counter to keep track of unique IDs for todos
    private static int todosCount = 0;

    /**
     * Resets the state of the todo list and ID counter.
     * This method is useful for testing and ensures a clean slate.
     */
    public static void resetState() {
        todos = new ArrayList<>(); // Clear the todo list
        todosCount = 0; // Reset the counter
    }

    // Static block to initialize the list with sample todos for demonstration purposes
    static {
        todos.add(new Todo(++todosCount, "in28minutes", "Get AWS Certified 1",
                LocalDate.now().plusYears(1), false));
        todos.add(new Todo(++todosCount, "in28minutes", "Learn DevOps 1",
                LocalDate.now().plusYears(2), false));
        todos.add(new Todo(++todosCount, "in28minutes", "Learn Full Stack Development 1",
                LocalDate.now().plusYears(3), false));
    }

    /**
     * Finds all todos for a given username.
     *
     * @param username The username whose todos should be retrieved.
     * @return A list of todos that belong to the specified username.
     */
    public List<Todo> findByUsername(String username) {
        return todos.stream()
                .filter(todo -> todo.getUsername().equalsIgnoreCase(username)) // Case-insensitive match
                .toList(); // Collect and return the filtered list
    }

    /**
     * Adds a new todo item to the list.
     *
     * @param username   The username to whom the todo belongs.
     * @param description A brief description of the task.
     * @param targetDate  The due date of the task.
     * @param done        The completion status of the task.
     */
    public void addTodo(String username, String description, LocalDate targetDate, boolean done) {
        Todo todo = new Todo(++todosCount, username, description, targetDate, done); // Create a new todo
        todos.add(todo); // Add the todo to the list
    }

    /**
     * Deletes a todo item by its ID.
     *
     * @param id The ID of the todo to be deleted.
     */
    public void deleteById(int id) {
        todos.removeIf(todo -> todo.getId() == id); // Remove the todo that matches the ID
    }

    /**
     * Finds a todo by its ID.
     *
     * @param id The ID of the todo to find.
     * @return The todo item if found, or null if no match is found.
     */
    public Todo findById(int id) {
        return todos.stream()
                .filter(todo -> todo.getId() == id) // Filter by matching ID
                .findFirst() // Return the first match, if any
                .orElse(null); // Return null if no match is found
    }

    /**
     * Updates an existing todo item.
     *
     * @param updatedTodo The todo item with updated fields.
     * @throws IllegalArgumentException If the todo with the specified ID is not found.
     */
    public void updateTodo(@Valid Todo updatedTodo) {
        for (int i = 0; i < todos.size(); i++) {
            if (todos.get(i).getId() == updatedTodo.getId()) {
                todos.set(i, updatedTodo); // Replace the existing todo with the updated one
                return;
            }
        }
        throw new IllegalArgumentException("Todo with ID " + updatedTodo.getId() + " not found."); // Handle not found
    }

    /**
     * Toggles the "done" status of a todo item by its ID.
     *
     * @param id The ID of the todo item to toggle.
     */
    public void toggleDone(int id) {
        Todo todo = findById(id); // Find the todo by ID
        if (todo != null) {
            todo.setDone(!todo.isDone()); // Toggle the "done" status
        }
    }
}
