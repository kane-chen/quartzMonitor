<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>


<script type="text/javascript">

jQuery(document).ready(function(){
	var selecttext = $("select[name='job.uuid'] option:selected").text();
	$("input[name='job.schedulerName']").val(selecttext);
});

function addOneJobData(){
	var index = $("#jobDataMapDataId tr").length;
	$("#jobDataMapDataId").append("<tr><td><input type='text' class='required' name='jobDataMapKey["+index+"]' /></td><td><input type='text' class='required' name='jobDataMapValue["+index+"]' /></td><td align='center'><img alt='删除一个参数' src='images/delete-16x16.png' onclick='deletecurtr(this);' /></td></tr>");
}

function deletecurtr(obj){
	$(obj).parent().parent().remove();  //删除当前行
}

function setJobSchedulerName(){
	var selecttext = $("select[name='job.uuid'] option:selected").text();
	$("input[name='job.schedulerName']").val(selecttext);
}

</script>


<div class="pageContent">
	<form method="post" action="<%=request.getContextPath()%>/job/add.action" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<div class="unit">
				<label>job名称：</label>
				<input name="job.jobName" class="required" type="text" size="30"  alt="请输入job名称" />
			</div>
			<div class="unit">
				<label>所属组：</label>
				<input type="hidden" name="job."/>
				<input type="text" class="required" name="job.group" alt="请输入组名"/>
			</div>
			<div class="unit">
				<label>描述：</label>
				<textarea name="job.description" cols="30" rows="2" class="textInput"></textarea>
			</div>
			<div class="unit">
				<label>job类型：</label>
				<!-- 
				<select name="job.jobClass" class="required">
					<s:iterator value="jobMap" id="map">
					   <option value="${value.uuid }">${value.jobName }</option>
					</s:iterator>
				</select>
				 -->
				 <input type="text" class="required" name="job.jobClass" alt="请输入全路径的类名" />
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
						
					</tbody>
				</table>
			</div>
			<div class="unit">
				<label>所属Schedule：</label>
				<select name="job.uuid" class="required" onchange="setJobSchedulerName();">
					<s:iterator value="schedulerMap" id="entry">
						<s:property value="#entry"/>{
					   		<option value='<s:property value="key"/>'><s:property value="value"/></option>
					   	}
					</s:iterator>
				</select>
				
				<s:hidden name="job.schedulerName" ></s:hidden>
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