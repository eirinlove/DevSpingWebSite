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
			str += "<img src='/GBoard/display?fileName="+fileCallPath+"'>";
			str += "</div>";
			str += "</li>";
		});
		console.log(str);
		uploadUL.append(str);
	}

	//=====================파일 확장자 및 크기 
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
		
		//====================첨부파일 목록 삭제
		$(".uploadResult").on("click","button",function(e){
			
			var targetFile = $(this).data("file");
			var type= $(this).data("type");
			var targetLi = $(this).closest("li");
			
			$.ajax({
				url: '${pageContext.request.contextPath}/GBoard/deleteFile',
				data:{fileName:targetFile, type:type},
				dataType:'text',
				type:'POST',
				success:function(result){
					targetLi.remove();
				}
			});//$.ajax
		});
		
		
		//======================이미지 파일과 함께 게시글 등록
		$("button[type='submit']").on("click",function(e){
			var formObj = $("form");
			e.preventDefault();
			
			var str = "";
			
			$(".uploadResult ul li").each(function(i, obj){
				var jobj = $(obj);
				console.dir(jobj);
				
				str += "<input type='hidden' name='attachList["+i+"].fileName' value='"+jobj.data("filename")+"'>";
				str += "<input type='hidden' name='attachList["+i+"].uuid' value='"+jobj.data("uuid")+"'>";
				str += "<input type='hidden' name='attachList["+i+"].uploadPath' value='"+jobj.data("path")+"'>";
				str += "<input type='hidden' name='attachList["+i+"].fileType' value='"+jobj.data("type")+"'>";
			});
				formObj.append(str).submit();
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
				url: "${pagecontext.request.contextPath}/GBoard/uploadAjaxAction",
				processData:false,
				contentType: false,
				data:formData,
				dataType:'json',
				type:"POST",
				success:function(result){
					console.log(result);
					showUploadedFile(result);
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
        <h1 class="page-header">글 쓰기 페이지</h1>
      </div>
      <!-- /.col-lg-12 -->
    </div>
    </div>
    <!-- /.row -->
     <div class="container">
      <form class="form-horizontal" method="post" >
        <!--=========================제목=========================-->
        <div class="form-group">
          <label class="control-label col-sm-2" for="title">제목</label>
          <div class="col-sm-10">
            <input type="text" class="form-control" id="title"  name="title">
          </div>
        </div>
        <!--=========================작성자=========================-->
        <div class="form-group">
          <label class="control-label col-sm-2" for="writer">작성자</label>
          <div class="col-sm-10">          
            <input type="text" class="form-control" id="writer" name="writer" >
          </div>
        </div>
         <!--=========================글내용=========================-->
      <div class="form-group">
        <br>
        <div class="col-sm-offset-1">
          <textarea class="form-control" rows="20" id="comment" name="content" placeholder="내용을 입력해주세요." required></textarea>
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
        
        <!-- ==============첨부파일 이름 출력================== -->
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
      
      <!--=========================등록/ 취소 버튼=========================-->
        <div class="form-group">        
          <div class="col-sm-offset-2 col-sm-10">
            <button type="submit" class="btn btn-default">등록</button>
            <a href="/GBoard/list" class="btn btn-default">취소</a>
          </div>
        </div>
      </form>
</div>
  
  <!-- ===============================footer=================================== -->
					<%@include file="../includes/footer.jsp"%>
