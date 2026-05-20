let pacienteEditandoId = null;

/* =========================
   UTILITÁRIOS
========================= */

function obterToken() {

    return localStorage.getItem("token");
}

function redirecionarLogin() {

    localStorage.removeItem("token");

    window.location.href = "/login";
}

function tratarErro401(response) {

    if (response.status === 401) {

        redirecionarLogin();

        return true;
    }

    return false;
}

function limparFormulario() {

    document.getElementById("nome").value = "";
    document.getElementById("cpf").value = "";
    document.getElementById("telefone").value = "";
    document.getElementById("email").value = "";

    pacienteEditandoId = null;

    alterarTextoBotao("Salvar");
}

function alterarTextoBotao(texto) {

    document.querySelector(".btn-success").innerText = texto;
}

function logout() {

    redirecionarLogin();
}

/* =========================
   EDIÇÃO
========================= */

function editarPaciente(paciente) {

    pacienteEditandoId = paciente.id;

    document.getElementById("nome").value = paciente.nome;
    document.getElementById("cpf").value = paciente.cpf;
    document.getElementById("telefone").value = paciente.telefone;
    document.getElementById("email").value = paciente.email;

    alterarTextoBotao("Atualizar");
}

/* =========================
   LISTAGEM
========================= */

async function carregarPacientes() {

    const token = obterToken();

    if (!token) {

        redirecionarLogin();
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

    if (tratarErro401(response)) {
        return;
    }

    if (!response.ok) {

        alert("Erro ao carregar pacientes");
        return;
    }

    const pacientes = await response.json();

    const tabela = document.getElementById(
        "tabelaPacientes"
    );

    tabela.innerHTML = "";

    pacientes.forEach(paciente => {

        tabela.innerHTML += `

            <tr>

                <td>${paciente.id}</td>

                <td>${paciente.nome}</td>

                <td>${paciente.cpf}</td>

                <td>${paciente.email}</td>

                <td>${paciente.telefone}</td>

                <td class="d-flex gap-2">

                    <button
                        class="btn btn-warning btn-sm"
                        onclick='editarPaciente(${JSON.stringify(paciente)})'>

                        Editar

                    </button>

                    <button
                        class="btn btn-danger btn-sm"
                        onclick="deletarPaciente(${paciente.id})">

                        Excluir

                    </button>

                </td>

            </tr>

        `;
    });
}

/* =========================
   CADASTRO / EDIÇÃO
========================= */

async function cadastrarPaciente() {

    const token = obterToken();

    const paciente = {

        nome: document.getElementById("nome").value,
        cpf: document.getElementById("cpf").value,
        telefone: document.getElementById("telefone").value,
        email: document.getElementById("email").value
    };

    let url = "http://localhost:8080/pacientes";
    let method = "POST";

    if (pacienteEditandoId !== null) {

        url =
            `http://localhost:8080/pacientes/${pacienteEditandoId}`;

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

    if (tratarErro401(response)) {
        return;
    }

    if (!response.ok) {

        alert("Erro ao salvar paciente");
        return;
    }

    alert("Paciente salvo com sucesso!");

    limparFormulario();

    carregarPacientes();
}

/* =========================
   EXCLUSÃO
========================= */

async function deletarPaciente(id) {

    const token = obterToken();

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

    if (tratarErro401(response)) {
        return;
    }

    if (!response.ok) {

        alert(
            "Não é possível excluir paciente com consultas vinculadas."
        );

        return;
    }

    alert("Paciente excluído com sucesso!");

    carregarPacientes();
}

/* =========================
   INICIALIZAÇÃO
========================= */

carregarPacientes();