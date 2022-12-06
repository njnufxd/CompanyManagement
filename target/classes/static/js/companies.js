//初始化header的选中样式
const index = document.querySelector(".index");
const companies = document.querySelector(".companies");
index.style.background = "linear-gradient(to top, #336699, #3366cc, #336699)";
index.style.color = "#ffffff";
companies.style.background = "#ffffff";
companies.style.color = "#336699";

//----------------------------------------------------------------------------------

//分页查询方法封装
function pagingFetch(pageNum, pageSize,query) {
    fetch(
      `/api/company/paging?pageNum=${pageNum}&pageSize=${pageSize}`,
      {
        method:"POST",
        headers: {
          "Content-Type": "application/json;charset=utf-8"
      },
        body:JSON.stringify(query)
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
          if (count == 0)
            li.innerHTML = `<ul><li style='background: #E0EEEE;  color: #369;'><a href="/updateCompany?id=${elem.id}">${elem.companyName}</a></li><li style='background: #E0EEEE;'>${elem.province}</li><li style='background: #E0EEEE;'>${elem.city}</li><li style='background: #E0EEEE;'>${elem.area}</li><li style='background: #E0EEEE;'>${elem.size?elem.size:""}</li><li style='background: #E0EEEE;'>${elem.category}</li><li style='background: #E0EEEE;'><a href="/updateCompany?id=${elem.id}"><img src='img/write.png'></a><a onclick="return confirmDelete('${elem.id}')"><img src='img/delete.png' ></a></li></ul>`;
          else
            li.innerHTML = `<ul><li style='background: #F0F0F0;  color: #369;'><a href="/updateCompany?id=${elem.id}">${elem.companyName}</a></li><li style='background: #F0F0F0;'>${elem.province}</li><li style='background: #F0F0F0;'>${elem.city}</li><li style='background: #F0F0F0;'>${elem.area}</li><li style='background: #F0F0F0;'>${elem.size?elem.size:""}</li><li style='background: #F0F0F0;'>${elem.category}</li><li style='background: #F0F0F0;'><a href="/updateCompany?id=${elem.id}"><img src='img/write.png'></a><a onclick="return confirmDelete('${elem.id}')"><img src='img/delete.png' ></a></li></ul>`;
          one.append(li);
          count = !count;
        });
      });
  }
//初始化列表数据
const one = document.querySelector(".left .content ul.box");
const totalCount = document.querySelector(".totalCount");
const start = document.querySelector(".start");
const end = document.querySelector(".end");
const pre = document.querySelector(".pre");
const next = document.querySelector(".next");
let count = 0;
let pageNum = 1;
let pageSize = 100;
let data = {};
let query={
    companyName:"",
    address:"",
    description:"",
    labels:"",
    province:"",
    city:"",
    area:"",
    size:"",
    category:""
}
pagingFetch(pageNum,pageSize,query);
//列表前一页操作
pre.addEventListener("click", function () {
  if (pageNum == 1) {
    alert("当前是第一页");
  } else {
    pageNum--;
    pagingFetch(pageNum,pageSize,query);
  }
});
//列表后一页操作
next.addEventListener("click", function () {
  if (pageNum >= data.totalPage) {
    alert("已到达最后一页");
  } else {
    pageNum++;
    pagingFetch(pageNum,pageSize,query);
  }
});

//高级查询展开收起
const advancedSearch = document.querySelector(".advancedSearch");
const option = document.querySelector(".option");
let isAdvancedSearch = 0;
advancedSearch.addEventListener("click", function () {
  if (isAdvancedSearch) {
    option.style.display = "none";
  } else {
    option.style.display = "block";
  }
  isAdvancedSearch = !isAdvancedSearch;
});
//查询按钮调取查询API
const address = document.querySelector(".address");
const description = document.querySelector(".description");
const labels = document.querySelector(".labels");
const advanceProvince=document.querySelector(".advanceProvince");
const advanceCity=document.querySelector(".advanceCity");
const advanceArea=document.querySelector(".advanceArea");
const advanceSize=document.querySelector(".advanceSize");
const advanceCategory=document.querySelector(".advanceCategory");
const advanceSearch=document.querySelector(".advance_search");
advanceSearch.addEventListener("click",function(){
    query.companyName="";
    query.address=address.value;
    query.description=description.value;
    query.labels=labels.value;
    query.province=advanceProvince.value;
    query.city=advanceCity.value;
    query.area=advanceArea.value;
    query.size=advanceSize.value;
    query.category=advanceCategory.value;
    pageNum=1;
    pagingFetch(pageNum,pageSize,query);
})



const companyName=document.querySelector(".companyName");
const province=document.querySelector(".province");
const city=document.querySelector(".city");
const area=document.querySelector(".area");
const size=document.querySelector(".size");
const category=document.querySelector(".category");
const search=document.querySelector(".search");
search.addEventListener("click",function(){
    query.companyName=companyName.value;
    query.province=province.value;
    query.city=city.value;
    query.area=area.value;
    query.size=size.value;
    query.category=category.value;
    query.address="";
    query.description="";
    query.labels="";
    pageNum=1;
    pagingFetch(pageNum,pageSize,query);
})



const addCompany=document.querySelector(".addCompany");
addCompany.addEventListener("click",function(){
  window.location.href="/addCompany";
})

  function confirmDelete(id){
    let res=confirm("删除单位会同时删除下属的联系人和联系记录，是否确认删除？");
    if(res){
        fetch(
            `/api/company/del?id=${id}`,
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