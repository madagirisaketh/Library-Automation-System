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
      <h1>Hello, <c:out value="${sessionScope.user.username}" /> 👋</h1>
      <p>Pick up where you left off, or find something new to read.</p>
    </div>
    <div class="hero-actions">
      <a class="btn btn-accent" href="${ctx}/books">Browse Books</a>
      <a class="btn btn-glass" href="${ctx}/borrow">My Borrowings</a>
    </div>
  </section>

  <div class="stat-grid">
    <div class="stat"><div class="stat-icon bg-blue">📖</div><div><div class="stat-num">${stats.issued}</div><div class="stat-label">Books With Me</div></div></div>
    <div class="stat"><div class="stat-icon bg-red">⏰</div><div><div class="stat-num">${stats.overdue}</div><div class="stat-label">Overdue</div></div></div>
    <div class="stat"><div class="stat-icon bg-green">📚</div><div><div class="stat-num">${stats.available}</div><div class="stat-label">Books Available</div></div></div>
  </div>

  <div class="section-title"><h2>My Current Books</h2><a href="${ctx}/borrow">See history →</a></div>
  <c:choose>
    <c:when test="${not empty myLoans}">
      <div class="book-grid compact">
        <c:forEach var="r" items="${myLoans}">
          <article class="book-card">
            <div class="cover" style="--h:${(r.bookId * 47) % 360}">
              <c:choose>
                <c:when test="${r.overdue}"><span class="badge badge-red">Overdue ${r.daysOverdue}d</span></c:when>
                <c:otherwise><span class="badge badge-blue">Due ${r.dueDate}</span></c:otherwise>
              </c:choose>
              <span class="ph"><c:out value="${r.bookTitle}" /></span>
              <c:if test="${not empty r.isbn}"><img src="${r.coverUrl}" alt="" loading="lazy" onerror="this.remove()"></c:if>
            </div>
            <div class="book-body"><h3 class="book-title"><c:out value="${r.bookTitle}" /></h3></div>
          </article>
        </c:forEach>
      </div>
    </c:when>
    <c:otherwise><div class="card empty-state"><div class="empty-icon">📖</div><h3>No books borrowed</h3><p>Visit the library desk to borrow a book.</p></div></c:otherwise>
  </c:choose>

  <div class="section-title"><h2>Available to Borrow</h2><a href="${ctx}/books">Browse all →</a></div>
  <div class="book-grid compact">
    <c:forEach var="b" items="${arrivals}">
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