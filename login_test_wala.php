<?php
   $con=mysqli_connect("files.000webhost.com","rajat1pnm","Rajat1","id686923_temp");

   if (mysqli_connect_errno($con)) {
      echo "Failed to connect to MySQL: " . mysqli_connect_error();
   }
	
   $username = $_POST['username'];
   $password = $_POST['password'];
   $result = mysqli_query($con,"SELECT Role FROM table1 where 
   Username='$username' and Password='$password'");
   $row = mysqli_fetch_array($result);
   $data = $row[0];

   if($data){
      echo $data;
   }
	
   mysqli_close($con);
?>