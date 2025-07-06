<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Login</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body class="bg-light d-flex align-items-center justify-content-center" style="height: 100vh;">

<div class="card shadow p-4 rounded-4" style="width: 100%; max-width: 400px;">
  <h3 class="text-center mb-4 fw-bold">Login</h3>

  <form action="${pageContext.request.contextPath}/login" method="post" novalidate>
    <div class="mb-3">
      <label for="email" class="form-label">Email</label>
      <div class="input-group">
        <span class="input-group-text"><i class="fa-solid fa-envelope"></i></span>
        <input type="email" class="form-control" id="email" name="email" required placeholder="example@email.com"/>
      </div>
    </div>

    <div class="mb-3">
      <label for="password" class="form-label">Password</label>
      <div class="input-group">
        <span class="input-group-text"><i class="fa-solid fa-lock"></i></span>
        <input type="password" class="form-control" id="password" name="password" required placeholder="••••••••"/>
        <button class="btn btn-outline-secondary" type="button" id="togglePassword">
          <i class="fa-solid fa-eye" id="eyeIcon"></i>
        </button>
      </div>
    </div>

    <button type="submit" class="btn btn-primary w-100">Login</button>
    <a href="${pageContext.request.contextPath}/register" class="btn btn-outline-secondary w-100 mt-2">Register</a>
  </form>

  <% String error = (String) request.getAttribute("ERROR");
     if (error != null && !error.isEmpty()) {
  %>
  <div class="mt-3 alert alert-danger text-center"><%= error %></div>
  <% } %>

  <% String errorAttr = (String) request.getAttribute("ERROR_ATTRIBUTE");
     if (errorAttr != null && !errorAttr.isEmpty()) {
  %>
  <div class="mt-3 alert alert-danger text-center"><%= errorAttr %></div>
  <% } %>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>
  const togglePassword = document.getElementById('togglePassword');
  const passwordInput = document.getElementById('password');
  const eyeIcon = document.getElementById('eyeIcon');

  togglePassword.addEventListener('click', function () {
    const isPassword = passwordInput.getAttribute('type') === 'password';
    passwordInput.setAttribute('type', isPassword ? 'text' : 'password');
    eyeIcon.classList.toggle('fa-eye');
    eyeIcon.classList.toggle('fa-eye-slash');
  });
</script>
</body>
</html>
