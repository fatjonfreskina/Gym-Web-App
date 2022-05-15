$(document).ready(function() {
    const form = $('#form')
    const expirationdate = $('#date')
    const buttonRegister = $('#btn-send')
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
        if (form[0].checkValidity()) {
            let now = new Date();
            // check if date is in the past
            if (expirationdate.val() < now) {
                showMessage("Expiration date is not valid")
                e.preventDefault();
                return false;
            }
            let avatar = document.getElementById("file")
            //File check
            if (avatar.files.length !== 0) {
                if (!isFileTypeValid()) {
                    showMessage("File type must be a .pdf")
                    e.preventDefault();
                    return false;
                }
                if (!isFileSizeValid()) {
                    showMessage("File size must be smaller than 5MB")
                    e.preventDefault();
                    return false;
                }
            }

            form.submit()
        }else
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
        const fileInput = document.getElementById('file');
        const filePath = fileInput.value;
        // Allowed file type
        const allowedExtensions = /(\.pdf)$/i;

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
})