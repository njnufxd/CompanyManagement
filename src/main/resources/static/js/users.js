//初始化header的选中样式
const index = document.querySelector(".index");
const users = document.querySelector(".users");
index.style.background = "linear-gradient(to top, #336699, #3366cc, #336699)";
index.style.color = "#ffffff";
users.style.background = "#ffffff";
users.style.color = "#336699";

const one = document.querySelector(".content ul.box");
const totalCount = document.querySelector(".totalCount");
const start = document.querySelector(".start");
const end = document.querySelector(".end");
const pre = document.querySelector(".pre");
const next = document.querySelector(".next");
const id = document.querySelector(".id");
const userName = document.querySelector(".userName");
const email = document.querySelector(".email");
const phone = document.querySelector(".phone");
const qq = document.querySelector(".qq");
const tel = document.querySelector(".tel");
const search=document.querySelector(".search");
let pageNum = 1;
let pageSize = 100;
let data = {};
pagingFetch(pageNum, pageSize);
function pagingFetch(pageNum, pageSize) {
  let url = `/api/user/paging?pageNum=${pageNum}&pageSize=${pageSize}`;
  let query = {
    id:id.value,
    userName: userName.value,
    phone: phone.value,
    tel: tel.value,
    qq: qq.value,
    email: email.value
  };
  fetch(url, {
    method: "POST",
    headers: {
      "Content-Type": "application/json;charset=utf-8",
    },
    body: JSON.stringify(query),
  })
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
          li.innerHTML = `<ul><li style='background: #E0EEEE;'>${elem.id}</li><li style='background: #E0EEEE;'><a href="/updateUser?id=${elem.id}">${elem.userName}</a></li><li style='background: #E0EEEE;'>${elem.email}</li><li style='background: #E0EEEE;'>${elem.phone}</li><li style='background: #E0EEEE;'>${elem.qq}</li><li style='background: #E0EEEE;'>${elem.tel}</li><li style='background: #E0EEEE;'><a href="/updateUser?id=${elem.id}"><img src='img/write.png'></a><a onclick="return confirmDelete('${elem.id}')"><img src='img/delete.png' ></a></li></ul>`;
        else
          li.innerHTML = `<ul><li style='background: #F0F0F0;'>${elem.id}</li><li style='background: #F0F0F0;'><a href="/updateUser?id=${elem.id}">${elem.userName}</a></li><li style='background: #F0F0F0;'>${elem.email}</li><li style='background: #F0F0F0;'>${elem.phone}</li><li style='background: #F0F0F0;'>${elem.qq}</li><li style='background: #F0F0F0;'>${elem.tel}</li><li style='background: #F0F0F0;'><a href="/updateUser?id=${elem.id}"><img src='img/write.png'></a><a onclick="return confirmDelete('${elem.id}')"><img src='img/delete.png' ></a></li></ul>`;
        one.append(li);
        count = !count;
      });
    });
}
search.addEventListener("click",function(){
    pagingFetch(pageNum,pageSize);
})
//列表前一页操作
pre.addEventListener("click", function () {
  if (pageNum == 1) {
    alert("当前是第一页");
  } else {
    pageNum--;
    pagingFetch(pageNum,pageSize);
  }
});
//列表后一页操作
next.addEventListener("click", function () {
  if (pageNum >= data.totalPage) {
    alert("已到达最后一页");
  } else {
    pageNum++;
    pagingFetch(pageNum,pageSize);
  }
});

  function confirmDelete(id){
    let res=confirm("是否确认删除");
    if(res){
        fetch(
            `/api/user/del?id=${id}`,
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