<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../includes/header.jsp"%>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<style>
  .register-btn,.postList{
    margin-bottom:50px;
  }
</style>
<script type="text/javascript">
	$(document).ready(function(){
		
		var arr = new Array();
		
		//배열에 글번호 리스트 추가
		<c:forEach items="${list}" var="item">
			arr.push("${item.gno}");
		</c:forEach>
		
		//글번호로 첨부파일 조회 후 첫번째 이미지 불러오기
		$.each(arr, function(i, value){
			$.getJSON("/Gboard/getAttachList", {gno: value}, function(attach){
				console.log(attach[0]);
				var fileCallPath = encodeURIComponent(attach[0].uploadPath+"/"+attach[0].uuid+"_"+attach[0].fileName);
				$(".thumbnail"+(i+1)+" .thumbnail-img").attr("src","/Gboard/display?fileName="+fileCallPath);
			});
		});
		
	});
</script>	

  <!--  =============================게시판 헤더 ==================================-->
    <div class="jumbotron">
      <h3>갤러리형 게시판</h3>
    </div>
 <!--  ===========================검색조건 및 글 등록버튼  ===================================-->
    <div class="row register-btn" >
      <div class="col-lg-12">
        <form id='searchForm' action="/Gboard/list" method='get'>
          <div class="col-lg-2">
           <select name='type' class="form-control">
				<option value=""
					<c:out value="${pageMaker.cri.type == null?'selected':''}"/>>--</option>
				<option value="T"
					<c:out value="${pageMaker.cri.type eq 'T'?'selected':''}"/>>제목</option>
				<option value="C"
					<c:out value="${pageMaker.cri.type eq 'C'?'selected':''}"/>>내용</option>
				<option value="W"
					<c:out value="${pageMaker.cri.type eq 'W'?'selected':''}"/>>작성자</option>
				<option value="TC"
					<c:out value="${pageMaker.cri.type eq 'TC'?'selected':''}"/>>제목
					or 내용</option>
				<option value="TW"
					<c:out value="${pageMaker.cri.type eq 'TW'?'selected':''}"/>>제목
					or 작성자</option>
				<option value="TWC"
					<c:out value="${pageMaker.cri.type eq 'TWC'?'selected':''}"/>>제목
					or 내용 or 작성자</option>
			</select> 
          </div>
          <div class="col-lg-4">
            <div class="input-group">
	            <input type='text' class="form-control" name='keyword' value='<c:out value="${pageMaker.cri.keyword}"/>' />
			 	<input type='hidden' name='pageNum' value='<c:out value="${pageMaker.cri.pageNum}"/>' />
			 	<input type='hidden' name='amount' value='<c:out value="${pageMaker.cri.amount}"/>' />
			 	<input type='hidden' name='type' value='<c:out value="${ pageMaker.cri.type }"/>'>
                <div class="input-group-btn">
                  <button class="btn btn-default" type="submit" ><i class="glyphicon glyphicon-search"></i></button>
                </div>
            </div>
          </div>
        </form>
          <div class="col-lg-6 text-right">
            <button id="regBtn" class="btn btn-default">글 등록</button>
          </div>
      </div>
    </div>
<!--  ===========================게시글 목록 ===================================-->
    <div class="wrapper">
		<c:forEach items="${list}" var="Gboard" varStatus="status">
		      <c:if test="${status.count%4 eq 1}">
			      <div class="row postList">
			  </c:if>
			        <div class="col-lg-3">
			          <div class="thumbnail${status.count}">
			            <a class="move" href="<c:out value="${Gboard.gno}" />" >
			             <img class="thumbnail-img"  alt="이미지" style="width:100%;height:300px;">
			            </a>
			              <div class="caption">
			                <p>
			                  <a class="move" href="<c:out value="${Gboard.gno}" />" >
			                 	<c:out value="${Gboard.title}" /> 
			                  </a>
			                </p>
			                  <img src="${pageContext.request.contextPath}/resources/img/userimage.jpg" style="width:30px;height:30px;"/> 
			                  <span><c:out value="${Gboard.writer}"/></span> 
			                  <button type="button" class="btn btn-default btn-sm">
			                    <span class="glyphicon glyphicon-thumbs-up"></span> 추천수 <c:out value="${Gboard.recommend}"/> 
			                  </button> 
			              </div>
			          </div>
			        </div><!--//col-lg-3--> 
		      <c:if test="${status.count%4 eq 0 }">
		      	</div><!--//row-->
		      </c:if>
		 </c:forEach>
		 
		  <!-- Modal -->
           <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
               <div class="modal-dialog">
                   <div class="modal-content">
                       <div class="modal-header">
                           <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                           <h4 class="modal-title" id="myModalLabel">알림</h4>
                       </div>
                       <!-- 글 등록/수정/삭제 알림 내용 -->
                       <div class="modal-body">
                       </div>
                       <div class="modal-footer">
                           <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                       </div>
                   </div>
                   <!-- /.modal-content -->
               </div>
               <!-- /.modal-dialog -->
           </div>
           <!-- /.modal -->
    </div><!--//wrapper-->  
