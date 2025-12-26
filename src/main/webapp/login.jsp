<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>用户登录</title>
    <style>
        body{
            margin:0;
            height:100vh;
            display:flex;
            align-items:center;
            justify-content:center;
            background: url("images/3.jpg") no-repeat center center fixed;
            background-size: cover;
            font-family: Arial, Helvetica, sans-serif;
        }


        .login-box{
            width:360px;
            background: rgba(255,255,255,0.80);
            padding:30px 35px;
            border-radius:12px;
            box-shadow:0 10px 25px rgba(0,0,0,0.50);
        }


        .login-box h2{
            text-align:center;
            color:#ff4f79;
            margin-bottom:25px;
        }

        .form-item{
            margin-bottom:15px;
            font-size:14px;
            color:#555;
        }

        .form-item label{
            display:block;
            margin-bottom:6px;
        }

        .form-item input[type=text],
        .form-item input[type=password]{
            width:100%;
            padding:8px 10px;
            border:1px solid #ddd;
            border-radius:6px;
            font-size:14px;
            box-sizing:border-box;
        }

        .form-item input:focus{
            outline:none;
            border-color:#ff6f91;
            box-shadow:0 0 0 2px rgba(255,111,145,0.15);
        }

        .remember{
            display:flex;
            align-items:center;
            font-size:13px;
            color:#666;
        }

        .remember input{
            margin-right:6px;
        }

        .error-msg{
            color:#e53935;
            font-size:13px;
            text-align:center;
            margin-bottom:10px;
        }

        .btn-box{
            text-align:center;
            margin-top:10px;
        }

        .login-btn{
            width:100%;
            background:#ff6f91;
            color:#fff;
            border:none;
            padding:10px 0;
            border-radius:6px;
            font-size:15px;
            cursor:pointer;
        }

        .login-btn:hover{
            background:#ff4f79;
        }

        .register-link{
            display:block;
            text-align:center;
            margin-top:15px;
            font-size:14px;
            color:#ff6f91;
            text-decoration:none;
        }

        .register-link:hover{
            text-decoration:underline;
        }
    </style>
</head>
<body>

<div class="login-box">
    <h2>🌸 用户登录</h2>

    <form action="loginServlet" method="post">
        <div class="form-item">
            <label>用户名</label>
            <input type="text" name="username" required>
        </div>

        <div class="form-item">
            <label>密码</label>
            <input type="password" name="password" required>
        </div>

        <div class="form-item remember">
            <input type="checkbox" name="remember" checked>
            记住密码
        </div>

        <% if(request.getAttribute("msg") != null){ %>
            <div class="error-msg">
                <%=request.getAttribute("msg")%>
            </div>
        <% } %>

        <div class="btn-box">
            <input type="submit" value="登录" class="login-btn">
        </div>

        <a href="register.jsp" class="register-link">还没有账号？去注册</a>
    </form>
</div>

</body>
</html>
