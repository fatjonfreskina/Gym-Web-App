$(document).ready(function() {
    const form = $('#form')
    const buttonUpdate = $('#btn-update')

    buttonUpdate.click(function (e) {
        //e.preventDefault();
        if (form[0].checkValidity()) {
            let isTrainee = document.getElementById("trainee").checked
            let isTrainer = document.getElementById("trainer").checked
            let isSecretary = document.getElementById("secretary").checked
            if (!isTrainer && !isTrainee && !isSecretary) {
                showWarningMessage("Please choose a role for the selected user")
                e.preventDefault();
                return false;
            }
            form.submit()
        } else {
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

})