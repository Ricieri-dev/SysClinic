let medicoEditandoId = null;

async function carregarMedicos() {

    const token = localStorage.getItem("token");

    if (!token) {

        window.location.href = "/login";
        return;
    }

    const response = await fetch(
        "http://localhost:8080/medicos",
        {
            method: "GET",

            headers: {
                "Authorization": `Bearer ${token}`
            }
        }
    );

    if (!response.ok) {

        alert("Erro ao carregar médicos");
        return;
    }

    const medicos = await response.json();

    const tabela = document.getElementById("tabelaMedicos");

    tabela.innerHTML = "";

    medicos.forEach(medico => {

        tabela.innerHTML += `

            <tr>

                <td>${medico.id}</td>
                <td>${medico.nome}</td>
                <td>${medico.especialidade}</td>
                <td>${medico.crm}</td>
                <td>${medico.email}</td>
                <td>${medico.telefone}</td>

                <td>

                    <button class="btn btn-warning btn-sm me-2"
                            onclick='editarMedico(${JSON.stringify(medico)})'>

                        Editar

                    </button>

                    <button class="btn btn-danger btn-sm"
                            onclick="deletarMedico(${medico.id})">

                        Excluir

                    </button>

                </td>

            </tr>

        `;
    });
}

async function cadastrarMedico() {

    const token = localStorage.getItem("token");

    const medico = {

        nome: document.getElementById("nome").value,
        especialidade: document.getElementById("especialidade").value,
        crm: document.getElementById("crm").value,
        telefone: document.getElementById("telefone").value,
        email: document.getElementById("email").value

    };

    let url = "http://localhost:8080/medicos";
    let method = "POST";

    if (medicoEditandoId !== null) {

        url = `http://localhost:8080/medicos/${medicoEditandoId}`;
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

            body: JSON.stringify(medico)
        }
    );

    if (!response.ok) {

        alert("Erro ao salvar médico");
        return;
    }

    alert("Médico salvo com sucesso!");

    limparFormulario();

    carregarMedicos();
}

function editarMedico(medico) {

    medicoEditandoId = medico.id;

    document.getElementById("nome").value = medico.nome;
    document.getElementById("especialidade").value = medico.especialidade;
    document.getElementById("crm").value = medico.crm;
    document.getElementById("telefone").value = medico.telefone;
    document.getElementById("email").value = medico.email;

    document.querySelector(".btn-success").innerText = "Atualizar";
}

async function deletarMedico(id) {

    const token = localStorage.getItem("token");

    const confirmar = confirm(
        "Deseja realmente excluir este médico?"
    );

    if (!confirmar) {
        return;
    }

    const response = await fetch(
        `http://localhost:8080/medicos/${id}`,
        {
            method: "DELETE",

            headers: {
                "Authorization": `Bearer ${token}`
            }
        }
    );

    if (!response.ok) {

        alert(
            "Não é possível excluir médico com consultas vinculadas."
        );

        return;
    }

    alert("Médico excluído com sucesso!");

    carregarMedicos();
}

function limparFormulario() {

    document.getElementById("nome").value = "";
    document.getElementById("especialidade").value = "";
    document.getElementById("crm").value = "";
    document.getElementById("telefone").value = "";
    document.getElementById("email").value = "";

    medicoEditandoId = null;

    document.querySelector(".btn-success").innerText = "Salvar";
}

function logout() {

    localStorage.removeItem("token");

    window.location.href = "/login";
}

carregarMedicos();