function onLoginResponse(){
    if(this.status === OK ){
        const user = JSON.parse(this.responseText);
        setAuthorization(user);
        onProfileLoad(user);
    } else {
        onOtherResponse(loginContentDivEl, this);
    }
}

function onLoginButtonClicked() {
    const loginForm = document.forms['login-form'];

    const usernameInputEl = loginForm.querySelector('input[name="username"]');
    const passwordInputEl = loginForm.querySelector('input[name="password"]');

    const username = usernameInputEl.value;
    const password = passwordInputEl.value;

    const parms = new URLSearchParams();
    parms.append('username',username);
    parms.append('password',password);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onLoginResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('POST','login');
    xhr.send(parms);
}

function onGoToRegisterButtonClicked(){
    showContents(['register-content']);
}
