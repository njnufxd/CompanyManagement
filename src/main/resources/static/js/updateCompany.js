const index = document.querySelector(".index");
index.style.background = "linear-gradient(to top, #336699, #3366cc, #336699)";
index.style.color = "#ffffff";

const id = document.querySelector(".id");
const companyName = document.querySelector(".companyName");
const address = document.querySelector(".address");
const tel = document.querySelector(".tel");
const webAddress = document.querySelector(".webAddress");
const province = document.querySelector(".province");
const city = document.querySelector(".city");
const area = document.querySelector(".area");
const size = document.querySelector(".size");
const starLevel = document.querySelector(".star_level");
const category = document.querySelector(".category");
const labels = document.querySelector(".labels");
const description = document.querySelector(".description");
const submit = document.querySelector(".submit");
const href = document.querySelector(".href");
const name = document.querySelectorAll(".name");
const error = document.querySelectorAll(".error");

let source = {};
let provinceList = [""];
let cityList = [""];
let areaList = [""];
let company = {};



const one = document.querySelector(".left .content ul.box");
const totalCount = document.querySelector(".totalCount");
const start = document.querySelector(".start");
const end = document.querySelector(".end");
const pre = document.querySelector(".pre");
const next = document.querySelector(".next");
let pageNum = 1;
let pageSize = 10;



fetch(`/api/company/get?id=${id.innerText}`)
  .then(function (response) {
    return response.json();
  })
  .then(function (result) {
    console.log(result);
    company = result.data;
    companyName.value = company.companyName;
    address.value = company.address;
    tel.value = company.tel;
    webAddress.value = company.webAddress;
    size.value = company.size;
    starLevel.value = company.starLevel;
    category.value = company.category;
    labels.value = company.labels;
    description.value = company.description;
    for (let index = 0; index < name.length; index++) {
      name[index].innerHTML = company.companyName;
    }
    loadContacts();
    fetch(`/js/source.json`)
      .then(function (response) {
        return response.json();
      })
      .then(function (result) {
        source = result;
        provinceList = source.中国;
        province.innerHTML = `<option value=""></option>`;
        if (provinceList) {
          for (let index = 0; index < provinceList.length; index++) {
            const option = document.createElement("option");
            option.innerHTML = `${provinceList[index]}`;
            if (company.province == provinceList[index]) {
              option.setAttribute("selected", "selected");
            }
            option.setAttribute("value", provinceList[index]);
            province.append(option);
          }
        }
      })
      .then(function () {
        cityList = source[`${company.province}`];
        city.innerHTML = `<option value=""></option>`;
        if (cityList) {
          for (let index = 0; index < cityList.length; index++) {
            const option = document.createElement("option");
            option.innerHTML = `${cityList[index]}`;
            if (company.city == cityList[index]) {
              option.setAttribute("selected", "selected");
            }
            option.setAttribute("value", cityList[index]);
            city.append(option);
          }
        }
      })
      .then(function () {
        areaList = source[`${company.city}`];
        area.innerHTML = `<option value=""></option>`;
        if (areaList) {
          for (let index = 0; index < areaList.length; index++) {
            const option = document.createElement("option");
            option.innerHTML = `${areaList[index]}`;
            if (company.area == areaList[index]) {
              option.setAttribute("selected", "selected");
            }
            option.setAttribute("value", areaList[index]);
            area.append(option);
          }
        }
      });
  });

var quit = document.querySelector(".quit");
quit.addEventListener("click", function () {
  fetch("/api/user/logout", {
    method: "GET",
  })
    .then(function (response) {
      return response.json;
    })
    .then(function (result) {
      window.location.href = "/index";
    });
});

href.addEventListener("click", function () {
  href.setAttribute("href", webAddress.value);
});

