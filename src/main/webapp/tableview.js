let scheduleViewTableEl;
let scheduleViewTableBodyEl;

var slider = document.getElementById("myRange");
var output = document.getElementById("demo");
output.innerHTML = slider.value;

slider.oninput = function() {
    output.innerHTML = this.value;
}

function onTheScheduleClicked() {

    getUserTasks();
}

function getUserTasks() {

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onParseTask);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('GET', 'protected/task');
    xhr.send();
}

function onParseTask() {
    if (this.status === OK) {
        clearMessages();
        taskLoad(JSON.parse(this.responseText));
    } else {
        onOtherResponse(tableViewContentDivEl, this);
    }
}

function taskLoad(tasks) {
    var taskList = document.getElementById("task-name");
    removeAllChildren(taskList);
    for (let i = 0; i < tasks.length; i++) {
        const task = tasks[i];
        addTaskToSelector(task,taskList);
    }
}

function addTaskToSelector(task,taskList) {
    var option = document.createElement("OPTION");
    txt = document.createTextNode(task.taskName);
    option.appendChild(txt);
    taskList.insertBefore(option,taskList.lastChild);
}
function addDayToSelector(day,dayList) {
    var option = document.createElement("OPTION");
    txt = document.createTextNode(day);
    option.appendChild(txt);
    dayList.insertBefore(option,dayList.lastChild);
}


function addTaskToSchedule() {

}

function createScheduleTableDisplay(schedule) {
    const table = tableViewContentDivEl.getElementsByTagName('table');
    if(table != null){
        table.parentNode.removeChild(table);
    }
    const tableEl = document.createElement('table');
    const theadEl = createScheduleTableHeader(schedule);
    const tbodyEl = createScheduleTableBody(schedule);
    tableEl.appendChild(theadEl);
    tableEl.appendChild(tbodyEl);
    tableViewContentDivEl.appendChild(tableEl);
}

function createScheduleTableHeader(schedule) {
    const trEl = document.createElement('tr');

    const eventNameThEl = document.createElement('th');
    eventNameThEl.classList.add('default-th');``
    eventNameThEl.textContent = 'Hours';

    trEl.appendChild(eventNameThEl);

    for(let i = 1; i <= schedule.scheduleLength; i++){
        const tableNameThEl = document.createElement('th');
        tableNameThEl.classList.add('default-th');
        tableNameThEl.textContent = 'Day ' + i;

        trEl.appendChild(tableNameThEl);
    }

    const theadEl = document.createElement('thead');
    theadEl.appendChild(trEl);
    return theadEl;
}

function createScheduleTableBody(schedule) {
    const tbodyEl = document.createElement('tbody');

    for(let i = 1; i < 24; i++){

        const eventNameTdEl = document.createElement('td');
        eventNameTdEl.classList.add('default-cell');
        eventNameTdEl.textContent = i;

        const trEl = document.createElement('tr');
        trEl.appendChild(eventNameTdEl);

        for(let m = 0; m < schedule.days; m++){

            const tableNameTdEl = document.createElement('td');
            tableNameTdEl.classList.add('default-cell');
            tableNameTdEl.textContent = ' ';
            trEl.appendChild(tableNameTdEl);
        }

        tbodyEl.appendChild(trEl);
    }

    return tbodyEl;
}
