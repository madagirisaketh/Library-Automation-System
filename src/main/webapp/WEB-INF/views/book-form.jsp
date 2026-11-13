<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1">
<title>${book.bookId == 0 ? 'Add Book' : 'Edit Book'} | Library Automation</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css?v=4">
</head>
<body class="app-body">
<c:set var="activePage" value="books" scope="request" />
<%@ include file="includes/header.jspf"%>

  <div class="page-heading">
    <div>
      <h1>${book.bookId == 0 ? 'Add New Book' : 'Edit Book'}</h1>
      <p>Fill in the details. Just type the ISBN and the cover shows up automatically.</p>
    </div>
    <a class="btn btn-light" href="${ctx}/books">← Back to Books</a>
  </div>

  <div class="form-layout">
    <section class="card form-card">
      <form method="post" action="${ctx}/books/save">
        <input type="hidden" name="bookId" value="${book.bookId}">
        <div class="form-grid">
          <div class="field span-2">
            <label>Book Title *</label>
            <input class="input" name="title" value="<c:out value='${book.title}'/>" placeholder="e.g. Clean Code" required autofocus>
          </div>
          <div class="field">
            <label>Author *</label>
            <input class="input" name="author" value="<c:out value='${book.author}'/>" placeholder="e.g. Robert C. Martin" required>
          </div>
          <div class="field">
            <label>Publisher</label>
            <input class="input" name="publisher" value="<c:out value='${book.publisher}'/>" placeholder="e.g. Prentice Hall">
          </div>
          <div class="field">
            <label>Category *</label>
            <select class="select" name="categoryId" required>
              <option value="">Select category</option>
              <c:forEach var="cat" items="${categories}">
                <option value="${cat.categoryId}" ${cat.categoryId == book.categoryId ? 'selected' : ''}><c:out value="${cat.name}" /></option>
              </c:forEach>
            </select>
          </div>
          <div class="field">
            <label>ISBN</label>
            <input class="input" id="isbnInput" name="isbn" value="<c:out value='${book.isbn}'/>" placeholder="e.g. 9780132350884">
          </div>
          <div class="field">
            <label>Date Published *</label>
            <input class="input" type="date" name="datePublished" value="${book.datePublished}" required>
          </div>
          <div class="field">
            <label>Number of Copies *</label>
            <input class="input" type="number" name="totalCopies" min="1" max="999" value="${book.totalCopies}" required>
          </div>
          <div class="field">
            <label>Status</label>
            <select class="select" name="status">
              <option value="ACTIVE" ${book.status == 'ACTIVE' ? 'selected' : ''}>Active (can be borrowed)</option>
              <option value="INACTIVE" ${book.status == 'INACTIVE' ? 'selected' : ''}>Inactive (hidden from issue)</option>
            </select>
          </div>
        </div>
        <div class="form-actions">
          <button class="btn btn-primary" type="submit">💾 Save Book</button>
          <a class="btn btn-light" href="${ctx}/books">Cancel</a>
        </div>
      </form>
    </section>

    <aside class="card preview-card">
      <h3>Cover Preview</h3>
      <div class="cover preview-cover" style="--h:230">
        <span class="ph">Cover appears here once you type a valid ISBN</span>
        <img id="coverPreview" alt="" style="display:none">
      </div>
      <p class="hint">Covers come from Openlibrary.org. If none is found, a coloured cover with the title is shown.</p>
    </aside>
  </div>

<%@ include file="includes/footer.jspf"%>
</body>
</html>