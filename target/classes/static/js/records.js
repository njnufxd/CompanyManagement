//初始化header的选中样式
const index = document.querySelector(".index");
const records = document.querySelector(".records");
index.style.background = "linear-gradient(to top, #336699, #3366cc, #336699)";
index.style.color = "#ffffff";
records.style.background = "#ffffff";
records.style.color = "#336699";


const one = document.querySelector(".content ul.box");
const totalCount = document.querySelector(".totalCount");
const start = document.querySelector(".start");
const end = document.querySelector(".end");
const pre = document.querySelector(".pre");
const next = document.querySelector(".next");
let pageNum = 1;
let pageSize = 50;
let data = {};
let query={
    companyName:"",
    position:"",
    contactName:"",
    content:"",
    province:"",
    city:"",
    area:"",
    category:"",
    size:""
}
pagingFetch();
function pagingFetch() {
    let url=`/api/record/query?pageNum=${pageNum}&pageSize=${pageSize}`;
    console.log(url);
    console.log(query);
    fetch(
      url,
    {
      method: "POST",
      headers: {
        "Content-Type": "application/json;charset=utf-8",
      },
      body: JSON.stringify(query),
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
            `<ul><li style ='background: #E0EEEE;'><a href="/updateCompany?id=${elem.contact.company.id}">${elem.contact.company.companyName}</a></li><li style ='background: #E0EEEE;'>${elem.contact.position}</li><li style ='background: #E0EEEE;'><a href="/updateContact?id=${elem.contact.id}">${elem.contact.name}</a></li><li style ='background: #E0EEEE;'>${elem.content}</li><li style ='background: #E0EEEE;'>${elem.contact.company.province}</li><li style ='background: #E0EEEE;'>${elem.contact.company.city}</li><li style ='background: #E0EEEE;'>${elem.contact.company.area}</li><li style ='background: #E0EEEE;'>${elem.contact.company.category}</li><li style ='background: #E0EEEE;'>${elem.contact.company.size?elem.contact.company.size:""}</li><li style ='background: #E0EEEE;'>${elem.gmtModified}</li><li style ='background: #E0EEEE;'><a href="/updateRecord?id=${elem.id}"><img src='img/write.png'></a><a onclick="return confirmDelete('${elem.id}')"><img src='img/delete.png' ></a></li></ul>`;
            else
            li.innerHTML =
            `<ul><li style ='background: #F0F0F0;'><a href="/updateCompany?id=${elem.contact.company.id}">${elem.contact.company.companyName}</a></li><li style ='background: #F0F0F0;'>${elem.contact.position}</li><li style ='background: #F0F0F0;'><a href="/updateContact?id=${elem.contact.id}">${elem.contact.name}</a></li><li style ='background: #F0F0F0;'>${elem.content}</li><li style ='background: #F0F0F0;'>${elem.contact.company.province}</li><li style ='background: #F0F0F0;'>${elem.contact.company.city}</li><li style ='background: #F0F0F0;'>${elem.contact.company.area}</li><li style ='background: #F0F0F0;'>${elem.contact.company.category}</li><li style ='background: #F0F0F0;'>${elem.contact.company.size?elem.contact.company.size:""}</li><li style ='background: #F0F0F0;'>${elem.gmtModified}</li><li style ='background: #F0F0F0;'><a href="/updateRecord?id=${elem.id}"><img src='img/write.png'></a><a onclick="return confirmDelete('${elem.id}')"><img src='img/delete.png' ></a></li></ul>`;
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
      pagingFetch();
    }
  });
  //列表后一页操作
  next.addEventListener("click", function () {
    if (pageNum >= data.totalPage) {
      alert("已到达最后一页");
    } else {
      pageNum++;
      pagingFetch();
    }
  });

  const companyName=document.querySelector(".companyName");
const position=document.querySelector(".position");
const contactName=document.querySelector(".contactName");
const recordContent=document.querySelector(".recordContent");
const province=document.querySelector(".province");
const city=document.querySelector(".city");
const area=document.querySelector(".area");
const category=document.querySelector(".category");
const size=document.querySelector(".size");
const submit=document.querySelector(".submit");
submit.addEventListener("click",function(){
    query.companyName=companyName.value;
    query.contactName=contactName.value;
    query.position=position.value;
    query.content=recordContent.value;
    query.province=province.value;
    query.city=city.value;
    query.area=area.value;
    query.category=category.value;
    query.size=size.value;
    pageNum=1;
    pagingFetch();
})
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