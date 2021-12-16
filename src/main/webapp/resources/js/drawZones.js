const canvas = document.getElementById('canvas');
let r = getR();

function getR() {
    let r = null;
    if (document.getElementById("input_form:select1_input").ariaChecked === "true") r = 1;
    if (document.getElementById("input_form:select2_input").ariaChecked === "true") r = 2;
    if (document.getElementById("input_form:select3_input").ariaChecked === "true") r = 3;
    if (document.getElementById("input_form:select4_input").ariaChecked === "true") r = 4;
    if (document.getElementById("input_form:select5_input").ariaChecked === "true") r = 5;
    return r;
}

function drawCanvas() {
    let labels
    r = getR()
    if (r === null ) {
        labels = ["R", "R/2", "", "-R/2", "-R"]
    } else {
        labels = [r.toString(), (r / 2).toString(), "", (-r / 2).toString(), (-r).toString()]
    }

    const ctx = canvas.getContext("2d")
    const canvasWidth = canvas.clientWidth
    const canvasHeight = canvas.clientHeight
    const xAxis = canvasWidth / 2
    const yAxis = canvasHeight / 2
    const xNameAxis = canvasWidth / 6
    const yNameAxis = canvasHeight / 6

    const offsetAxis = 5

    ctx.beginPath()
    ctx.fillStyle = "#000"
    ctx.strokeStyle = "#000"
    ctx.moveTo(xAxis, 0)
    ctx.lineTo(xAxis, canvasHeight)
    ctx.moveTo(0, yAxis);
    ctx.lineTo(canvasWidth, yAxis)
    ctx.stroke();
    ctx.closePath();

    ctx.font = "15px Arial"
    ctx.fillText("y", xAxis + offsetAxis, offsetAxis * 2)
    ctx.moveTo(xAxis - offsetAxis / 2, offsetAxis)
    ctx.lineTo(xAxis, 0);
    ctx.moveTo(xAxis + offsetAxis / 2, offsetAxis);
    ctx.lineTo(xAxis, 0);
    ctx.stroke();
    for (let i = 0; i < labels.length; i++) {
        ctx.moveTo(xAxis - offsetAxis / 2, yNameAxis + yNameAxis * i)
        ctx.lineTo(xAxis + offsetAxis / 2, yNameAxis + yNameAxis * i)
        ctx.stroke()
        ctx.fillText(labels[i], xAxis + offsetAxis, yNameAxis + yNameAxis * i)
    }

    ctx.fillText("x", canvasWidth - offsetAxis * 2, yAxis + 20)
    ctx.moveTo(canvasWidth - offsetAxis, yAxis - offsetAxis / 2);
    ctx.lineTo(canvasWidth, yAxis);
    ctx.moveTo(canvasWidth - offsetAxis, yAxis + offsetAxis / 2);
    ctx.lineTo(canvasWidth, yAxis);
    ctx.stroke();
    for (let i = 0; i < labels.length; i++) {
        ctx.moveTo(xNameAxis + xNameAxis * i, yAxis - offsetAxis / 2);
        ctx.lineTo(xNameAxis + xNameAxis * i, yAxis + offsetAxis / 2);
        ctx.stroke();
        ctx.fillText(labels[labels.length - i - 1], xNameAxis + xNameAxis * i - offsetAxis, yAxis + 20);
    }

    ctx.fillStyle = "#6600ff"
    ctx.globalAlpha = 0.4
    ctx.fillRect(xAxis, yAxis, 2 * xNameAxis, -yNameAxis)

    ctx.beginPath();
    ctx.moveTo(xAxis, yAxis);
    ctx.lineTo(xAxis, yAxis + 2 * yNameAxis);
    ctx.lineTo(xAxis + 2 * xNameAxis, yAxis);
    ctx.fill();
    ctx.closePath();

    ctx.beginPath();
    ctx.moveTo(xAxis, yAxis);
    ctx.arc(xAxis, yAxis, xAxis - xNameAxis, Math.PI , Math.PI * 1.5 );
    ctx.fill();
    ctx.closePath();
}

function reDrawPoints() {
    const ctx = canvas.getContext('2d');
    canvas.getContext("2d").clearRect(0, 0, canvas.clientWidth, canvas.clientHeight);
    ctx.globalAlpha = 1;
    let hits = document.getElementsByClassName("hitres");
    let xs = document.getElementsByClassName("xVal");
    let ys = document.getElementsByClassName("yVal");
    let rs = document.getElementsByClassName("rVal");
    r = getR();
    drawCanvas();
    for (let i = 0; i < hits.length; i++) {
        if (rs[i].innerHTML !== r.toString() ) continue;
        if (hits[i].innerHTML === "Попал") {
            drawPoint(xs[i].innerHTML, ys[i].innerHTML, r, "#22ff00");
        } else {
            drawPoint(xs[i].innerHTML, ys[i].innerHTML, r, "#ff0000");
        }
    }
}


canvas.addEventListener('click', (event) => {
    document.getElementById("messageArea").innerHTML = "";
    let rValue = getR();
    if (!validateRadius(rValue)) {
        document.getElementById("messageArea").innerHTML = "Радиус не задан\n";
        return;
    }
    if (countRadius() > 1) {
        document.getElementById("messageArea").innerHTML = "Выберете один радиус\n";
        return;
    }
    let xFormCanvas = (event.offsetX - 125) / 82 * rValue
    let yFromCanvas = (-event.offsetY + 125) / 82 * rValue;

    $(".pointX").val(Math.floor(xFormCanvas * 100) / 100);
    $(".pointY").val(Math.floor(yFromCanvas * 100) / 100);
    $(".pointR").val(rValue);
    $(".submitCanvas").click();
})

function validateRadius(value) {
    return isNumeric(value) && value >= 1 && value <= 5
}

function countRadius() {
    let r = 0;
    if (document.getElementById("input_form:select1_input").ariaChecked === "true") r ++;
    if (document.getElementById("input_form:select2_input").ariaChecked === "true") r ++;
    if (document.getElementById("input_form:select3_input").ariaChecked === "true") r ++;
    if (document.getElementById("input_form:select4_input").ariaChecked === "true") r ++;
    if (document.getElementById("input_form:select5_input").ariaChecked === "true") r ++;
    return r;
}

function drawPoint(xPosition, yPosition, radius, color) {
    let xPos = xPosition.toString().replace(',', '.')
    let yPos = yPosition.toString().replace(',', '.')

    yPosition = 125 - 82 * yPos / radius
    xPosition = 125 + 82 * xPos / radius
    const ctx = canvas.getContext("2d")
    ctx.beginPath()
    ctx.moveTo(xPosition, yPosition)
    ctx.fillStyle = color
    ctx.globalAlpha = 1
    ctx.arc(xPosition, yPosition, 2, 0, 2 * Math.PI)
    ctx.fill()
    ctx.closePath()
}

function check() {
    r = getR();
    drawCanvas();
    reDrawPoints();
}

function clearPointsFromCanvas() {
    const ctx = canvas.getContext('2d');
    const canvasGraphWidth = canvas.clientWidth;
    const canvasGraphHeight = canvas.clientHeight;
    ctx.clearRect(0, 0, canvasGraphWidth, canvasGraphHeight);
    ctx.globalAlpha = 1;
    drawCanvas();
}

drawCanvas();
reDrawPoints();
