
function checkEmail() {
    let inputEmail = document.getElementById("email");
    let errMsg = document.getElementById("emailError");
    let reg = /^([0-9a-zA-Z_\.-]+)@([0-9a-zA-Z_-]+)(\.[0-9a-zA-Z_-]+){1,2}$/;
    if (inputEmail.value == "") {
        errMsg.innerText = "이메일을 입력해주세요.";
        return false;
    } else if (!reg.test(inputEmail.value)) {
        errMsg.innerText = "이메일이 양식에 맞지 않습니다.";
        return false;
    }
    errMsg.innerText = "";
    return true;
}

