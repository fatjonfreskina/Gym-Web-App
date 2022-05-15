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
        //e.preventDefault();

        if (form[0].checkValidity())
        {
            if (telephone.val().length !== 10)
            {
                showMessage("Telephone length is not correct");
                e.preventDefault();
                return false;
            }
            else
            {
                let parsed = Number(telephone.val())
                if (isNaN(parsed))
                {
                    showMessage("Provided Telephone not valid")
                    e.preventDefault();
                    return false;
                }
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
            form.submit();
        } else
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

    function isFileTypeValid() {
        const fileInput =
            document.getElementById('file');

        const filePath = fileInput.value;

        // Allowed file type
        const allowedExtensions =
            /(\.jpg|\.jpeg|\.png)$/i;

        if (!allowedExtensions.exec(filePath)) {
            return false       //Show error message
        }
        else {
            return true        //Proceed with validation
        }
    }

    function isFileSizeValid(){
        const fileInput =
            document.getElementById('file');

        // 5 MB
        if (fileInput.files[0].size/1024 > 5120){

            return false //File is too big
        }
        else {
            return true //Proceed with validation
        }
    }
    //TODO: minimal password check
    /*
    function isPswSafe(){
        var psw = document.getElementById("psw");

        //Check length
        if(psw.length < 8 || psw.length > 16) {
             document.getElementById("message").innerHTML = "**Password length must be atleast 8 characters";
             return false; }

        //Check at least 1 upper case and 1 lower case
        var lowerCaseLetters = /[a-z]/g;
        var upperCaseLetters = /[A-Z]/g;
        if (!psw.match(upperCaseLetters) || !psw.match(lowerCaseLetters)){
            return false }

        //Check 1 number
        var numbers = /[0-9]/g;
        if (!psw.match(numbers)) {
            return false }

        return true;

    }
    */
})
