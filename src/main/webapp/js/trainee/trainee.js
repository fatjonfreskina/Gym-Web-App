$(document).ready(function () {
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
    let d_course_substitution = document.getElementById('d-course-substitution');
    let e_message = document.getElementById('e-message');
    let div_substitution = document.getElementById('div-substitution');

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
        eventClick: clickHandler,
        eventContent: function (info) {
            let container = document.createElement('div')
            container.className = 'container-fluid'
            let row1 = document.createElement('div')
            row1.className = 'row overflow-hidden'
            let c12 = document.createElement('div')
            c12.className = 'col-sm font-weight-bold text-uppercase'
            c12.innerHTML = info.event._def.extendedProps.customLTS.courseName
            row1.appendChild(c12)

            let row2 = document.createElement('div')
            row2.className = 'row overflow-hidden'
            let c21 = document.createElement('div')
            c21.className = 'col-sm'
            c21.innerHTML = info.event._def.extendedProps.customLTS.roomName
            row2.appendChild(c21)

            let event = info.event;
            // retrieve slot time and subtract two hours limiting reservations
            event_date = parseInt(event._instance.range.start.getTime())- 2*60*60*1000
            actual_date = new Date().getTime()

            let row3 = document.createElement('div')
            row3.className = 'row overflow-hidden'
            let c31 = document.createElement('div')
            c31.className = 'col-sm'
            if(event_date >= actual_date && info.event._def.extendedProps.customLTS.availableSlots > 0)
                c31.innerHTML = "<i class=\"fa-solid fa-check-to-slot text-success\"></i>"
            else
                c31.innerHTML = "<i class=\"fa-solid fa-hand text-danger\"></i>"
            row3.appendChild(c31)

            container.appendChild(row1)
            container.appendChild(row2)
            container.appendChild(row3)
            let arrayOfDomNodes = [container]
            return {domNodes: arrayOfDomNodes}
        }
    });

    /**
     * Function associated to the click of a calendar object
     * @param info object that has been clicked
     */
    function clickHandler(info) {
        let event = info.event;
        selectedLectureTimeSlot = event.extendedProps.customLTS;
        event_date = parseInt(event._instance.range.start.getTime())- 2*60*60*1000
        actual_date = new Date().getTime()

        if(event_date < actual_date){
            e_message.textContent = "Selected slot is no more available";
            $("#e-reservation").modal("show");
            return
        }
        if(parseInt(selectedLectureTimeSlot.availableSlots) <= 0){
            e_message.textContent = "Selected slot is full";
            $("#e-reservation").modal("show");
            return
        }
        if (selectedLectureTimeSlot.booked) {

            if(selectedLectureTimeSlot.substitution !== undefined)
            {
                div_substitution.style.removeProperty("display")
                //div_substitution.removeAttribute("style")
                d_course_substitution.textContent = "There is a substitute for this lecture!"
            }else
                div_substitution.style.setProperty("display","none")

            c_course_name.textContent = selectedLectureTimeSlot.courseName
            c_course_date.textContent = selectedLectureTimeSlot.customDate
            c_course_startTime.textContent = selectedLectureTimeSlot.customStartTime
            c_course_roomName.textContent = selectedLectureTimeSlot.roomName
            $("#c-reservation").modal("show");
        } else {
            d_course_name.textContent = selectedLectureTimeSlot.courseName
            d_course_date.textContent = selectedLectureTimeSlot.customDate
            d_course_startTime.textContent = selectedLectureTimeSlot.customStartTime
            d_course_roomName.textContent = selectedLectureTimeSlot.roomName
            $("#d-reservation").modal("show");
        }
    }

    /**
     * Performs the AJAX call to insert a new reservation
     */
    function insertReservation() {
        value = {}
        value.room = selectedLectureTimeSlot.roomName;
        value.lectureDate = selectedLectureTimeSlot.customDate;
        value.lectureStartTime = selectedLectureTimeSlot.customStartTime;
        res = JSON.stringify(value)
        $.ajax({
            url: "trainee/rest/reservation",
            cache: false,
            type: "POST",
            contentType: "application/json",
            data: res,
            success: function (response) {
                selectedLectureTimeSlot = undefined;
                renderCalendar()
            },
            error: function (response) {
                //console.log(response.responseJSON.message);
                e_message.textContent = response.responseJSON.message;
                $("#e-reservation").modal("show");
            }
        });
    }

    /**
     * Performs the AJAX call to delete a reservation
     */
    function deleteReservation() {
        $.ajax({
            url: "trainee/rest/reservation/room/" + selectedLectureTimeSlot.roomName + "/date/" + selectedLectureTimeSlot.customDate + "/starttime/" + selectedLectureTimeSlot.customStartTime,
            cache: false,
            type: "DELETE",
            dataType: 'json',
            success: function (response) {
                selectedLectureTimeSlot = undefined;
                renderCalendar()
            },
            error: function (response) {
                //console.log(response.responseJSON.message);
                e_message.textContent = response.responseJSON.message;
                $("#e-reservation").modal("show");
            }
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
            url: "trainee/rest/reservation/from-date/" + start + "/to-date/" + end,
            cache: false,
            type: "GET",
            dataType: 'json',
            success: function (response) {
                reservations = response
                $.ajax({
                    url: "trainee/rest/available/from-date/" + start + "/to-date/" + end,
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
                            //startDate = new Date(Date.parse(lts.date + ' ' + lts.startTime + ' GMT+2'));
                            startDate = new Date(Date.parse(lts.date + 'T' + lts.startTime.toString()));
                            event.start = startDate;
                            event.end = moment(startDate).add(2, 'hours').toDate();

                            let present = false;
                            for (let i = 0; i < reservations.length; i++) {
                                if (
                                    reservations[i].room.localeCompare(lts.roomName) == 0 &&
                                    reservations[i].lectureDate.localeCompare(lts.date) == 0 &
                                    reservations[i].lectureStartTime.localeCompare(lts.startTime) == 0) {
                                    reservations.splice(i, 1)
                                    present = true;
                                    break;
                                }
                            }

                            event.title = lts.courseName;
                            if (present)
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
                            lectureTimeSlot.substitution = lts.substitution

                            //Add the custom object to the calendar object
                            event.customLTS = lectureTimeSlot;

                            //Add the element to the calendar
                            calendar.addEvent(event);
                        }
                        //Render the new calendar
                        if (calendarEl != null)
                            calendar.render();
                    },
                    error: function (response) {
                        //console.log(response.responseJSON.message);
                        e_message.textContent = response.responseJSON.message;
                        $("#e-reservation").modal("show");
                    }
                });
            }
        });
    }

    /**
     * Associates a function to the click of the button
     */
    $("#button-delete-reservation").click(() => {
        if (selectedLectureTimeSlot !== undefined) {
            deleteReservation();
        } else {
            //console.log("Error, event not found");
            e_message.textContent = "No event has been selected";
            $("#e-reservation").modal("show");
        }
    });

    /**
     * Associates a function to the click of the button
     */
    $("#button-save-reservation").click(() => {
        if (selectedLectureTimeSlot !== undefined) {
            insertReservation();
        } else {
            //console.log("Error, event not found");
            e_message.textContent = "No event has been selected";
            $("#e-reservation").modal("show");
        }
    });

    //Attach render calendar to button for week change
    $('body').on('click', 'button.fc-next-button', renderCalendar);
    $('body').on('click', 'button.fc-prev-button', renderCalendar);

    //Initial render when page loaded
    renderCalendar(calendar);
});