<%--
    Document   : home page
    Author     : Vikky
--%>


<%@page import="com.team.app.domain.*"%>
<%@page import="com.itextpdf.text.log.SysoLogger"%>
<%@page import="java.util.List"%>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    
    <title>Node Sync</title>
    
	<script type="text/javascript" src="js/jquery-latest.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    
	  <link href="css/bootstrap.min.css" rel="stylesheet">
	  <link href="css/custom_siemens.css" rel="stylesheet">
	   <link href="css/marquees.css" rel="stylesheet">
	       
    
   
	  <!-- Font Awesome -->
	  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.5.0/css/font-awesome.min.css">
	  <!-- Ionicons -->
	  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css">
	  <link rel="stylesheet" href="css/AdminLTE.min.css">
	  <link rel="stylesheet" href="css/AdminLTE.css">
	  <link rel="stylesheet" href="css/skins/_all-skins.min.css">
	 

	<script src="js/app.min.js"></script>
	<script src="js/demo.js"></script>
	
<!-- Pie Charts... -->
<script src="https://code.highcharts.com/highcharts.js"></script>
<script src="https://code.highcharts.com/modules/exporting.js"></script>
    
   <script type="text/javascript">
   
   function confirmValidate(){
	   
	   var orgid=document.getElementById("orgid").value;
 	  	var appid=document.getElementById("appid").value;
 	  	var devid=document.getElementById("devid").value;
 		
	   	   
	   if(orgid=="0"){
		   alert("Please select organisation!");
		   return false;
	   }else if(appid=="0"){
		   alert("Please select application!");
		   return false;
	   }else if(devid=="0"){
		   alert("Please select devEUI!");
		   return false;
	   }else{		   
			   $.ajax({
	               url: 'syncDev',
	               type: 'POST',
	               //data: 'orgId='+orgid+'&appId='+appid+'&devId='+devid,
	               data: jQuery.param({ orgId: orgid, appId : appid, devId : devid}) ,
	               success: function (data) {
	               alert(data);
	               window.location.reload();
	            	   //$(".success").html(data);
	                   	
	                   },
	  		 		error: function(e){
	  	     			        alert('Error: ' + e);
	  	     		 }
	
	                  
	               });
			   
			  return false;
	   		} 
	   		
   }
   
function getAppByOrgID()
{    
            	var orgid=document.getElementById("orgid").value;
            	
            	if(orgid=="0")
                	{      
            	
                	var appid=document.getElementById("appid");
                	var devid=document.getElementById("devid");
                		appid.innerHTML='<select name="appname" id="appid" onchange="getDevEUIByAppID()"> <option value="0" >--Choose Application--</option></select>';
                		devid.innerHTML='<select name="devname" id="devid"> <option value="0" >--Choose Device EUI--</option></select>';
                		return;
                	}
                else
                	{
                	                
                var url="getApplications?orgId="+orgid;                                    
                xmlHttp=GetXmlHttpObject()
                if (xmlHttp==null)
                {
                    alert ("Browser does not support HTTP Request");
                    return
                }                    
                xmlHttp.onreadystatechange=setApplication;	
                xmlHttp.open("GET",url,true);                
                xmlHttp.send(null);
                
                	}
 }
            
            
