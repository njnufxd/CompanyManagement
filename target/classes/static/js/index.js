var quit=document.querySelector(".quit");
quit.addEventListener("click",function(){
    fetch(
        '/api/user/logout',
    ).then(function(response){
        return response.json;
    }).then(function(result){
        window.location.href='/index'
    })
})