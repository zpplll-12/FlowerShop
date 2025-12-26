<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>用户注册</title>
    <style>
        body{
            margin:0;
            height:100vh;
            display:flex;
            align-items:center;
            justify-content:center;

            /* 背景图片 */
            background:
                linear-gradient(
                    rgba(0,0,0,0.1),
                    rgba(0,0,0,0.1)
                ),
                url("images/4.jpg") no-repeat center center;

            background-size:cover;
            font-family: Arial, Helvetica, sans-serif;
        }


        .register-box{
            width:420px;
            background: rgba(255,255,255,0.85);
            padding:30px 35px;
            border-radius:12px;
            box-shadow:0 10px 25px rgba(0,0,0,0.35);
        }

        .register-box h2{
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

        /* 验证码 */
        .verify-box{
            display:flex;
            align-items:center;
            gap:10px;
        }

        .verify-box input{
            width:120px;
        }

        .verify-box img{
            height:38px;
            cursor:pointer;
            border-radius:6px;
            border:1px solid #ddd;
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

        .register-btn{
            width:100%;
            background:#ff6f91;
            color:#fff;
            border:none;
            padding:10px 0;
            border-radius:6px;
            font-size:15px;
            cursor:pointer;
        }

        .register-btn:hover{
            background:#ff4f79;
        }

        .login-link{
            display:block;
            text-align:center;
            margin-top:15px;
            font-size:14px;
            color:#ff6f91;
            text-decoration:none;
        }

        .login-link:hover{
            text-decoration:underline;
        }
    </style>
</head>
<body>

<div class="register-box">
    <h2>🌸 用户注册</h2>

    <form action="registerServlet" method="post">

        <div class="form-item">
            <label>用户名</label>
            <input type="text" name="username" required>
        </div>

        <div class="form-item">
            <label>密码</label>
            <input type="password" name="password" required>
        </div>

        <div class="form-item">
            <label>手机号</label>
            <input type="text" name="phone">
        </div>

        <div class="form-item">
            <label>收货地址</label>
            <input type="text" name="address">
        </div>

        <div class="form-item">
            <label>验证码</label>
            <div class="verify-box">
                <input type="text" name="verifyCode" required>
                <img src="verifyCodeServlet"
                     title="点击刷新验证码"
                     onclick="this.src='verifyCodeServlet?'+Math.random()">
            </div>
        </div>

        <% if(request.getAttribute("msg") != null){ %>
            <div class="error-msg">
                <%=request.getAttribute("msg")%>
            </div>
        <% } %>

        <div class="btn-box">
            <input type="submit" value="注册" class="register-btn">
        </div>

        <a href="login.jsp" class="login-link">已有账号？去登录</a>

    </form>
</div>

</body>
</html>
