let schedulesTableEl;
let schedulesTableBodyEl;


function onGoToSchedulesButtonClicked(){
    showContents(['schedules-content', 'header']);
    onScheduleClicked();
}

function onScheduleAddResponse() {
    clearMessages();
    if (this.status === OK) {
        appendSchedule(JSON.parse(this.responseText));
    } else {
        onOtherResponse(scheduleContentDivEl, this);
    }
}

function onScheduleResponse() {
    clearMessages();
    if (this.status === OK) {
        onSchedulesLoad(JSON.parse(this.responseText));
    } else {
        onOtherResponse(scheduleContentDivEl, this);
    }
}

function appendSchedules(schedules) {
    removeAllChildren(schedulesTableBodyEl);

    for (let i = 0; i < schedules.length; i++) {
        const schedule = schedules[i];
        appendSchedule(schedule);
    }
}

function createSchedule() {
    const createScheduleForm = document.forms['create-schedule-form'];

    const scheduleNameInputEl = createScheduleForm.querySelector('input[name="schedulename"]');
    const scheduleLengthInputEl = createScheduleForm.querySelector('input[name="length"]');
    const scheduleIsPublicInputEl = createScheduleForm.querySelector('input[name="ispublic"]');

    const scheduleName = scheduleNameInputEl.value;
    const scheduleLength = scheduleLengthInputEl.value;
    const parsedScheduleLength = parseInt(scheduleLength);
    const ispublic = scheduleIsPublicInputEl.value;

    const parms = new URLSearchParams();
    parms.append('schedulename',scheduleName);
    parms.append('schedulelength',parsedScheduleLength);
    parms.append('ispublic',ispublic);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onScheduleAddResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('POST','protected/schedule');
    xhr.send(parms);
}
function appendSchedule(schedule) {


    /*const nameTdEl = document.createElement('td');
    nameTdEl.textContent = schedule.scheduleName;
    const aEl = document.createElement('a');
    aEl.appendChild(nameTdEl);
    aEl.href = 'javascript:void(0);';
    aEl.dataset.scheduleId = schedule.id;
    aEl.setAttribute("id", schedule.id)
    aEl.addEventListener('click', onScheduleViewClicked);*/

    const nameTdEl = createButtonTd(schedule.scheduleName,schedule.scheduleName);
    nameTdEl.onclick = function () { onScheduleViewClicked(schedule) };

    const deleteButton = createButtonTd('X','X');
    deleteButton.onclick = function () { onScheduleDeleteClicked(schedule.id,schedule.scheduleName) };

    const editButton = createButtonTd('Edit','Edit');
    editButton.onclick = function () { onScheduleEditClicked(schedule.id,schedule.scheduleName,schedule.scheduleLength) };

    const lengthTdEl = document.createElement('td');
    lengthTdEl.textContent = schedule.scheduleLength;

    const isPublicTdEl = document.createElement('td');
    isPublicTdEl.textContent = schedule.isPublic;

    const trEl = document.createElement('tr');
    trEl.appendChild(nameTdEl);
    trEl.appendChild(lengthTdEl);
    trEl.appendChild(isPublicTdEl);
    trEl.appendChild(editButton);
    trEl.appendChild(deleteButton);
    schedulesTableEl=document.getElementById('schedules-table');
    schedulesTableBodyEl=schedulesTableEl.querySelector('tbody');
    schedulesTableBodyEl.appendChild(trEl);
}

function onSchedulesLoad(schedules) {
    schedulesTableEl = document.getElementById('schedules-table');
    schedulesTableBodyEl = schedulesTableEl.querySelector('tbody');

    appendSchedules(schedules);
}
function onScheduleClicked() {

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onScheduleResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('GET', 'protected/schedule');
    xhr.send();
}

function onScheduleDeleteClicked(id,scheduleName) {
    if (confirm("Do you want to delete the " + scheduleName +" schedule?")) {
        const params = new URLSearchParams();
        params.append('scheduleId',id);
        const xhr = new XMLHttpRequest();
        xhr.addEventListener('load', onScheduleClicked);
        xhr.addEventListener('error', onNetworkError);
        xhr.open('DELETE', 'protected/schedule?' + params.toString());
        xhr.send();
    } else {
        alert("Then why did you click on it?");
    }
}

function onScheduleEditClicked(id,oldName,oldLength) {
    var name = prompt("Please enter the new name: ", oldName);
    if (name == null || name == "") {
        name = oldName;
    }
    var length = prompt("Please enter the length name: ", oldLength);
    length = parseInt(length);
    if(length > 8 && length < 1) {
        length = oldLength;
    }
    var status = confirm("Do you want to make this schedule public?");
    const params = new URLSearchParams();
    params.append('scheduleId',id);
    params.append('scheduleName',name);
    params.append('scheduleLength',length);
    params.append('scheduleStatus',status);
    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onScheduleClicked);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('PUT', 'protected/schedule?' + params.toString());
    xhr.send();
}


function onScheduleViewClicked(schedule) {
    const scheduleId = schedule.scheduleId;
    getUserTasks();
    var scheduleDayList = document.getElementById("day");
    removeAllChildren(scheduleDayList);
    var length = schedule.scheduleLength+1;
    for (let i = 1; i < length; i++) {
        console.log(i);
        const day = i;
        addDayToSelector(day,scheduleDayList);
    }
    createScheduleTableDisplay(schedule);
    showContents(['the-schedule-content', 'header']);
}
