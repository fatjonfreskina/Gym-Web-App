$(document).ready(function () {
    const form = $('#form')
    const buttonUpdate = $('#btn-update')

    buttonUpdate.click(function (e) {
        //e.preventDefault();

        //Enable HTML5 validity checks
        if (!form[0].checkValidity()) {
            form[0].reportValidity()
            return false;
        }

        let isTrainee = document.getElementById("trainee").checked
        let isTrainer = document.getElementById("trainer").checked
        let isSecretary = document.getElementById("secretary").checked
        if (!isTrainer && !isTrainee && !isSecretary) {
            showWarningMessage("Please choose a role for the selected user")
            e.preventDefault();
            return false;
        }
        form.submit()

    })

})