
let authNum = " ";
let emailValidated = false;
let pwdEqValidated = false;

function checkId() {
    let inputId = document.getElementById("userId");
    let reg = /^(?=.*\d{1,15})(?=.*[a-zA-Z]{1,15}).{4,15}$/;
    let errMsg = document.getElementById("idError");

    if (inputId.value == "") {
        errMsg.innerText = "아이디를 작성해주세요.";
        return false;
    } else if (!reg.test(inputId.value)) {
        errMsg.innerText = "아이디는 영문/숫자 조합으로 4자이상 15자 이하로 작성해주세요.";
        return false;
    }
    errMsg.innerText = "";
    return true;
}

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

function checkName() {

    let inputName = document.getElementById("userName");
    let reg = /^[가-힣|a-z|A-Z].{1,10}$/;
    let errMsg = document.getElementById("nameError");

    if (inputName.value == "") {
        errMsg.innerText = "이름을 입력해주세요.";
        return false;
    } else if (!reg.test(inputName.value)) {
        errMsg.innerText = "정상적인 이름을 10자 이내로 작성해주세요";
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

function checkBirth() {
    if (document.getElementById("birthDate").value == "") {
        document.getElementById("birthDateError").innerText = "생년월일을 기입해주세요";
        return false;
    }
    document.getElementById("birthDateError").innerText = "";
    return true;
}

function joinValidate() {
    const joinForm = document.getElementById("joinForm");

    let submitFlag = true;

    if (!checkId()) {
        submitFlag = false;
    }
    if (!checkPwd()) {
        submitFlag = false;
    }
    if (!checkName()) {
        submitFlag = false;
    }
    if (!checkBirth()) {
        submitFlag = false;
    }

    if (emailValidated == false) {
        document.getElementById("emailValidated").innerText = "이메일인증을 해주세요";
        submitFlag = false;
    }
    if (pwdEqValidated == false) {
        submitFlag = false;
    }
    if (submitFlag) {
        joinForm.submit();
    }
}




function getCookie(str) {
    const value = document.cookie.match('(^|;) ?' + str + '=([^;]*)(;|$)');
    return value ? value[2] : null;
}

function sendEmail() {

    const authInput = document.getElementById("auth");
    const emailBtn = document.getElementById("emailButton");
    let emailInput = document.getElementById("email");

    if (!checkEmail()) {
        return;
    }

    document.getElementById("emailError").innerText = "";
    alert('이메일로 인증요청을 전송했습니다.');

    authInput.disabled = !authInput.disabled;
    emailInput.readOnly = true;
    emailBtn.innerText = "인증번호 확인";
    emailBtn.setAttribute("onclick", "checkEmailAuth()");

    const jEmail = {
        email: emailInput.value
    }

    let token = getCookie("XSRF-TOKEN");

    const xhr = new XMLHttpRequest();
    xhr.open("POST", "/join/sendEmail");
    xhr.setRequestHeader("content-type", "application/json");
    xhr.setRequestHeader("X-XSRF-TOKEN", token);
    xhr.send(JSON.stringify(jEmail));

    xhr.onload = () => {

        if (xhr.status === 200) {
            authNum = JSON.parse(xhr.response);
            console.log(authNum)

        } else {
            alert('서버오류로 이메일 전송이 실패했습니다.');
        }
    }

}

function checkEmailAuth() {
    const authInput = document.getElementById("auth");
    const emailBtn = document.getElementById("emailButton");
    const inputAuthNum = authInput.value;
    console.log(inputAuthNum);
    let emailValMsg = document.getElementById("emailValidated");
    if (inputAuthNum == authNum) {
        emailValidated = true;
        authInput.disabled = !authInput.disabled;

        emailValMsg.setAttribute("class", "field-error-solve");
        emailValMsg.innerText = "이메일인증을 성공했습니다";

        emailBtn.innerText = "인증 완료";
        emailBtn.setAttribute("onclick", "");


    } else {
        emailValMsg.innerText = "인증번호가 틀립니다. 다시시도해주세요";
    }
}
