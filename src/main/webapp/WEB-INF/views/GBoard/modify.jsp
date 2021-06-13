<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script>
	//====================업로드 파일 목록 출력
	function showUploadedFile(uploadResultArr){
		if(!uploadResultArr || uploadResultArr.length ==0){return;}
		var uploadUL = $(".uploadResult ul");
		var str = "";
		
		$(uploadResultArr).each(function(i,obj){
			var fileCallPath = encodeURIComponent(obj.uploadPath+"/s_"+obj.uuid+"_"+obj.fileName);
			str += "<li data-path='"+obj.uploadPath+"' ";
			str += "data-uuid='"+obj.uuid+"' data-filename='"+obj.fileName+"' data-type='"+obj.fileType+"'";
			str +="><div>";
			str += "<span> "+obj.fileName+"</span>";
			str += "<button type='button' data-file=\'"+fileCallPath+"\' data-type='image' class='btn btn-warning btn-circle'><i class='fa fa-times'></i></button><br>";
			str += "<img src='/Gboard/display?fileName="+fileCallPath+"'>";
			str += "</div>";
			str += "</li>";
		});
		console.log(str);
		uploadUL.append(str);
	}
	var regex= new RegExp("(.*?)\.(exe|sh|zip|alz)$");
	var maxSize = 5242880; //5MB
	
	//======================파일 사이즈 및 확장자 제한
	function checkExtension(fileName, fileSize){
		if(fileSize>=maxSize){
			alert("파일 사이즈 초과");
			return false;
		}
		if(regex.test(fileName)){
			alert("해당 종류의 파일은 업로드할 수 없습니다.");
			return false;
		}
		return true;
	}
	$(document).ready(function(){
		var formObj = $("#actionForm");
		
	//1.버튼의 data-oper속성을 통해 수정/취소 판단 후 실행==========================================================
		$('button').on("click", function(e){
			e.preventDefault();
			//button 의 data-oper 값 가져오기
			var operation = $(this).data("oper");
			console.log(operation);
			
			if(operation === 'list'){
				formObj.attr("action", "/Gboard/list").attr("method","get");
				var pageNum= $("input[name='pageNum']").clone();
				var amount=	$("input[name='amount']").clone();
				
				formObj.empty();
				formObj.append(pageNum);
				formObj.append(amount);
			//4.update 게시물 수정================================================================================
			}else if(operation == 'update'){
				console.log("수정 버튼 클릭");
				var str="";
				$(".uploadResult ul li").each(function(i,obj){
					var obj = $(obj);
					//5.수정된 파일정보 히든값으로 넘기기=====================================
					str += "<input type='hidden' name='attachList["+i+"].fileName' value='"+obj.data("filename")+"'>"
					str += "<input type='hidden' name='attachList["+i+"].uuid' value='"+obj.data("uuid")+"'>"
					str += "<input type='hidden' name='attachList["+i+"].uploadPath' value='"+obj.data("path")+"'>"
					str += "<input type='hidden' name='attachList["+i+"].fileType' value='"+obj.data("type")+"'>"
				});
				//formObj.append(str);
				formObj.append(str).submit();
			}
			formObj.submit();
		});
		
		
		(function(){
			var gno = '<c:out value="${Gboard.gno}"/>';
			$.getJSON("/Gboard/getAttachList", {gno:gno}, function(arr){
				var str = "";
				
				$(arr).each(function(i,attach){
					if(attach.fileType){
						var path = encodeURIComponent(attach.uploadPath+"/s_"+attach.uuid+"_"+attach.fileName);
						str += "<li data-path='"+attach.uploadPath+"' data-uuid='"+attach.uuid+"' data-filename='"+attach.fileName+"' data-type='"+attach.fileType+"' ><div>";
						str += "<span> "+attach.fileName+"</span>";
						str += "<button type='button' data-file=\'"+path+"\' data-type='image' ";
						str += "class='btn btn-warning btn-circle'><i class='fa fa-times'></i></button><br>";
						str += "<img src='/Gboard/display?fileName="+path+"'>";
						str += "</div>";
						str += "</li>";
					}
				});
				$(".uploadResult ul").html(str);
				
			});//end getjson
		})();//end function
		
		//첨부파일 목록 삭제
		$(".uploadResult").on("click","button",function(e){
			if(confirm("해당 파일을 삭제하시겠습니까? ")){
				var targetLi = $(this).closest("li");
				targetLi.remove();
			}
		});
		
		//=======================파일 확장자 및 크기 확인 후 등록
		$("input[type='file']").change(function(e){
			
			var formData = new FormData();
			var inputFile = $("input[name='uploadFile']");
			var files = inputFile[0].files;
		
			for(var i=0;i<files.length; i++){
				if(!checkExtension(files[i].name,files[i].size)){
					return false;
				}
				formData.append("uploadFile",files[i]);
			}
		
			$.ajax({
				url: "${pagecontext.request.contextPath}/Gboard/uploadAjaxAction",
				processData:false,
				contentType: false,
				data:formData,
				dataType:'json',
				type:"POST",
				success:function(result){
					console.log(result);
					
					showUploadedFile(result);
					
					//$(".uploadDiv").html(cloneObj.html());
				},
				error:function(request,status,error){
					alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
				}
			});//$.ajax
		
		});//on.change
	});
