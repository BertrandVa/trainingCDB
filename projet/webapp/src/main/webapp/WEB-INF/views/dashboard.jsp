<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-INF/taglib.tld" prefix="pagination" %>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<link href="/webapp/css/bootstrap.min.css" rel="stylesheet"
	media="screen">
<link href="/webapp/css/font-awesome.css" rel="stylesheet"
	media="screen">
<link href="/webapp/css/main.css" rel="stylesheet"
	media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="dashboard"> Application - Computer
				Database </a>
		</div>
	</header>

	<section id="main">
		<div class="container">
			<h1 id="homeTitle">${nbComputer} Computers found</h1>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" action="#" method="GET" class="form-inline">

						<input type="search" id="searchbox" name="search"
							class="form-control" placeholder="Search name" /> <input
							type="submit" id="searchsubmit" value="Filter by name"
							class="btn btn-primary" />
					</form>
				</div>
				<div class="pull-right">
					<a class="btn btn-success" id="addComputer" href="addComputer">Add
						Computer</a> <a class="btn btn-default" id="editComputer" href="#"
						onclick="$.fn.toggleEditMode();">Edit</a>
				</div>
			</div>
		</div>

		<form id="deleteForm" action="#" method="POST">
			<input type="hidden" name="selection" value="">
		</form>

		<div class="container" style="margin-top: 10px;">
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<!-- Variable declarations for passing labels as parameters -->
						<!-- Table header for Computer Name -->

						<th class="editMode" style="width: 60px; height: 22px;"><input
							type="checkbox" id="selectall" /> <span
							style="vertical-align: top;"> - <a href="dashboard"
								id="deleteSelected" onclick="$.fn.deleteSelected();"> <i
									class="fa fa-trash-o fa-lg"></i>
							</a>
						</span></th>
						<th><a href="dashboard?sort=name">Computer name</a></th>
						<th><a href="dashboard?sort=introduce">Introduced Date</a></th>
						<!-- Table header for Discontinued Date -->
						<th><a href="dashboard?sort=discontinued">Discontinued Date</a></th>
						<!-- Table header for Company -->
						<th><a href="dashboard?sort=company">Company</a></th>

					</tr>
				</thead>
				<!-- Browse attribute computers -->
				<tbody id="results">
					<c:forEach var="elem" items="${computerList}">
						<tr>
							<td class="editMode"><input type="checkbox" name="cb"
								class="cb" value="${elem.getId()}"></td>
							<td><a href="editComputer?id=${elem.getId()}"><c:out value="${elem.getName()}"/></a></td>
							<td><c:out value="${elem.getIntroduceDate()}"/></td>
							<td><c:out value="${elem.getDiscontinuedDate()}"/></td>
							<td><c:out value="${elem.getManufacturerName()}"/></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</section>

	<footer class="navbar-fixed-bottom">
		<div class="container text-center">
			<!-- <ul class="pagination">-->
		 	<c:url var="searchUri" value="/dashboard?search=${search}&sort=${sort}&page=##" />
             <pagination:display  maxLinks="5" currentPage="${currentPage}" maxPages="${maxPage}" uri="${searchUri}" />
			<div class="btn-group btn-group-sm pull-right" role="group">
				<form action="dashboard" method="get">
					<input type="submit" name="submit" value="10" class="btn btn-default">
					<input type="submit" name="submit" value="50" class="btn btn-default">
					<input type="submit" name="submit" value="100" class="btn btn-default">
				</form>
			</div>
		</div>
	</footer>
	<script src="/webapp/js/jquery.min.js"></script>
	<script src="/webapp/js/bootstrap.min.js"></script>
	<script src="/webapp/js/dashboard.js"></script>

</body>
</html>