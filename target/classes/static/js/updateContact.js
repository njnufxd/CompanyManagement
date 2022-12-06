const index = document.querySelector(".index");
index.style.background = "linear-gradient(to top, #336699, #3366cc, #336699)";
index.style.color = "#ffffff";

const userId=document.querySelector(".userId");
const contactNames = document.querySelectorAll(".contactName");
const id = document.querySelector(".id");
const companyName = document.querySelector(".companyName");
const address = document.querySelector(".address");
const companyTel = document.querySelector(".companyTel");
const size = document.querySelector(".size");
const webAddress = document.querySelector(".webAddress");
const description = document.querySelector(".description");
const time = document.querySelector(".time");
const companyInput = document.querySelector(".companyInput");
const name = document.querySelector(".name");
const gender = document.querySelector(".gender");
const position = document.querySelector(".position");
const phone = document.querySelector(".phone");
const secondPhone = document.querySelector(".secondPhone");
const tel = document.querySelector(".tel");
const qq = document.querySelector(".qq");
const email = document.querySelector(".email");
const responsibility = document.querySelector(".responsibility");
const notes = document.querySelector(".notes");
const updateContact = document.querySelector(".updateContact");
let supplierList = [];
let data = {};
let records=[];
fetch(`/api/contact/get?id=${id.innerHTML}`)
  .then(function (response) {
    return response.json();
  })
  .then(function (result) {
    data = result.data;
    if (!data.supplierId) {
      data.supplierId = "";
    }
    let company = data.company;
    console.log(data);
    records=data.records;
    console.log(records);
    contactNames.forEach(function (contactName, index, a) {
      contactName.innerHTML = data.name;
    });
    companyName.innerHTML = `<a href="/updateCompany?id=${company.id}">${company.companyName}</a>`;
    address.innerHTML = company.address;
    companyTel.innerHTML = company.tel;
    size.innerHTML = company.size ? company.size : "";
    webAddress.innerHTML = `<a href="${company.webAddress}">${company.webAddress}</a>`;
    description.innerHTML = company.description;
    time.innerHTML = company.gmtModified;
    loadRecords();
    name.value = data.name;
    gender.value = data.gender;
    position.value = data.position;
    phone.value = data.phone;
    secondPhone.value = data.secondPhone;
    tel.value = data.tel;
    qq.value = data.qq;
    email.value = data.email;
    responsibility.value = data.responsibility;
    notes.value = data.notes;
    fetch(`/api/supplier/all`)
      .then(function (response) {
        return response.json();
      })
      .then(function (result) {
        supplierList = result.data;
        const supplierName = document.querySelector(".supplierName");
        supplierName.innerHTML = "";
        const option = document.createElement("option");
        option.setAttribute("value", "");
        option.innerHTML = "未选定供应商";
        supplierName.append(option);
        supplierList.forEach(function (elem, index, a) {
          const option = document.createElement("option");
          option.setAttribute("value", elem.id);
          option.innerHTML = elem.name;
          if (elem.id == data.supplierId) {
            option.setAttribute("selected", "selected");
          }
          supplierName.append(option);
        });
      });
  });
const confirmButton = document.querySelector(".confirm");
const supplierName = document.querySelector(".supplierName");
function supplier() {
  if (supplierName.value == data.supplierId) {
    confirmButton.style.display = "none";
  } else {
    confirmButton.style.display = "inline-block";
  }
}
confirmButton.addEventListener("click", function () {
  fetch(
    `/api/contact/updateSupplier?id=${data.id}&supplierId=${supplierName.value}`
  )
    .then(function (response) {
      return response.json();
    })
    .then(function (result) {
      if (result.isSuccess == true) {
        alert("修改成功");
        confirmButton.style.display = "none";
        data.supplierId = result.data.supplierId;
      } else {
        alert("修改失败");
      }
    });
});
updateContact.addEventListener("click", function () {
  let data = {
    id: id.innerHTML,
    companyName: "",
    name: name.value,
    gender: gender.value,
    position: position.value,
    phone: phone.value,
    secondPhone: secondPhone.value,
    tel: tel.value,
    qq: qq.value,
    email: email.value,
    responsibility: responsibility.value,
    notes: notes.value,
  };
  fetch(`/api/contact/update`, {
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
        alert("修改成功");
        window.location.href="/contacts";
      } else {
        alert("修改失败");
        const error=document.querySelector(".contactError");
        error.innerHTML=`${result.message}`;
      }
    });
});