<!--  ===========================페이지 매김 ===================================-->
    <ul class="breadcrumb text-center" >
	    <c:if test="${pageMaker.prev}">
			<li class="paginate_button previous">
				<a href="${pageMaker.startPage -1}">Previous</a>
			</li>
		</c:if>
		<c:forEach var="num" begin="${pageMaker.startPage}"
			end="${pageMaker.endPage}">
			<li>
				<a href="${num}" ${pageMaker.cri.pageNum == num ?'style="color:red;"':""} >${num}</a>
			</li>
		</c:forEach>

		<c:if test="${pageMaker.next}">
			<li class="paginate_button next">
				<a href="${pageMaker.endPage+1 }">Next</a>
			</li>
		</c:if>
    </ul>

    <form id='actionForm' action="/Gboard/list" method='get'>
		<input type='hidden' name='pageNum' value='<c:out value="${ pageMaker.cri.pageNum }"/>'>
		<input type='hidden' name='amount' value='<c:out value="${ pageMaker.cri.amount }"/>'>
		<input type='hidden' name='type' value='<c:out value="${ pageMaker.cri.type}"/>'>
		<input	type='hidden' name='keyword' value='<c:out value="${ pageMaker.cri.keyword }"/>'>
	</form>
  	<br><br>




<script type="text/javascript">
	var actionForm = $("#actionForm")
	
	$(document).ready(function(){
		
	$(".move").on("click",function(e){
		e.preventDefault();
		actionForm.append("<input type='hidden' name='gno' value='"+$(this).attr("href")+"'>");
		actionForm.attr("method","get");
		actionForm.attr("action", "/Gboard/get").submit();
		
	});
	
	//검색 버튼 후 이벤트 처리==============================================
	var searchForm = $("#searchForm");
	
	$("#searchForm button").on("click", function(e){
		
		if(!searchForm.find("option:selected").val()){
			alert("검색종류를 선택하세요");
			return false;
		}
		if(!searchForm.find("input[name='keyword']").val()){
			alert("키워드를 입력하세요");
			return false;
		}
		//검색을 하면 1페이지가 첫페이지로 나오게끔
		searchForm.find("input[name='pageNum']").val("1");
		e.preventDefault();
		
		searchForm.submit();
	});
	
	//페이지 번호 누르면 이동 ==============================================
    $(".paginate_button a").on("click", function(e){
    	e.preventDefault();
    	
    	console.log("페이지 버튼 틀릭");
    	
    	actionForm.find("input[name='pageNum']").val($(this).attr("href"));
    	actionForm.submit();
    });
	
	//글 등록/수정/삭제 알림 창=============================================
	 var result ='<c:out value="${result}"/>';
	 
	 checkModal(result);
	 
	 history.replaceState({},null,null);
	 
	//글 등록 페이지=========================================
	 $("#regBtn").on("click",function(){
		self.location = "/Gboard/register"; 
	 });
	
	});
	
	function checkModal(result){
		 if(result==='' || history.state){
			 return;
		 }
		 
		 if(parseInt(result)>0){
			 $(".modal-body").html("게시글"+parseInt(result)+"번이 등록되었습니다.");
		 }else if(result.toString()==='msuccess'){
			 $(".modal-body").html("수정이 완료되었습니다.");
		 }else if(result.toString()==='dsuccess'){
			 $(".modal-body").html("삭제가 완료되었습니다.");
		 }
		 
		 $("#myModal").modal("show");
	 }
	 
	 
	 
</script>

<%@ include file="../includes/footer.jsp"%>