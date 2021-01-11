const OK = 200;
const BAD_REQUEST = 400;
const UNAUTHORIZED = 401;
const NOT_FOUND = 404;
const INTERNAL_SERVER_ERROR = 500;

let loginContentDivEl;
let registerContentDivEl;
let headerContentDivEl;
let homeContentDivEl;
let scheduleContentDivEl;
let schedulesContentDivEl;
let tasksContentDivEl;
let taskContentDivEl;
let logoutContentDivEl;
let guestContentDivEl;
let userContentDivEl;
let tableViewContentDivEl;

function showContents(ids) {
    const contentEls = document.getElementsByClassName('content');
    for (let i = 0; i < contentEls.length; i++) {
        const contentEl = contentEls[i];
        if (ids.includes(contentEl.id)) {
            contentEl.classList.remove('hidden');
        } else {
            contentEl.classList.add('hidden');
        }
    }
}

function newInfo(targetEl, message) {
    newMessage(targetEl, 'info', message);
}

function newError(targetEl, message) {
    newMessage(targetEl, 'error', message);
}

function newMessage(targetEl, cssClass, message) {
    clearMessages();

    const pEl = document.createElement('p');
    pEl.classList.add('message');
    pEl.classList.add(cssClass);
    pEl.textContent = message;

    targetEl.appendChild(pEl);
}

function clearMessages() {
    const messageEls = document.getElementsByClassName('message');
    for (let i = 0; i < messageEls.length; i++) {
        const messageEl = messageEls[i];
        messageEl.remove();
    }
}
function removeAllChildren(el) {
    while (el.firstChild) {
        el.removeChild(el.firstChild);
    }
}

function onNetworkError(response) {
    document.body.remove();
    const bodyEl = document.createElement('body');
    document.appendChild(bodyEl);
    newError(bodyEl, 'Network error, please try reloading the page');
}
/*
function onOtherResponse(targetEl, xhr) {
    if (xhr.status === NOT_FOUND) {
        newError(targetEl, 'Not found');
        console.error(xhr);
    } else {
        const json = JSON.parse(xhr.responseText);
        if (xhr.status === INTERNAL_SERVER_ERROR) {
            newError(targetEl, `Server error: ${json.message}`);
        } else if (xhr.status === UNAUTHORIZED || xhr.status === BAD_REQUEST) {
            newError(targetEl, json.message);
        } else {
            newError(targetEl, `Unknown error: ${json.message}`);
        }
    }
}*/

function hasAuthorization() {
    return localStorage.getItem('user') !== null;
}

function setAuthorization(user) {
    return localStorage.setItem('user', JSON.stringify(user));
}

function getAuthorization() {
    return JSON.parse(localStorage.getItem('user'));
}

function setUnauthorized() {
    return localStorage.removeItem('user');
}

