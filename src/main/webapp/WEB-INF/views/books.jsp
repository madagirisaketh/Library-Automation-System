<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1">
<title>Books | Library Automation</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css?v=4">
</head>
<body class="app-body">
<c:set var="activePage" value="books" scope="request" />
<%@ include file="includes/header.jspf"%>

  <div class="page-heading">
    <div>
      <h1>Books</h1>
      <p>${isAdmin ? 'Manage your collection. Covers appear automatically from the ISBN.' : 'Find a book you like, then ask the librarian to issue it.'}</p>
    </div>
    <c:if test="${isAdmin}"><a class="btn btn-primary" href="${ctx}/books/new">＋ Add Book</a></c:if>
  </div>

  <div class="filter-bar">
    <input type="search" id="bookSearch" class="search" placeholder="🔍  Search by title, author or ISBN...">
    <select id="bookAvail" class="select">
      <option value="">All books</option>
      <option value="true">Available only</option>
    </select>
  </div>

  <div class="chips" id="bookChips">
    <button type="button" class="chip active" data-cat="">All</button>
    <c:forEach var="cat" items="${categories}">
      <button type="button" class="chip" data-cat="${cat.categoryId}"><c:out value="${cat.name}" /></button>
    </c:forEach>
  </div>

  <div class="book-grid" id="bookGrid">
    <c:forEach var="b" items="${books}">
      <article class="book-card" data-cat="${b.categoryId}" data-avail="${b.available}">
        <div class="cover" style="--h:${(b.bookId * 47) % 360}">
          <c:choose>
            <c:when test="${b.status != 'ACTIVE'}"><span class="badge badge-gray">Inactive</span></c:when>
            <c:when test="${b.availableCopies > 0}"><span class="badge badge-green">${b.availableCopies} available</span></c:when>
            <c:otherwise><span class="badge badge-red">All issued</span></c:otherwise>
          </c:choose>
          <span class="ph"><c:out value="${b.title}" /></span>
          <c:if test="${not empty b.isbn}"><img src="${b.coverUrl}" alt="" loading="lazy" onerror="this.remove()"></c:if>
        </div>
        <div class="book-body">
          <span class="cat-tag"><c:out value="${b.categoryName}" /></span>
          <h3 class="book-title"><c:out value="${b.title}" /></h3>
          <p class="book-author">by <c:out value="${b.author}" /></p>
          <p class="book-meta"><c:out value="${b.publisher}" /> · ${b.year}</p>
          <p class="book-meta">${b.availableCopies} of ${b.totalCopies} copies in</p>
          <c:if test="${not empty b.isbn}"><p class="book-isbn">ISBN <c:out value="${b.isbn}" /></p></c:if>
        </div>
        <c:if test="${isAdmin}">
          <div class="book-actions">
            <a class="btn btn-light btn-sm" href="${ctx}/books/edit/${b.bookId}">Edit</a>
            <c:if test="${b.available}"><a class="btn btn-primary btn-sm" href="${ctx}/borrow?bookId=${b.bookId}">Issue</a></c:if>
            <form class="inline-form" method="post" action="${ctx}/books/delete/${b.bookId}" onsubmit="return confirm('Delete this book?');">
              <button class="btn btn-danger btn-sm" type="submit">Delete</button>
            </form>
          </div>
        </c:if>
      </article>
    </c:forEach>
  </div>

  <div class="card empty-state" id="noResults" style="${empty books ? '' : 'display:none'}">
    <div class="empty-icon">📚</div>
    <h3>No books found</h3>
    <p>${empty books ? 'Add your first book using the button above.' : 'Try a different search or category.'}</p>
  </div>

<%@ include file="includes/footer.jspf"%>
</body>
</html>