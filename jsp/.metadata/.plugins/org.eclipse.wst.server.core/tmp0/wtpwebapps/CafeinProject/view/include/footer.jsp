<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
  <!-- Main Footer -->
  <footer class="main-footer">
    <!-- To the right -->
    <div class="float-right d-none d-sm-inline">
      <!--  http://cafein.freehost.kr/view/include/index.jsp-->
    </div>
    <!-- Default to the left -->
    <!-- <strong>Copyright &copy; 2014-2019 <a href="https://adminlte.io">AdminLTE.io</a>.</strong> All rights reserved.-->
  </footer>
</div>
<!-- ./wrapper -->

<!-- REQUIRED SCRIPTS -->

<!-- jQuery -->
<script src="../../assets/plugins/jquery/jquery.min.js"></script>
<!-- Bootstrap 4 -->
<script src="../../assets/plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
<!-- DataTables -->
<script src="../../assets/plugins/datatables/jquery.dataTables.js"></script>
<script src="../../assets/plugins/datatables-bs4/js/dataTables.bootstrap4.js"></script>
<!-- AdminLTE App -->
<script src="../../assets/dist/js/adminlte.min.js"></script>
<!-- AdminLTE for demo purposes -->
<script src="../../assets/dist/js/demo.js"></script>

<!-- Toastr -->
<script src="../../assets/plugins/toastr/toastr.min.js"></script>

<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.18.0/locale/ko.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.18.0/moment.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/js/bootstrap-datepicker.js"></script>

<!-- date-range-picker -->
<script src="../../assets/plugins/daterangepicker/daterangepicker.js"></script>

<!-- 체크박스/라디오 버튼 디자인 -->
<script src="../../assets/icheck.js"></script>

<!-- 다음 지도. -->
<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>

<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
 
<!-- input mask -->
<script src="../../assets/plugins/moment/moment.min.js"></script>
<script src="../../assets/plugins/inputmask/min/jquery.inputmask.bundle.min.js"></script>

<!-- Tempusdominus Bootstrap 4 -->
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/tempusdominus-bootstrap-4/5.0.1/js/tempusdominus-bootstrap-4.min.js"></script>

