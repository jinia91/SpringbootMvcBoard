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
