$(document).ready(function() {

    const password = $('#password')
    const confirmedPassword = $('#confirm_password')
    const form = $('#form')
    const button =$('#button')

    //error boxes
    let alertBox = $("#alert-box")
    let messageBody = $("#alert-message-body")

    button.click(function (e) {
        //e.preventDefault();
        if (form[0].checkValidity()) {
            if (password.val() === "" || confirmedPassword.val() === "") {
                showMessage("You must insert at least one character for the password")
                e.preventDefault();
                return false;
            }

            //password are not the same
            if (password.val() !== confirmedPassword.val()) {
                showMessage("Password Are Different")
                e.preventDefault();
                return false;
            }
            form.submit()
        }
        else
        {
            //To call html5 validation without recursive calls
            form[0].reportValidity()
        }
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
