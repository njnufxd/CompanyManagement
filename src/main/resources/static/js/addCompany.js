const companyName = document.querySelector(".companyName");
const address = document.querySelector(".address");
const tel = document.querySelector(".tel");
const webAddress = document.querySelector(".webAddress");
const province = document.querySelector(".province");
const city = document.querySelector(".city");
const area = document.querySelector(".area");
const size=document.querySelector(".size");
const starLevel=document.querySelector(".star_level");
const category=document.querySelector(".category");
const labels = document.querySelector(".labels");
const description = document.querySelector(".description");
const submit = document.querySelector(".submit");
const href = document.querySelector(".href");

const index = document.querySelector(".index");
index.style.background = "linear-gradient(to top, #336699, #3366cc, #336699)";
index.style.color = "#ffffff";

href.addEventListener("click", function () {
  href.setAttribute("href", webAddress.value);
});
submit.addEventListener("click", function () {
    let data={
        companyName:companyName.value,
        address:address.value,
        tel:tel.value,
        webAddress:webAddress.value,
        province:province.value,
        city:city.value,
        area:area.value,
        size:size.value,
        starLevel:starLevel.value,
        category:category.value,
        labels:labels.value,
        description:description.value
    }
    fetch(
        `/api/company/add`,
        {
            method:"POST",
            headers: {
                "Content-Type": "application/json;charset=utf-8"
            },
            body:JSON.stringify(data)
        }
    ).then(function(response){
        return response.json();
    }).then(function(result){
        if(result.isSuccess==true){
            alert("新建成功");
            window.location.href="/companies";
        }else{
            alert(`${result.message}!`);
        }
    })
});
let source={};
let provinceList=[];
let cityList=[];
let areaList=[];
fetch(
        `/js/source.json`,
).then(function(response){
    return response.json();
}).then(function(result){
    source=result;
    provinceList=source.中国;
    province.innerHTML=`<option value=""></option>`;
for (let index = 0; index < provinceList.length; index++) {
    const option = document.createElement("option");
    option.innerHTML=`${provinceList[index]}`;
    option.setAttribute("value",provinceList[index]);
    province.append(option);
}
})
function chooseProvince(){
    cityList=source[`${province.value}`];
    city.innerHTML=`<option value=""></option>`;
    for (let index = 0; index < cityList.length; index++) {
        const option = document.createElement("option");
        option.innerHTML=`${cityList[index]}`;
        option.setAttribute("value",cityList[index]);
        city.append(option);
    }
}
function chooseCity(){
    areaList=source[`${city.value}`];
    area.innerHTML=`<option value=""></option>`;
    for (let index = 0; index < areaList.length; index++) {
        const option = document.createElement("option");
        option.innerHTML=`${areaList[index]}`;
        option.setAttribute("value",areaList[index]);
        area.append(option);
    }
}

const companyList=document.querySelector(".companyList");
companyList.addEventListener("click",function(){
    window.location.href="/companies";
})