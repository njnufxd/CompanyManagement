const index = document.querySelector(".index");
index.style.background = "linear-gradient(to top, #336699, #3366cc, #336699)";
index.style.color = "#ffffff";
const supplierName = document.querySelectorAll(".supplierName");
const description = document.querySelector(".description");
const submit = document.querySelector(".submit");
const error=document.querySelector(".error");

submit.addEventListener("click", function () {
  let add = {
    name: supplierName.value,
    description: description.value,
  };
  fetch(`/api/supplier/add`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json;charset=utf-8",
    },
    body: JSON.stringify(add),
  })
    .then(function (response) {
      return response.json();
    })
    .then(function (result) {
      if (result.isSuccess) {
        alert("新建成功");
        location.reload();
      } else {
        error.innerHTML=result.message;
        alert(`${result.message}`);
      }
    });
});
