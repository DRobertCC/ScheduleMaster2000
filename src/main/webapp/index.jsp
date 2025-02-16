<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="google-signin-scope" content="profile email">
    <meta name="google-signin-client_id" content="18129417719-rntl1l3s5c6d8soqip7r2nugn48hnc9l.apps.googleusercontent.com">
    <script src="https://apis.google.com/js/platform.js" async defer></script>
    <title>Schedule Master 2000</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <c:url value="/index.js" var="indexScriptUrl"/>
    <c:url value="/register.js" var="registerScriptUrl"/>
    <c:url value="/login.js" var="loginScriptUrl"/>
    <c:url value="/logout.js" var="logoutScriptUrl"/>
    <c:url value="/profile.js" var="profileScriptUrl"/>
    <c:url value="/schedules.js" var="schedulesScriptUrl"/>
    <c:url value="/tasks.js" var="tasksScriptUrl"/>
    <c:url value="/guest.js" var="guestScriptUrl"/>
    <c:url value="/users.js" var="adminScriptUrl"/>
    <c:url value="/tableview.js" var="tableScriptUrl"/>
    <c:url value="/slider.js" var="sliderScriptUrl"/>
    <c:url value="/googlesignin.js" var="googlesigninScriptUrl"/>
    <script src="${indexScriptUrl}"></script>
    <script src="${loginScriptUrl}"></script>
    <script src="${registerScriptUrl}"></script>
    <script src="${profileScriptUrl}"></script>
    <script src="${logoutScriptUrl}"></script>
    <script src="${schedulesScriptUrl}"></script>
    <script src="${tasksScriptUrl}"></script>
    <script src="${guestScriptUrl}"></script>
    <script src="${adminScriptUrl}"></script>
    <script src="${tableScriptUrl}"></script>
    <script src="${sliderScriptUrl}"></script>
    <script src="${googlesigninScriptUrl}"></script> <%--https://medium.com/@skywalkerhunter/implement-google-sign-in-integration-1-the-basic-the-platform-js-6e93637a78c6--%>
</head>
<body>
<%--<script>
    function onSignIn(googleUser) {
        // Useful data for your client-side scripts:
        var profile = googleUser.getBasicProfile();
        console.log("ID: " + profile.getId()); // Don't send this directly to your server!
        console.log('Full Name: ' + profile.getName());
        console.log('Given Name: ' + profile.getGivenName());
        console.log('Family Name: ' + profile.getFamilyName());
        console.log("Image URL: " + profile.getImageUrl());
        console.log("Email: " + profile.getEmail());

        // The ID token you need to pass to your backend:
        var id_token = googleUser.getAuthResponse().id_token;
        console.log("ID Token: " + id_token);
    }
</script>--%>
<div id="login-content" class="content">
    <div class = "log-reg">
        <h1>Schedule Master 2000</h1>
        <form id="login-form" onsubmit="return false;">
            <input type="text" name="username" placeholder="username" requied>
            <input type="password" name="password" placeholder="password" requied>
            <button id="login-button">Login</button>

            <div id="signInId" class="g-signin2" data-onsuccess="onSignIn" data-theme="dark" type="hidden"></div>

            <button id="go-to-register-button">Sign Up</button>
            <button id="guest-login-button">Guest</button>
        </form>
    </div>
</div>
<div id="register-content" class="hidden content">
    <div class = "log-reg">
        <h1>Schedule Master 2000</h1>
        <form id="register-form" onsubmit="return false;">
            <input type="text" name="username" placeholder="username" requied>
            <input type="password" name="password" placeholder="password" requied>
            <button id="back-to-login-button">Back to login</button>
            <button id="register-button">Register</button>
        </form>
    </div>
</div>
<div id="header" class="hidden content">
    <div class="header">
        <a id="SM2000" title="Click to go to home" onclick="goToHome();">Schedule Master 2000</a>
        <div class="header-menu">
            <p>Current user: <span id="user-username"></span></p>
            <button id="go-to-schedules">Schedules</button>
            <button id="go-to-tasks">Tasks</button>
            <button id="logout-button">Logout</button>
        </div>
    </div>
</div>
<div id="admin-header" class="hidden content">
    <div class="header">
        <a title="Click to go to home" onclick="goToHome();">Schedule Master 2000</a>
        <div class="header-menu">
            <button id="all-schedule">Schedules</button>
            <button id="all-task">Tasks</button>
            <a href="javascript:void(0);" onclick="onGoToUsersButtonClicked();"><button id="all-user">Users</button></a>
            <button id="a-logout-button">Logout</button>
        </div>
    </div>
