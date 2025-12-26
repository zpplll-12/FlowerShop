<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.flower.pojo.Order,com.flower.pojo.OrderItem,com.flower.pojo.User,java.util.List,com.flower.service.OrderService" %>
<html>
<head>
    <title>我的订单</title>
    <style>
        body{
            margin:20px;
            background:#fafafa;
            font-family: Arial, Helvetica, sans-serif;
        }

        h2{
            color:#ff4f79;
        }

        a{
            color:#ff6f91;
            text-decoration:none;
            margin-right:15px;
        }

        a:hover{
            text-decoration:underline;
        }

        .order-box{
            background:#fff;
            border-radius:10px;
            padding:20px;
            margin:25px 0;
            box-shadow:0 8px 20px rgba(0,0,0,0.06);
        }

        /* 订单头部 */
        .order-header{
            display:flex;
            justify-content:space-between;
            margin-bottom:10px;
            font-weight:bold;
        }

        .order-id{
            color:#333;
        }

        .order-status{
            padding:4px 10px;
            border-radius:20px;
            font-size:13px;
        }

        /* 不同状态颜色 */
        .status-待付款{ background:#fff3cd; color:#856404; }
        .status-已付款{ background:#e8f5e9; color:#2e7d32; }

        /* 订单信息 */
        .order-info{
            font-size:14px;
            color:#555;
            line-height:1.8;
            margin-bottom:10px;
        }

        .order-total{
            font-size:16px;
            font-weight:bold;
            color:#ff4f79;
            margin-bottom:15px;
        }

        /* 商品表格 */
        table{
            width:100%;
            border-collapse:collapse;
            margin-top:10px;
        }

        th{
            background:#ff6f91;
            color:#fff;
            padding:10px;
        }

        td{
            padding:10px;
            border-bottom:1px solid #eee;
        }

        /* 按钮 */
        .pay-btn{
            background:#4caf50;
            color:#fff;
            border:none;
            padding:8px 16px;
            border-radius:6px;

            .cancel-btn{
                background:#f44336;
                color:#fff;
                border:none;
                padding:8px 16px;
                border-radius:6px;
                cursor:pointer;
            }

            .cancel-btn:hover{
                background:#d32f2f;
            }


    </style>
</head>
<body>
<h2>我的订单列表</h2>
<a href="${pageContext.request.contextPath}/index.jsp">返回首页</a>
<a href="${pageContext.request.contextPath}/user/cart.jsp" style="margin-left:20px;">去购物车</a>

<%
    User user = (User) session.getAttribute("user");
    if(user == null){
        response.sendRedirect(request.getContextPath() + "/login.jsp");

        return;
    }
    OrderService orderService = new OrderService();
    List<Order> orderList = orderService.findByUserId(user.getUserId());
    if(orderList == null || orderList.isEmpty()){
%>
        <h3>您暂无订单记录，请先下单！</h3>
<%
    }else{
        for(Order order : orderList){
%>
            <div class="order-box">
                <div class="order-header">
                    <div class="order-id">订单号：<%=order.getOrderId()%></div>
                    <div class="order-status status-<%=order.getOrderStatus()%>">
                        <%=order.getOrderStatus()%>
                    </div>
                </div>


                <div class="order-info">
                    下单时间：<%=order.getOrderTime()%>
                </div>

                <div class="order-info">
                    <div>收货人：<%=order.getReceiver()%></div>
                    <div>联系电话：<%=order.getPhone()%></div>
                    <div>收货地址：<%=order.getAddress()%></div>
                </div>

                <p>订单总价：¥<%=order.getTotalPrice()%></p>

                <h4>订单商品明细</h4>
                <table>
                    <tr>
                        <th>商品ID</th>
                        <th>购买数量</th>
                        <th>商品单价</th>
                    </tr>
                    <% for(OrderItem item : order.getItemList()){ %>
                        <tr>
                            <td><%=item.getFlowerId()%></td>
                            <td><%=item.getNum()%></td>
                            <td>¥<%=item.getPrice()%></td>
                        </tr>
                    <% } %>
                </table>

                <% if("待付款".equals(order.getOrderStatus())){ %>
                    <form action="${pageContext.request.contextPath}/orderServlet"
                          method="post"
                          style="margin-top:10px; display:inline-block;">

                        <input type="hidden" name="action" value="updateStatus">
                        <input type="hidden" name="oid" value="<%=order.getOrderId()%>">
                        <input type="hidden" name="status" value="已付款">

                        <input type="submit"
                               value="确认付款"
                               class="pay-btn"
                               onclick="return confirm('确定要支付该订单吗？')">
                    </form>

                    <form action="${pageContext.request.contextPath}/orderServlet"
                          method="post"
                          style="margin-top:10px; display:inline-block; margin-left:10px;">

                        <input type="hidden" name="action" value="updateStatus">
                        <input type="hidden" name="oid" value="<%=order.getOrderId()%>">
                        <input type="hidden" name="status" value="已取消">

                        <input type="submit"
                               value="取消订单"
                               class="cancel-btn"
                               onclick="return confirm('确定要取消该订单吗？')">
                    </form>
                <% } %>

            </div>
<%
        }
    }
%>
</body>
</html>