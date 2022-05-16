$(document).ready(function () {

    const password = $('#password')
    const confirmedPassword = $('#confirm_password')
    const form = $('#form')
    const button = $('#button')

    button.click(function (e) {

        //Enable HTML5 validity checks
        if (!form[0].checkValidity()) {
            form[0].reportValidity()
            return false;
        }

        if (password.val() === "" || confirmedPassword.val() === "") {
            showWarningMessage("You must insert at least one character for the password")
            e.preventDefault();
            return false;
        }

        //password are not the same
        if (password.val() !== confirmedPassword.val()) {
            showWarningMessage("Password Are Different")
            e.preventDefault();
            return false;
        }
        form.submit()

    })

})
