function onProfileLoad(user) {
    clearMessages();
    showContents(['home-content', 'header']);
    document.getElementById('user-username').innerHTML = user.username;
}

function goToHome() {
    showContents(['home-content', 'header']);
}
