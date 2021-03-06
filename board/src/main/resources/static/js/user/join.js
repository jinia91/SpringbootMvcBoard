let authNum = null;
let emailValidated = false;
let pwdEqValidated = false;

function checkBirth() {
    if (document.getElementById("birthDate").value == "") {
        document.getElementById("birthDateError").innerText = "생년월일을 기입해주세요";
        return false;
    }
    document.getElementById("birthDateError").innerText = "";
    return true;
}

function sendEmail() {

    const authInput = document.getElementById("auth");
    const emailBtn = document.getElementById("emailButton");
    let emailInput = document.getElementById("email");

    if (!checkEmail()) {
        alert("이메일을 다시기입해주세요");
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

    let token = getCsrfToken();

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