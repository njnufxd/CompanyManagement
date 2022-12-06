const index = document.querySelector(".index");
index.style.background = "linear-gradient(to top, #336699, #3366cc, #336699)";
index.style.color = "#ffffff";

const id = document.querySelector(".supplierId");
const supplierName = document.querySelectorAll(".supplierName");
const description = document.querySelector(".description");
const createdTime = document.querySelector(".createdTime");
const modifiedTime = document.querySelector(".modifiedTime");
let data = {};
fetch(`http://127.0.0.1:8080/api/supplier/get?id=${id.innerHTML}`)
  .then(function (response) {
    return response.json();
  })
  .then(function (result) {
    if (!result.isSuccess) {
      alert(`result.message`);
      window.location.href = "/suppliers";
    } else {
      console.log(result.data);
      data = result.data;
      supplierName[0].innerHTML = data.name;
      supplierName[1].value = data.name;
      description.innerHTML = data.description;
      createdTime.innerHTML = data.gmtCreated;
      modifiedTime.innerHTML = data.gmtModified;
    }
    loadContacts();
  });
const submit = document.querySelector(".submit");
submit.addEventListener("click", function () {
  if (
    description.value == data.description &&
    supplierName[1].value == data.name
  ) {
    alert("没有修改需要提交");
  } else {
    let update = {
      id: id.innerHTML,
      name: supplierName.value,
      description: description.value,
    };
    fetch(`http://127.0.0.1:8080/api/supplier/update`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json;charset=utf-8",
      },
      body: JSON.stringify(update),
    })
      .then(function (response) {
        return response.json();
      })
      .then(function (result) {
        if (result.isSuccess) {
          alert("修改成功");
          location.reload();
        } else {
          alert(`result.message`);
        }
      });
  }
});

const one = document.querySelector(".content ul.box");
const totalCount = document.querySelector(".totalCount");
const start = document.querySelector(".start");
const end = document.querySelector(".end");
const pre = document.querySelector(".pre");
const next = document.querySelector(".next");
let pageNum = 1;
let pageSize = 10;
let contacts = [];

function loadContacts() {
  fetch(`http://127.0.0.1:8080/api/contact/getBySupplierId?id=${id.innerHTML}`)
    .then(function (response) {
      return response.json();
    })
    .then(function (result) {
      contacts = result.data;
      console.log(contacts);
      totalCount.innerHTML = contacts.length;
      let startNumber = (pageNum - 1) * pageSize + 1;
      start.innerHTML = startNumber;
      let endNumber = startNumber + pageSize - 1;
      if (endNumber > contacts.length) {
        endNumber = contacts.length;
      }
      end.innerHTML = endNumber;
      const contactsUL = document.querySelector(".contactList");
      if (contacts.length == 0) {
        contactsUL.innerHTML = `<li>暂无联系人</li>`;
      } else {
        contactsUL.innerHTML = "";
        for (let index = startNumber - 1; index < endNumber; index++) {
          const li = document.createElement("li");
          let item = contacts[index];
          li.innerHTML = `${item.name} ${item.position} <strong>手机:</strong> ${item.phone} <strong>QQ:</strong> ${item.qq} <strong>Email:</strong> ${item.email} <strong>电话:</strong> ${item.tel} <strong>联系人备注:</strong> ${item.notes} <strong>更新时间:</strong> ${item.gmtModified}  <a href="/updateContact?id=${item.id}"><strong>修改</strong></a>`;
          contactsUL.append(li);
        }
      }
    });
}
pre.addEventListener("click", function () {
  if (pageNum == 1) {
    alert("当前是第一页");
  } else {
    pageNum--;
    loadContacts();
  }
});
//列表后一页操作
next.addEventListener("click", function () {
  if (pageNum * pageSize >= contacts.length) {
    alert("已到达最后一页");
  } else {
    pageNum++;
    loadContacts();
  }
});
