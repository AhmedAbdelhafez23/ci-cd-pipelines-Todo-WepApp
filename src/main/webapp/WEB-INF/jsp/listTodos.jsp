<%@ include file="common/header.jspf" %>
<%@ include file="common/navigation.jspf" %>
<div class="container">
    <h1>Your Todos</h1>
    <table class="table table-bordered">
        <thead class="table-dark">
            <tr>
                <th>Description</th>
                <th>Target Date</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${todos}" var="todo">
                <tr class="${todo.done ? 'table-success' : ''}">
                    <td>
                        ${todo.description}
                        <c:if test="${todo.done}">
                            <span class="badge bg-success ms-2">Completed</span>
                        </c:if>
                    </td>
                    <td>${todo.targetDate}</td>
                    <td>
                        <div class="d-flex">
                            <a href="delete-todo?id=${todo.id}" class="btn btn-danger btn-sm me-2">
                                <i class="fa fa-trash"></i> Delete
                            </a>
                            <a href="update-todo?id=${todo.id}" class="btn btn-warning btn-sm me-2">
                                <i class="fa fa-edit"></i> Update
                            </a>
                            <a href="toggle-todo?id=${todo.id}" 
                               class="btn ${todo.done ? 'btn-primary' : 'btn-success'} btn-sm">
                                <i class="${todo.done ? 'fa fa-undo' : 'fa fa-check'}"></i>
                                ${todo.done ? 'Undo' : 'Mark as Done'}
                            </a>
                        </div>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <a href="add-todo" class="btn btn-success mt-3">Add Todo</a>
</div>
<%@ include file="common/footer.jspf" %>
