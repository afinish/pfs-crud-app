<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>part Management Application</title>
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
          crossorigin="anonymous">
</head>
<body>
<style>
    li{
        margin: 10px;
    }
</style>
<header>
    <nav class="navbar navbar-expand-md navbar-dark"
         style="background-color: black">
        <div class="container">
            <ul class="navbar-nav">
                <li><a href="<%=request.getContextPath()%>/list" class="btn btn-dark">Parts</a></li>

            </ul>
        </div>

    </nav>
</header>
<br>
<div class="container col-md-7">
    <div class="card">
        <div class="card-body">
            <c:if test="${part != null}">
            <form action="update" method="post">
                </c:if>
                <c:if test="${part == null}">
                <form action="insert" method="post">
                    </c:if>

                    <caption>
                        <h2 class="text-center">
                            <c:if test="${part != null}">
                                Edit part
                            </c:if>
                            <c:if test="${part == null}">
                                Add New part
                            </c:if>
                        </h2>
                    </caption>

                    <c:if test="${part != null}">
                        <input type="hidden" name="id" value="<c:out value='${part.id}' />" />
                    </c:if>

                    <fieldset class="form-group">
                        <label>Part Name:</label>
                        <input type="text" value="<c:out value='${part.name}' />" class="form-control" name="name" required="required">
                    </fieldset>

                    <fieldset class="form-group">
                        <label>Part type:</label>
                        <input type="text" value="<c:out value='${part.type}' />" class="form-control" name="type">
                    </fieldset>

                    <fieldset class="form-group">
                        <label>Part brand:</label>
                        <input type="text" value="<c:out value='${part.brand}' />" class="form-control" name="brand">
                    </fieldset>

                    <button type="submit" class="btn btn-secondary">Save</button>
                </form>
        </div>
    </div>
</div>
</body>
</html>