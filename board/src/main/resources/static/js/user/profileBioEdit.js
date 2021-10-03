function profileEditValidate() {
    const joinForm = document.getElementById("profileEditForm");

    let submitFlag = true;

    if (!checkBio()) {
        submitFlag = false;
    }
    if (!checkName()) {
        submitFlag = false;
    }
    if (submitFlag) {
        joinForm.submit();
    }
}