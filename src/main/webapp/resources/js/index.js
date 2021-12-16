let date = document.getElementById("dateItem");
let time = document.getElementById("timeItem");

date.innerHTML = "";
date.innerHTML = new Date().toLocaleDateString();
time.innerHTML = "";
time.innerHTML = new Date().toLocaleTimeString();

setInterval(() => {
    let day = new Date();

    date.innerHTML = day.toLocaleDateString();
    time.innerHTML = day.toLocaleTimeString();
}, 12000)