function getDevEUIByAppID()
{     
	var appid=document.getElementById("appid").value;
	
	if(appid=="0")
    	{                	
    	var devid=document.getElementById("devid");
    		devid.innerHTML='<select name="devname" id="devid"> <option value="0" >--Choose Device EUI--</option></select>';
    	return;
    	}
    else
    	{
    var url="getDevEUISync?appId="+appid;                                    
    xmlHttp=GetXmlHttpObj()
    if (xmlHttp==null)
    {
        alert ("Browser does not support HTTP Request");
        return
    }                    
    xmlHttp.onreadystatechange=setDevEUI;	
    xmlHttp.open("GET",url,true);                
    xmlHttp.send(null);
    
    	}
}
                
            function GetXmlHttpObject()
            {
                var xmlHttp=null;
                if (window.XMLHttpRequest) 
                {
                    xmlHttp=new XMLHttpRequest();
                }                
                else if (window.ActiveXObject) 
                { 
                    xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");
                }

                return xmlHttp;
            }
            
            
            function GetXmlHttpObj()
            {
                var xmlHttp=null;
                if (window.XMLHttpRequest) 
                {
                    xmlHttp=new XMLHttpRequest();
                }                
                else if (window.ActiveXObject) 
                { 
                    xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");
                }

                return xmlHttp;
            }
        
            function setApplication() 
            {                      
                if (xmlHttp.readyState==4 || xmlHttp.readyState=="complete")
                { 
                    var returnText=xmlHttp.responseText;
                    var appid=document.getElementById("appid");
                    appid.innerHTML='<select  name="appname" id="appid" onchange="getDevEUIByAppID()"><Option value="0">--Choose Application--</Option>'+returnText+'</select>';                                             
                }
            }
            
            function setDevEUI() 
            {                      
                if (xmlHttp.readyState==4 || xmlHttp.readyState=="complete")
                { 
                    var returnText=xmlHttp.responseText;
                    var devid=document.getElementById("devid");
                    devid.innerHTML='<select  name="devname" id="devid"><Option value="0">--Choose Device EUI--</Option>'+returnText+'</select>';                                             
                }
            }
     </script>       
     
  </head>
  
  <body class="hold-transition skin-blue sidebar-mini">
  
  			<% AdminUser adminUser=(AdminUser)request.getSession().getAttribute("adminUser");
  			String orgName=request.getAttribute("name").toString();
  			String orgId=request.getAttribute("id").toString();
  			
  			%>
  			
							 
  <div class="wrapper">  
  	<header class="main-header" >
   
	    <a href="#" class="logo affix">
			    <svg width="135px" height="50px" >
						  <defs>
						    <filter id="MyFilter" filterUnits="userSpaceOnUse" x="50" y="50" width="200" height="120">
						        <feGaussianBlur in="SourceGraphic" stdDeviation="15" />
						      <feOffset result="offOut" in="SourceAlpha" dx="60" dy="60" />
						    
						    </filter>
						  </defs>
						  <text stroke-width="3.5"  stroke="white" font-size="26" font-family="Verdana" x="0" y="40">CMS</text>
						  
						  Sorry, your browser does not support inline SVG.
				</svg>
		</a> 
	    

	    <!-- Header Navbar: style can be found in header.less -->
	    <nav class="navbar navbar-static-top affix" >
	      <!-- Sidebar toggle button-->
	      <a href="#" class="sidebar-toggle" style="width:2.5em;" data-toggle="offcanvas" role="button">
	      </a> 
	    </nav>
	    
   </header>
  
  
  <aside class="main-sidebar" style="position:fixed;">
   
    <section class="sidebar">
         
      <ul class="sidebar-menu">
        <li class="header"><b>MAIN NAVIGATION</b></li>
        <li class="active treeview">
          <a href="adminHome">
            <i class="fa fa-dashboard"></i> <span><b>Dashboard</b></span>
          </a>
        </li>
        <li class="treeview"> 
          <a href="#">
            <i class="fa fa-files-o"></i>
            <span><b>Uplink/Downlink Logs</b></span>
            <span class="pull-right-container">
              <span class="label label-primary pull-right">3</span>
            </span>
          </a>
          <ul class="treeview-menu">
            <li><a href="userInfoHistory"><i class="fa fa-circle-o"></i><b>Admin User</b></a></li>
            <li><a href="frameInfos"><i class="fa fa-circle-o"></i> <b>Uplink Log</b></a></li>
             <li><a href="downlinkQueue"><i class="fa fa-circle-o"></i> <b>Downlink Log</b></a></li>
          </ul>
          
             
        </li>
        
        
         <li class="treeview">
          <a href="#">
            <i class="fa fa-arrow-right"></i>
            <span><b>LoRa Node Sync</b></span>
            <span class="pull-right-container">
              <i class="fa fa-angle-left pull-right"></i>
            </span>
          </a>
          <ul class="treeview-menu">
            <li><a href="#"><i class="fa fa-circle-o"></i><b>Sync</b></a></li>
           
          </ul>
        </li>
        
        <li class="treeview">
          <a href="#">
            <i class="fa fa-arrow-right"></i>
            <span><b>LoRa Node Config</b></span>
            <span class="pull-right-container">
              <i class="fa fa-angle-left pull-right"></i>
            </span>
          </a>
          <ul class="treeview-menu">
            <li><a href="deleteNode"><i class="fa fa-circle-o"></i><b>Delete Node</b></a></li>
           
          </ul>
           <ul class="treeview-menu">
            <li><a href="delDevEUI"><i class="fa fa-circle-o"></i><b>Delete DevEUI</b></a></li>
           
          </ul>
        </li>
      
        
                
      </ul>
    </section>
  
  </aside>
 
	<div class="content-wrapper">
		
			<section class="content">
		 		<div class="content-wrap box box-primary">
		 		
					
		 		<div class="row">
							<div class="col-sm-12 text-right">
								<img src="images/user_iocn_header.png" />&nbsp;<b>Welcome  <%=adminUser.getDisplayname()%></b>  &nbsp;&nbsp;&nbsp;<a href="logout"><img src="images/logout_icon_header.png" />&nbsp;<b>Log Out</b></a>
							</div>
					
				</div>
		 		
		 		<div class="box-header with-border">
  					  <h5 class="text-blue text-left "><span class="fa fa-dashboard"></span>&nbsp;&nbsp;<b>Sync</b></h5>
       
   				</div><!-- /.box-header -->
		 							
   						
   						  <div class="row" >
    				    	<div class="col-sm-12">	
    				    	
    				    	<form name="form1" action="syncDev" onsubmit="return confirmValidate();" method="post">
										
								  <table class="table">
								 
								  		<%-- <% if(session.getAttribute("status")!=null){%>
										 <p align="center" class="formbutton text-bold text-green"><%=session.getAttribute("status") %></p>
															
										<%}%> --%>
										
										
								 	
								  	<tr>
								  		<td align="right"><b>Organization</b></td>
								  			<%-- <td><input type="text" value="<%=orgName%>"  class="formbutton" id="<%=orgId%>" name="orgName" /></td>--%>
										
										<td>
										 <select name="orgname" id="orgid" onchange="getAppByOrgID()">
										    <option value="0">--Choose Organisation--</option>	
										    <option value="<%=orgId%>"><%=orgName%></option>
										 </select> 
										</td>
									</tr>
									<tr>	
									   <td align="right"><b>Applications</b></td>
									   
										 <td>
										 	<select name="appname" id="appid" onchange="getDevEUIByAppID()">
										    	<option value="0">--Choose Application--</option>	
										    </select> 
										</td>
									</tr>
									
									<tr>	
									   <td align="right"><b>Device EUI</b></td>
									   
										 <td>
										 	<select name="devname" id="devid" >
										    	<option value="0">--Choose Device EUI--</option>	
										    </select> 
										</td>
									</tr>
									<tr>	
										<td align="right"></td>
											<td> <input type="submit"  class="formbutton text-bold " style="background-color:#3c8dbc; " value="Sync"/></td>
										 
									</tr>	
									<%-- <% if(session.getAttribute("status")!=null){%>
									<tr>	
										<td align="right"></td>
											<td><p><%=session.getAttribute("status") %></p></td>
										 
									</tr>
									<% }%> --%>
									
								  </table>	
							 </form> 
							</div>	
					   </div>
										
						</div>	
			</section>	
			<%@include file="Footer.jsp"%>  		
	</div>	
	</div>
			
  </body>
</html>