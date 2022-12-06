const index = document.querySelector(".index");
index.style.background = "linear-gradient(to top, #336699, #3366cc, #336699)";
index.style.color = "#ffffff";

const userId = document.querySelector(".userId");
const id = document.querySelector(".recordId");
const companyName = document.querySelector(".companyName");
const contactName = document.querySelector(".contactName");
const contactPhone = document.querySelector(".contactPhone");
const size = document.querySelector(".size");
const webAddress = document.querySelector(".webAddress");
const recordContent = document.querySelector(".recordContent");
const createdTime = document.querySelector(".createdTime");
const modifiedTime = document.querySelector(".modifiedTime");
const createdName = document.querySelector(".createdName");
const modifiedName = document.querySelector(".modifiedName");
const supplierName = document.querySelector(".supplierName");
let data = {};
fetch(`/api/record/get?id=${id.innerHTML}`)
  .then(function (response) {
    return response.json();
  })
  .then(function (result) {
    console.log(result.data);
    data = result.data;
    companyName.innerHTML = data.contact.company.companyName;
    contactName.innerHTML = data.contact.name;
    contactPhone.innerHTML = data.contact.phone;
    size.innerHTML = data.contact.company.size ? data.contact.company.size : "";
    webAddress.innerHTML = data.contact.company.webAddress;
    recordContent.innerHTML = data.content;
    createdTime.innerHTML = data.gmtCreated;
    modifiedTime.innerHTML = data.gmtModified;
    if(data.createdUser){
        createdName.innerHTML = data.createdUser.userName;
    }else{
        createdName.innerHTML="该管理员已被删除";
    }
    if(data.modifiedUser){
        modifiedName.innerHTML = data.modifiedUser.userName;
    }else{
        modifiedName.innerHTML="该管理员已被删除";
    }
    supplierName.innerHTML = data.supplier.name;
    companyName.setAttribute("href",`/updateCompany?id=${data.contact.company.id}`);
    contactName.setAttribute("href",`/updateContact?id=${data.contact.id}`);
    webAddress.setAttribute("href",data.contact.company.webAddress);
    supplierName.setAttribute("href",`updateSupplier?id=${data.supplier.id}`);
  });
const submit = document.querySelector(".submit");
submit.addEventListener("click", function () {
  if (recordContent.value == data.content) {
    alert("没有修改需要提交");
  } else {
    let update = {
      id: id.innerHTML,
      content: recordContent.value,
      modifiedId: userId.innerHTML,
    };
    fetch(`/api/record/update`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json;charset=utf-8",
      },
      body: JSON.stringify(update),
    }).then(function(response){
        return response.json();
    }).then(function(result){
        if(result.isSuccess){
            alert("修改成功");
            location.reload();
        }else{
            alert(`result.message`);
        }
    })
  }
});
