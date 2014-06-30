<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<script type="text/javascript">
	$.pdialog.close("triggerList");
</script>
<div class="pageContent">
	<form method="post" action="<%=request.getContextPath()%>/trigger/update.action" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<div class="unit">
				<label>名称：</label>
				<input type="hidden" name="triggeruuid" value="${triggeruuid}"/>
				<input type="hidden" name="uuid" value="${uuid}"/>
				<input name="triggerInput.name" class="required" type="text" value="${triggerInput.name }" readonly="readonly" />
				<span class="info">必填</span>
			</div>
			<div class="unit">
				<label>组名：</label>
				<input type="text" class="required" name="triggerInput.group"  value="${triggerInput.group }" readonly="readonly" />
				<span class="info">必填</span>
			</div>
			<div class="unit">
				<label>Cron Expression：</label>
				<input type="text" name="triggerInput.cron" id="expression" value="${triggerInput.cron }"/>
			</div>
			<div class="unit">
				<label>备注信息：</label>
				<textarea name="triggerInput.description" cols="40" rows="4" class="textInput" value="${triggerInput.description }" ></textarea>
			</div>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>