<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.flower.pojo.CartItem,java.util.List" %>
<html>
<head>
    <title>我的购物车</title>
    <style>body{
               margin:0;
               padding:30px;
               background:#fafafa;
               font-family: "Microsoft YaHei", sans-serif;
           }

           h2{
               color:#ff4f79;
               margin-bottom:10px;
           }

           a{
               color:#ff4f79;
               text-decoration:none;
           }

           a:hover{
               text-decoration:underline;
           }

           /* 卡片容器 */
           .cart-box{
               background:#fff;
               padding:20px;
               border-radius:10px;
               box-shadow:0 8px 20px rgba(0,0,0,0.08);
               max-width:900px;
           }

           /* 表格 */
           table{
               width:100%;
               border-collapse:collapse;
               margin:20px 0;
           }

           th{
               background:#ff6f91;
               color:#fff;
               padding:12px;
           }

           td{
               border-bottom:1px solid #eee;
               padding:12px;
               text-align:center;
           }

           tr:hover{
               background:#fff6f8;
           }

           /* 数量输入框 */
           input[type=number]{
               width:60px;
               padding:5px;
               border-radius:4px;
               border:1px solid #ccc;
           }

           /* 按钮 */
           .btn-submit{
               background:#ff6f91;
               color:#fff;
               border:none;
               padding:10px 18px;
               border-radius:6px;
               cursor:pointer;
               font-size:15px;
           }

           .btn-submit:hover{
               background:#ff4f79;
           }

           .btn-clear{
               margin-left:20px;
               color:#999;
           }

           .btn-clear:hover{
               color:#ff4f79;
           }

           /* 表单区 */
           .order-form div{
               margin:12px 0;
           }

           .order-form input[type=text]{
               padding:6px;
               width:260px;
               border-radius:4px;
               border:1px solid #ccc;
           }

           /* 空购物车 */
           .empty-cart{
               padding:40px;
               text-align:center;
               color:#999;
               font-size:16px;
           }

           .order-form{
               margin-top:20px;
           }

           .form-item{
               display:flex;
               align-items:center;
               margin:12px 0;
           }

           .form-item label{
               display:inline-block;
               width:90px;          /* 所有 label 占用一样的宽度 */
               text-align:right;    /* 中文右对齐，冒号自然对齐 */
               margin-right:10px;
               white-space:nowrap; /* 防止换行 */
           }


           .form-item input{
               width:260px;         /* ✅ 所有输入框统一长度 */
               padding:6px 8px;
               border:1px solid #ccc;
               border-radius:4px;
           }

</style>
</head>
<body>
<h2>🛒 我的购物车</h2>
<a href="${pageContext.request.contextPath}/index.jsp">← 返回首页继续购物</a>

<div style="color:red;margin-top:10px;">
    <%=request.getAttribute("msg")==null?"":request.getAttribute("msg")%>
</div>

<div class="cart-box">


<%
    List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
    if(cart == null || cart.isEmpty()){
%>
        <div class="empty-cart">
            🛒 购物车空空如也～<br><br>
            <a href="${pageContext.request.contextPath}/index.jsp">去逛逛鲜花吧 🌸</a>
        </div>

<%
    }else{
%>
        <table>
            <tr>
                <th>商品名称</th>
                <th>单价</th>
                <th>数量</th>
                <th>小计</th>
                <th>操作</th>
            </tr>
            <% for(CartItem item : cart){ %>
                <tr>
                    <td><%=item.getFlower().getFlowerName()%></td>
                    <td>¥<%=item.getFlower().getPrice()%></td>
                    <td>
                        <form action="${pageContext.request.contextPath}/cartServlet" method="get" style="display:inline;">
                            <input type="hidden" name="action" value="update">
                            <input type="hidden" name="fid" value="<%=item.getFlower().getFlowerId()%>">
                            <input type="number" name="num" value="<%=item.getNum()%>" min="1" max="<%=item.getFlower().getStock()%>" onchange="this.form.submit()">
                        </form>
                    </td>
                    <td>¥<%=item.getSubTotal()%></td>
                    <td>
                        <a href="${pageContext.request.contextPath}/cartServlet?action=remove&fid=<%=item.getFlower().getFlowerId()%>" onclick="return confirm('确定要删除该商品吗？')">删除</a>
                    </td>
                </tr>
            <% } %>
            <tr>
                <td colspan="3" style="text-align:right;font-size:16px;">
                    <b>订单总计：</b>
                </td>
                <td colspan="2" style="font-size:18px;color:#ff4f79;">
                    <b>¥
                        <%
                            double total = 0;
                            for(CartItem item : cart){
                                total += item.getSubTotal();
                            }
                            out.print(total);
                        %>
                    </b>
                </td>
            </tr>

        </table>

        <form action="${pageContext.request.contextPath}/orderServlet" method="post">
            <input type="hidden" name="action" value="create">
           <div class="order-form">
               <div class="form-item">
                   <label>收货人：</label>
                   <input type="text" name="receiver" required>
               </div>
               <div class="form-item">
                   <label>联系电话：</label>
                   <input type="text" name="phone" required>
               </div>
               <div class="form-item">
                   <label>收货地址：</label>
                   <input type="text" name="address" required>
               </div>
           </div>

            <div>
                <input type="submit" value="提交订单" style="background-color:#4CAF50;color:white;border:none;padding:8px 16px;cursor:pointer;">
                <a href="${pageContext.request.contextPath}/cartServlet?action=clear" onclick="return confirm('确定要清空购物车吗？')" style="margin-left:20px;">清空购物车</a>
            </div>
        </form>
<%
    }
%>
</div>

</body>
</html>