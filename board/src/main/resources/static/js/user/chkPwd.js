function checkPwd() {

    let inputPwd = document.getElementById("userPwd");
    let reg = /(?=.*\d{1,30})(?=.*[~`!@#$%\^&*()-+=]{1,30})(?=.*[a-zA-Z]{1,30}).{8,30}$/;
    let errMsg = document.getElementById("pwdError");

    if (inputPwd.value == "") {
        errMsg.innerText = "비밀번호를 입력해주세요.";
        return false;
    } else if (!reg.test(inputPwd.value)) {
        errMsg.innerText = "비밀번호는 영문/숫자/특수문자를 1회 이상 사용하여 8자이상 30자 이내로 작성해주세요";
        return false;
    }
    errMsg.innerText = "";
    return true;
}

function checkEqualPwd() {

    let pwdConfirm = document.getElementById("userPwdConfirm");
    let errMsg = document.getElementById("pwdConfError");

    if (pwdConfirm.value == "") {
        errMsg.innerText = "비밀번호를 확인해주세요";
        pwdEqValidated = false;
    } else if (document.getElementById("userPwd").value != pwdConfirm.value) {
        errMsg.setAttribute("class", "field-error");
        errMsg.innerText = "비밀번호가 일치하지 않습니다.";
        pwdEqValidated = false;
    } else {
        errMsg.setAttribute("class", "field-error-solve");
        errMsg.innerText = "비밀번호가 일치합니다.";
        pwdEqValidated = true;
    }

}
