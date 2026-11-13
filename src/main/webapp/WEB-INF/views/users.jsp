<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1">
<title>Users | Library Automation</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css?v=4">
</head>
<body class="app-body">
<c:set var="activePage" value="users" scope="request" />
<%@ include file="includes/header.jspf"%>

  <div class="page-heading">
    <div>
      <h1>Users</h1>
      <p>Everyone who can log in. Student logins are created automatically when you add a student.</p>
    </div>
  </div>

  <section class="card table-card">
    <div class="table-toolbar">
      <input type="search" id="tblSearch" class="search" placeholder="🔍  Search users...">
    </div>
    <div class="table-wrap">
      <table class="data-table" id="tbl">
        <thead><tr><th class="num">#</th><th>Username</th><th>Role</th><th>Linked Student</th><th>Status</th></tr></thead>
        <tbody>
          <c:forEach var="u" items="${users}">
            <tr data-status="">
              <td class="num">${u.userId}</td>
              <td class="primary-text"><c:out value="${u.username}" /></td>
              <td><span class="badge ${u.role == 'ADMIN' ? 'badge-amber' : 'badge-blue'}">${u.role}</span></td>
              <td><c:out value="${empty u.studentName ? '—' : u.studentName}" /></td>
              <td><span class="badge ${u.status == '1' ? 'badge-green' : 'badge-gray'}">${u.status == '1' ? 'Active' : 'Disabled'}</span></td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </div>
  </section>

<%@ include file="includes/footer.jspf"%>
</body>
</html>