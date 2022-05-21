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

        if (!form[0].checkValidity()) {
            form[0].reportValidity()
            return;
        }

        let avatar = document.getElementById("avatar")

        //File check
        if (avatar.files.length !== 0) {
            if (!isImageFileValid()) {
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

    })

})