let contextPath =  "/wa2122-gwa"

//Construct the calendar
let calendarEl = document.getElementById('trainer__calendar');
let addWeeks = 0;
let clickCnt = 0;
let calendar = new FullCalendar.Calendar(calendarEl, {
    initialView: 'timeGridWeek',
    initialDate: new Date(),
    themeSystem: "bootstrap",
    headerToolbar: {
        left: '',
        center: 'title',
        right: 'prev,next'
    },
    allDaySlot: false,
    timeFormat: 'HH:mm',
    slotMinTime: "05:00:00",
    slotMaxTime: "23:00:00",
    slotLabelFormat: {
        hour: "2-digit",
        minute: "2-digit",
        hour12: false
    },
    firstDay: 1,
    eventTimeFormat: {
        hour: "2-digit",
        minute: "2-digit",
        hour12: false
    },
    nowIndicator: true,
    eventClick: clickHandler
});
function clickHandler (info) {


    let event = info.event;
    let selectedLectureTimeSlot = event.extendedProps.lectureTimeSlot;

    clickCnt++;
    if (clickCnt === 1) {
        oneClickTimer = setTimeout(function () {
            clickCnt = 0;
            $("#lecture-title").empty();
            $("#lecture-info").empty();
            $("#lecture-title").append("Lecture : "+selectedLectureTimeSlot.courseName+" "+selectedLectureTimeSlot.date);
            $("#lecture-info").append("Start Time : "+ selectedLectureTimeSlot.startTime+ "<br/>");
            $("#lecture-info").append("Room : "+ selectedLectureTimeSlot.roomName);
            $("#modal-info-course").modal("show");
        }, 400);
    } else if (clickCnt === 2) {
        clearTimeout(oneClickTimer);
        clickCnt = 0;
        let now = Date.now()
        if (event.start < now && event.end > now) {
            location.href = location.href + "/attendance";
        } else {
            $("#lecture-title").empty();
            $("#lecture-info").empty();
            $("#lecture-title").append("Wrong Time!");
            $("#lecture-info").append("No Lectures right now! <br> Please, try again when there is a lecture");
            $("#modal-info-course").modal("show");
        }
    }
}



/**
 * Performs a refresh of the calendar object of this page
 */
function renderCalendar() {
    //Perform the AJAX request to fill the calendar
    $.ajax({
        url: contextPath + "/rest/trainer/weekly_calendar", data: {
            "addWeeks": addWeeks,
        }, cache: false, type: "GET", dataType: 'json', success: function (response) {
            //Remove all the events
            calendar.removeAllEvents();
            //Iterate over all the elements in the response
            for (const lts of response) {
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