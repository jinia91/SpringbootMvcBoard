let authNum = null;
let emailValidated = false;

function sendEmailWithId() {

    const authInput = document.getElementById("auth");
    const idInput = document.getElementById("userId");
    const nameInput = document.getElementById("userName");
    const emailBtn = document.getElementById("emailButton");
    let emailInput = document.getElementById("email");

    if (!checkEmail()) {
        alert("이메일을 다시기입해주세요");
        return;
    }

    document.getElementById("emailError").innerText = "";
    alert('이메일로 인증요청을 전송했습니다. 인증번호가 오지 않으면 입력하신 정보가 회원정보와 일치하는지 확인해주세요.');

    authInput.disabled = !authInput.disabled;
    emailInput.readOnly = true;
    emailBtn.innerText = "인증번호 확인";
    emailBtn.setAttribute("onclick", "checkEmailAuth()");

    const emailWithNameAndId = {
        email: emailInput.value,
        userName: nameInput.value,
        userId: idInput.value
    }

    let token = getCsrfToken();

    const xhr = new XMLHttpRequest();
    xhr.open("POST", "/user/help/sendEmailWithName");
    xhr.setRequestHeader("content-type", "application/json");
    xhr.setRequestHeader("X-XSRF-TOKEN", token);
    xhr.send(JSON.stringify(emailWithNameAndId));

    xhr.onload = () => {

        if (xhr.status === 200||xhr.status === 201||xhr.status === 202) {
            authNum = JSON.parse(xhr.response);
            console.log(authNum);
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

function idInquiryValidated() {
    const idInquiryForm = document.getElementById("idInquiryForm");

    let submitFlag = true;

    if (!checkName()) {
        submitFlag = false;
    }
    if (emailValidated == false) {
        document.getElementById("emailValidated").innerText = "이메일인증을 해주세요";
        submitFlag = false;
    }
    if (submitFlag) {
        idInquiryForm.submit();
    }
}