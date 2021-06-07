<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<style>
	/* 최신/인기글 영역 */
    section{
      float:left;
      padding:10px;
      width:100%;
    }
    section .article{
      margin: 40px auto;
    }

    /* 최신/인기글 영역 탭버튼 */
    section .article .tabBtn{
      width: 200px;
      text-align: center;
      padding:10px;
      border-radius: 5px 5px 0 0;
      border:1px solid #ededed;
      background-color: royalblue;
    }

    section .article .tabBtn{
      font-size: 17px;
      color:white;
    }

    /* 최신/인기글 테두리 */
    section .article .wrapper{
      border: 1px solid #ededed;
    }

	/*글제목*/
    section .article .wrapper a{
      color:black;
      display:block;
      font-size:17px;
      overflow:hidden;
      text-overflow:ellipsis;
      white-space:nowrap;
    }
    
    /* 줄 표시*/
    section .article .wrapper .tab{
		margin:20px;
    	border-bottom: 1px solid #ededed;
    }
    
    section .article .wrapper .row:last-of-type{
      border:none;
    }

 	.gallery img{
      width:100%;
      height:100%;
      min-height: 200px;
      max-height: 200px;
    }
    
    .postInfo{
      color:gray;
    }

    .writer, .postInfo, .gallery{
      margin:20px 0;
    }
</style>
<script type="text/javascript">

	function getAttachImg(arr, type){
		console.log(type);
		$.each(arr, function(i, value){
			$.getJSON("${pageContext.request.contextPath}/GBoard/getAttachList", {bno: value}, function(attach){
				console.log(attach[0]);
				var fileCallPath = encodeURIComponent(attach[0].uploadPath+"/"+attach[0].uuid+"_"+attach[0].fileName);
				$(".gallery-"+type+" .item"+(i+1)).attr("src","/GBoard/display?fileName="+fileCallPath);
			});
		});
		console.log(arr);
	}
	
	$(document).ready(function(){
		//갤러리게시판 최신글이미지 ===========================================================================================
		var arr= new Array(); 	
		<c:forEach items="${gNewList}" var="vo">
			arr.push("${vo.bno}")
		</c:forEach>
		
		getAttachImg(arr, "new");				
			
		//갤러리게시판 베스트글 ==========================================================================================
		arr= new Array(); 	
		<c:forEach items="${gBestList}" var="vo">
			arr.push("${vo.bno}")
		</c:forEach>
		
		getAttachImg(arr, "best");	
	});
