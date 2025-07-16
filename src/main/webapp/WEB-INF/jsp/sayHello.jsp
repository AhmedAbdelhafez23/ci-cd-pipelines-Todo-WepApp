<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Colorful To-Do List</title>
    <style>
        /* Basic Reset */
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Poppins', sans-serif;
        }

        /* Center content */
        body, html {
            height: 100%;
            display: flex;
            align-items: center;
            justify-content: center;
            background: linear-gradient(135deg, #ff6a00, #ee0979, #ff206e);
            padding: 20px;
        }

        /* Main container styling */
        .todo-container {
            width: 100%;
            max-width: 500px;
            background-color: #ffffff;
            padding: 20px;
            border-radius: 15px;
            box-shadow: 0px 8px 20px rgba(0, 0, 0, 0.2);
            animation: fadeIn 1.5s ease;
        }

        /* Title */
        .todo-container h2 {
            font-size: 2em;
            color: #333;
            margin-bottom: 20px;
            font-weight: 700;
            text-align: center;
            color: #ff007f;
            text-shadow: 1px 1px #ffe53b;
        }

        /* Sub-title */
        .todo-container h3 {
            font-size: 1.5em;
            color: #ff4d4d;
            margin: 20px 0 10px;
            font-weight: 600;
            text-align: center;
            text-shadow: 1px 1px #ffaf7b;
        }

        /* Input box and button container */
        .input-container {
            display: flex;
            margin-bottom: 20px;
            gap: 10px;
        }

        /* Input styling */
        .todo-input {
            flex: 1;
            padding: 12px;
            font-size: 1em;
            border: 2px solid #ddd;
            border-radius: 10px;
            outline: none;
            transition: border-color 0.3s, transform 0.3s;
        }

        .todo-input:focus {
            border-color: #ff007f;
            transform: scale(1.05);
        }

        /* Add button */
        .add-button {
            padding: 12px 20px;
            background-color: #ff4e50;
            color: #fff;
            font-weight: bold;
            border: none;
            cursor: pointer;
            border-radius: 10px;
            transition: background-color 0.3s, transform 0.3s;
        }

        .add-button:hover {
            background-color: #f9d423;
            transform: scale(1.1);
        }

        /* To-do list */
        .todo-list, .done-list {
            margin-top: 10px;
            list-style-type: none;
            padding: 0;
        }

        /* Individual to-do item */
        .todo-item {
            display: flex;
            align-items: center;
            justify-content: space-between;
            padding: 12px 15px;
            background-color: #f9f9f9;
            border: 1px solid #ddd;
            border-radius: 10px;
            margin-bottom: 10px;
            box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.05);
            transition: background-color 0.3s, transform 0.3s;
        }

        .todo-item:hover {
            background-color: #ffd54f;
            transform: translateY(-3px);
        }

        /* Checkbox styling */
        .todo-item input[type="checkbox"] {
            accent-color: #ff6a00;
            transform: scale(1.3);
            cursor: pointer;
            margin-right: 10px;
        }

        /* Task text styling */
        .task-text {
            flex: 1;
            font-size: 1em;
            color: #333;
        }

        /* Completed task */
        .todo-item.completed {
            background-color: #c6e5b1;
            color: #6c757d;
            text-decoration: line-through;
            transition: background-color 0.3s;
        }

        /* Delete button */
        .delete-button {
            background-color: #ff206e;
            color: #ffffff;
            border: none;
            padding: 6px 12px;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s, transform 0.3s;
        }

        .delete-button:hover {
            background-color: #ff4e50;
            transform: scale(1.1);
        }

        /* Spacing between Active and Done sections */
        .done-section {
            margin-top: 40px;
            padding-top: 20px;
            border-top: 3px dashed #ddd;
        }

        /* Fade-in animation */
        @keyframes fadeIn {
            from {
                opacity: 0;
                transform: translateY(-10px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }
    </style>
</head>
<body>

<div class="todo-container">
    <h2>Colorful To-Do List</h2>
    <div class="input-container">
        <input type="text" class="todo-input" placeholder="Add a new task...">
        <button class="add-button">Add</button>
    </div>
    <h3>Active Tasks</h3>
    <ul class="todo-list"></ul>
    
    <div class="done-section">
        <h3>Marked as Done</h3>
        <ul class="done-list"></ul>
    </div>
</div>

<script>
    // Selecting elements
    const todoInput = document.querySelector('.todo-input');
    const addButton = document.querySelector('.add-button');
    const todoList = document.querySelector('.todo-list');
    const doneList = document.querySelector('.done-list');

    // Function to add a new to-do item
    function addTodo() {
        const taskText = todoInput.value.trim();
        if (taskText === '') return; // Don't add empty tasks

        // Create a new list item
        const todoItem = document.createElement('li');
        todoItem.classList.add('todo-item');

        // Create checkbox
        const checkbox = document.createElement('input');
        checkbox.type = 'checkbox';
        checkbox.onclick = () => markAsDone(todoItem);

        // Create the task text
        const task = document.createElement('span');
        task.textContent = taskText;
        task.classList.add('task-text');

        // Create delete button
        const deleteButton = document.createElement('button');
        deleteButton.classList.add('delete-button');
        deleteButton.textContent = 'Delete';
        deleteButton.onclick = () => {
            todoItem.remove(); // Remove the item
        };

        // Append elements to the list item
        todoItem.appendChild(checkbox);
        todoItem.appendChild(task);
        todoItem.appendChild(deleteButton);

        // Append list item to the todo list
        todoList.appendChild(todoItem);

        // Clear the input
        todoInput.value = '';
    }

    // Function to mark a task as done
    function markAsDone(todoItem) {
        // Remove from active tasks
        todoList.removeChild(todoItem);

        // Remove the checkbox to prevent unchecking
        todoItem.querySelector('input[type="checkbox"]').remove();
        
        // Append to the done list
        doneList.appendChild(todoItem);

        // Update styling to show it's completed
        todoItem.classList.add('completed');
    }

    // Event listeners
    addButton.addEventListener('click', addTodo);
    todoInput.addEventListener('keypress', (e) => {
        if (e.key === 'Enter') addTodo(); // Add on Enter key press
    });
</script>

</body>
</html>
