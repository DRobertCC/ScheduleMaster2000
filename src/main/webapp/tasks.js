let tasksTableEl;
let tasksTableBodyEl;

function onGoToTasksButtonClicked(){
    onTaskClicked();
}

function onTaskAddResponse() {
    clearMessages();
    if (this.status === OK) {
        console.log(this.responseText);
        appendTasks(JSON.parse(this.responseText));
    } else {
        onOtherResponse(taskContentDivEl, this);
    }
}

function onCreateTaskButtonClicked() {
    const couponFormEl = document.forms['create-task-form'];

    const taskNameInputEl = couponFormEl.querySelector('input[name="taskname"]');
    const taskContentInputEl = couponFormEl.querySelector('input[name="content"]');

    const taskName = taskNameInputEl.value;
    const content = taskContentInputEl.value;

    const params = new URLSearchParams();
    params.append('taskName', taskName);
    params.append('content', content);


    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onTaskAddResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('POST', 'protected/task');
    xhr.send(params);

}



function appendTask(task) {

    const deleteButton = createButtonTd('X','X');
    deleteButton.onclick = function () { onTasksDeleteClicked(task.id,task.taskName) };

    const editButton = createButtonTd('edit','Edit');
    editButton.onclick = function () { onTasksEditClicked(task.id,task.taskName,task.taskContent) };

    const nameTdEl = document.createElement('td');
    nameTdEl.textContent = task.taskName;

    const contentTdEl = document.createElement('td');
    contentTdEl.textContent = task.taskContent;

    const idTdEl = document.createElement('td');
    idTdEl.textContent = task.id;

    const trEl = document.createElement('tr');
    trEl.appendChild(nameTdEl);
    trEl.appendChild(contentTdEl);
    trEl.appendChild(editButton);
    trEl.appendChild(deleteButton);
    tasksTableEl=document.getElementById('tasks-table');
    tasksTableBodyEl=tasksTableEl.querySelector('tbody');
    tasksTableBodyEl.appendChild(trEl);
}

function onTasksLoad(taskList) {
    tasksTableEl = document.getElementById('tasks-table');
    tasksTableBodyEl = tasksTableEl.querySelector('tbody');
    appendTasks(taskList);
}

function appendTasks(tasks) {
    removeAllChildren(tasksTableBodyEl);

    for (let i = 0; i < tasks.length; i++) {
        const task = tasks[i];
        appendTask(task);
    }
}

function onTaskClicked() {

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onTaskResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('GET', 'protected/task');
    xhr.send();
}

function onTaskResponse() {
    if (this.status === OK) {
        clearMessages();
        showContents(['tasks-content','header']);
        onTasksLoad(JSON.parse(this.responseText));
    } else {
        onOtherResponse(tasksContentDivEl, this);
    }
}

function onTasksDeleteClicked(id,taskName) {
    if (confirm("Do you want to delete the " + taskName +" task?")) {
        const params = new URLSearchParams();
        params.append('taskId',id);
        const xhr = new XMLHttpRequest();
        xhr.addEventListener('load', onTaskClicked);
        xhr.addEventListener('error', onNetworkError);
        xhr.open('DELETE', 'protected/task?' + params.toString());
        xhr.send();
    } else {
        alert("Then why did you click on it?");
    }

}

function onTasksEditClicked(id,oldName,oldContent) {
    var name = prompt("Please enter the new task name: ", oldName);
    if (name == null || name == "") {
        name = oldName;
    }
    var content = prompt("Please enter the new content: ", oldContent);
    if (content == null || content == "") {
        content = oldContent;
    }
    const params = new URLSearchParams();
    params.append('taskId',id);
    params.append('taskName',name);
    params.append('taskContent',content);
    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onTaskClicked);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('PUT', 'protected/task?' + params.toString());
    xhr.send();
}
