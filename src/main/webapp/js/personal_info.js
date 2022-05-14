$(document).ready(function() {
    const form = $('#form')
    const button = $('#button')
    //error boxes
    let alertBox = $("#alert-box")
    alertBox.hide()
    let messageBody = $("#alert-message-body")

    //handle file upload label change
    $(".custom-file-input").on("change", function() {
        var fileName = $(this).val().split("\\").pop();
        if(fileName === "")
            fileName = "Choose File"
        $(this).siblings(".custom-file-label").addClass("selected").html(fileName);
    });

    button.click(function (e) {
        e.preventDefault();
        //TODO: CHECK WRONG?? ALSO BACKEND

        let avatar = document.getElementById("avatar")

        //File check
        if (avatar.files.length !== 0 )
        {
            if(!isFileTypeValid()){
                showMessage("File type must be .jpg, .jpeg, .png")
                return
            }
            if(!isFileSizeValid()){
                showMessage("File size must be smaller than 5MB")
                return
            }
        }
        form.submit()
    })

    /**
     *
     * @param message
     */
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
            document.getElementById('avatar');

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
            document.getElementById('avatar');

        // 5 MB
        if (fileInput.files[0].size/1024 > 5120){

            return false //File is too big
        }
        else {
            return true //Proceed with validation
        }
    }
})