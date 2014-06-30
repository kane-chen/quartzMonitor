<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>


<script type="text/javascript">

function dialogAjax(json){
	$.pdialog.reloadDialog("triggerList");
}
function closeTriggerList(){
	$.pdialog.close("triggerList");
}
</script>

<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="trigger/addShow?jobId=${jobId }&triggeruuid=${triggeruuid}" target="navTab" rel="addTrigger"><span>添加</span></a></li>
			<li><a class="delete"  href="<%=request.getContextPath()%>/trigger/delete.action?uuid={sid_user}&triggeruuid=${triggeruuid}" callback="dialogAjax" target="ajaxTodo" title="确定要删除吗?" fresh="true"><span>删除</span></a></li>
			<li><a class="edit" href="<%=request.getContextPath()%>/trigger/edit.action?uuid={sid_user}&triggeruuid=${triggeruuid}" target="dialog" warn="请选择一记录"><span>修改</span></a></li>
		</ul>
	</div>
	<input type="hidden" name="jobId" value="${jobId }"/>
	<table class="list" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="80">Trigger名称</th>
				<th width="80">Trigger组名</th>
				<th width="130">上一次触发时间</th>
				<th width="130">下一次触发时间</th>
				<th width="130">开始时间</th>
				<th width="100" align="middle">操作</th>
				<th>描述</th>
			</tr>
		</thead>
		<tbody>
		<s:iterator value="triggerList" id="trigger">
			<tr target="sid_user" rel="${trigger.uuid }">
				<td align="middle">${trigger.name }</td>
				<td align="middle">${trigger.group }</td>
				<td><s:date name="#trigger.previousFireTime"   format="yyyy-MM-dd HH:mm:ss"/></td>
				<td><s:date name="#trigger.nextFireTime"   format="yyyy-MM-dd HH:mm:ss"/></td>
				<td><s:date name="#trigger.startTime"   format="yyyy-MM-dd HH:mm:ss"/></td>
				<td align="middle">
					<s:if test="#trigger.sTriggerState == 'NORMAL'">
	    				 <a href="<%=request.getContextPath()%>/trigger/pause.action?uuid=${trigger.uuid}&triggeruuid=${triggeruuid}" callback="dialogAjax" target="ajaxTodo">暂停</a>
					</s:if>
					<s:elseif test="#trigger.sTriggerState == 'COMPLETE'">
					</s:elseif>
					<s:elseif test="#trigger.sTriggerState == 'PAUSED'">
					      <a href="<%=request.getContextPath()%>/trigger/resume.action?uuid=${trigger.uuid}&triggeruuid=${triggeruuid}" callback="dialogAjax" target="ajaxTodo">恢复</a>
					</s:elseif>
					<s:else>
	      				未知
					</s:else>
				</td>
				<td align="middle">${trigger.description }</td>
			</tr>
		</s:iterator>
		</tbody>
	</table>
</div>