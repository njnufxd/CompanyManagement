//初始化header的选中样式
const index = document.querySelector(".index");
index.style.background = "linear-gradient(to top, #336699, #3366cc, #336699)";
index.style.color = "#ffffff";

const userName = document.querySelector(".userName");
const pwd = document.querySelector(".pwd");
const email = document.querySelector(".email");
const phone = document.querySelector(".phone");
const qq = document.querySelector(".qq");
const tel = document.querySelector(".tel");
const submit = document.querySelector(".submit");
const id = document.querySelector(".id");
submit.addEventListener("click", function () {
  let data = {
    userName: userName.value,
    email: email.value,
    pwd: pwd.value,
    phone: phone.value,
    qq: qq.value,
    tel: tel.value,
  };
  console.log(data);
  let res = confirm("确认新建？");
  if (res) {
    fetch(`/api/user/add`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json;charset=utf-8",
      },
      body: JSON.stringify(data),
    })
      .then(function (response) {
        return response.json();
      })
      .then(function (result) {
        if (result.isSuccess == true) {
          alert("创建成功");
          window.location.href = "/users";
        } else {
          alert("创建失败");
          const error = document.querySelector(".error");
          error.style.color = "red";
          error.innerHTML = result.message;
        }
      });
  }
});
