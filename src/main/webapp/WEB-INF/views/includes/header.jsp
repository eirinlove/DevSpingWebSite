<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>      
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>  
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>스프링 게시판</title>

    <!-- Bootstrap Core CSS -->
    <link href="/resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="/resources/vendor/metisMenu/metisMenu.min.css" rel="stylesheet">

    <!-- DataTables CSS -->
    <link href="/resources/vendor/datatables-plugins/dataTables.bootstrap.css" rel="stylesheet">

    <!-- DataTables Responsive CSS -->
    <link href="/resources/vendor/datatables-responsive/dataTables.responsive.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="/resources/dist/css/sb-admin-2.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="/resources/vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<script type="text/javascript">

$(document).ready(function(){
	
	//csrf 토큰값===========================================================================
	var csrfHeaderName = "${_csrf.headerName}";
	var csrfTokenValue = "${_csrf.token}";

	var socket = null;
	//쪽지 알림 기능 ===========================================================================
	connect();
	
	//최근 받은 쪽지 3개 보여주기==================================================================
	$("#recentMsg").on("click",function(e){
		e.preventDefault();
		var str = "";
		
		$.ajax({
			type:"post",
			dataType:"json",
			url:"/message/recent",
			data: {"userId": $("#id").val()},
			beforeSend: function(xhr){
				xhr.setRequestHeader(csrfHeaderName, csrfTokenValue);
			},
			success:function(data){
				var list = data.result;
				var i = 1;
				list.forEach(function(result){
					if($("#"+i).length==0){
						//읽지않은 쪽지표시====================================================================
						if(result.readYN=='N'){
							str += "<li style='background:#ccc' id='"+i+"'>";
						}else{
							str += "<li id='"+i+"'>";
						}
	                    str += ' <a href="/message/list">';
	                    str += "<div>";
	                    str += "<strong>"+result.sender+"</strong>";
	                    str += '<span class="pull-right text-muted">';
	                    str += "<em>"+formatDate(result.msgDate)+"</em>";
	                    str += "</span></div>";
	                    str += "<div>"+result.content+"</div>";
	                    str += "</a></li><li class='divider'></li>";
						i++;
					}
				});
				$("#msgResult").append(str);
			}
		});//end ajax
	})//end on click

}); //end document ready

//날짜포맷 함수=======================================================================================================
	function formatDate(date) { 
		var d = new Date(date), 
		month = '' + (d.getMonth() + 1), 
		day = '' + d.getDate(), 
		year = d.getFullYear(); 
		
		if (month.length < 2) 
			month = '0' + month; 
		if (day.length < 2) 
			day = '0' + day; 
		
		return [year, month, day].join('-'); 
		}

	 
// 로그인 시 읽지 않은 쪽지 개수 알림 ================================================================================================
function connect(){
	var ws = new WebSocket("ws://localhost:8181/echo?gno=32");
	socket = ws;
	var id = $("#id").val();
	
	ws.onopen = function () {
	    console.log('Info: connection opened.');
	    socket.send(id);
	};

	ws.onmessage = function (event) {
	    var splitdata = event.data.split(":");
	    var str = "";
	    var list = new Array();
	    if(splitdata[0].indexOf("recMsg")>-1)
	    	$("#recMsg").append(splitdata[1]);
	    else
	    	$("#recMsg").append(event.data);
	};

	ws.onclose = function (event) { 
		console.log('Info: connection closed.'); 
		};
	
	ws.onerror = function (event) { console.log('Info: connection closed.'); 
	};

}

</script>
<body>
<sec:authorize access="isAuthenticated()">
		<sec:authentication property="principal.username" var="id"/>
