<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<li class="nav-item has-treeview">
  <a href="order.jsp" class="nav-link">
    <!-- <span class="nav-icon oi oi-home" aria-hidden="true"></span> -->
    <img src = "../../img/order.png" width="18" height="18"> &nbsp;
    <p>
            주문관리   
      <i class="fas fa-angle-left right"></i>
      <span class="badge badge-info right"><!-- 숫자 --></span>
    </p>
  </a>
  <ul class="nav nav-treeview">
    <li class="nav-item">
      <a href="order.jsp" class="nav-link">
        <i class="far fa-circle nav-icon"></i>
        <p>주문 요청 목록</p>
      </a>
    </li>
    <li class="nav-item">
      <a href="refuse_order_list.jsp" class="nav-link">
        <i class="far fa-circle nav-icon"></i>
        <p>거부 목록</p>
      </a>
    </li>
  </ul>
</li>