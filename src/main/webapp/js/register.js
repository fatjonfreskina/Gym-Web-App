$(document).ready(function() {
    const form = $('#form')
    const birtDate = $('#birth_date')
    const password = $('#password')
    const confirmedPassword = $('#confirm_password')
    const telephone = $('#telephone_number')
    const buttonRegister = $('#btn-register')

    //handle label change when a user selects a file to upload
    upload_listener()

    buttonRegister.click(function (e) {

        //telephone control
        if (!isPhoneLengthValid(telephone.val())) {
            showWarningMessage("Telephone length is not correct");
            e.preventDefault();
            return false;
        }

        if (document.location.pathname === "/wa2122-gwa/register")
        {
            // < 14 years
            if(moment().diff(moment(birtDate.val(),'YYYY-MM-DD'),'years') < 14)
            {
                showWarningMessage("You must be at least 14 years old to sign in")
                e.preventDefault();
                return false;
            }
        }
        else
        {
            //check if a role is selected
            let isTrainee = document.getElementById("trainee").checked
            let isTrainer = document.getElementById("trainer").checked
            let isSecretary = document.getElementById("secretary").checked
            if (!isTrainer && !isTrainee && !isSecretary)
            {
                showWarningMessage("Select a role for the new user")
                e.preventDefault();
                return false;
            }
            let years_threshold = 14
            if (isTrainer || isSecretary)
            {
                years_threshold = 18
            }
            //check age of the new user
            if(moment().diff(moment(birtDate.val(),'YYYY-MM-DD'),'years') < years_threshold)
            {
                let msg = `The new user must be at least ${years_threshold} years old`
                showWarningMessage(msg)
                e.preventDefault();
                return false;
            }
        }

        if (document.location.pathname === "/wa2122-gwa/register")
        {
            //password are not the same
            if(!isConfirmedPswMatching(password.val(), confirmedPassword.val()))
            {
                showWarningMessage("Password Are Different")
                e.preventDefault();
                return false;
            }
        }

        let avatar = document.getElementById("file")
        //File check
        if (avatar.files.length !== 0 ){
            if(!isImageFileValid()){
                showWarningMessage("File type must be .jpg, .jpeg, .png")
                e.preventDefault();
                return false;
            }
            if(!isFileSizeValid()){
                showWarningMessage("File size must be smaller than 5MB")
                e.preventDefault();
                return false;
            }
        }
        //check password requirements
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
        form.submit();
    })
    
})
