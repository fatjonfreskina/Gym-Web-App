/**
 * Implements label change when a user selects a file to upload
 */
function upload_listener() {
    //handle file upload label change
    $(".custom-file-input").on("change", function() {
        var fileName = $(this).val().split("\\").pop();
        if(fileName === "")
            fileName = "Choose File"
        $(this).siblings(".custom-file-label").addClass("selected").html(fileName);
    });
}