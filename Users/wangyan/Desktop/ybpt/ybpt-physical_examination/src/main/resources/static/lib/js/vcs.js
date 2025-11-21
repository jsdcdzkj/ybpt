let scripts = document.getElementsByTagName("script");
for(let i = 0;i < scripts.length;i ++){
    if(scripts[i].src){
        scripts[i].src = scripts[i].src+"?t="+new Date().getTime();
    }
}