let pwdEqValidated = false;

function profileEditValidate() {
    const pwdEditForm = document.getElementById("pwdEditForm");

    let submitFlag = true;

    if (!checkPwd()) {
        submitFlag = false;
    }
    if (pwdEqValidated == false) {
        submitFlag = false;
    }

    if (submitFlag) {
        pwdEditForm.submit();
    }
}