submit.addEventListener("click", function () {
  let data = {
    id: id.innerText,
    companyName: companyName.value,
    address: address.value,
    tel: tel.value,
    webAddress: webAddress.value,
    province: province.value,
    city: city.value,
    area: area.value,
    size: size.value,
    starLevel: starLevel.value,
    category: category.value,
    labels: labels.value,
    description: description.value,
  };
  console.log(data);
  fetch(`/api/company/update`, {
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
      if (result.isSuccess == false) {
        alert("修改失败");
        error[0].innerHTML = `${result.message}`;
      } else {
        alert("修改成功");
        window.location.href = "/companies";
      }
    });
});

const companyList = document.querySelector(".companyList");
companyList.addEventListener("click", function () {
  window.location.href = "/companies";
});
const addCompany = document.querySelector(".addCompany");
addCompany.addEventListener("click", function () {
  window.location.href = "/addCompany";
});

function chooseProvince() {
  cityList = source[`${province.value}`];
  console.log(province.value);
  city.innerHTML = `<option value=""></option>`;
  area.innerHTML = "";
  if (cityList) {
    for (let index = 0; index < cityList.length; index++) {
      const option = document.createElement("option");
      option.innerHTML = `${cityList[index]}`;
      option.setAttribute("value", cityList[index]);
      city.append(option);
    }
  }
}
function chooseCity() {
  areaList = source[`${city.value}`];
  area.innerHTML = `<option value=""></option>`;
  if (areaList) {
    for (let index = 0; index < areaList.length; index++) {
      const option = document.createElement("option");
      option.innerHTML = `${areaList[index]}`;
      option.setAttribute("value", areaList[index]);
      area.append(option);
    }
  }
}

function submitContact(form) {
  let data = {
    name: form.name.value,
    companyId: id.innerText,
    gender: form.gender.value,
    position: form.position.value,
    email: form.email.value,
    tel: form.tel.value,
    qq:form.qq.value,
    phone: form.phone.value,
    secondPhone: form.secondPhone.value,
    responsibility: form.responsibility.value,
    notes: form.notes.value,
  };
  fetch(`/api/contact/add`, {
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
      if (result.isSuccess == false) {
        alert("新建失败");
        error[1].innerHTML = `${result.message}`;
      } else {
        alert("新建成功");
        window.location.href = `/updateCompany?id=${id.innerText}`;
      }
    });
  return false;
}

function loadContacts() {
  let contacts = company.contacts;
  totalCount.innerHTML =contacts.length;
  let startNumber = (pageNum - 1) * pageSize + 1;
  start.innerHTML = startNumber;
  let endNumber= startNumber + pageSize - 1;
  if(endNumber>contacts.length){
    endNumber=contacts.length;
  }
  end.innerHTML =endNumber;
  const contactsUL = document.querySelector(".contactList");
  if (contacts.length == 0) {
    contactsUL.innerHTML = `<li>暂无联系人</li>`;
  } else {
    contactsUL.innerHTML = "";
    for (let index = startNumber-1; index <endNumber; index++) {
      const li = document.createElement("li");
      let item = contacts[index];
      li.innerHTML = `${item.name} ${item.position} <strong>手机:</strong> ${item.phone} <strong>QQ:</strong> ${item.qq} <strong>Email:</strong> ${item.email} <strong>电话:</strong> ${item.tel} <strong>联系人备注:</strong> ${item.notes} <strong>更新时间:</strong> ${item.gmtModified}  <a href="/updateContact?id=${item.id}"><strong>修改</strong></a> <a onclick="return confirmDelete('${item.id}')"><strong>删除</strong></a>`;
      contactsUL.append(li);
    }
  }
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
  if (pageNum*pageSize>=company.contacts.length ) {
    alert("已到达最后一页");
  } else {
    pageNum++;
    loadContacts();
  }
});
  function confirmDelete(id){
    let res=confirm("删除联系人会同时删除下属联系记录，是否确认删除");
    if(res){
        fetch(
            `/api/contact/del?id=${id}`,
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