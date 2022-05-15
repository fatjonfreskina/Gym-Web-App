/**
 * This set of functions is used to show messages that are declared in jsp/message.jsp
 */

/**
 * Shows a success message in the appropriate alert
 * @param message message to show
 */
function showSuccessMessage(message) {

    const messageBody = $('#alert-success-message-body')
    messageBody.empty()
    messageBody.text(message)

    const alertBox = $('#alert-success');
    alertBox.show();

    alertBox.fadeTo(2000, 500).slideUp(500, function () {
        $(this).slideUp(500);
    });

}

/**
 * Shows an error / warning message in the appropriate alert
 * @param message message to show
 */
function showWarningMessage(message) {

    const messageBody = $('#alert-warning-message-body')
    messageBody.empty()
    messageBody.text(message)

    const alertBox = $('#alert-warning');
    window.scrollTo(0, 0);
    alertBox.show();

    alertBox.fadeTo(2000, 500).slideUp(500, function () {
        $(this).slideUp(500);
    });

}

/**
 * If a backend message has been displayed on the page then fade it
 */
$("#error-backend").fadeTo(2000, 500).slideUp(500, function(){
    $("#error-backend").slideUp(500);
});