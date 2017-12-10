<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<nav class="navbar navbar-expand-md navbar-dark bg-dark fixed-top">
    <a class="navbar-brand" href="./">I MUSIC</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarsExampleDefault" aria-controls="navbarsExampleDefault" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarsExampleDefault">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a class="nav-link" href="./">
                    Home 
                    <span class="sr-only">(current)</span>
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="./store">CD Category</a>
            </li>    
            <li class="nav-item">
                <a class="nav-link" href="./cart">Shopping Cart</a>
            </li>   
        </ul>
        <form class="form-inline my-2 my-lg-0">
            <c:choose>
	        <c:when test="${sessionScope.username == null}">
		    <a class="btn btn-dark" href="./account">Sign up</a>
	            <a class="btn btn-dark" href="./login">Login</a>
		</c:when>    
		<c:otherwise>
		    <a class="btn btn-dark" href="./logout">Logout</a>
		</c:otherwise>
	    </c:choose>
        </form>
    </div>
</nav>
