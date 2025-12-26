<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.flower.pojo.Flower,com.flower.pojo.Announcement,com.flower.service.FlowerService,com.flower.service.AnnouncementService,java.util.List" %>
<html>
<head>
    <title>网上花店-首页</title>
    <style>
        body{
            margin:0;
            padding:20px;
            background:#fafafa;
        }

        .header{
            text-align:center;
            margin-bottom:30px;
        }

        .ann{
            background:#fff6f8;
            padding:15px;
            margin-bottom:20px;
            border-left:5px solid #ff6f91;
            border-radius:6px;
        }

        .notice-text{
            color:#666;
            font-size:14px;
            margin:8px 0 12px 0;
        }

        .flower{
            border:1px solid #eee;
            padding:15px;
            margin:10px;
            float:left;
            width:250px;
            background:#fff;
            border-radius:8px;
            box-shadow:0 2px 8px rgba(0,0,0,0.05);
        }

        .flower{
            transition: all 0.25s ease;
        }

        .flower:hover{
            transform: translateY(-6px) scale(1.02);
            box-shadow: 0 10px 25px rgba(0,0,0,0.12);
        }


        .flower h4{
            color:#ff4f79;
            margin-bottom:8px;
        }

        .flower input[type=submit]{
            background:#ff6f91;
            color:#fff;
            border:none;
            padding:6px 10px;
            border-radius:4px;
            cursor:pointer;
        }

        .flower input[type=submit]:hover{
            background:#ff4f79;
        }

.nav{
    margin-top:10px;
    background:#ff6f91;
    padding:10px 0;
    border-radius:6px;
}

.nav a{
    color:#fff;
    text-decoration:none;
    margin:0 15px;
    font-size:15px;
    padding:6px 10px;
    border-radius:4px;
    transition: background 0.2s;
}

.nav a:hover{
    background:#ff4f79;
}

.username{
    color:#fff;
    margin-left:10px;
    font-weight:bold;
}

.flower-img{
    width:100%;
    height:160px;
    object-fit:cover;
    border-radius:6px;
    margin-bottom:8px;
}

    .add-cart-btn{
        position: relative;
        overflow: hidden;
    }

    .add-cart-btn:active{
        transform: scale(0.95);
    }

    .add-cart-btn::after{
        content:"✔ 已加入";
        position:absolute;
        left:0;
        top:0;
        width:100%;
        height:100%;
        background:#4caf50;
        color:#fff;
        display:flex;
        align-items:center;
        justify-content:center;
        opacity:0;
        transition: opacity 0.3s;
    }

    .add-cart-btn.clicked::after{
        opacity:1;
    }

    .category-box{
        margin:15px 0;
    }

    .category-box a{
        display:inline-block;
        padding:8px 16px;
        margin:6px 8px 6px 0;
        background:#fff;
        color:#ff4f79;
        border:1px solid #ff6f91;
        border-radius:20px;
        text-decoration:none;
        font-size:14px;
        transition: all 0.25s ease;
    }

    .category-box a:hover{
        background:#ff6f91;
        color:#fff;
        transform: translateY(-2px);
        box-shadow:0 4px 10px rgba(0,0,0,0.12);
    }

    /* 当前选中分类 */
    .category-box a.active{
        background:#ff4f79;
        color:#fff;
        font-weight:bold;
    }


    </style>

</head>
<body>
<div class="header">
    <h1>🌸 浪漫花语网上花店 🌸</h1>

    <div class="nav">
            <a href="index.jsp">首页</a>
            <a href="user/cart.jsp">购物车</a>
            <a href="user/order_list.jsp">我的订单</a>

            <% if(session.getAttribute("user")==null){ %>
                <a href="login.jsp">登录</a>
                <a href="register.jsp">注册</a>
            <% }else{ %>
                <a href="login.jsp">退出</a>
                <span class="username">
                    <%=((com.flower.pojo.User)session.getAttribute("user")).getUsername()%>
                </span>
            <% } %>
        </div>

</div>

<% AnnouncementService annService = new AnnouncementService(); List<Announcement> annList = annService.findAll(); %>
<div class="ann">
    <h3>📢 店铺公告</h3>
    <p class="notice-text">
        🌸 本店鲜花当天采摘，当日发货，支持同城急送，祝您生活如花般绽放 🌸
    </p>

    <% for(Announcement ann : annList){ %>
        <p><b><%=ann.getTitle()%></b> - <%=ann.getPublishTime()%></p>
        <p><%=ann.getContent()%></p>
    <% } %>
</div>

<div class="category-box">
    <h3>💐 鲜花分类</h3>

    <%
        String currentCategory = request.getParameter("category");
    %>

    <a href="index.jsp"
       class="<%=currentCategory==null ? "active" : ""%>">
        全部鲜花
    </a>

    <a href="index.jsp?category=爱情花束"
       class="<%="爱情花束".equals(currentCategory) ? "active" : ""%>">
        爱情花束
    </a>

    <a href="index.jsp?category=祝福花束"
       class="<%="祝福花束".equals(currentCategory) ? "active" : ""%>">
        祝福花束
    </a>
</div>


<div style="clear:both;margin-top:20px;">
    <h3>🌹 鲜花列表</h3>
    <% FlowerService flowerService = new FlowerService(); List<Flower> flowerList;
       String category = request.getParameter("category");
       if(category!=null) flowerList = flowerService.findByCategory(category);
       else flowerList = flowerService.findAll();
       for(Flower flower : flowerList){ %>
        <div class="flower">
        <img src="<%=request.getContextPath()%>/images/flower<%=flower.getFlowerId()%>.jpg"
             class="flower-img">


            <h4><%=flower.getFlowerName()%></h4>
            <p>价格：¥<%=flower.getPrice()%></p>
            <p>分类：<%=flower.getCategory()%></p>
            <p>库存：<%=flower.getStock()%>件</p>
            <p><%=flower.getDescription()%></p>
            <form action="cartServlet" method="get">
                <input type="hidden" name="action" value="add">
                <input type="hidden" name="fid" value="<%=flower.getFlowerId()%>">
                数量：<input type="number" name="num" value="1" min="1" max="<%=flower.getStock()%>">
                <input type="submit" value="加入购物车" class="add-cart-btn">



            </form>
        </div>
    <% } %>
</div>

<script>
    function isLogin(){
        return <%=session.getAttribute("user")!=null%>;
    }

    document.querySelectorAll(".add-cart-btn").forEach(btn=>{
        btn.addEventListener("click",function(){
            if(!isLogin()){
                alert("请先登录后再加入购物车 🌸");
                location.href="login.jsp";
                return;
            }

            this.classList.add("clicked");

            let formId = this.getAttribute("data-form");
            let form = document.getElementById(formId);

            setTimeout(()=>{
                form.submit();
            },500);
        });
    });
</script>




</body>
</html>