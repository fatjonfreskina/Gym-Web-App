$(document).ready(function() {
    const form = $('#form')
    const birtDate = $('#birth_date')
    const password = $('#password')
    const confirmedPassword = $('#confirm_password')
    const telephone = $('#telephone_number')
    const buttonRegister = $('#btn-register')
    const file = $('#file')

    //error boxes
    let alertBox = $("#alert-box")
    let messageBody = $("#alert-message-body")

    //handle file upload label change
    $(".custom-file-input").on("change", function() {
        var fileName = $(this).val().split("\\").pop();
        if(fileName === "")
            fileName = "Choose File"
        $(this).siblings(".custom-file-label").addClass("selected").html(fileName);
    });

    buttonRegister.click(function (e) {

        //telephone control
        if (!isPhoneLengthValid(telephone.val())) {
            showMessage("Telephone length is not correct");
            e.preventDefault();
            return false;
        }

        if (document.location.pathname === "/wa2122-gwa/register")
        {
            // < 14 years
            if(moment().diff(moment(birtDate.val(),'YYYY-MM-DD'),'years') < 14)
            {
                showMessage("You must be at least 14 years old to sign in")
                e.preventDefault();
                return false;
            }
        }
        else
        {
            let isTrainee = document.getElementById("trainee").checked
            let isTrainer = document.getElementById("trainer").checked
            let isSecretary = document.getElementById("secretary").checked
            if (!isTrainer && !isTrainee && !isSecretary)
            {
                showMessage("Select a role for the new user")
                e.preventDefault();
                return false;
            }
            let years_threshold = 14
            if (isTrainer || isSecretary)
            {
                years_threshold = 18
            }
            if(moment().diff(moment(birtDate.val(),'YYYY-MM-DD'),'years') < years_threshold)
            {
                let msg = `The new user must be at least ${years_threshold} years old`
                showMessage(msg)
                e.preventDefault();
                return false;
            }
        }

        if (document.location.pathname === "/wa2122-gwa/register")
        {
            //password are not the same
            if(password.val() !== confirmedPassword.val())
            {
                showMessage("Password Are Different")
                e.preventDefault();
                return false;
            }
        }

        let avatar = document.getElementById("file")
        //File check
        if (avatar.files.length !== 0 ){
            if(!isFileTypeValid()){
                showMessage("File type must be .jpg, .jpeg, .png")
                e.preventDefault();
                return false;
            }
            if(!isFileSizeValid()){
                showMessage("File size must be smaller than 5MB")
                e.preventDefault();
                return false;
            }
        }

        if (!isPswLengthSafe(password.val())){
            showMessage("Password must be between 8 and 16 characters");
            e.preventDefault();
            return false;
        }

        if (!isPswCharSafe(password.val())){
            showMessage("Password must contain upper and lower case letters");
            e.preventDefault();
            return false;
        }

        if (!hasNumbers(password.val())){
            showMessage("Password must contain numbers");
            e.preventDefault();
            return false;
        }
        form.submit();
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