</sec:authorize>
    <div id="wrapper">

        <!-- Navigation -->
        <nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="/">스프링 커뮤니티</a>
            </div>
            <!-- /.navbar-header -->
			
            <ul class="nav navbar-top-links navbar-right">
            	<!-- ========================로그인한 사용자만 쪽지함 보여짐====================================== -->
                <sec:authorize access="isAuthenticated()">
					<sec:authentication property="principal.username" var="id"/>
						<input type="hidden" id="id" value="${id}" />
                <li><strong>${id}님</strong> </li>
                <!-- =========================================================쪽지함====================================== -->
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" id="recentMsg">
                        <i class="fa fa-envelope fa-fw"></i> <span class="badge" id="recMsg"></span> <i class="fa fa-caret-down" ></i>
                    </a>
                    <ul class="dropdown-menu dropdown-messages" id="msgResult">
                       <!-- =================================쪽지내용 들어갈 부분===================================== --> 
                    	<li>
                            <a class="text-center" href="/message/list">
                                <strong>MY 쪽지함</strong>
                                <i class="fa fa-angle-right"></i>
                            </a>
                        </li>
                    </ul>
                    <!-- /.dropdown-messages -->
                </li>
                </sec:authorize>
                <!-- /.dropdown -->
 <!--                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                        <i class="fa fa-tasks fa-fw"></i> <i class="fa fa-caret-down"></i>
                    </a>
                    <ul class="dropdown-menu dropdown-tasks">
                        <li>
                            <a href="#">
                                <div>
                                    <p>
                                        <strong>Task 1</strong>
                                        <span class="pull-right text-muted">40% Complete</span>
                                    </p>
                                    <div class="progress progress-striped active">
                                        <div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width: 40%">
                                            <span class="sr-only">40% Complete (success)</span>
                                        </div>
                                    </div>
                                </div>
                            </a>
                        </li>
                        <li class="divider"></li>
                        <li>
                            <a href="#">
                                <div>
                                    <p>
                                        <strong>Task 2</strong>
                                        <span class="pull-right text-muted">20% Complete</span>
                                    </p>
                                    <div class="progress progress-striped active">
                                        <div class="progress-bar progress-bar-info" role="progressbar" aria-valuenow="20" aria-valuemin="0" aria-valuemax="100" style="width: 20%">
                                            <span class="sr-only">20% Complete</span>
                                        </div>
                                    </div>
                                </div>
                            </a>
                        </li>
                        <li class="divider"></li>
                        <li>
                            <a href="#">
                                <div>
                                    <p>
                                        <strong>Task 3</strong>
                                        <span class="pull-right text-muted">60% Complete</span>
                                    </p>
                                    <div class="progress progress-striped active">
                                        <div class="progress-bar progress-bar-warning" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: 60%">
                                            <span class="sr-only">60% Complete (warning)</span>
                                        </div>
                                    </div>
                                </div>
                            </a>
                        </li>
                        <li class="divider"></li>
                        <li>
                            <a href="#">
                                <div>
                                    <p>
                                        <strong>Task 4</strong>
                                        <span class="pull-right text-muted">80% Complete</span>
                                    </p>
                                    <div class="progress progress-striped active">
                                        <div class="progress-bar progress-bar-danger" role="progressbar" aria-valuenow="80" aria-valuemin="0" aria-valuemax="100" style="width: 80%">
                                            <span class="sr-only">80% Complete (danger)</span>
                                        </div>
                                    </div>
                                </div>
                            </a>
                        </li>
                        <li class="divider"></li>
                        <li>
                            <a class="text-center" href="#">
                                <strong>See All Tasks</strong>
                                <i class="fa fa-angle-right"></i>
                            </a>
                        </li>
                    </ul>
                    <!-- /.dropdown-tasks -->
                <!--  </li>--> 
                <!-- /.dropdown -->
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                        <i class="fa fa-bell fa-fw"></i> <i class="fa fa-caret-down"></i>
                    </a>
                    <ul class="dropdown-menu dropdown-alerts">
                        <li>
                            <a href="#">
                                <div>
                                    <i class="fa fa-comment fa-fw"></i> New Comment
                                    <span class="pull-right text-muted small">4 minutes ago</span>
                                </div>
                            </a>
                        </li>
                        <li class="divider"></li>
                        <li>
                            <a href="#">
                                <div>
                                    <i class="fa fa-twitter fa-fw"></i> 3 New Followers
                                    <span class="pull-right text-muted small">12 minutes ago</span>
                                </div>
                            </a>
                        </li>
                        <li class="divider"></li>
                        <li>
                            <a href="#">
                                <div>
                                    <i class="fa fa-envelope fa-fw"></i> Message Sent
                                    <span class="pull-right text-muted small">4 minutes ago</span>
                                </div>
                            </a>
                        </li>
                        <li class="divider"></li>
                        <li>
                            <a href="#">
                                <div>
                                    <i class="fa fa-tasks fa-fw"></i> New Task
                                    <span class="pull-right text-muted small">4 minutes ago</span>
                                </div>
                            </a>
                        </li>
                        <li class="divider"></li>
                        <li>
                            <a href="#">
                                <div>
                                    <i class="fa fa-upload fa-fw"></i> Server Rebooted
                                    <span class="pull-right text-muted small">4 minutes ago</span>
                                </div>
                            </a>
                        </li>
                        <li class="divider"></li>
                        <li>
                            <a class="text-center" href="#">
                                <strong>See All Alerts</strong>
                                <i class="fa fa-angle-right"></i>
                            </a>
                        </li>
                    </ul>
                    <!-- /.dropdown-alerts -->
                </li>
                <!-- /.dropdown -->
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                        <i class="fa fa-user fa-fw"></i> <i class="fa fa-caret-down"></i>
                    </a>
<ul class="dropdown-menu dropdown-user">
    <li><a href="#"><i class="fa fa-user fa-fw"></i>마이 프로필</a>
    </li>
    <li><a href="#"><i class="fa fa-gear fa-fw"></i> Settings</a>
    </li>

    
    <li class="divider"></li>
		<sec:authorize access="isAuthenticated()"> 
		 
		<li><a href="/member/customLogout"><i class="fa fa-sign-out fa-fw"></i>
		    로그아웃</a></li>
		</sec:authorize>
		
		 <sec:authorize access="isAnonymous()"> 
		
		<li><a href="/member/customLogin"><i class="fa fa-sign-out fa-fw"></i>
		    로그인</a></li>
		 </sec:authorize> 
</ul>
                    <!-- /.dropdown-user -->
                </li>
                <!-- /.dropdown -->
            </ul>
            <!-- /.navbar-top-links -->

            <div class="navbar-default sidebar" role="navigation">
                <div class="sidebar-nav navbar-collapse">
                    <ul class="nav" id="side-menu">
                        <li class="sidebar-search">
                            <div class="input-group custom-search-form">
                                <input type="text" class="form-control" placeholder="Search...">
                                <span class="input-group-btn">
                                <button class="btn btn-default" type="button">
                                    <i class="fa fa-search"></i>
                                </button>
                            </span>
                            </div>
                            <!-- /input-group -->
                        </li>
                        <li>
                            <a href="/board/list">자유게시판</a>
                        </li>
                        <li>
                            <a href="/Gboard/list">갤러리게시판</a>
                        </li>
                        
                    </ul>
                </div>
                <!-- /.sidebar-collapse -->
            </div>
            <!-- /.navbar-static-side -->
        </nav>

        <div id="page-wrapper">
        
       <!--  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script> -->
       <script src="/resources/js/jquery.min.js"></script>