</script>
<%@ include file="./includes/header.jsp"  %>
	<div class="container-fluid">
	 <section>
	  <!-- =======================================자유게시판 최신글========================================================= -->
        <div class="row article">
          <div class="col-lg-6">
            <div class="tabBtn">
              <strong>자유게시판 최신글</strong>
            </div>
            <div class="wrapper">
            
	        	<div class="row tab">
	            	<div class="col-lg-8">
	              		<a href="#none">오늘의 공지사항1</a>
	              	</div>
	                <div class="col-lg-2">
	                	작성자
	                </div>
	                <div class="col-lg-2">
	                	2021-04-43
	                </div>
	            </div><!-- end row -->
	        	<div class="row tab">
	            	<div class="col-lg-8">
	              		<a href="#none">오늘의 공지사항1</a>
	              	</div>
	                <div class="col-lg-2">
	                	작성자
	                </div>
	                <div class="col-lg-2">
	                	2021-04-43
	                </div>
	            </div><!-- end row -->
	        	<div class="row tab">
	            	<div class="col-lg-8">
	              		<a href="#none">오늘의 공지사항글제목제목제목글제목제목제목글제목제목제목글제목제목제목글제목제목제목글제목제목제목글제목제목제목글제목제목제목1</a>
	              	</div>
	                <div class="col-lg-2">
	                	작성자
	                </div>
	                <div class="col-lg-2">
	                	2021-04-43
	                </div>
	            </div><!-- end row -->
	        	<div class="row tab">
	            	<div class="col-lg-8">
	              		<a href="#none">오늘의 공지사항1</a>
	              	</div>
	                <div class="col-lg-2">
	                	작성자
	                </div>
	                <div class="col-lg-2">
	                	2021-04-43
	                </div>
	            </div><!-- end row -->
	        	<div class="row tab">
	            	<div class="col-lg-8">
	              		<a href="#none">오늘의 공지사항1</a>
	              	</div>
	                <div class="col-lg-2">
	                	작성자
	                </div>
	                <div class="col-lg-2">
	                	2021-04-43
	                </div>
	            </div><!-- end row -->
          	</div><!-- end wrapper -->
         </div><!-- end col-lg-6 -->
        <!-- =======================================자유게시판 베스트글========================================================= -->
          <div class="col-lg-6">
            <div class="tabBtn">
              <strong>자유게시판 베스트글</strong>
            </div>
            <div class="wrapper">
               <div class="row tab">
	            	<div class="col-lg-8">
	              		<a href="#none">오늘의 공지사항1</a>
	              	</div>
	                <div class="col-lg-2">
	                	작성자
	                </div>
	                <div class="col-lg-2">
	                	2021-04-43
	                </div>
	            </div><!-- end row -->
               <div class="row tab">
	            	<div class="col-lg-8">
	              		<a href="#none">오늘의 공지사항1</a>
	              	</div>
	                <div class="col-lg-2">
	                	작성자
	                </div>
	                <div class="col-lg-2">
	                	2021-04-43
	                </div>
	            </div><!-- end row -->
               <div class="row tab">
	            	<div class="col-lg-8">
	              		<a href="#none">오늘의 공지사항1</a>
	              	</div>
	                <div class="col-lg-2">
	                	작성자
	                </div>
	                <div class="col-lg-2">
	                	2021-04-43
	                </div>
	            </div><!-- end row -->
               <div class="row tab">
	            	<div class="col-lg-8">
	              		<a href="#none">오늘의 공지사항1</a>
	              	</div>
	                <div class="col-lg-2">
	                	작성자
	                </div>
	                <div class="col-lg-2">
	                	2021-04-43
	                </div>
	            </div><!-- end row -->
               <div class="row tab">
	            	<div class="col-lg-8">
	              		<a href="#none">오늘의 공지사항1</a>
	              	</div>
	                <div class="col-lg-2">
	                	작성자
	                </div>
	                <div class="col-lg-2">
	                	2021-04-43
	                </div>
	            </div><!-- end row -->
            </div><!-- end wrapper -->
          </div><!-- end col-lg-6 -->
        </div>
     <!-- =======================================갤러리게시판 최신글========================================================= -->
     <div class="row article">
          <div class="col-lg-6">
            <div class="tabBtn">
            <strong>갤러리게시판 최신글</strong>
            </div>
            <div class="gallery-new wrapper">
              <c:forEach items="${gNewList}" var="vo" varStatus="status">
            	<c:if test="${status.count%3==1}">
	            	<div class="row new gallery text-center">
            	</c:if>
	            	<div class="col-lg-4 text-center">
					<!-- ================================이미지 들어갈 자리====================================== -->
					<img class="item${status.count}"/>
	                  	<a class="title"><c:out value="${vo.title}"/></a>
	                  <span class="writer"><c:out value="${vo.writer}"/><span><br>
	                  <span class="regdate">
	                  	<fmt:formatDate pattern="yyyy-MM-dd" value="${vo.regdate}"/>
	                  </span>&nbsp;&nbsp;
	                  <span class="recommend">추천수<c:out value="${vo.recommend}"/></span>
	                </div>
                <c:if test="${status.count%3==0 || status.last}">
	            	</div>
            	</c:if>
            </c:forEach>
            </div><!-- end wrapper -->
    		</div><!-- end col-lg-6 -->
     <!-- =======================================갤러리게시판 베스트글========================================================= -->
          <div class="col-lg-6">
            <div class="tabBtn">
            	<strong>갤러리게시판 베스트글</strong>
            </div>
            <div class="gallery-best wrapper">
            	<c:forEach items="${gBestList}" var="vo" varStatus="status">
            		<c:if test="${status.count%3==1}">
	            		<div class="row gallery text-center">
            		</c:if>
	            		<div class="col-lg-4 text-center">
					<!-- ================================이미지 들어갈 자리====================================== -->
							<img class="item${status.count}"/>
	                  	<a class="title"><c:out value="${vo.title}"/></a>
	                  <span class="writer"><c:out value="${vo.writer}"/><span><br>
	                  <span class="regdate">
	                  	<fmt:formatDate pattern="yyyy-MM-dd" value="${vo.regdate}"/>
	                  </span>&nbsp;&nbsp;
	                  <span class="recommend">추천수<c:out value="${vo.recommend}"/></span>
	                </div>
                	<c:if test="${status.count%3==0 || status.last }">
	            		</div>
            		</c:if>
            	</c:forEach>
            	</div>
            </div> <!-- end wrapper -->
    	</div><!-- end col-lg-6 -->
    	</div><!-- end row article -->
    </section>
	</div>
<%@ include file="./includes/footer.jsp"  %>