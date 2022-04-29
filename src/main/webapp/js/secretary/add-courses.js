$("#form").submit(function(e){
    e.preventDefault()
    const formValues= $(this).serialize();
    $.ajax({
        url: "addcourses",
        cache: false,
        type: "POST",
        data : formValues,
        success: function (response) {
            $("#show-message").empty()
            $("#show-message").append('<div class="alert alert-warning alert-dismissible fade show" role="alert">'
                +response.message+
                '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button></div>')
        }
    });
});