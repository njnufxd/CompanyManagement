//初始化header的选中样式
const index = document.querySelector(".index");
const contacts = document.querySelector(".contacts");
index.style.background = "linear-gradient(to top, #336699, #3366cc, #336699)";
index.style.color = "#ffffff";
contacts.style.background = "#ffffff";
contacts.style.color = "#336699";

const option = document.querySelector(".option");
const advanceSearch = document.querySelector(".advanceSearch");
let isAdvancedSearch = 0;
advanceSearch.addEventListener("click", function () {
  if (isAdvancedSearch) {
    option.style.display = "none";
  } else {
    option.style.display = "block";
  }
  isAdvancedSearch = !isAdvancedSearch;
});

const one = document.querySelector(".content ul.box");
const totalCount = document.querySelector(".totalCount");
const start = document.querySelector(".start");
const end = document.querySelector(".end");
const pre = document.querySelector(".pre");
const next = document.querySelector(".next");
let pageNum = 1;
let pageSize = 100;
let data = {};
let advanceQuery = {
  name: "",
  gender: "",
  position: "",
  phone: "",
  secondPhone: "",
  tel: "",
  qq: "",
  email: "",
  responsibility: "",
  notes: "",
};
let normalQuery={
    companyName:"",
    position:"",
    name:"",
    gender:"",
    province:"",
    city:"",
    area:"",
    category:"",
    size:""
}
query={
    status:0,
    data:advanceQuery
}
pagingFetch(pageNum, pageSize, query);
function pagingFetch(pageNum, pageSize, query) {
    fetch(
    `/api/contact/${query.status==0?"advance":"normal"}Paging?pageNum=${pageNum}&pageSize=${pageSize}`,
    {
      method: "POST",
      headers: {
        "Content-Type": "application/json;charset=utf-8",
      },
      body: JSON.stringify(query.data),
    }
  )
    .then(function (response) {
      return response.json();
    })
    .then(function (result) {
      data = result.data;
      totalCount.innerHTML = result.data.totalCount;
      let startNumber = (result.data.pageNum - 1) * result.data.pageSize + 1;
      if(data.data.length==0){
        startNumber=0;
      }
      start.innerHTML = startNumber;
      end.innerHTML = startNumber + data.data.length - 1;
      if(startNumber==0){
        end.innerHTML=0;
      }
      one.innerHTML = "";
      data.data.forEach(function (elem, index, a) {
        const li = document.createElement("li");
        let count = 0;
        if (count == 0)
          li.innerHTML =
            `<ul><li style ='background: #E0EEEE;'><a href="/updateCompany?id=${elem.company.id}">${elem.company.companyName}</a></li><li style ='background: #E0EEEE;'>${elem.position}</li><li style ='background: #E0EEEE;'><a href="/updateContact?id=${elem.id}">${elem.name}</a></li><li style ='background: #E0EEEE;'>${elem.gender}</li><li style ='background: #E0EEEE;'>${elem.company.province}</li><li style ='background: #E0EEEE;'>${elem.company.city}</li><li style ='background: #E0EEEE;'>${elem.company.area}</li><li style ='background: #E0EEEE;'>${elem.company.category}</li><li style ='background: #E0EEEE;'>${elem.company.size?elem.company.size:""}</li><li style ='background: #E0EEEE;'>${elem.gmtModified}</li><li style ='background: #E0EEEE;'><a href="/updateContact?id=${elem.id}"><img src='img/write.png'></a><a onclick="return confirmDelete('${elem.id}')"><img src='img/delete.png' ></a></li></ul>`;
        else
          li.innerHTML =
            `<ul><li style ='background: #F0F0F0;'><a href="/updateCompany?id=${elem.company.id}">${elem.company.companyName}</a></li><li style ='background: #F0F0F0;'>${elem.position}</li><li style ='background: #F0F0F0;'><a href="/updateContact?id=${elem.id}">${elem.name}</a></li><li style ='background: #F0F0F0;'>${elem.gender}</li><li style ='background: #F0F0F0;'>${elem.company.province}</li><li style ='background: #F0F0F0;'>${elem.company.city}</li><li style ='background: #F0F0F0;'>${elem.company.area}</li><li style ='background: #F0F0F0;'>${elem.company.category}</li><li style ='background: #F0F0F0;'>${elem.company.size?elem.company.size:""}</li><li style ='background: #F0F0F0;'>${elem.gmtModified}</li><li style ='background: #F0F0F0;'><a href="/updateContact?id=${elem.id}"><img src='img/write.png'></a><a onclick="return confirmDelete('${elem.id}')"><img src='img/delete.png' ></a></li></ul>`;
        one.append(li);
        count = !count;
      });
    });
}
pre.addEventListener("click", function () {
  if (pageNum == 1) {
    alert("当前是第一页");
  } else {
    pageNum--;
    pagingFetch(pageNum, pageSize, query);
  }
});
//列表后一页操作
next.addEventListener("click", function () {
  if (pageNum >= data.totalPage) {
    alert("已到达最后一页");
  } else {
    pageNum++;
    pagingFetch(pageNum, pageSize, query);
  }
});

const advanceName = document.querySelector(".advanceName");
const advanceGender = document.querySelector(".advanceGender");
const advancePosition = document.querySelector(".advancePosition");
const phone = document.querySelector(".phone");
const secondPhone = document.querySelector(".secondPhone");
const tel = document.querySelector(".tel");
const qq = document.querySelector(".qq");
const email = document.querySelector(".email");
const responsibility = document.querySelector(".responsibility");
const notes = document.querySelector(".notes");
const advance_button = document.querySelector(".advance_button");
advance_button.addEventListener("click", function () {
  advanceQuery.name = advanceName.value;
  advanceQuery.gender = advanceGender.value;
  advanceQuery.position = advancePosition.value;
  advanceQuery.phone = phone.value;
  advanceQuery.secondPhone = secondPhone.value;
  advanceQuery.tel = tel.value;
  advanceQuery.qq = qq.value;
  advanceQuery.email = email.value;
  advanceQuery.responsibility = responsibility.value;
  advanceQuery.notes = notes.value;
  pageNum = 1;
  query.data=advanceQuery;
  query.status=0;
  pagingFetch(pageNum, pageSize, query);
});


const companyName=document.querySelector(".companyName");
const position=document.querySelector(".position");
const normalName=document.querySelector(".normalName");
const gender=document.querySelector(".gender");
const province=document.querySelector(".province");
const city=document.querySelector(".city");
const area=document.querySelector(".area");
const category=document.querySelector(".category");
const size=document.querySelector(".size");
const normal_button=document.querySelector(".normalSubmit");
normal_button.addEventListener("click",function(){
    normalQuery.companyName=companyName.value;
    normalQuery.name=normalName.value;
    normalQuery.position=position.value;
    normalQuery.gender=gender.value;
    normalQuery.province=province.value;
    normalQuery.city=city.value;
    normalQuery.area=area.value;
    normalQuery.category=category.value;
    normalQuery.size=size.value;
    pageNum=1;
    query.data=normalQuery;
    query.status=1;
    pagingFetch(pageNum,pageSize,query);
})
  function confirmDelete(id){
    let res=confirm("删除联系人会同时删除下属的联系记录，是否确认删除");
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