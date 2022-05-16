$(document).ready(function () {
    const form = $('#form')
    const expirationdate = $('#date')
    const buttonRegister = $('#btn-send')

    upload_listener()

    buttonRegister.click(function (e) {

        //Enable HTML5 validity checks
        if (!form[0].checkValidity()) {
            form[0].reportValidity()
            return false;
        }

        let now = new Date();
        // check if date is in the past
        if (expirationdate.val() < now) {
            showWarningMessage("Expiration date is not valid")
            e.preventDefault();
            return false;
        }
        let cert = document.getElementById("file")
        //File check
        if (cert.files.length !== 0) {
            if (!isDocumentFileValid()) {
                showWarningMessage("File type must be a .pdf")
                e.preventDefault();
                return false;
            }
            if (!isFileSizeValid()) {
                showWarningMessage("File size must be smaller than 5MB")
                e.preventDefault();
                return false;
            }
        }
        form.submit()
    })
})