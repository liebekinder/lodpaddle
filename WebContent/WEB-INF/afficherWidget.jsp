<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

		<!-- A faire : une boucle qui lit la liste widgets et qui cré un div pour chaque -->
		
<div id="widgets">
	<c:forEach var="item" items="${communes}" >
		
		<div id="widget" style="width:<c:out value="${item.x}" />px; height:<c:out value="${item.y}" />px;
				background-color:<c:out value="${item.color}" /> ">
			<center> <h3><c:out value="${item.title}" /></h3> </center>
			<c:out value="${ item.content}" />
		</div>
			
	</c:forEach>
</div>