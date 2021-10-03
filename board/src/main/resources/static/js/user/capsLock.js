function checkCapsLock(event)  {
    if (event.getModifierState("CapsLock")) {
        document.getElementById("capsAlert").innerText
            = "Caps Lock이 켜져 있습니다."
    }else {
        document.getElementById("capsAlert").innerText
            = ""
    }
}