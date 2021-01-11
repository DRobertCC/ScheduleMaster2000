function onGuestLoginClicked() {
    onGuestLogin();
}

function onGuestLogin() {
    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onGuestResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('GET', 'guest');
    xhr.send();
}

function onGuestResponse() {
    if (this.status === OK) {
        clearMessages();
        showContents(['guest-content']);
        onGuestLoad(JSON.parse(this.responseText));
    } else {
        onOtherResponse(guestContentDivEl, this);
    }
}

function onGuestLoad(schedules) {
    schedulesTableEl = document.getElementById('guest-schedules-table');
    schedulesTableBodyEl = schedulesTableEl.querySelector('tbody');

    appendGuestSchedules(schedules);
}

function appendGuestSchedule(schedule) {

    const aEl = document.createElement('a');
    aEl.textContent = schedule.scheduleName;
    aEl.href = 'javascript:void(0);';
    aEl.dataset.scheduleId = schedule.id;
    aEl.addEventListener('click', onTaskClicked);

    const nameTdEl = document.createElement('td');
    nameTdEl.appendChild(aEl);

    const lengthTdEl = document.createElement('td');
    lengthTdEl.textContent = schedule.scheduleLength;

    const trEl = document.createElement('tr');
    trEl.appendChild(nameTdEl);
    trEl.appendChild(lengthTdEl);
    schedulesTableEl=document.getElementById('guest-schedules-table');
    schedulesTableBodyEl=schedulesTableEl.querySelector('tbody');
    schedulesTableBodyEl.appendChild(trEl);
}

function appendGuestSchedules(schedules) {
    removeAllChildren(schedulesTableBodyEl);

    for (let i = 0; i < schedules.length; i++) {
        const schedule = schedules[i];
        appendGuestSchedule(schedule);
    }
}
