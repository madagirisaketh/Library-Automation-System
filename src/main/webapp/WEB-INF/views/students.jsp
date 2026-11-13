<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1">
<title>Students | Library Automation</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css?v=4">
</head>
<body class="app-body">
<c:set var="activePage" value="students" scope="request" />
<%@ include file="includes/header.jspf"%>

  <div class="page-heading">
    <div>
      <h1>Students</h1>
      <p>Register students and keep their details up to date.</p>
    </div>
    <a class="btn btn-primary" href="${ctx}/students/new">＋ Add Student</a>
  </div>

  <section class="card table-card">
    <div class="table-toolbar">
      <input type="search" id="tblSearch" class="search" placeholder="🔍  Search students...">
    </div>
    <div class="table-wrap">
      <table class="data-table" id="tbl">
        <thead>
          <tr><th>Student</th><th>Student ID</th><th>Contact</th><th>Department / Course</th><th>Status</th><th>Action</th></tr>
        </thead>
        <tbody>
          <c:forEach var="s" items="${students}">
            <tr data-status="">
              <td>
                <div class="person">
                  <span class="mini-avatar"><c:out value="${s.firstName.substring(0,1)}" /></span>
                  <span><span class="primary-text"><c:out value="${s.fullName}" /></span><span class="subtext"><c:out value="${s.email}" /></span></span>
                </div>
              </td>
              <td><c:out value="${s.code}" /></td>
              <td><c:out value="${s.contact}" /></td>
              <td><c:out value="${s.department}" /><span class="subtext"><c:out value="${s.course}" /></span></td>
              <td>
                <c:choose>
                  <c:when test="${s.status == 'ACTIVE' || s.status == '1'}"><span class="badge badge-green">Active</span></c:when>
                  <c:otherwise><span class="badge badge-gray">Inactive</span></c:otherwise>
                </c:choose>
              </td>
              <td>
                <div class="row-actions">
                  <a class="btn btn-light btn-sm" href="${ctx}/students/edit/${s.studentId}">Edit</a>
                  <form class="inline-form" method="post" action="${ctx}/students/delete/${s.studentId}" onsubmit="return confirm('Delete this student?');">
                    <button class="btn btn-danger btn-sm" type="submit">Delete</button>
                  </form>
                </div>
              </td>
            </tr>
          </c:forEach>
          <c:if test="${empty students}">
            <tr><td colspan="6"><div class="empty-state"><div class="empty-icon">🎓</div><h3>No students yet</h3><p>Add your first student with the button above.</p></div></td></tr>
          </c:if>
        </tbody>
      </table>
    </div>
  </section>

<%@ include file="includes/footer.jspf"%>
</body>
</html>