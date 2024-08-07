<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js" integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy" crossorigin="anonymous"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <title>Login - Welcome to Home Library</title>
    <link rel="stylesheet" type="text/css" href="styleLogin.css">
    <script>
        function redirectToHome(event) {
            event.preventDefault();
            window.location.href = 'home.jsp';
        }

    </script>
</head>

<body>

<form class="form-signin" method='post' action='login' onsubmit="redirectToHome(event)">
    <img class="mb-3" src="logo.svg" alt="" width="100" height="100">
    <h1 class="h4 fw-normal">
        <span style="color: #fff;">First, log in to your</span>
        <span style="color: #0076a3;">HL</span>
        <span style="color: #fff;">account:</span>
    </h1>

    <div class="mt-2 form-floating login-email-custom">
        <input type="email" class="form-control" id="floatingInput" name="username" placeholder="name@example.com" required>
        <label for="floatingInput">Email address</label>
    </div>
    <div class="mt-2 form-floating login-password-custom">
        <input type="password" class="form-control" id="floatingPassword" name="password" placeholder="Password" required>
        <label for="floatingPassword">Password</label>
    </div>

    <div class="mt-2 mb-2 form-check text-start my-3">
        <input class="form-check-input" type="checkbox" name="remember-me" id="flexCheckDefault">
        <label class="form-check-label" style="color: #0076a3;" for="flexCheckDefault">
            Remember me
        </label>
    </div>
    <button class="btn btn-primary w-100 py-2" id="loginButton" type="submit">Log in</button>
</form>

</body>


</html>

