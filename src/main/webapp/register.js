function onRegisterButtonClicked() {
    const registerForm = document.forms['register-form'];

    const usernameInputEl = registerForm.querySelector('input[name="username"]');
    const passworInputEl = registerForm.querySelector('input[name="password"]');

    const username = usernameInputEl.value;
    const password = passworInputEl.value;

    const parms = new URLSearchParams();
    parms.append('username', username);
    parms.append('password', password);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onLoginResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('POST','register');
    xhr.send(parms);
}

function onBackToLoginButtonClicked(){
    showContents(['login-content']);
}
