$("#form").submit(function(e){
    e.preventDefault()
    const formValues= $(this).serialize();
    $.ajax({
        url: "rest/addsubscription",
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

$("#partial_email").on('input',function (){
    $.ajax({
        url: "rest/listlikepersons",
        data: {
            "partial_email" : $("#partial_email").val()
        },
        cache: false,
        type: "GET",
        dataType: 'json',
        success: function(response) {
            $("#list_emails").empty()

            console.log(response)

            for(const person of response)
            {
                $("#list_emails").append("<li class='list-group-item'>"+"<input type='radio' name = 'trainee' value = '"+person.email+"'"+"/>"+" "+person.email+"</li>")
            }
        },
        error: function(xhr)
        {
            console.log(xhr);
        }
    });
})

$("#course_name").change(function (){
    $.ajax({
        url: "rest/timeschedules",
        data: {
            "course_name" : $("#course_name").val()
        },
        cache: false,
        type: "GET",
        dataType: 'json',
        success: function(response) {
            $("#list_last_date").empty()
            for(const lastDate of response)
            {
                $("#list_last_date").append("<li class='list-group-item'>"+"<input type='radio' name='course_edition_id' value='"+lastDate.courseEditionId+"'/>"+" "+lastDate.date+"</li>")
            }
        },
        error: function(xhr)
        {
            console.log(xhr);
        }
    });
})

$("#course_name").trigger('change')