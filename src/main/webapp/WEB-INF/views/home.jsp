<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1">
<title>Library Automation System</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css?v=4">
</head>
<body class="auth-page">
  <%@ include file="includes/coverwall.jspf"%>
  <div class="auth-shell">
    <section class="auth-brand">
      <div class="auth-logo">📚</div>
      <h1>Library<br>Automation System</h1>
      <p>Find your next great read, borrow it in seconds and keep every return on track.</p>
    </section>
    <section class="auth-card">
      <div class="role-badge">WELCOME</div>
      <h2>Sign in to continue</h2>
      <p class="subtitle">Choose how you want to log in.</p>
      <div class="choice-list">
        <a class="choice" href="${pageContext.request.contextPath}/admin/login">
          <span class="choice-icon">⚙️</span>
          <span class="choice-text"><strong>Admin Login</strong><small>Manage books, students and borrowing</small></span>
          <span class="choice-arrow">→</span>
        </a>
        <a class="choice" href="${pageContext.request.contextPath}/student/login">
          <span class="choice-icon">🎓</span>
          <span class="choice-text"><strong>Student Login</strong><small>Browse books and see your borrowings</small></span>
          <span class="choice-arrow">→</span>
        </a>
      </div>
    </section>
  </div>
</body>
</html>