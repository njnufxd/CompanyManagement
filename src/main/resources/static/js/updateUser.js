//初始化header的选中样式
const index = document.querySelector(".index");
index.style.background = "linear-gradient(to top, #336699, #3366cc, #336699)";
index.style.color = "#ffffff";


const userName=document.querySelector(".userName")
const email=document.querySelector(".email");
const phone=document.querySelector(".phone");
const qq=document.querySelector(".qq");
const tel=document.querySelector(".tel");
const id=document.querySelector(".id");
fetch(
    `api/user/get?id=${id.innerHTML}`
).then(function(response){
    return response.json();
}).then(function(result){
    userName.value=result.data.userName;
    email.value=result.data.email;
    phone.value=result.data.phone;
    qq.value=result.data.qq;
    tel.value=result.data.tel;
})