//初始化header的选中样式
const index = document.querySelector(".index");
const suppliers = document.querySelector(".suppliers");
index.style.background = "linear-gradient(to top, #336699, #3366cc, #336699)";
index.style.color = "#ffffff";
suppliers.style.background = "#ffffff";
suppliers.style.color = "#336699";

const id=document.querySelector(".id");
const supplierName=document.querySelector(".supplierName");
const search=document.querySelector(".search");
let pageNum=1;
let pageSize=100;
let data={};
loadingSuppliers();

const one=document.querySelector(".box")
const totalCount = document.querySelector(".totalCount");
const start = document.querySelector(".start");
const end = document.querySelector(".end");
const pre = document.querySelector(".pre");
const next = document.querySelector(".next");

//列表前一页操作
pre.addEventListener("click", function () {
   if (pageNum == 1) {
     alert("当前是第一页");
   } else {
     pageNum--;
     loadingSuppliers();
   }
 });
 //列表后一页操作
 next.addEventListener("click", function () {
   if (pageNum >= data.totalPage) {
     alert("已到达最后一页");
   } else {
     pageNum++;
     loadingSuppliers();
   }
 });
search.addEventListener("click",function(){
   loadingSuppliers();
})
function loadingSuppliers(){
   let url=`/api/supplier/paging?pageNum=${pageNum}&pageSize=${pageSize}`;
   let query={
      id:id.value,
      name:supplierName.value
   }
   fetch(
      url,
      {
         method:"POST",
         headers: {
           "Content-Type": "application/json;charset=utf-8"
       },
         body:JSON.stringify(query)
      }
   ).then(function(response){
      return response.json();
   }).then(function(result){
      data = result.data;
      totalCount.innerHTML = data.totalCount;
      let startNumber = (data.pageNum - 1) * data.pageSize + 1;
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
          li.innerHTML = `<ul><li style='background:#E0EEEE'>${elem.id}</li><li style='background:#E0EEEE'><a href="/updateSupplier?id=${elem.id}">${elem.name}</a></li><li style='background:#E0EEEE'><a href="/updateSupplier?id=${elem.id}"><img src='img/write.png'></a><a onclick="return confirmDelete('${elem.id}')"><img src='img/delete.png' ></a></li></ul>`;
        else
          li.innerHTML = `<ul><li style='background:#E0EEEE'>${elem.id}</li><li style='background:#E0EEEE'><a href="/updateSupplier?id=${elem.id}">${elem.name}</a></li><li style='background:#E0EEEE'><a href="/updateSupplier?id=${elem.id}"><img src='img/write.png'></a><a onclick="return confirmDelete('${elem.id}')"><img src='img/delete.png' ></a></li></ul>`;
        one.append(li);
        count = !count;
      });
   })
}
  function confirmDelete(id){
    let res=confirm("是否确认删除");
    if(res){
        fetch(
            `/api/supplier/del?id=${id}`,
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