$(document).ready(function () {
    const form = $('#form')
    const button = $('#button')

    //handle file upload label change
    $(".custom-file-input").on("change", function () {
        var fileName = $(this).val().split("\\").pop();
        if (fileName === "")
            fileName = "Choose File"
        $(this).siblings(".custom-file-label").addClass("selected").html(fileName);
    });

    button.click(function (e) {
        //e.preventDefault();
        //TODO: CHECK WRONG?? ALSO BACKEND
        if (form[0].checkValidity()) {
            let avatar = document.getElementById("avatar")

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
            } else {
                showWarningMessage("You must upload a file")
                e.preventDefault();
                return;
            }
            form.submit()
        } else {
            //To call html5 validation without recursive calls
            form[0].reportValidity()
        }
    })

    function isFileTypeValid() {
        const fileInput =
            document.getElementById('avatar');

        const filePath = fileInput.value;

        // Allowed file type
        const allowedExtensions =
            /(\.jpg|\.jpeg|\.png)$/i;

        return allowedExtensions.exec(filePath);
    }

    function isFileSizeValid() {
        const fileInput =
            document.getElementById('avatar');

        // 5 MB
        return fileInput.files[0].size / 1024 <= 5120;
    }
})