function onLoad() {

    loginContentDivEl = document.getElementById('login-content');
    registerContentDivEl = document.getElementById('register-content');
    logoutContentDivEl = document.getElementById('logout-content');
    homeContentDivEl = document.getElementById('home-content');
    schedulesContentDivEl = document.getElementById('schedules-content');
    headerContentDivEl = document.getElementById('header');
    taskContentDivEl = document.getElementById('tasks-content');
    guestContentDivEl = document.getElementById('guest-content');userContentDivEl
    userContentDivEl = document.getElementById('user-content');
    tableViewContentDivEl = document.getElementById('the-schedule-content');


    //LOGIN BUTTON
    const loginButtonEl = document.getElementById('login-button');
    loginButtonEl.addEventListener('click', onLoginButtonClicked);

    //LOGOUT BUTTON
    const logoutButtonEl = document.getElementById('logout-button');
    logoutButtonEl.addEventListener('click', onLogoutButtonClicked);

    //SIGN UP BUTTON
    const registerButtonEl = document.getElementById('register-button');
    registerButtonEl.addEventListener('click', onRegisterButtonClicked);

    //GO TO SIGN UP PAGE BUTTON
    const goToRegisterButtonEl = document.getElementById('go-to-register-button');
    goToRegisterButtonEl.addEventListener('click', onGoToRegisterButtonClicked);

    //BACK TO LOGIN PAGE BUTTON
    const backToLoginButtonEl = document.getElementById('back-to-login-button');
    backToLoginButtonEl.addEventListener('click', onBackToLoginButtonClicked);

    //GO TO SCHEDULES PAGE BUTTON
    const goToSchedulesButtonEl = document.getElementById('go-to-schedules');
    goToSchedulesButtonEl.addEventListener('click', onGoToSchedulesButtonClicked);

    //GO TO TASKS PAGE BUTTON
    const goToTaskButtonEl = document.getElementById('go-to-tasks');
    goToTaskButtonEl.addEventListener('click', onGoToTasksButtonClicked);

    //CREATE SCHEDULE BUTTON
    const createScheduleButtonEl = document.getElementById('create-schedule-button');
    createScheduleButtonEl.addEventListener('click', createSchedule);

    //CREATE TASK BUTTON
    const createTaskButtonEl = document.getElementById('create-task-button');
    createTaskButtonEl.addEventListener('click',onCreateTaskButtonClicked);

    //GUEST LOGIN BUTTON
    const guestLogingButtonEl = document.getElementById('guest-login-button');
    guestLogingButtonEl.addEventListener('click',onGuestLoginClicked);

    //ADMIN BUTTON BUTTONS

        //ADMIN SCHEDULES BUTTON
    const allScheduleButtonEl = document.getElementById('all-schedule');
    allScheduleButtonEl.addEventListener('click',onGoToSchedulesButtonClicked);

        //ADMIN TASKS BUTTON
    const allTaskButtonEl = document.getElementById('all-task');
    allTaskButtonEl.addEventListener('click',onGoToTasksButtonClicked);

        //ADMIN LIST USERS BUTTON
    const listUsersButtonEl = document.getElementById('all-user');
    listUsersButtonEl.addEventListener('click',onGoToUsersButtonClicked);

        //ADMIN LOGOUT BUTTON
    const adminLogoutButtonEl = document.getElementById('a-logout-button');
    adminLogoutButtonEl.addEventListener('click',onLogoutButtonClicked);

    const addTaskToScheduleButtonEl = document.getElementById('task-adder-button');
    addTaskToScheduleButtonEl.addEventListener('click',addTaskToSchedule);


    if (hasAuthorization()) {
        onProfileLoad(getAuthorization());
    }
}

function onOtherResponse(targetEl, xhr) {
    if (xhr.status === NOT_FOUND) {
        newError(targetEl, 'Not found');
        console.error(xhr);
        console.log(this.responseText);
    } else {
        const json = JSON.parse(xhr.responseText);
        console.log(this.responseText);
        if (xhr.status === INTERNAL_SERVER_ERROR) {
            newError(targetEl, `Server error: ${json.message}`);
        } else if (xhr.status === UNAUTHORIZED || xhr.status === BAD_REQUEST) {
            newError(targetEl, json.message);
        } else {
            newError(targetEl, `Unknown error: ${json.message}`);
        }
    }
}

function createButtonTd(name, value) {
    const buttonEl = document.createElement('input');
    buttonEl.setAttribute('type', 'button');
    buttonEl.setAttribute('name', name);
    buttonEl.setAttribute('value', value);

    const tdEl = document.createElement('td');
    tdEl.appendChild(buttonEl);
    return tdEl;
}

function createLinkTd(name, value) {
    const textEl = document.createElement('a');
    textEl.setAttribute('name', name);
    textEl.setAttribute('value', value);

    const tdEl = document.createElement('td');
    tdEl.appendChild(buttonEl);
    return tdEl;
}


document.addEventListener('DOMContentLoaded', onLoad);
