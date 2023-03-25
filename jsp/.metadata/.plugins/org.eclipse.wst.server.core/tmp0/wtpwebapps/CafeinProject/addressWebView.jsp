<!DOCTYPE html>
<html lang="en">
<style>
    * {
        margin:0;
        padding:0;
    }
    html {
        width: 100%;
        height : 100%;
        overflow-y:hidden;
    }
    body {
        width : 100%;
        height : 100%;
        overflow : hidden;
    }
    iframe {
        overflow : hidden;
    }
</style>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<script src="https://ssl.daumcdn.net/dmaps/map_js_init/postcode.v2.js"></script>
<script>
  new daum.Postcode({
    oncomplete: function(data) {
      if(data.userSelectedType=="R"){
        if(data.autoJibunAddress) {
                window.testApp.setAddress(data.zonecode, data.autoJibunAddress, data.buildingName);
        } else {
                window.testApp.setAddress(data.zonecode, data.jibunAddress, data.buildingName);
        }
      }
      else{
        window.testApp.setAddress(data.zonecode, data.jibunAddress, data.buildingName);
      }
 
    },
  width : '100%',
  height : '100%',
 
  }).embed(document.body);
</script>
</body>
</html>