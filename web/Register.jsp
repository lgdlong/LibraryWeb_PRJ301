<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@page import="dto.UserError" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Register</title>


  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">


  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body class="bg-light d-flex align-items-center justify-content-center" style="min-height: 100vh;">

<div class="card shadow p-4" style="width: 400px;">
  <h3 class="text-center mb-4">Register</h3>

  <%
    UserError userError = (UserError) request.getAttribute("USER_ERROR");
    if (userError == null) {
      userError = new UserError();
    }
  %>

  <form action="${pageContext.request.contextPath}/register" method="post">

    <div class="mb-3">
      <label class="form-label">Full Name</label>
      <input type="text" name="fullname" class="form-control" required/>
      <% if (userError.getNameError() != null) { %>
      <div class="text-danger small mt-1"><%= userError.getNameError() %>
      </div>
      <% } %>
    </div>


    <div class="mb-3">
      <label class="form-label">Email</label>
      <input type="email" name="email" class="form-control" required/>
      <% if (userError.getEmailError() != null) { %>
      <div class="text-danger small mt-1"><%= userError.getEmailError() %>
      </div>
      <% } %>
    </div>


    <div class="mb-3">
      <label class="form-label">Password</label>
      <div class="input-group">
        <input type="password" id="password" name="password" class="form-control" required/>
        <button type="button" class="btn btn-outline-secondary" id="togglePassword">
          <i class="fa-solid fa-eye" id="eyeIcon1"></i>
        </button>
      </div>
      <% if (userError.getPasswordError() != null) { %>
      <div class="text-danger small mt-1"><%= userError.getPasswordError() %>
      </div>
      <% } %>
    </div>

    <!-- Confirm Password -->
    <div class="mb-3">
      <label class="form-label">Confirm Password</label>
      <div class="input-group">
        <input type="password" id="confirm" name="confirm" class="form-control" required/>
        <button type="button" class="btn btn-outline-secondary" id="toggleConfirm">
          <i class="fa-solid fa-eye" id="eyeIcon2"></i>
        </button>
      </div>
      <% if (userError.getConfirmError() != null) { %>
      <div class="text-danger small mt-1"><%= userError.getConfirmError() %>
      </div>
      <% } %>
    </div>

    <div class="d-grid gap-2">
      <input type="submit" name="action" value="Register" class="btn btn-primary"/>
    </div>

  </form>
</div>


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

<script>
  const togglePassword = document.getElementById("togglePassword");
  const passwordInput = document.getElementById("password");
  const eyeIcon1 = document.getElementById("eyeIcon1");

  togglePassword.addEventListener("click", () => {
    const type = passwordInput.type === "password" ? "text" : "password";
    passwordInput.type = type;
    eyeIcon1.classList.toggle("fa-eye");
    eyeIcon1.classList.toggle("fa-eye-slash");
  });

  const toggleConfirm = document.getElementById("toggleConfirm");
  const confirmInput = document.getElementById("confirm");
  const eyeIcon2 = document.getElementById("eyeIcon2");

  toggleConfirm.addEventListener("click", () => {
    const type = confirmInput.type === "password" ? "text" : "password";
    confirmInput.type = type;
    eyeIcon2.classList.toggle("fa-eye");
    eyeIcon2.classList.toggle("fa-eye-slash");
  });
</script>
</body>
</html>