</div>
<div id="home-content" class="hidden content">
<%--    <p>Username: <span id="user-username"></span></p>--%>
    <div class="home-content">
        <div class="half-screen-box">
            <div class = "home-box">
                <div class = "home-box-header">
                    Schedules
                </div>
                <div class="tables">
                    <table id="home-schedules-table">
                        <thead>
                        <tr>
                            <th>Name</th>
                            <th>Length</th>
                            <th>Public</th>
                        </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <div class="half-screen-box">
            <div class = "home-box">
                <div class = "home-box-header">
                    Tasks
                </div>
                <div class="tables">
                    <table id="home-tasks-table">
                        <thead>
                        <tr>
                            <th>Name</th>
                            <th>Content</th>
                        </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="schedules-content" class="hidden content">
    <div class="schedules-content">
        <div class="half-screen-box">
            <div class = "create-box">
                <div class = "home-box-header">
                    Create Schedule
                </div>
                <form id="create-schedule-form" onsubmit="return false;">
                    <table>
                        <tr>
                            <th>Name</th>
                            <td><input type="text" name="schedulename" placeholder="Schedule's name" requied></td>
                        </tr>
                        <tr>
                            <th>Length</th>
                            <td><input type="number" name="length" placeholder="Schedule's length" min="1" max="7" requied></td>
                        </tr>
                        <tr>
                            <th>Public</th>
                            <td><input type="checkbox" name="ispublic"></td>
                        </tr>
                    </table>
                    <button id="create-schedule-button">Create</button>
                </form>
            </div>
        </div>
        <div class="half-screen-box">
            <div class = "home-box">
                <div class = "home-box-header">
                    Schedules
                </div>
                <div class="tables">
                    <table id="schedules-table">
                        <thead>
                        <tr>
                            <th>Name</th>
                            <th>Length</th>
                            <th>Public</th>
                            <th>Edit</th>
                            <th>Delete</th>
                        </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="user-content" class="hidden content">
    <div class="user-content">
        <div class="one-box">
            <div class = "home-box">
                <div class = "home-box-header">
                    Users
                </div>
                <div class="tables">
                    <table id="user-table">
                        <thead>
                        <tr>
                            <th>Name</th>
                            <th>Admin</th>
                            <th>Edit</th>
                            <th>Delete</th>
                        </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
    <div id="tasks-content" class="hidden content">
        <div class="tasks-content">
            <div class="half-screen-box">
                <div class = "create-box">
                    <div class = "home-box-header">
                        Create Task
                    </div>
                    <form id="create-task-form" onsubmit="return false;">
                        <table>
                            <tr>
                                <th>Name</th>
                                <td><input type="text" name="taskname" placeholder="Taskname" requied></td>
                            </tr>
                            <tr>
                                <th>Content</th>
                                <td><input type="text" name="content" placeholder="Content" requied></td>
                            </tr>
                        </table>
                        <button id="create-task-button">Create</button>
                    </form>
                </div>
            </div>
            <div class="half-screen-box">
                <div class = "home-box">
                    <div class = "home-box-header">
                        Tasks
                    </div>
                    <div class="tables">
                        <table id="tasks-table">
                            <thead>
                            <tr>
                                <th>Name</th>
                                <th>Content</th>
                                <th>Edit</th>
                                <th>Delete</th>
                            </tr>
                            </thead>
                            <tbody>

                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
<div id="the-schedule-content" class="hidden content">
    <div class="schedule-content">
        <div class="shedule-view">
            <table id="view-schedule">
                <thead>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>
        <div class="task-adder">
            <div class="task-adder-header">
                Task Adder
            </div>
            <form id="task-adder">
                <table>
                    <tr>
                        <th>Task name: </th>
                        <td>
                            <select id="task-name" style="width: 116px">
                                <option value="default"></option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <th>Day: </th>
                        <td>
                            <select id="day" style="width: 116px">
                                <option value="default"></option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <th>Starting hour: </th>
                        <td>
                            <div class="slidecontainer">
                                <input class="slider" id="startinghour" type="range" style="width: 160px; height: 30px" min="0" max="23" value="0" step ="1" list="increments1" oninput="showStartinghourHourValue(this.value)">
                                <datalist id="increments1">
                                    <option>0</option>
                                    <option>4</option>
                                    <option>8</option>
                                    <option>12</option>
                                    <option>16</option>
                                    <option>20</option>
                                    <option>23</option>
                                </datalist>
                                <span id="range1">0</span>h
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <th>Task length: </th>
                        <td>
                            <div class="slidecontainer">
                                <input id="taskLength" class="slider" type="range" style="width: 160px; height: 30px" min="1" max="24" value="1" step ="1" list="increments2" oninput="showTaskLengthValue(this.value)">
                                <datalist id="increments2">
                                    <option>1</option>
                                    <option>4</option>
                                    <option>8</option>
                                    <option>12</option>
                                    <option>16</option>
                                    <option>20</option>
                                    <option>24</option>
                                </datalist>
                                <span id="range2">1</span>h
                            </div>
                        </td>
                    </tr>
                </table>
                <button id="task-adder-button">Add Task</button>
            </form>
        </div>
    </div>
</div>
<div id="guest-content" class="hidden content">
    <table id="guest-schedules-table">
        <thead>
        <tr>
            <th>Name</th>
            <th>Length</th>>
        </tr>
        </thead>
        <tbody>
        </tbody>
    </table>
</div>
</body>
</html>