const recordText = document.querySelector(".record-text");
const newRecord = document.querySelector(".newRecord");
newRecord.addEventListener("click", function () {
  let record = {
    createdId:userId.innerHTML,
    modifiedId:userId.innerHTML,
    supplierId: supplierName.value,
    content: recordText.value,
    contactId: data.id,
  };
  fetch(`/api/record/add`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json;charset=utf-8",
    },
    body: JSON.stringify(record),
  })
    .then(function (response) {
      return response.json();
    })
    .then(function (result) {
      if (result.isSuccess == true) {
        alert("新建成功");
        location.reload();
      } else {
        alert("修改失败");
        const error=document.querySelector(".recordError");
        error.innerHTML=`${result.message}`;
      }
    });
});

const recordList = document.querySelector(".record-list");
const totalCount = document.querySelector(".totalCount");
const start = document.querySelector(".start");
const end = document.querySelector(".end");
const pre = document.querySelector(".pre");
const next = document.querySelector(".next");
let pageNum = 1;
let pageSize = 10;

pre.addEventListener("click", function () {
    if (pageNum == 1) {
      alert("当前是第一页");
    } else {
      pageNum--;
      loadRecords();
    }
  });
  //列表后一页操作
  next.addEventListener("click", function () {
    if (pageNum*pageSize>=records.length ) {
      alert("已到达最后一页");
    } else {
      pageNum++;
      loadRecords();
    }
  });
  function loadRecords() {
    totalCount.innerHTML =records.length;
    let startNumber = (pageNum - 1) * pageSize + 1;
    if(records.length==0){
        startNumber=0;
    }
    start.innerHTML = startNumber;
    let endNumber= startNumber + pageSize - 1;
    if(endNumber>records.length){
      endNumber=records.length;
    }
    end.innerHTML =endNumber;
    if (records.length == 0) {
      recordList.innerHTML = `<div>暂无联系记录</div>`;
    } else {
      recordList.innerHTML = "";
      for (let index = startNumber-1; index <endNumber; index++) {
        const div = document.createElement("div");
        div.setAttribute("class","record");
        let elem = records[index];
        div.innerHTML = `<ul>
        <li><strong>操作: </strong> <div> <a href="/updateRecord?id=${elem.id}"> 修改 </a></div> <div> <a onclick="return confirmDelete('${elem.id}')"> 删除 </a></div></li>
        <li><strong>联系记录:</strong><div class="recordContent">${elem.content}</div> </li>
        <li><strong>创建人ID:</strong><span>${elem.createdUser?elem.createdUser.userName:"该管理员已被删除"}</span> </li>
        <li><strong>创建时间:</strong><span>${elem.gmtCreated}</span> </li>
        <li><strong>更新时间:</strong><span>${elem.gmtModified}</span> </li>
        <li><strong>更新人ID:</strong><span>${elem.modifiedUser?elem.modifiedUser.userName:"该管理员已被删除"}</span> </li>
        <li><strong>供应商ID:</strong><span>${elem.supplier.name}</span> </li>
    </ul>`;
        recordList.append(div);
      }
    }
  }
  function confirmDelete(id){
    let res=confirm("是否确认删除");
    if(res){
        fetch(
            `/api/record/del?id=${id}`,
        ).then(function(response){
            return response.json();
        }).then(function(result){
            if(result.isSuccess){
                alert("删除成功");
                location.reload();
            }else{
                alert(`${result.message}`);
            }
        })
    }
  }
