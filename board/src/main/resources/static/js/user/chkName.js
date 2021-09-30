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