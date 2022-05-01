//Construct the calendar
let calendarEl = document.getElementById('trainee__calendar');
let selectedLectureTimeSlot = undefined;
let c_course_name = document.getElementById('c-course-name');
let c_course_date = document.getElementById('c-course-date');
let c_course_startTime = document.getElementById('c-course-startTime');
let c_course_endTime = document.getElementById('c-course-endTime');
let c_course_roomName = document.getElementById('c-course-roomName');
let d_course_name = document.getElementById('d-course-name');
let d_course_date = document.getElementById('d-course-date');
let d_course_startTime = document.getElementById('d-course-startTime');
let d_course_endTime = document.getElementById('d-course-endTime');
let d_course_roomName = document.getElementById('d-course-roomName');

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
    selectedLectureTimeSlot = event.extendedProps.customLTS;
    console.log(selectedLectureTimeSlot)
    if(selectedLectureTimeSlot.booked){
        c_course_name.textContent = selectedLectureTimeSlot.courseName
        c_course_date.textContent = selectedLectureTimeSlot.customDate
        c_course_startTime.textContent = selectedLectureTimeSlot.customStartTime
        c_course_roomName.textContent = selectedLectureTimeSlot.roomName
        $("#c-reservation").modal("show");
    }
    else{
        d_course_name.textContent = selectedLectureTimeSlot.courseName
        d_course_date.textContent = selectedLectureTimeSlot.customDate
        d_course_startTime.textContent = selectedLectureTimeSlot.customStartTime
        d_course_roomName.textContent = selectedLectureTimeSlot.roomName
        $("#d-reservation").modal("show");
    }
}

function insertReservation(){
    value = {}
    value.room = selectedLectureTimeSlot.roomName;
    value.lectureDate = selectedLectureTimeSlot.customDate;
    value.lectureStartTime = selectedLectureTimeSlot.customStartTime;
    res = JSON.stringify(value)
    $.ajax({
        url: "trainee/rest/reservation",
        async: false,
        cache: false,
        type: "POST",
        contentType : "application/json",
        data : res,
    });
}

function deleteReservation(){
    $.ajax({
        url: "trainee/rest/reservation/room/"+selectedLectureTimeSlot.roomName+"/date/"+selectedLectureTimeSlot.customDate+"/starttime/"+selectedLectureTimeSlot.customStartTime,
        async: false,
        cache: false,
        type: "DELETE",
        dataType: 'json'
    });
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

                let title = "COURSE NAME: "+lts.courseName;
                title+= " AVAILABLE SLOTS: "+(lts.totalSlots - lts.occupiedSlots);
                event.title = title;
                if(present)
                    event.backgroundColor = GetColorOfCourse("Reserved");
                else
                    event.backgroundColor = GetColorOfCourse(lts.courseName);
                let lectureTimeSlot = {};
                lectureTimeSlot.courseEditionId = lts.courseEditionId;
                lectureTimeSlot.courseName = lts.courseName
                lectureTimeSlot.roomName = lts.roomName;
                lectureTimeSlot.customDate = lts.date;
                lectureTimeSlot.customStartTime = lts.startTime;
                lectureTimeSlot.availableSlots = lts.totalSlots - lts.occupiedSlots
                lectureTimeSlot.booked = !present


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

$("#button-delete-reservation").click(() => {
    if (selectedLectureTimeSlot !== undefined) {
        deleteReservation();
        selectedLectureTimeSlot = undefined;
        renderCalendar();
    } else {
        console.log("Error, event not found");
    }
});

$("#button-save-reservation").click(() => {
    if (selectedLectureTimeSlot !== undefined) {
        insertReservation();
        selectedLectureTimeSlot = undefined;
        renderCalendar();
    } else {
        console.log("Error, event not found");
    }
});


//Attach render calendar to button for week change
$('body').on('click', 'button.fc-next-button', renderCalendar);
$('body').on('click', 'button.fc-prev-button', renderCalendar);
//Initial render when page loaded
renderCalendar(calendar);