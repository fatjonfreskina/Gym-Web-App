$(document).ready(function() {

    const password = $('#password')
    const confirmedPassword = $('#confirm_password')
    const buttonRegister = $('#btn-register')


    //error boxes
    let alertBox = $("#alert-box")
    alertBox.hide()
    let messageBody = $("#alert-message-body")

    form.addEventListener("submit",function (e) {
        e.preventDefault();

            //password are not the same
            if(password.val() !== confirmedPassword.val())
            {
                showMessage("Password Are Different")
                return
            }
        form.submit()
    })

    function showMessage(message) {
        messageBody.empty()
        messageBody.text(message)
        alertBox.show()
        alertBox.fadeTo(2000, 500).slideUp(500, function () {
            $(this).slideUp(500);
        });
    }


})
