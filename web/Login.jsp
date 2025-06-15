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

<div class="card shadow p-4" style="min-width: 350px;">
  <h3 class="text-center mb-4">Login</h3>

  <form action="${pageContext.request.contextPath}/login" method="post">
    <div class="mb-3">
      <label for="email" class="form-label">Email</label>
      <input type="email" class="form-control" id="email" name="email" required/>
    </div>

    <div class="mb-3">
      <label for="password" class="form-label">Password</label>
      <div class="input-group">
        <input type="password" class="form-control" id="password" name="password" required/>
        <button class="btn btn-outline-secondary" type="button" id="togglePassword">
          <i class="fa-solid fa-eye" id="eyeIcon"></i>
        </button>
      </div>
    </div>

    <button type="submit" class="btn btn-primary w-100">Login</button>
    <a href="${pageContext.request.contextPath}/register" class="btn btn-secondary w-100 mt-2">Register</a>
    <%--            <a href="${pageContext.request.contextPath}/index.html" class="btn btn-secondary w-100 mt-2">Back to Home</a>--%>


  </form>

  <% String s = (String) request.getAttribute("ERROR");
    if (s != null && !s.isEmpty()) {
  %>
  <div class="mt-3 alert alert-danger text-center"><%= s %>
  </div>
  <% } %>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>


<script>
  const togglePassword = document.getElementById('togglePassword');
  const passwordInput = document.getElementById('password');
  const eyeIcon = document.getElementById('eyeIcon');

  togglePassword.addEventListener('click', function () {
    const type = passwordInput.getAttribute('type') === 'password' ? 'text' : 'password';
    passwordInput.setAttribute('type', type);
    eyeIcon.classList.toggle('fa-eye');
    eyeIcon.classList.toggle('fa-eye-slash');
  });
</script>
</body>
</html>
