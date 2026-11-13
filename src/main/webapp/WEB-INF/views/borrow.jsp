<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1">
<title>${isAdmin ? 'Issue & Return' : 'My Borrowings'} | Library Automation</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css?v=4">
</head>
<body class="app-body">
<c:set var="activePage" value="borrow" scope="request" />
<%@ include file="includes/header.jspf"%>

  <div class="page-heading">
    <div>
      <h1>${isAdmin ? 'Issue &amp; Return' : 'My Borrowings'}</h1>
      <p>${isAdmin ? 'Issue books to students and mark them returned. A student can hold up to 3 books.' : 'Your current and past borrowings.'}</p>
    </div>
  </div>

  <c:if test="${isAdmin}">
    <section class="card form-card">
      <div class="form-title">Issue a Book</div>
      <form method="post" action="${ctx}/borrow/issue">
        <div class="form-grid">
          <div class="field">
            <label>Student</label>
            <select class="select" name="studentId" required>
              <option value="">Select student</option>
              <c:forEach var="s" items="${students}">
                <option value="${s.studentId}"><c:out value="${s.code} - ${s.fullName}" /></option>
              </c:forEach>
            </select>
          </div>
          <div class="field">
            <label>Book</label>
            <select class="select" name="bookId" required>
              <option value="">Select book</option>
              <c:forEach var="b" items="${books}">
                <option value="${b.bookId}" ${b.bookId == selectedBookId ? 'selected' : ''}><c:out value="${b.title} - ${b.author}" /> (${b.availableCopies} left)</option>
              </c:forEach>
            </select>
          </div>
          <div class="field">
            <label>Due Date</label>
            <input class="input" type="date" name="dueDate" value="${defaultDue}" min="${today}" required>
          </div>
        </div>
        <div class="form-actions">
          <button class="btn btn-primary" type="submit">🔁 Issue Book</button>
        </div>
      </form>
    </section>
  </c:if>

  <section class="card table-card">
    <div class="table-toolbar">
      <input type="search" id="tblSearch" class="search" placeholder="🔍  Search...">
      <select id="tblStatus" class="select">
        <option value="">All</option>
        <option value="BORROWED">Borrowed</option>
        <option value="OVERDUE">Overdue</option>
        <option value="RETURNED">Returned</option>
      </select>
    </div>
    <div class="table-wrap">
      <table class="data-table" id="tbl">
        <thead>
          <tr>
            <th>Book</th>
            <c:if test="${isAdmin}"><th>Student</th></c:if>
            <th>Issued</th><th>Due</th><th>Returned</th><th>Status</th>
            <c:if test="${isAdmin}"><th>Action</th></c:if>
          </tr>
        </thead>
        <tbody>
          <c:forEach var="r" items="${borrowings}">
            <tr data-status="${r.status == 'RETURNED' ? 'RETURNED' : (r.overdue ? 'OVERDUE' : 'BORROWED')}">
              <td>
                <div class="person">
                  <div class="thumb" style="--h:${(r.bookId * 47) % 360}">
                    <c:if test="${not empty r.isbn}"><img src="${r.coverUrl}" alt="" loading="lazy" onerror="this.remove()"></c:if>
                  </div>
                  <span class="primary-text"><c:out value="${r.bookTitle}" /></span>
                </div>
              </td>
              <c:if test="${isAdmin}">
                <td><span class="primary-text"><c:out value="${r.studentName}" /></span><span class="subtext"><c:out value="${r.studentCode}" /></span></td>
              </c:if>
              <td>${r.borrowDate}</td>
              <td>${r.dueDate}</td>
              <td>${empty r.returnDate ? '—' : r.returnDate}</td>
              <td>
                <c:choose>
                  <c:when test="${r.status == 'RETURNED'}"><span class="badge badge-green">Returned</span></c:when>
                  <c:when test="${r.overdue}"><span class="badge badge-red">Overdue ${r.daysOverdue}d</span></c:when>
                  <c:otherwise><span class="badge badge-blue">Borrowed</span></c:otherwise>
                </c:choose>
              </td>
              <c:if test="${isAdmin}">
                <td>
                  <c:if test="${r.status == 'BORROWED'}">
                    <form class="inline-form" method="post" action="${ctx}/borrow/return/${r.borrowId}" onsubmit="return confirm('Mark this book as returned?');">
                      <button class="btn btn-primary btn-sm" type="submit">Mark Returned</button>
                    </form>
                  </c:if>
                </td>
              </c:if>
            </tr>
          </c:forEach>
          <c:if test="${empty borrowings}">
            <tr><td colspan="7"><div class="empty-state"><div class="empty-icon">🔁</div><h3>Nothing here yet</h3><p>Borrowed books will show up here.</p></div></td></tr>
          </c:if>
        </tbody>
      </table>
    </div>
  </section>

<%@ include file="includes/footer.jspf"%>
</body>
</html>