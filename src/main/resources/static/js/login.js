var index=document.querySelector(".index");
var login=document.querySelector(".login");
index.style.background="linear-gradient(to top, #336699, #3366cc, #336699)";
index.style.color="#ffffff";
login.style.background="#ffffff";
login.style.color="#336699";
var loginForm_userName=document.getElementById("LoginForm_username");
var loginForm_pwd=document.getElementById("LoginForm_pwd");
var submitButton=document.querySelector(".submit");
submitButton.addEventListener("click",function(){
    var userName=loginForm_userName.value;
    var pwd=loginForm_pwd.value;
    let formData=new FormData();
    formData.append("userName",userName);
    formData.append("pwd",pwd);
    fetch(
        '/api/user/login',
        {
            method:'POST',
            body:formData
        }

    ).then(function(response){
        return response.json();
    }).then
    (function(result){
        if(result.isSuccess==false){
            console.log(result.message);
            const error=document.querySelector(".error");
            error.innerHTML=`${result.message}`;
        }else{
           window.location.href='/index';
        }
    })
})