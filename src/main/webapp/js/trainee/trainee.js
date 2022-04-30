//Construct the calendar
let calendarEl = document.getElementById('trainee__calendar');
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
    slotMinTime: "05:00:00",
    slotMaxTime: "23:00:00",
    businessHours: false,
    firstDay: 1,
    nowIndicator: true,
    eventClick: clickHandler
});

function clickHandler(info) {
    let event = info.event;
    let selectedLectureTimeSlot = event.extendedProps.customLTS;
    console.log(selectedLectureTimeSlot)
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

/**
 * Performs a refresh of the calendar object of this page
 */
function renderCalendar() {
    //Get the current active window of the calendar
    let start = moment(calendar.view.activeStart).format('YYYY-MM-DD');
    let end = moment(calendar.view.activeEnd).format('YYYY-MM-DD');
    let reservations = null
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
            for (const lts of response) {
                //Create an event object
                let event = {};
                //Calculate dates
                startDate = new Date(Date.parse(lts.date + ' ' + lts.startTime + ' GMT+2'));

                event.start = startDate;
                event.end = moment(startDate).add(2, 'hours').toDate();

                let present = false;
                for(let i = 0; i<reservations.length; i++){
                    if(
                        reservations[i].room.localeCompare(lts.roomName)==0 &&
                        reservations[i].lectureDate.localeCompare(lts.date)==0 &
                        reservations[i].lectureStartTime.localeCompare(lts.startTime)==0){
                        reservations.splice(i,1)
                        present = true;
                        break;
                    }
                }

                //Set title and background color based on the title
                event.title = lts.courseName;
                if(present)
                    event.backgroundColor = GetColorOfCourse("Reserved");
                else
                    event.backgroundColor = GetColorOfCourse(lts.courseName);
                let lectureTimeSlot = {};
                lectureTimeSlot.courseEditionId = lts.courseEditionId;
                lectureTimeSlot.courseName = lts.courseName
                lectureTimeSlot.roomName = lts.roomName;
                lectureTimeSlot.customdate = lts.date;
                lectureTimeSlot.customstartTime = lts.startTime;
                lectureTimeSlot.availableSlots = lts.totalSlots - lts.occupiedSlots


                //Add the custom object to the calendar object
                event.customLTS = lectureTimeSlot;

                //Add the element to the calendar
                calendar.addEvent(event);
            }
            //Render the new calendar
            calendar.render();
        }
    });
}


//Attach render calendar to button for week change
$('body').on('click', 'button.fc-next-button', renderCalendar);
$('body').on('click', 'button.fc-prev-button', renderCalendar);
//Initial render when page loaded
renderCalendar(calendar);