<%@ page import="com.carpartstorage.dao.PartDAO" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>CRUD App</title>
    <!-- Bootstrap -->
    <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
            rel="stylesheet"
            integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
            crossorigin="anonymous"
    />
    <!-- Axios -->
    <script src="https://unpkg.com/axios/dist/axios.min.js"></script>
    <!-- Vue -->
    <script src="https://cdn.jsdelivr.net/npm/vue@2/dist/vue.js"></script>
</head>
<body>

<%-- ======================================== Header ======================================== --%>
<header>
    <nav class="navbar navbar-expand-md navbar-dark"
         style="background-color: #000000">
        <div class="container">
            <ul class="navbar-nav">
                <li><a href="<%=request.getContextPath()%>/list" class="btn btn-dark">Parts</a></li>
                <li><a href="<%=request.getContextPath()%>/new" class="btn btn-dark">Add New Part</a></li>
                <li><form action="list" method="get"  style="display:flex;">
                    <select name="order" class="browser-default custom-select">
                        <option selected>Choose the Ordering</option>
                        <option name="name">Name</option>
                        <option name="type">type</option>
                        <option name="brand">brand</option>
                    </select>
                    <input type="submit" value="Order" class="btn btn-dark">
                </form></li>

                <li><form action="filter" method="get"  style="display:flex;">
                    <input type="text" placeholder="Enter brand name to filter:" name="brand-filter" id="brand-filter" class="form-control">
                    <input type="submit" value="Filter" class="btn btn-dark">
                </form></li>
            </ul>
        </div>
    </nav>
</header>
<br>
<%-- ======================================== /Header ======================================== --%>

<div class="row">
    <div class="container">
        <h1 class="text-center">List of Parts</h1>
        <%-- ======================================== Table ======================================== --%>
        <table class="table table-striped table-dark">
            <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Type</th>
                <th>Brand</th>
                <th></th>
            </tr>
            </thead>
            <tbody>
<%--            <c:forEach var="part" items="${listPart}">--%>
<%--                <tr>--%>
<%--                    <td><c:out value="${part.id}" /></td>--%>
<%--                    <td><c:out value="${part.name}" /></td>--%>
<%--                    <td><c:out value="${part.type}" /></td>--%>
<%--                    <td><c:out value="${part.brand}" /></td>--%>
<%--                    <td><a href="edit?id=<c:out value='${part.id}' />" class="btn btn-warning">Edit</a>--%>
<%--                        <a href="delete?id=<c:out value='${part.id}' /> "class="btn btn-danger">Delete</a></td>--%>
<%--                </tr>--%>
<%--            </c:forEach>--%>
            </tbody>
        </table>
        <%-- ======================================== /Table ======================================== --%>
        <div class="container m-5" id="app">
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th scope="col">ID</th>
                    <th scope="col">Name</th>
                    <th scope="col">Type</th>
                    <th scope="col">Brand</th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="part in parts">
                    <td>{{ part.id }}</td>
                    <td>{{ part.name }}</td>
                    <td>{{ part.type }}</td>
                    <td>{{ part.brand }}</td>
                </tr>
                </tbody>
            </table>
        </div>

        <%-- ======================================== /Table ======================================== --%>
        <div class="text-center">
            <%
                PartDAO partDAO = new PartDAO();
                int page_count = partDAO.getRowCount() / 5 + 1;
                for(int i=1; i<=page_count; i++) {
                    out.print("<a class='btn btn-light' href='?page="+i+"'>"+i+"</a>  ");
                }
            %>
        </div>
    </div>
</div>

<%--<App part-list="${listPart}" />--%>

<script>
    var app = new Vue({
        el: "#app",
        data: {
            message: 'hello',
            parts: []
        },
        mounted: function () {
            <c:forEach var="part" items="${listPart}">
                this.parts.add(
                    <c:out value="${part.id}" />,
                    <c:out value="${part.name}" />,
                    <c:out value="${part.type}" />,
                    <c:out value="${part.brand}" />
                    )
            </c:forEach>

            <%--this.parts = <c:out value="&{}" />;--%>
            // axios
            //     .get("/list")
            //     .then((response) => {
            //         this.users = response.listPart;
            //         console.log(response);
            //     })
            //     .catch((error) => {
            //         console.log(error);
            //     });
        },
    });
</script>
</body>
</html>