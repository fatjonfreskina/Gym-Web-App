$(document).ready(function () {
    const form = $('#form')
    const birtDate = $('#birth_date')
    const password = $('#password')
    const confirmedPassword = $('#confirm_password')
    const telephone = $('#telephone_number')
    const buttonRegister = $('#btn-register')

    //handle label change when a user selects a file to upload
    upload_listener()

    buttonRegister.click(function (e) {
        //e.preventDefault();

        //Enable HTML5 validity checks
        if (!form[0].checkValidity()) {
            form[0].reportValidity()
            return;
        }

        //telephone control
        if (!isPhoneLengthValid(telephone.val())) {
            showWarningMessage("Telephone length is not correct");
            e.preventDefault();
            return;
        }

        if (document.location.pathname === "/wa2122-gwa/register") {
            // < 14 years
            if (moment().diff(moment(birtDate.val(), 'YYYY-MM-DD'), 'years') < 14) {
                showWarningMessage("You must be at least 14 years old to sign in")
                e.preventDefault();
                return;
            }
        } else {
            //check if a role is selected
            let isTrainee = document.getElementById("trainee").checked
            let isTrainer = document.getElementById("trainer").checked
            let isSecretary = document.getElementById("secretary").checked
            if (!isTrainer && !isTrainee && !isSecretary) {
                showWarningMessage("Select a role for the new user")
                e.preventDefault();
                return;
            }
            let years_threshold = 14
            if (isTrainer || isSecretary) {
                years_threshold = 18
            }
            //check age of the new user
            if (moment().diff(moment(birtDate.val(), 'YYYY-MM-DD'), 'years') < years_threshold) {
                let msg = `The new user must be at least ${years_threshold} years old`
                showWarningMessage(msg)
                e.preventDefault();
                return;
            }
        }

        if (document.location.pathname === "/wa2122-gwa/register") {
            //password are not the same
            if (password.val() !== confirmedPassword.val()) {
                showWarningMessage("Password Are Different")
                e.preventDefault();
                return;
            }
        }

        let avatar = document.getElementById("file")
        //File check
        if (avatar.files.length !== 0) {
            if (!isFileTypeValid()) {
                showWarningMessage("File type must be .jpg, .jpeg, .png")
                e.preventDefault();
                return;
            }
            if (!isFileSizeValid()) {
                showWarningMessage("File size must be smaller than 5MB")
                e.preventDefault();
                return;
            }
        }

        form.submit();
    })

})
