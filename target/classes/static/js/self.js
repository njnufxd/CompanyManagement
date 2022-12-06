//初始化header的选中样式
const index = document.querySelector(".index");
const self = document.querySelector(".self");
index.style.background = "linear-gradient(to top, #336699, #3366cc, #336699)";
index.style.color = "#ffffff";
self.style.background = "#ffffff";
self.style.color = "#336699";



const pwd=document.querySelector(".pwd");
const email=document.querySelector(".email");
const phone=document.querySelector(".phone");
const qq=document.querySelector(".qq");
const tel=document.querySelector(".tel");
const submit=document.querySelector(".submit");
fetch(
    `api/user/get`
).then(function(response){
    return response.json();
}).then(function(result){
    console.log(result);
    email.value=result.data.email;
    phone.value=result.data.phone;
    qq.value=result.data.qq;
    tel.value=result.data.tel;
})

submit.addEventListener("click",function(){
    let data={
        email:email.value,
        pwd:pwd.value,
        phone:phone.value,
        qq:qq.value,
        tel:tel.value
    }
    fetch(
        `/api/user/update`,
        {
            method:"POST",
            headers: {
                "Content-Type": "application/json;charset=utf-8"
            },
            body:JSON.stringify(data)
        }
    ).then(function(response){
        return response.json();
    }).then(function(result){
        if(result.isSuccess==true){
            alert("修改成功");
            window.location.href="/self"
        }else{
            alert("修改失败");
            const error=document.querySelector(".error");
            error.style.color="red";
            error.innerHTML=result.message;
        }
    })
})