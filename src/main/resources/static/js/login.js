let loginButton = document.getElementById("login");


loginButton.addEventListener("click", event => {
    // Se elimina la 'sesion'
    if (sessionStorage.getItem('logued')) {
        sessionStorage.removeItem('logued');

        loginButton.classList.remove("btn-warning");
        loginButton.classList.add("btn-primary");
        loginButton.innerText = "Iniciar Sesión";

        alert('Sesión cerrada con exito');
    } else { // Se crea la 'sesion'
        sessionStorage.setItem("logued", true);
        
        loginButton.innerText = "Cerrar Sesión";
        loginButton.classList.remove("btn-primary");
        loginButton.classList.add("btn-warning");

        alert('Usted se ha logueado');
    }
});