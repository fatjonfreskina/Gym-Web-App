$(document).ready(function() {
    const form = $('#form')
    const buttonUpdate = $('#btn-update')

    //error boxes
    let alertBox = $("#alert-box")
    alertBox.hide()
    let messageBody = $("#alert-message-body")

    form.addEventListener("submit", function (e) {
        e.preventDefault();
        let isTrainee = document.getElementById("trainee").checked
        let isTrainer = document.getElementById("trainer").checked
        let isSecretary = document.getElementById("secretary").checked
        if (!isTrainer && !isTrainee && !isSecretary)
        {
            showMessage("Please choose a role for the selected user")
            return;
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

})