<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/parsley.css">
    <script src="js/lib/jquery-3.2.1.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.3/umd/popper.min.js" integrity="sha384-vFJXuSJphROIrBnz7yo7oB41mKfc8JzQZiCq4NCceLEaO4IHwicKwpJf9c9IpFgh" crossorigin="anonymous"></script>
    <script src="js/lib/bootstrap.min.js"></script>
    <script src="js/lib/parsley.min.js"></script>
    <title>Order</title>
</head>
<body style="padding-top: 5rem;">
    <%@ include file="../../template/navbar.jsp" %>
    <%@ include file="../../template/messages.jsp" %>
    <div class="container">
   	<h2 class="pb-2">Confirm order details</h2>
   	<form method="POST" action="order">
	    <div class="row">
	        <div class="col-sm-6">
		    <div class="card">
		        <h4 class="card-header">Customer details</h4>
		        <div class="card-body">
			    <div class="card-text">${firstName} ${lastName}</div>
			    <div class="card-text">${email}</div>
			    <input type="hidden" name="email" value="${email}" />
			</div>
		    </div>
		</div>
		<div class="col-sm-6">
		    <div class="card">
		        <h4 class="card-header">Shipping method</h4>
			<div class="card-body">
			    <div class="form-group">
				<select class="form-control" name="ship" id="shippingOption">
				    <c:forEach var="ship" items="${shippingOptions}">
				        <option value="<c:out value="${ship.id}" />">
					    <c:out value="${ship.id}. ${(fn:replace(ship.company, '\"',' '))} - 
						${(fn:replace(ship.type, '\"',' '))} - $${ship.price}" />
					</option>
				    </c:forEach>
				</select>
			    </div>
			</div>
	            </div>
		</div>
	    </div>
	    <br>
	    <div class="row">
	        <div class="col-sm-12">
	            <div class="card">
		        <h4 class="card-header">Item list</h4>
	                <div class="card-body">
			    <table class="table">
				<thead>
				    <tr>
				        <th scope="col">Title</th>
					<th scope="col">Artist</th>
					<th scope="col">Quantity</th>
					<th scope="col">Price</th>
				    </tr>
				</thead>
			        <tbody>								    
				    <c:forEach var="item" items="${items}">
					<input type="hidden" name="cdid" value="${(fn:replace(item.cdid, '\"',''))}" />
					<input type="hidden" name="quantity" value="${item.quantity}" />
					<input type="hidden" name="price" value="${item.price}" />
					<tr>
					    <td><c:out value="${(fn:replace(item.title, '\"',''))}" /></td>
					    <td><c:out value="${(fn:replace(item.artist, '\"',''))}" /></td>
					    <td><c:out value="${item.quantity}" /></td>
					    <td><c:out value="$${item.price}" /></td>
				        </tr>
			            </c:forEach>
				</tbody>
			    </table>
	                </div>
	            </div>
	        </div>
            </div>
	    <br>
	    <div class="mb-5">
                <a class="btn btn-secondary" href="store">Cancel</a>
	        <button type="submit" class="btn btn-primary">Submit</button>
	    </div>
        </form>
    </div>
</body>
</html>
