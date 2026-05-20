let pacienteEditandoId = null;

function editarPaciente(paciente) {

    pacienteEditandoId = paciente.id;

    document.getElementById("nome").value = paciente.nome;
    document.getElementById("cpf").value = paciente.cpf;
    document.getElementById("telefone").value = paciente.telefone;
    document.getElementById("email").value = paciente.email;

    document.querySelector(".btn-success").innerText = "Atualizar";
}



async function deletarPaciente(id) {

    const token = localStorage.getItem("token");

    const confirmar = confirm(
        "Deseja realmente excluir este paciente?"
    );

    if (!confirmar) {
        return;
    }

    const response = await fetch(
        `http://localhost:8080/pacientes/${id}`,
        {
            method: "DELETE",

            headers: {
                "Authorization": `Bearer ${token}`
            }
        }
    );

    if (!response.ok) {

        alert("Não é possível excluir paciente com consultas vinculadas.");
        return;
    }

    alert("Paciente excluído com sucesso!");

    carregarPacientes();
}

async function cadastrarPaciente() {

    const token = localStorage.getItem("token");

    const paciente = {

        nome: document.getElementById("nome").value,
        cpf: document.getElementById("cpf").value,
        telefone: document.getElementById("telefone").value,
        email: document.getElementById("email").value

    };

    let url = "http://localhost:8080/pacientes";
    let method = "POST";

    if (pacienteEditandoId !== null) {

        url = `http://localhost:8080/pacientes/${pacienteEditandoId}`;
        method = "PUT";
    }

    const response = await fetch(
        url,
        {
            method: method,

            headers: {

                "Content-Type": "application/json",
                "Authorization": `Bearer ${token}`

            },

            body: JSON.stringify(paciente)
        }
    );

    if (!response.ok) {

        alert("Erro ao salvar paciente");
        return;
    }

    alert("Paciente salvo com sucesso!");

    limparFormulario();

    pacienteEditandoId = null;

    document.querySelector(".btn-success").innerText = "Salvar";

    carregarPacientes();
}

function logout() {

    localStorage.removeItem("token");

    window.location.href = "/login";
}

function limparFormulario() {

    document.getElementById("nome").value = "";
    document.getElementById("cpf").value = "";
    document.getElementById("telefone").value = "";
    document.getElementById("email").value = "";

    pacienteEditandoId = null;

    document.querySelector(".btn-success").innerText = "Salvar";
}



async function carregarPacientes() {

    const token = localStorage.getItem("token");

    if (!token) {

        window.location.href = "/login";
        return;
    }

    const response = await fetch(
        "http://localhost:8080/pacientes",
        {
            method: "GET",

            headers: {
                "Authorization": `Bearer ${token}`
            }
        }
    );

    if (!response.ok) {

        alert("Erro ao carregar pacientes");
        return;
    }

    const pacientes = await response.json();

    const tabela = document.getElementById("tabelaPacientes");

    tabela.innerHTML = "";

    pacientes.forEach(paciente => {

        tabela.innerHTML += `

            <tr>

                <td>${paciente.id}</td>
                <td>${paciente.nome}</td>
                <td>${paciente.cpf}</td>
                <td>${paciente.email}</td>
                <td>${paciente.telefone}</td>
                <td>

                    <button class="btn btn-danger btn-sm"
                    onclick="deletarPaciente(${paciente.id})">

                         Excluir

                        </button>
                    
                        <button class="btn btn-warning btn-sm me-2"
                        onclick='editarPaciente(${JSON.stringify(paciente)})'>

                        Editar

                       </button>

                </td>

            </tr>

        `;
    });
}

function logout() {

    localStorage.removeItem("token");

    window.location.href = "/login";
}

carregarPacientes();