<!-- page script -->
<script>
  //테이블 필터.
  $(function () {
    $("#menuTBL").DataTable();
    $('#example2').DataTable({
      "paging": true,
      "lengthChange": false,
      "searching": false,
      "ordering": true,
      "info": true,
      "autoWidth": false,
    });
  });

  
  
  $(function() {
	 //달력 - 주문시.
     $('input[name="datetimes"]').daterangepicker({
       timePicker: true,
       startDate: moment().startOf('hour'),
       endDate: moment().startOf('hour').add(32, 'hour'),
       locale: {
         format: 'M/DD hh:mm A'
       }
     });
    
	 
     //Datemask dd/mm/yyyy
     $('#datemask').inputmask('dd/mm/yyyy', { 'placeholder': 'dd/mm/yyyy' })
     //Datemask2 mm/dd/yyyy
     $('#datemask2').inputmask('mm/dd/yyyy', { 'placeholder': 'mm/dd/yyyy' })
     //Money Euro
     $('[data-mask]').inputmask()
     
     //Timepicker
     $('#timepicker').datetimepicker({
       format: 'LT'
     })
     
     //Timepicker
     $('#timepicker2').datetimepicker({
       format: 'LT'
     })
	 
  });
  
  $('.orderApproal').click(function() {
      toastr.success('Lorem ipsum dolor sit amet, consetetur sadipscing elitr.')
  });
  
  function ep_edit(num, pos, name, id, phone, sdate, stime, etime) {
     $("#ep_edit").on('show.bs.modal', function (event){
        $("#ep_pos").text(pos);
        $("#ep_name").text(name);
          $(".modal-body #ep_id").val(id);
          $(".modal-body #ep_phone").val(phone);
          $(".modal-body #ep_sdate").val(sdate);
          $(".modal-body #ep_stime").val(stime);
          $(".modal-body #ep_etime").val(etime);
        
     });
  }
  
  //직원 상세
  function ep_detail(num, pos, name, id, phone, sdate, stime, etime) {
     $("#ep_detail").on('show.bs.modal', function (event){
        $("#ep_pos_detail").text(pos);
        $("#ep_name_detail").text(name);
          $(".modal-body #ep_id").val(id);
          $(".modal-body #ep_phone").val(phone);
          $(".modal-body #ep_sdate").val(sdate);
          $(".modal-body #ep_stime").val(stime);
          $(".modal-body #ep_etime").val(etime);
        
     });
  }
  
  
  
  function epUpdate(){
     employeeEdit.action="employee_edit.jsp";
     employeeEdit.submit();
  }
  
  function cpUpdate(){
     complainEdit.action="complain_edit.jsp";
     complainEdit.submit();
  }
  
  //재고 등록 버튼을 누를 때.
  function inInsert(){
     if (!$('[name=in_name]').val()){ //값이 없으면.
        alert("재고명을 작성해주세요.");
        ingredientInsert.in_name.focus();
        return;
        
     } else if(!$('[name=in_amnt]').val()){
        alert("재고 수량을 입력해주세요.");
        ingredientInsert.in_amnt.focus();
        return;
        
     }
     
     ingredientInsert.action="ingredient_insert.jsp";
     ingredientInsert.submit();
     alert("재고가 등록되었습니다.");
  }
  
  
  //재고 수정 버튼을 누를 때.
  function inUpdate(){
     if (!$('[name=in_name_edit]').val()){ //값이 없으면.
        alert("재고명을 작성해주세요.");
        ingredientUpdate.in_num_edit.focus();
        return;
        
     } else if(!$('[name=in_amnt_edit]').val()){
        alert("재고 수량을 입력해주세요.");
        ingredientUpdate.in_num_edit.focus();
        return;
        
     }
     
     ingredientUpdate.action="ingredient_edit.jsp";
     ingredientUpdate.submit();
     alert("재고가 등록되었습니다.");
  }
  
  //메뉴 등록 버튼을 누를 때.
  function mnInsert(){
     if (!$('[name=mn_img]').val()){ //값이 없으면.
        alert("사진을 등록해주세요.");
        menuInsert.mn_img.focus();
        return;
        
     } else if(!$('[name=mn_name]').val()){
        alert("메뉴 이름을 작성해주세요.");
        menuInsert.mn_name.focus();
        return;
        
     } else if(!$('[name=mn_price]').val()){
        alert("메뉴 가격을 작성해주세요.");
        menuInsert.mn_price.focus();
        return;
        
     } else if($('[name=mn_price]').val() <= 0){
        alert("메뉴 가격을 다시 입력해주세요.");
        menuInsert.mn_price.focus();
        return; 
        
     }else if($("input:checkbox[name=in_num]:checked").length == 0){
        alert("적어도 한 가지 이상 재료를 선택해주세요.");
        return;
        
     }
     
     /*var ingre_check = new Array(); //배열선언

     $("input:checkbox[name=in_num]:checked").each(function() {
        ingre_check.push($(this).val());   // 값 찍어보시면 a,b 이런식으로 콤마 구분자가 같이 들어갑니다. 
          alert($(this).val());
     });*/
     
     menuInsert.action="menu_insert.jsp";
     menuInsert.submit();
     alert("메뉴를 등록하였습니다.");
    
     
  }
  
  
  //메뉴 수정
  function mnUpdate(){
     if (!$('[name=mn_img_edit]').val()){ //값이 없으면.
        alert("사진을 등록해주세요.");
        menuInsert.mn_img_edit.focus();
        return;
        
     } else if(!$('[name=mn_name_edit]').val()){
        alert("메뉴 이름을 작성해주세요.");
        menuInsert.mn_name_edit.focus();
        return;
        
     } else if(!$('[name=mn_price_edit]').val()){
        alert("메뉴 가격을 작성해주세요.");
        menuInsert.mn_price_edit.focus();
        return;
        
     } else if($('[name=mn_price]').val() <= 0){
        alert("메뉴 가격을 다시 입력해주세요.");
        menuInsert.mn_price.focus();
        return; 
        
     }else if($("input:checkbox[name=in_num2]:checked").length == 0){
        alert("적어도 한 가지 이상 재료를 선택해주세요.");
        return;
        
     }
     
     menuUpdate.action="menu_edit.jsp";
     menuUpdate.submit();
     alert("메뉴 정보가 수정되었습니다.");
  }
  
  
  
  
  $("#fileInput").on('change', function(){  // 값이 변경되면
      
      if(window.FileReader){  // modern browser

         var filename = $(this)[0].files[0].name;

      } else {  // old IE

         var filename = $(this).val().split('/').pop().split('\\').pop();  // 파일명만 추출

      }



      // 추출한 파일명 삽입

      $("#userfile").val(filename);

   });
  
  $(document).ready(function(){
     $('input').iCheck({
          checkboxClass: 'icheckbox_square-blue',
          radioClass: 'iradio_square-blue',
          increaseArea: '20%' // optional
     });
  });
  
  //매출관리메인
  //Date range picker
  $('#salesDP').daterangepicker({
     locale: {
        format: 'YYYY년 MM월 DD일',
        language : 'ko',
        startDate: new Date()
     }
  });
  
  //컴플레인 수정
  function cp_edit(cp_id, us_id, title, type, date, wdate, reply, content) {
      $("#cp_edit").on('show.bs.modal', function (event){
           $("#cp_type").text(type);
           $("#cp_title").text(title);
           $(".modal-body #cp_id").val(cp_id);
           $(".modal-body #us_id").val(us_id);
           $(".modal-body #cp_date").val(date);
           $(".modal-body #cp_wdate").val(wdate);
           $(".modal-body #cp_reply").val(reply);
           $(".modal-body #cp_content").val(content);
           
           if (!$(".modal-body #cp_reply").val()) {//값이 없으면 
              $(".modal-body #cp_reply").prop('readonly', false);
              $('#cp_edit_btn').prop('disabled', false);
           } else { //값이 있으면
              $(".modal-body #cp_reply").prop('readonly', true); 
              $('.modal>#cp_edit').modal("hide"); //닫기 

           }
           
      });
  }
  
  
  //메뉴 수정
  function menu_edit(num, name, price, type, img, tem) {
      $("#menu_edit").on('show.bs.modal', function (event){ //모달을 클릭시.
    	  //값 지정하기.
    	  $(".modal-body #mn_num_edit").val(num);
          $(".modal-body #mn_name_edit").val(name);
          $(".modal-body #mn_price_edit").val(price);
          
          if (type == "커피"){
        	  $("#cRadio").iCheck('check');
          } else if(type == "논커피") {
        	  $("#nRadio").iCheck('check');
          } else if(type == "베이커리") {
        	  $("#bRadio").iCheck('check');
          }
          
          $(".modal-body #userfile_edit").val(img);
          
          if (tem == 'hi') {
        	  $("#h_edit").iCheck('check');
        	  $("#i_edit").iCheck('check');
          } else if (tem == 'i') {
        	  $("#i_edit").iCheck('check');
          } else if (tem == 'h') {
        	  $("#h_edit").iCheck('check');
          }
          
      });
  }
 
  
  
  //재고 수정.
  function in_edit(in_num, in_name, in_amnt) {
      $("#in_edit").on('show.bs.modal', function (event){
           $(".modal-body #in_num_edit").val(in_num);
           $(".modal-body #in_name_edit").val(in_name);
           $(".modal-body #in_amnt_edit").val(in_amnt);
      });
  }
  
  //거부된 주문 사유 작성
  function ro_edit(od_num, mn_name, od_amnt, od_date, reason) {
      $("#ro_edit").on('show.bs.modal', function (event){
           $(".modal-body #od_num").val(od_num);
           $(".modal-body #mn_name").val(mn_name);
           $(".modal-body #od_amnt").val(od_amnt);
           $(".modal-body #od_date").val(date);
           $(".modal-body #od_reason").val(reason);
           
          
      });
  }
  
  //회원가입 빈칸 확인
  function Insert(){
	  if (frmmemberInsert.ep_name.value == "") {
		   alert("이름이 입력되지 않았습니다.");
		   frmmemberInsert.ep_name.focus();
		   return false;
		}
		if (frmmemberInsert.ep_id.value == "") {
		   alert("아이디가 입력되지 않았습니다.");
		   frmmemberInsert.ep_id.focus();
		   return false;
		}
		if (frmmemberInsert.ep_pw.value == "") {
		   alert("비밀번호가 입력되지 않았습니다.");
		   frmmemberInsert.ep_pw.focus();
		   return false;
		}
		if (frmmemberInsert.ep_pos.value == "") {
		   alert("직급이 선택되지 않았습니다.");
		   frmmemberInsert.ep_pos.focus();
		   return false;
		}
		if (frmmemberInsert.ep_sdate.value == "") {
		   alert("입사일이 입력되지 않았습니다.");
		   frmmemberInsert.ep_sdate.focus();
		   return false;
		}
		if (frmmemberInsert.ep_stime.value == "") {
		   alert("출근 시간이 입력되지 않았습니다.");
		   frmmemberInsert.ep_stime.focus();
		   return false;
		}
		if (frmmemberInsert.ep_etime.value == "") {
		   alert("퇴근시간이 입력되지 않았습니다.");
		   frmmemberInsert.ep_etime.focus();
		   return false;
		}

		frmmemberInsert.action = "join_action.jsp";
		frmmemberInsert.submit();
		alert("회원가입이 완료되었습니다.");
  }
  
  //다음 주소
  function sample6_execDaumPostcode() {
       new daum.Postcode({
          oncomplete : function(data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var fullAddr = ''; // 최종 주소 변수
            var extraAddr = ''; // 조합형 주소 변수

            // 사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                fullAddr = data.roadAddress;

            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                fullAddr = data.jibunAddress;
            }

            // 사용자가 선택한 주소가 도로명 타입일때 조합한다.
            if (data.userSelectedType === 'R') {
                //법정동명이 있을 경우 추가한다.
                if (data.bname !== '') {
                    extraAddr += data.bname;
                }
                // 건물명이 있을 경우 추가한다.
                if (data.buildingName !== '') {
                    extraAddr += (extraAddr !== '' ? ', '
                            + data.buildingName : data.buildingName);
                }
                // 조합형주소의 유무에 따라 양쪽에 괄호를 추가하여 최종 주소를 만든다.
                fullAddr += (extraAddr !== '' ? ' (' + extraAddr
                        + ')' : '');
            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById('st_postcode').value = data.zonecode; //5자리 새우편번호 사용
            document.getElementById('st_address').value = fullAddr;

            // 커서를 상세주소 필드로 이동한다.
            document.getElementById('st_address2').focus();
          }
      }).open();
    }
  
  
  

 
  
</script>

</body>
</html>