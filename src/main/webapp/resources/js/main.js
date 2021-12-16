$(document).ready(function () {
    const xHidden = document.getElementById('input_form:x_hidden');
    let index
    switch (xHidden.value) {
        case -2: {index = 0; break }
        case -1.5: index = 1; break
        case -1: index = 2; break
        case -0.5: index = 3; break
        case 0: index = 4; break
        case 0.5: index = 5; break
        case 1: index = 6; break
        case 1.5: index = 7; break
        case 2: index = 8; break
        default: index = null
    }
    let buttons = document.querySelectorAll(".x-group");
    if (index != null && buttons != null) {
        buttons[index].style.color = 'red';
    }

    buttons.forEach(click);

    function click(element) {
        element.onclick = function () {
            buttons.forEach(function (element) {
                element.style.color = "black";
            });
            this.style.color = "red";
            xHidden.value = this.textContent;
        }
    }
});


function validateValues() {
    document.getElementById("messageArea").innerHTML = "";
    let message = "";
    let check = true;
    if (!validateX()) {
        check = false;
        message += "Выберете Х\n";
    }
    if (!validateY()) {
        check = false;
        message += "Введите Y в диапазоне (-5; 5)\n";
    }
    if (!validateR()) {
        check = false;
        message += "Выберете R\n";
    }
    if (!check) document.getElementById("messageArea").innerHTML = message;
    return true;
}

function isNumeric(n) {
    return !isNaN(parseFloat(n)) && isFinite(n);
}

function validateX() {
    return document.getElementById('input_form:x_hidden').value !== "";
}

function validateY() {
    const Y_MIN = -5.0;
    const Y_MAX = 5.0;
    let yVal = document.getElementById("input_form:y_field").value;
    return isNumeric(yVal) && yVal > Y_MIN && yVal < Y_MAX;
}

function validateR() {
    let check = false;
    if (document.getElementById("input_form:select1_input").ariaChecked === "true") {
        check = true;
    }
    if (document.getElementById("input_form:select2_input").ariaChecked === "true") {
        check = true;
    }
    if (document.getElementById("input_form:select3_input").ariaChecked === "true") {
        check = true;
    }
    if (document.getElementById("input_form:select4_input").ariaChecked === "true") {
        check = true;
    }
    if (document.getElementById("input_form:select5_input").ariaChecked === "true") {
        check = true;
    }
    return check;
}

