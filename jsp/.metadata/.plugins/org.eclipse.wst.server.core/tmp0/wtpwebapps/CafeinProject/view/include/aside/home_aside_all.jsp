<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- Main Sidebar Container -->
<!--   <aside class="main-sidebar sidebar-dark-primary elevation-4">  -->
    <!-- Brand Logo -->
    <a href="index.jsp" class="brand-link">
      <img src="../../assets/dist/img/AdminLTELogo.png" alt="AdminLTE Logo" class="brand-image img-circle elevation-3"
           style="opacity: .8">
      <span class="brand-text font-weight-light"><b>Cafe-in</b> admin</span>
    </a>

    <!-- Sidebar -->
    <div class="sidebar">

      <!-- Sidebar Menu -->
      <nav class="mt-2">
        <ul class="nav nav-pills nav-sidebar flex-column" data-widget="treeview" role="menu" data-accordion="false">
          <!-- Add icons to the links using the .nav-icon class
               with font-awesome or any other icon font library -->
   
          <%@ include file ="home_aside.jsp" %>
          
          <%@ include file ="user.jsp" %>
          
          <%@ include file ="order.jsp" %>
          
          <%@ include file ="employee.jsp" %>
          
          <%@ include file ="sales.jsp" %>
          
          <%@ include file ="ingredient.jsp" %>

          <%@ include file ="menu.jsp" %>
                  
		  <%@ include file ="cs.jsp" %>
		  
		  <%@ include file ="sitemap.jsp" %>

        </ul>
      </nav>
      <!-- /.sidebar-menu -->
    </div>
    <!-- /.sidebar -->
  <!--   </aside>  -->