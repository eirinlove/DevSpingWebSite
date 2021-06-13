<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>My 쪽지함</title>
</head>
<script>
//csrf 토큰값===========================================================================
var csrfHeaderName = "${_csrf.headerName}";
var csrfTokenValue = "${_csrf.token}";


	$(document).ready(function(){
		//쪽지 내용 클릭 시 모달 창에 값 전달======================================================================================================
		$("#myModal").on("show.bs.modal",function(event){
			if($(event.relatedTarget).data("type")=='R'){
				$("#myModal input[name=id]").before("보낸 아이디");
				$("#myModal input[name=id]").val($(event.relatedTarget).data("sender"));
			}else{
				$("#myModal input[name=id]").before("받은 아이디");
				$("#myModal input[name=id]").val($(event.relatedTarget).data("receiver"));
			}
			$("#myModal textarea").text($(event.relatedTarget).data('content'));
			
			//읽지않음 표시일 경우만
			if($(event.relatedTarget).data("read")=='N'){
				//읽음 표시로 전환===================================================================================================================
					$.ajax({
						url:"/message/read",
						type:"post",
						data:{"mno":$(event.relatedTarget).data("mno")},
						beforeSend: function(xhr){
							xhr.setRequestHeader(csrfHeaderName, csrfTokenValue);
						},
						success:function(result){
							if(result=="success"){
								console.log("읽음 처리");	
							}
						},
						error:function(request,status,error){
							alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
						}
					}); //end ajax
			}
		});//end modal
		
		
		//읽음 표시 반영 새로고침===============================================================================================================
		$("#myModal").on("hide.bs.modal",function(){
			history.go();
		});
	
	}); //end document ready
	
		//읽지 않은 쪽지 발송 취소===============================================================================================================
		function cancel(mno){
			console.log(mno);
			$.ajax({
				url:"/message/cancel",
				type:"post",
				data:{"mno":mno},
				beforeSend: function(xhr){
					xhr.setRequestHeader(csrfHeaderName, csrfTokenValue);
				},
				success:function(result){
					if(result=="success"){
						console.log("전송 취소 완료");	
						history.go();
					}
				},
				error:function(request,status,error){
					alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
				}
			}); //end ajax
		} // end cancel function
		
		//전체 선택 이벤트======================================================================================================
		$(function(){
			var chkObj = document.getElementsByName("RowCheck");
			var rowCnt = chkObj.length;
			
			$("input[name='allCheck']").click(function(){
				var chk_listArr = $("input[name='RowCheck']");
				for (var i=0; i<chk_listArr.length; i++){
					chk_listArr[i].checked = this.checked;
				}
			});
			$("input[name='RowCheck']").click(function(){
				if($("input[name='RowCheck']:checked").length == rowCnt){
					$("input[name='allCheck']")[0].checked = true;
				}
				else{
					$("input[name='allCheck']")[0].checked = false;
				}
			});
		});//end 모두 선택
	
		//쪽지내역 삭제===========================================================================================================
			function deleteMessage(){
			var valueArr = new Array();
		    var list = $("input[name='RowCheck']");
		    for(var i = 0; i < list.length; i++){
		        if(list[i].checked){ //선택되어 있으면 배열에 값을 저장함
		            valueArr.push(list[i].value);
		        }
		    }
		    if (valueArr.length == 0){
		    	alert("선택된 쪽지가 없습니다.");
		    }
		    else{
				var chk = confirm("정말 삭제하시겠습니까?");				 
				$.ajax({
				    url : "/message/delete",                   
				    type : 'POST', 
				    traditional : true,			//배열 전송
				    data : {
				    	"mnos" : valueArr        
				    },
					beforeSend : function(xhr){
						xhr.setRequestHeader(csrfHeaderName, csrfTokenValue);
					},
					contentType: "application/x-www-form-urlencoded; charset=UTF-8",
	                success: function(result){
	                	if(result=="success"){
		                	console.log("삭제완료");
							history.go();                		
	                	}
	                },  error: function (request,status,errorData){   
	    		    	alert('error code: '+request.status+"\n"
	    		    			+'message:' +request.reponseText+'\n'
	    		    			+ 'error :'+  errorData);
	    		    }
				});//end ajax
		    }
		}//end deleteMessage function
		
