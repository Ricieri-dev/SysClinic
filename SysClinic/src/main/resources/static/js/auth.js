async function fazerLogin() {

    const login = document.getElementById("login").value;
    const senha = document.getElementById("senha").value;

    const response = await fetch(
        "http://localhost:8080/auth/login",
        {
            method: "POST",

            headers: {
                "Content-Type": "application/json"
            },

            body: JSON.stringify({
                login,
                senha
            })
        }
    );

    if (!response.ok) {

        alert("Login inválido");
        return;
    }

    const data = await response.json();

    localStorage.setItem("token", data.token);

    window.location.href = "/pacientes-view";

    console.log(data.token);
}