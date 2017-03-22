<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="/computer-database/css/bootstrap.min.css" rel="stylesheet"
	media="screen">
<link href="/computer-database/css/font-awesome.css" rel="stylesheet"
	media="screen">
<link href="/computer-database/css/main.css" rel="stylesheet"
	media="screen">
<script src="/computer-database/js/script.js"></script>
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
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<h1>Add Computer</h1>
					<form action="addComputer" method="post" onsubmit="return verifForm(this)">
						<fieldset>
							<div class="form-group">
								<label for="computerName">Computer name</label> <input
									type="text" class="form-control" id="computerName"
									placeholder="Computer name" name="computerName" onblur="verifName(this)"
									value="<c:out value="${param.computerName}"/>" required>
								<span class="erreur">${form.erreurs['computerName']}</span>
							</div>
							<div class="form-group">
								<label for="introduced">Introduced date</label> <input
									type="date" class="form-control" id="introduced" onblur="verifName(this)"
									placeholder="Introduced date" name="introduce"> <span class="erreur">${form.erreurs['introduce']}</span>
							</div>
							<div class="form-group">
								<label for="discontinued">Discontinued date</label> <input
									type="date" class="form-control" id="discontinued" onblur="verifName(this)"
									placeholder="Discontinued date" name="discontinued"> <span class="erreur">${form.erreurs['discontinued']}</span>
							</div>
							<div class="form-group">
								<label for="companyId">Company</label> <select
									class="form-control" id="companyId" name=companyId>
									<option value="">--</option>
									<c:forEach var="elem" items="${requestScope.companyList}">
										<option value="${elem.id}">${elem.name}</option>
									</c:forEach>
								</select>
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" value="Add" class="btn btn-primary">
							or <a href="dashboard" class="btn btn-default">Cancel</a>
						</div>
						<p class="${empty form.erreurs ? 'succes' : 'erreur'}">${form.getResultat()}</p>
					</form>
				</div>
			</div>
		</div>
	</section>
</body>
</html>