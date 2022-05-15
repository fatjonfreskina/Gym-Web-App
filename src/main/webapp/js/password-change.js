$(document).ready(function() {

    const password = $('#password')
    const confirmedPassword = $('#confirm_password')
    const form = $('#form')
    const button =$('#button')


    button.click(function (e) {
        //perform password validation
        if(password.val() !== confirmedPassword.val())
        {
            showWarningMessage("Password Are Different")
            e.preventDefault();
            return false;
        }

        if (!isPswLengthSafe(password.val())){
            showWarningMessage("Password must be between 8 and 16 characters");
            e.preventDefault();
            return false;
        }

        if (!isPswCharSafe(password.val())){
            showWarningMessage("Password must contain upper and lower case letters");
            e.preventDefault();
            return false;
        }

        if (!hasNumbers(password.val())){
            showWarningMessage("Password must contain numbers");
            e.preventDefault();
            return false;
        }
        form.submit()
    })

})
