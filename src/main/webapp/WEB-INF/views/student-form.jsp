<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1">
<title>${student.studentId == 0 ? 'Add Student' : 'Edit Student'}
	| Library Automation</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/style.css?v=4">
</head>
<body class="app-body">
	<c:set var="activePage" value="students" scope="request" />
	<%@ include file="includes/header.jspf"%>

	<div class="page-heading">
		<div>
			<h1>${student.studentId == 0 ? 'Add New Student' : 'Edit Student'}</h1>
			<p>${student.studentId == 0 ? 'A login is created automatically: username = Student ID in lowercase, password = student123.' : 'Update the student details below.'}</p>
		</div>
		<a class="btn btn-light" href="${ctx}/students">← Back to Students</a>
	</div>

	<section class="card form-card">
		<form method="post" action="${ctx}/students/save">
			<input type="hidden" name="studentId" value="${student.studentId}">
			<div class="form-grid">
				<div class="field">
					<label>Student ID *</label> <input class="input" name="code"
						value="<c:out value='${student.code}'/>"
						placeholder="e.g. STUDENT109" required>
				</div>
				<div class="field">
					<label>First Name *</label> <input class="input" name="firstName"
						value="<c:out value='${student.firstName}'/>" required>
				</div>
				<div class="field">
					<label>Last Name</label> <input class="input" name="lastName"
						value="<c:out value='${student.lastName}'/>">
				</div>
				<div class="field">
					<label>Email</label> <input class="input" type="email" name="email"
						value="<c:out value='${student.email}'/>"
						placeholder="name@college.edu">
				</div>
				<div class="field">
					<label>Contact Number</label> <input class="input" name="contact"
						value="<c:out value='${student.contact}'/>"
						placeholder="10-digit mobile number">
				</div>
				<div class="field">
					<label>Department</label> <input class="input" name="department"
						list="deptList" value="<c:out value='${student.department}'/>"
						placeholder="Choose or type">
					<datalist id="deptList">
						<option value="Computer Science">
						<option value="Electronics">
						<option value="Mechanical">
						<option value="Civil">
						<option value="Pharmacy">
						<option value="Business">
					</datalist>
				</div>
				<div class="field">
					<label>Course</label> <input class="input" name="course"
						value="<c:out value='${student.course}'/>"
						placeholder="e.g. B.Tech CSE">
				</div>
				<div class="field">
					<label>Status</label> <select class="select" name="status">
						<option value="ACTIVE"
							${student.status == 'ACTIVE' || student.status == '1' ? 'selected' : ''}>Active</option>
						<option value="INACTIVE"
							${student.status == 'INACTIVE' ? 'selected' : ''}>Inactive</option>
					</select>
				</div>
			</div>
			<div class="form-actions">
				<button class="btn btn-primary" type="submit">💾 Save
					Student</button>
				<a class="btn btn-light" href="${ctx}/students">Cancel</a>
			</div>
		</form>
	</section>

	<%@ include file="includes/footer.jspf"%>
</body>
</html>