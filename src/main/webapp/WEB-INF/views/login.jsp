<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1">
<title>${title} | Library Automation</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css?v=4">
</head>
<body class="auth-page">
  <%@ include file="includes/coverwall.jspf"%>
  <div class="auth-shell">
    <section class="auth-brand">
      <div class="auth-logo">📚</div>
      <h1>Library<br>Automation System</h1>
      <p>Sign in to access your library workspace.</p>
    </section>
    <section class="auth-card">
      <div class="role-badge">${role} ACCESS</div>
      <h2>${title}</h2>
      <p class="subtitle">Enter your username and password to continue.</p>
      <form class="auth-form" method="post" action="${pageContext.request.contextPath}/login">
        <input type="hidden" name="role" value="${role}">
        <div class="field">
          <label>Username</label>
          <input class="input" type="text" name="username" placeholder="Enter username" value="<c:out value='${param.username}'/>" required autofocus>
        </div>
        <div class="field">
          <label>Password</label>
          <input class="input" type="password" name="password" placeholder="Enter password" required>
        </div>
        <c:if test="${not empty error}"><div class="auth-error"><c:out value="${error}"/></div></c:if>
        <button class="btn btn-primary auth-submit" type="submit">Sign in →</button>
      </form>
      <div class="auth-footer"><a href="${pageContext.request.contextPath}/">← Back</a></div>
    </section>
  </div>
</body>
</html>