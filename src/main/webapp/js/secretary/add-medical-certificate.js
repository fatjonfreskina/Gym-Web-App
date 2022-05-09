$(document).ready(function() {
    const form = $('#form')
    const expirationdate = $('#date')
    const buttonRegister = $('#btn-send')
    const file = $('#file')

    //error boxes
    let alertBox = $("#alert-box")
    alertBox.hide()
    let messageBody = $("#alert-message-body")

    buttonRegister.click(function (e) {
        e.preventDefault();
        let now = new Date();
        // check if date is in the past
        if(expirationdate.val() < now)
        {
            showMessage("Provided Telephone not valid")
            return
        }
        //File check
        if (file !== undefined){
            if(!isFileTypeValid()){
                showMessage("File type must be a .pdf")
                return
            }
            if(!isFileSizeValid()){
                showMessage("File size must be smaller than 5MB")
                return
            }
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

    function isFileTypeValid() {
        const fileInput =
            document.getElementById('file');

        const filePath = fileInput.value;

        // Allowed file type
        const allowedExtensions = "pdf"

        if (allowedExtensions != filePath) {
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