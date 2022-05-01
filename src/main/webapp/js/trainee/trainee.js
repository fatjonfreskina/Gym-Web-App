let contextPath = $('#contextPathHolder').attr('data'); // "/wa2122-gwa"

//Construct the calendar
let calendarEl = document.getElementById('trainee__calendar');
let addWeeks = 0;
let clickCnt = 0;
let calendar = new FullCalendar.Calendar(calendarEl, {
    initialView: 'timeGridWeek',
    initialDate: new Date(),
    themeSystem: "bootstrap",
    headerToolbar: {
        left: 'prev', center: 'title', right: 'next'
    },
    allDaySlot: false,
    slotLabelFormat: {
        hour: 'numeric', minute: '2-digit', hour12: false
    },
    eventTimeFormat: {
        hour: '2-digit', minute: '2-digit', hour12: false
    },
    slotMinTime: "05:00:00",
    slotMaxTime: "23:00:00",
    businessHours: false,
    firstDay: 1,
    slotDuration: '00:30',
    nowIndicator: true,
});

/**
 * Performs a refresh of the calendar object of this page
 */
function renderCalendar() {
    //Perform the AJAX request to fill the calendar
    let start = moment(calendar.view.activeStart).format('YYYY-MM-DD');
    let end = moment(calendar.view.activeEnd).format('YYYY-MM-DD');
    $.ajax({
        url: "trainee/rest/reservation/from-date/"+start+"/to-date/"+end,
        async: false,
        cache: false,
        type: "GET",
        dataType: 'json',
        success: function (response) {
            reservations = response
        }
    });
    //Perform the AJAX request to fill the calendar
    $.ajax({
        url: "trainee/rest/available/from-date/"+start+"/to-date/"+end,
        cache: false,
        type: "GET",
        dataType: 'json',
        success: function (response) {
            //Remove all the events
            calendar.removeAllEvents();
            //Iterate over all the elements in the response
            console.log(response)
            for (const lts of response) {
                console.log(lts)
                //Create an event object
                let event = {};
                //Calculate dates
                startDate = new Date(Date.parse(lts.date + ' ' + lts.startTime + ' GMT+2'))
                event.start = startDate;
                event.end = moment(startDate).add(2, 'hours').toDate();
                event.title = lts.courseName;
                event.backgroundColor = GetColorOfCourse(lts.courseName);

                //Add the custom object to the calendar object
                event.lectureTimeSlot = lts;

                //Add the element to the calendar
                calendar.addEvent(event);
                console.log(event)
            }
            //Render the new calendar
            calendar.render();
        }, error: function (xhr) {
            console.log(xhr);
        }
    });
}

function nextWeek() {
    addWeeks = addWeeks + 1;
    renderCalendar();
}

function prevWeek() {
    addWeeks = addWeeks - 1;
    renderCalendar();
}

function goToToday() {
    addWeeks = 0;
    renderCalendar();
}

/**
 * Returns the color associated to the course name, black for non associated courses
 * @param courseName name of the course (string)
 * @returns {string} color associated
 */
function GetColorOfCourse(courseName) {
    switch (courseName) {
        case "Cardio":
            return "DarkCyan";
        case "Yoga":
            return "YellowGreen";
        case "Crossfit":
            return "DarkRed";
        case "Calisthenics":
            return "LightSalmon";
        case "Bodybuilding":
            return "Indigo";
        case "Powerlifting":
            return "DarkSlateBlue";
        default:
            return "Black";
    }
}


//Initial render when page loaded
renderCalendar();

//Attach render calendar to button for week change
let $body = $('body');
$body.on('click', 'button.fc-next-button', nextWeek);
$body.on('click', 'button.fc-prev-button', prevWeek);
//$body.on('click', 'button.fc-today-button', goToToday());