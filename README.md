FlowerShop 花店管理系统（Java Web 课程设计）
一、项目简介
FlowerShop 是一个基于 Java Web 技术的花店购物系统，采用 JSP + Servlet + JDBC + SQL Server 实现，包含用户注册登录、商品浏览、购物车、下单、订单管理等功能，适合作为 Java Web 课程设计项目。
________________________________________
二、项目功能说明
用户功能
•	用户注册（含验证码）
•	用户登录与退出
•	浏览鲜花商品
•	加入购物车
•	修改商品数量
•	提交订单
•	查看订单列表
•	查看订单详情
•	取消未付款订单
•	订单支付（模拟）
________________________________________
三、技术栈与开发环境
分类	技术
后端	Java、JSP、Servlet
数据库	SQL Server
服务器	Apache Tomcat 9
前端	HTML、CSS
开发工具	IntelliJ IDEA
JDBC	mssql-jdbc-13.2.1.jre11.jar

________________________________________
四、数据库设计
1. 用户表 user
CREATE TABLE [user](
    user_id INT PRIMARY KEY IDENTITY(1,1),
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(50) NOT NULL,
    phone VARCHAR(20),
    address VARCHAR(200),
    role VARCHAR(20) DEFAULT 'user'
);
2. 鲜花表 flower
CREATE TABLE flower(
    flower_id INT PRIMARY KEY IDENTITY(1,1),
    flower_name VARCHAR(100) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    category VARCHAR(50),
    description VARCHAR(500)
);
3. 库存表 inventory
CREATE TABLE inventory(
    flower_id INT PRIMARY KEY,
    stock INT NOT NULL
);
4. 公告表 announcement
CREATE TABLE announcement(
    ann_id INT PRIMARY KEY IDENTITY(1,1),
    title VARCHAR(100) NOT NULL,
    content VARCHAR(1000) NOT NULL,
    publish_time DATETIME DEFAULT GETDATE()
);
________________________________________
五、项目运行步骤
1. 导入数据库
1.	打开 SQL Server
2.	新建数据库：FlowerShop
3.	执行项目中的 SQLQuery1.sql 文件
________________________________________
2. 配置数据库连接
修改 DBUtil.java 中的数据库配置：
String url = "jdbc:sqlserver://localhost:1433;DatabaseName=FlowerShop";
String user = "sa";
String password = "数据库密码";
________________________________________
3. 配置 Tomcat
•	使用 Tomcat 9
•	项目部署路径为：/FlowerShop
________________________________________
4. 启动项目
浏览器访问：
http://localhost:8080/FlowerShop
________________________________________
六、项目说明
本项目为 Java Web 课程设计项目，仅用于学习与教学展示，不用于商业用途。