</script>
<body>
<%@ include file="../includes/header.jsp"%>
	<div class="jumbotron text-center">
		<h2>My 쪽지함</h2>
	</div>
	<!-- 기본 선택: 받은 쪽지함 // 보낸쪽지함 선택시 보낸 쪽지리스트 호출 ============================================================================-->
	<input type="radio" name="type" onclick='location.href="/message/list?type=R";' ${type=='R'?"checked":""}/>받은쪽지함 &nbsp;&nbsp;&nbsp;
	<input type="radio" name="type" onclick='location.href="/message/list?type=S";' ${type=='S'?"checked":""}/>보낸쪽지함
	<br><br>
	<table class="table table-condensed" >
			<thead>
				<tr>
					<th style="width:10%; text-overflow:ellipsis; overflow:hidden; white-space:nowrap;">&nbsp;&nbsp;<input type="checkbox" name="allCheck">모두선택</th>
					<th style="width:30%; text-overflow:ellipsis; overflow:hidden; white-space:nowrap;">내용</th>
					<th style="width:10%; text-overflow:ellipsis; overflow:hidden; white-space:nowrap;">
						${type=='R'?"보낸 아이디":"받은 아이디"}						
					</th>
					<th style="width:10%; text-overflow:ellipsis; overflow:hidden; white-space:nowrap;">날짜</th>
				</tr>
			</thead>
			<tbody>
			<!-- ==============================================받은쪽지함리스트========================================================== -->
			<c:if test="${type=='R'}" >
 			<c:forEach var="ms" varStatus="status" items="${receptionList}"> 
 				<!-- ============================================읽지않음 표시=========================================== -->
				<c:if test="${ms.readYN=='N'}">
					<tr style="background:#ccc">
				</c:if>
				<c:if test="${ms.readYN=='Y'}">
					<tr>
				</c:if>
 					<td>&nbsp;&nbsp;<input type="checkbox" name="RowCheck" value="${ms.mno}"></td> 
					<td>
					<a data-toggle="modal" data-target="#myModal" 
					data-type="R" data-read="${ms.readYN}" data-sender="${ms.sender}" data-content="${ms.content}" data-mno="${ms.mno}">
					${ms.content}</a></td>
					<td>${ms.sender }</td>
					<td><fmt:formatDate pattern="yyyy-MM-dd" value="${ms.msgDate}" /></td>
				</tr>
 			</c:forEach> 
 			</c:if>
			<!-- ==============================================보낸쪽지함리스트========================================================== -->
			<c:if test="${type=='S'}">
 			<c:forEach var="ms" varStatus="status" items="${sentList}"> 
 				<tr>
					<td>&nbsp;&nbsp;<input type="checkbox" name="RowCheck" value="${ms.mno}"></td> 
					<td><a data-toggle="modal" data-target="#myModal" 
					data-type="S" data-receiver="${ms.receiver}" data-read="${ms.readYN}" data-mno="${ms.mno}" data-content="${ms.content}">
					${ms.content }</a></td>
					<td>${ms.receiver}</td>
					<td>
					<fmt:formatDate pattern="yyyy-MM-dd" value="${ms.msgDate}" />&nbsp;
					<!-- ============================================읽지않음 표시=========================================== -->
						<c:if test="${ms.readYN=='N'}">
							<font color="red">읽지않음</font>
					<!-- ============================================읽지않은 쪽지 전송취소=========================================== -->
							<button type="button" class="btn btn-default btn-sm cancel" onclick="cancel(${ms.mno});">
								발송취소
							</button>
						</c:if>
						<c:if test="${ms.readYN=='Y'}">
							<font color="red">읽음</font>
						</c:if>
					</td>
				</tr>
 			</c:forEach> 
			</c:if>
			<tr>
				<td colspan="4">
					<button type="button" onclick="deleteMessage();" class="btn btn-default btn-sm" >내역삭제</button>
				</td>
			<tr>
		</tbody>
		</table>
		<!-- ==================================================쪽지확인 모달창===================================================================== -->			
			<!-- Modal -->
			<div id="myModal" class="modal fade" role="dialog">
			  <div class="modal-dialog">
			
			    <!-- Modal content-->
			    <div class="modal-content">
			      <div class="modal-header">
			        <button type="button" class="close" data-dismiss="modal">&times;</button>
			        <h4 class="modal-title">쪽지</h4>
			      </div>
			      <div class="modal-body">
			      <div class="row">
					<div class="col-lg-6">
						<input type="text" name="id" class="form-control" readonly/><br><br>
					</div>
					</div>
					<div class="row">
						<div class="col-lg-12">
							내용
							<textarea cols="200" name="content" class="form-control" readonly></textarea>
						</div>
					</div>		
					</form>
			      </div>
			      <div class="modal-footer">
			        <button type="button" class="btn btn-default" class="close" data-dismiss="modal" >닫기</button>
			      </div>
			    </div>
			
			  </div>
			</div>
             	
		
<%@ include file="../includes/footer.jsp"%>
</body>
</html>