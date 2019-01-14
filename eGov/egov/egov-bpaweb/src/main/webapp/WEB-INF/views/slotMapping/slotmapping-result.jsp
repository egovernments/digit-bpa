<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="main-content">
	<div class="row">
		<div class="col-md-12">
			<div class="panel panel-primary" data-collapsed="0">
				<div class="panel-heading">
					<div class="panel-title"></div>
				</div>
				<div class="panel-body">
					<div class="panel-title text-center no-float">
						<strong>${message}</strong>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="form-group">
		<div class="text-center">
			<a href='javascript:void(0)' class='btn btn-default'
				onclick='self.close()'><spring:message code='lbl.close' /></a>
		</div>
	</div>
</div>