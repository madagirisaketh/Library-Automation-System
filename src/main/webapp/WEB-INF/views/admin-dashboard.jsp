<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1">
<title>Dashboard | Library Automation</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css?v=4">
</head>
<body class="app-body">
<c:set var="activePage" value="home" scope="request" />
<%@ include file="includes/header.jspf"%>

  <section class="hero-banner">
    <div>
      <h1>Welcome back, <c:out value="${sessionScope.user.username}" /> 👋</h1>
      <p>Here is what is happening in your library today.</p>
    </div>
    <div class="hero-actions">
      <a class="btn btn-accent" href="${ctx}/books/new">＋ Add Book</a>
      <a class="btn btn-glass" href="${ctx}/borrow">Issue a Book</a>
    </div>
  </section>

  <div class="stat-grid">
    <div class="stat"><div class="stat-icon bg-indigo">📚</div><div><div class="stat-num">${stats.titles}</div><div class="stat-label">Book Titles</div></div></div>
    <div class="stat"><div class="stat-icon bg-amber">🗂️</div><div><div class="stat-num">${stats.categories}</div><div class="stat-label">Categories</div></div></div>
    <div class="stat"><div class="stat-icon bg-green">🎓</div><div><div class="stat-num">${stats.students}</div><div class="stat-label">Active Students</div></div></div>
    <div class="stat"><div class="stat-icon bg-blue">🔁</div><div><div class="stat-num">${stats.issued}</div><div class="stat-label">Books Issued</div></div></div>
    <div class="stat"><div class="stat-icon bg-red">⏰</div><div><div class="stat-num">${stats.overdue}</div><div class="stat-label">Overdue</div></div></div>
    <div class="stat"><div class="stat-icon bg-slate">✅</div><div><div class="stat-num">${stats.returned}</div><div class="stat-label">Returned</div></div></div>
  </div>

  <div class="dash-grid">
    <section class="card panel">
      <div class="panel-head"><h2>Currently Issued</h2><a href="${ctx}/borrow">View all →</a></div>
      <c:forEach var="r" items="${activeLoans}">
        <div class="loan-row">
          <div class="thumb" style="--h:${(r.bookId * 47) % 360}">
            <c:if test="${not empty r.isbn}"><img src="${r.coverUrl}" alt="" loading="lazy" onerror="this.remove()"></c:if>
          </div>
          <div class="loan-info">
            <strong><c:out value="${r.bookTitle}" /></strong>
            <span><c:out value="${r.studentName}" /> · due ${r.dueDate}</span>
          </div>
          <c:choose>
            <c:when test="${r.overdue}"><span class="badge badge-red">Overdue ${r.daysOverdue}d</span></c:when>
            <c:otherwise><span class="badge badge-blue">Borrowed</span></c:otherwise>
          </c:choose>
        </div>
      </c:forEach>
      <c:if test="${empty activeLoans}"><p class="muted">No books are issued right now.</p></c:if>
    </section>

    <section class="card panel">
      <div class="panel-head"><h2>Quick Actions</h2></div>
      <div class="quick-links">
        <a class="quick" href="${ctx}/books/new"><span>📖</span>Add a new book</a>
        <a class="quick" href="${ctx}/borrow"><span>🔁</span>Issue or return a book</a>
        <a class="quick" href="${ctx}/students/new"><span>🎓</span>Register a student</a>
        <a class="quick" href="${ctx}/categories"><span>🗂️</span>Manage categories</a>
      </div>
    </section>
  </div>

  <div class="section-title"><h2>Recently Added Books</h2><a href="${ctx}/books">Browse all →</a></div>
  <div class="book-grid compact">
    <c:forEach var="b" items="${recentBooks}">
      <article class="book-card">
        <div class="cover" style="--h:${(b.bookId * 47) % 360}">
          <span class="ph"><c:out value="${b.title}" /></span>
          <c:if test="${not empty b.isbn}"><img src="${b.coverUrl}" alt="" loading="lazy" onerror="this.remove()"></c:if>
        </div>
        <div class="book-body">
          <h3 class="book-title"><c:out value="${b.title}" /></h3>
          <p class="book-author"><c:out value="${b.author}" /></p>
        </div>
      </article>
    </c:forEach>
  </div>

<%@ include file="includes/footer.jspf"%>
</body>
</html>