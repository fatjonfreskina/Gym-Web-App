$(document).ready(function() {
    const form = $('#form')
    //const birtDate = $('#birth_date')
    const password = $('#password')
    const confirmedPassword = $('#confirm_password')
    const telephone = $('#telephone_number')

    //error boxes
    let alertBox = $("#alert-box")
    alertBox.hide()
    let messageBody = $("#alert-message-body")

    form.one(function (e) {
        e.preventDefault();

        //telephone control
        if (telephone.val().length !== 10)
        {
            showMessage("Telephone length is not correct")
            return;
        }
        else
        {
            let parsed = Number(telephone.val())
            if (isNaN(parsed))
            {
                showMessage("Provided Telephone not valid")
                return
            }
        }

        //age control
        /*if(moment(birtDate.val()).fromNow() < 14)
        {
            showMessage("Provided Telephone not valid")
            return
        }*/

        //password are not the same
        if(password.val() !== confirmedPassword.val())
        {
            showMessage("Password Are Different")
            return
        }
        $(this).submit()
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