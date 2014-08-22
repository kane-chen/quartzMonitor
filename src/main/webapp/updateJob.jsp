<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<script type="text/javascript">

function addOneJobData(){
	var index = $("#jobDataMapDataId tr").length;
	$("#jobDataMapDataId").append("<tr><td><input type='text' class='required' name='jobDataMapKey["+index+"]' /></td><td><input type='text' class='required' name='jobDataMapValue["+index+"]' /></td><td align='center'><img alt='删除一个参数' src='images/delete-16x16.png' onclick='deletecurtr(this);' /></td></tr>");
}

function deletecurtr(obj){
	$(obj).parent().parent().remove();  //删除当前行
}

</script>
<div class="pageContent">
	<form method="post" action="<%=request.getContextPath()%>/job/update.action" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<div class="unit">
				<label>job名称：</label>
				<input type="hidden" name="jobuuid" value="${job.uuid }"/>
				<input name="job.jobName" class="required" type="text" value="${job.jobName }" readonly="readonly" />
			</div>
			<div class="unit">
				<label>所属组：</label>
				<input type="text" class="required" name="job.group" value="${job.group }" readonly="readonly" />
			</div>
			<div class="unit">
				<label>描述：</label>
				<textarea name="job.description" cols="30" rows="2" class="textInput">${job.description }</textarea>
			</div>
			<div class="unit">
				<label>job类型：</label>
				 <input type="text" class="required" name="job.jobClass"  value="${job.jobClass }" />
			</div>
			<div class="unit">
				<label>job参数：</label>
				<img alt="添加一个参数" src="images/add-16x16.png" onclick="addOneJobData();" />
				<table width="400">
					<thead>
						<tr>
							<td align="center" width="33%">jobDataMapKey</td>
							<td align="center" width="33%">jobDataMapValue</td>
							<td align="center" width="33%">删除操作</td>
						</tr>
					</thead>
					<tbody id="jobDataMapDataId">
						<s:iterator value="jobDataMap.keySet" id="key" status="pos" >
							<tr>
								<td><input type='text' class='required' name='jobDataMapKey[${pos.index}]' value='<s:property value="key" />'/></td>
								<td><input type='text' class='required' name='jobDataMapValue[${pos.index}]' value='<s:property value="jobDataMap.get(#key)" />'/></td>
								<td align='center'><img alt='删除一个参数' src='images/delete-16x16.png' onclick='deletecurtr(this);' /></td>
							</tr>
						</s:iterator>
					</tbody>
				</table>
			</div>
			<div class="unit">
				<label>所属Schedule：</label>
				<input name="job.schedulerName" class="required" type="text" value="${job.schedulerName }" readonly="readonly" />
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