</script>
<style>
	.uploadResult{
		width:100%;
		background-color:gray;
	}
	
	.uploadResult ul{
		display:flex;
		flex-flow:row;
		justify-cotnet: center;
		align-items:center;
	}
	
	.uploadResult ul li {
		list-style:none;
		padding:10px;
	}
	
	.uploadResult ul li img{
		width:200px;
	}
	
	.uploadResult ul li span{
		color:white;
	}
</style>

<!-- =====================header============================== -->
<%@include file="../includes/header.jsp"%>
 	<div class="container-fluid">
    <div class="row">
      <div class="col-lg-12">
        <h1 class="page-header">글 수정</h1>
      </div>
      <!-- /.col-lg-12 -->
    </div>
    </div>
    <!-- /.row -->
     <div class="container">
      <form class="form-horizontal" id="actionForm" method="post" >
        <!--=========================제목=========================-->
        <div class="form-group">
          <label class="control-label col-sm-2" for="title">제목</label>
          <div class="col-sm-10">
            <input type="text" class="form-control" id="title"  name="title" value="<c:out value="${Gboard.title}"/>" />
          </div>
        </div>
        <!--=========================작성자=========================-->
        <div class="form-group">
          <label class="control-label col-sm-2" for="writer">작성자</label>
          <div class="col-sm-10">          
            <input type="text" class="form-control" id="writer" name="writer" value="<c:out value="${Gboard.writer}"/>" readonly>
          </div>
        </div>
         <!--=========================글내용=========================-->
      <div class="form-group">
        <br>
        <div class="col-sm-offset-1">
          <textarea class="form-control" rows="20" id="comment" name="content" placeholder="내용을 입력해주세요." required>
          <c:out value="${Gboard.content}"/>
          </textarea>
        </div>
      </div>
       
      <!--=========================첨부파일=========================-->
      		<div class="form-group">
        	<label for="attached-images" class="col-sm-2 control-label">
          		첨부파일
        	</label>
        	<div class="uploadDiv">
        		<input type="file" name ="uploadFile" id="attached-images" multiple/>
        	</div>
        
        <!-- ==============첨부파일 목록 출력================== -->
        	<div class="uploadResult">
        		<ul>
        	
        		</ul>
        	</div>
      		</div>
      <!--=========================해쉬태그=========================-->
      <div class="form-group">
        <label for="hashtags" class="col-sm-2 control-label">
          해쉬태그
        </label>
        <div class="col-sm-8">
        <input type="text" class="form-control input-lg" id="hashtag" name="hashtag" placeholder="#태그 선택" >
       </div>
      </div>
      
      <!--=========================수정/ 취소 버튼=========================-->
        <div class="form-group">        
          <div class="col-sm-offset-2 col-sm-10">
            <button type="submit" data-oper="update" class="btn btn-default">수정</button>
            <button type="submit" data-oper="list" class="btn btn-default">취소</button>
            <input type="hidden" name="gno" value='<c:out value="${Gboard.gno }"/>' >
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
          </div>
        </div>
      </form>
</div>
  
  
	<input type='hidden' name='pageNum' value='<c:out value="${cri.pageNum}"/>'>
	<input type='hidden' name='amount' value='<c:out value="${cri.amount}"/>'>
	<input type='hidden' name='type' value='<c:out value="${cri.type }"/>'>
	<input type='hidden' name='keyword' value='<c:out value="${cri.keyword }"/>'>
  
  <!-- ===============================footer=================================== -->
					<%@include file="../includes/footer.jsp"%>