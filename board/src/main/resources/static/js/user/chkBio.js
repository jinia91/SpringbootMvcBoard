function checkBio() {

    let inputBio = document.getElementById("bio");
    let reg = /^.{0,40}$/;
    let errMsg = document.getElementById("bioError");

    if (!reg.test(inputBio.value)) {
        errMsg.innerText = "자기소개는 40자 이내로 작성해주세요";
        return false;
    }
    errMsg.innerText = "";
    return true;
}