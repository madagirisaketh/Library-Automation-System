<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1">
<title>Categories | Library Automation</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css?v=4">
</head>
<body class="app-body">
<c:set var="activePage" value="categories" scope="request" />
<%@ include file="includes/header.jspf"%>

  <div class="page-heading">
    <div>
      <h1>Categories</h1>
      <p>Group your books so students can find them quickly.</p>
    </div>
  </div>

  <section class="card form-card">
    <form class="inline-add" method="post" action="${ctx}/categories/save">
      <input class="input" name="name" placeholder="New category name, e.g. Mystery" required>
      <button class="btn btn-primary" type="submit">＋ Add Category</button>
    </form>
  </section>

  <section class="card table-card">
    <div class="table-wrap">
      <table class="data-table">
        <thead><tr><th class="num">#</th><th>Category</th><th>Books</th><th>Action</th></tr></thead>
        <tbody>
          <c:forEach var="cat" items="${categories}">
            <tr>
              <td class="num">${cat.categoryId}</td>
              <td class="primary-text"><c:out value="${cat.name}" /></td>
              <td><span class="badge badge-blue">${cat.bookCount} books</span></td>
              <td>
                <c:choose>
                  <c:when test="${cat.bookCount == 0}">
                    <form class="inline-form" method="post" action="${ctx}/categories/delete/${cat.categoryId}" onsubmit="return confirm('Delete this category?');">
                      <button class="btn btn-danger btn-sm" type="submit">Delete</button>
                    </form>
                  </c:when>
                  <c:otherwise><span class="muted">In use</span></c:otherwise>
                </c:choose>
              </td>
            </tr>
          </c:forEach>
          <c:if test="${empty categories}">
            <tr><td colspan="4"><div class="empty-state"><div class="empty-icon">🗂️</div><h3>No categories yet</h3><p>Add your first category above.</p></div></td></tr>
          </c:if>
        </tbody>
      </table>
    </div>
  </section>

<%@ include file="includes/footer.jspf"%>
</body>
</html>