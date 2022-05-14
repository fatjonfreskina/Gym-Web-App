$(document).ready(function() {

    const password = $('#password')
    const confirmedPassword = $('#confirm_password')
    const form = $('#form')
    const button =$('#button')

    //error boxes
    let alertBox = $("#alert-box")
    let messageBody = $("#alert-message-body")

    button.click(function (e) {
        e.preventDefault();

        //password are not the same
        if(password.val() !== confirmedPassword.val())
        {
            showMessage("Password Are Different")
            e.preventDefault();
            return false;
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
