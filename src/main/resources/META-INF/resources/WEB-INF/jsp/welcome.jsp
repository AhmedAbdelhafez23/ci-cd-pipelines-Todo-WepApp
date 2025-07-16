<%@ include file="common/header.jspf" %>
<%@ include file="common/navigation.jspf" %>	

<div class="container text-center">
	<h1>Welcome, ${name}!</h1>
	<p>
		<a href="list-todos" class="btn btn-primary btn-lg mt-3">
			<i class="fa fa-tasks"></i> Manage Your Todos
		</a>
	</p>
</div>

<%@ include file="common/footer.jspf" %>
