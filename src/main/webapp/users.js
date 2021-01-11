let usersTableEl;
let usersTableBodyEl;


function onGoToUsersButtonClicked(){
    showContents(['user-content','admin-header']);
    onUsersClicked();
}

function onUserResponse() {
    clearMessages();
    if (this.status === OK) {
        onUsersLoad(JSON.parse(this.responseText));
    } else {
        onOtherResponse(userContentDivEl, this);
    }
}

function appendUsers(users) {
    removeAllChildren(usersTableBodyEl);

    for (let i = 0; i < users.length; i++) {
        const user = users[i];
        appendUser(user);
    }
}

function appendUser(user) {



    const usernameTdEl = document.createElement('td');
    usernameTdEl.textContent = user.username;

    const deleteButton = createButtonTd('X','X');
    deleteButton.onclick = function () { onUserDeleteClicked(user.id,user.username) };

    const editButton = createButtonTd('Admin','Admin');
    editButton.onclick = function () { onUserEditClicked(user.id,user.username) };

    const isAdminTdEl = document.createElement('td');
    isAdminTdEl.textContent = user.admin;

    const trEl = document.createElement('tr');
    trEl.appendChild(usernameTdEl);
    trEl.appendChild(isAdminTdEl);
    trEl.appendChild(editButton);
    trEl.appendChild(deleteButton);
    usersTableEl=document.getElementById('user-table');
    usersTableBodyEl=usersTableEl.querySelector('tbody');
    usersTableBodyEl.appendChild(trEl);
}

function onUsersLoad(users) {
    usersTableEl = document.getElementById('user-table');
    usersTableBodyEl = usersTableEl.querySelector('tbody');

    appendUsers(users);
}
function onUsersClicked() {

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onUserResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('GET', 'protected/adminuser');
    xhr.send();
    
}

function onUserDeleteClicked(id,username) {
    if (confirm("Do you want to delete " + username +"?")) {
        const params = new URLSearchParams();
        params.append('userId',id);
        const xhr = new XMLHttpRequest();
        xhr.addEventListener('load', onUsersClicked);
        xhr.addEventListener('error', onNetworkError);
        xhr.open('DELETE', 'protected/adminuser?' + params.toString());
        xhr.send();
    } else {
        alert("Then why did you click on it?");
    }
}

function onUserEditClicked(id,name) {
    var status = confirm("Do you want to promote " + name + "to admin?");
    const params = new URLSearchParams();
    params.append('userId',id);
    params.append('adminStatus',status);
    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onUsersClicked);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('PUT', 'protected/adminuser?' + params.toString());
    xhr.send();
}
