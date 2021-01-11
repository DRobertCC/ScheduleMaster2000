const xhr = new XMLHttpRequest();
xhr.onload = function() {
    showContents(['login-content']);
}
xhr.open('GET', 'protected/auth');
xhr.send();