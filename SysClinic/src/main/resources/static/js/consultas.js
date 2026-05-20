let consultaEditandoId = null;

async function carregarPacientes() {

    const token = localStorage.getItem("token");

    const response = await fetch(
        "http://localhost:8080/pacientes",
        {
            headers: {
                "Authorization": `Bearer ${token}`
            }
        }
    );

    const pacientes = await response.json();

    const select = document.getElementById("pacienteId");

    select.innerHTML =
        `<option value="">Selecione</option>`;

    pacientes.forEach(paciente => {

        select.innerHTML += `

            <option value="${paciente.id}">

                ${paciente.nome}

            </option>

        `;
    });
}

async function carregarMedicos() {

    const token = localStorage.getItem("token");

    const response = await fetch(
        "http://localhost:8080/medicos",
        {
            headers: {
                "Authorization": `Bearer ${token}`
            }
        }
    );

    const medicos = await response.json();

    const select = document.getElementById("medicoId");

    select.innerHTML =
        `<option value="">Selecione</option>`;

    medicos.forEach(medico => {

        select.innerHTML += `

            <option value="${medico.id}">

                ${medico.nome}

            </option>

        `;
    });
}

async function carregarConsultas() {

    const token = localStorage.getItem("token");

    if (!token) {

        window.location.href = "/login";
        return;
    }

    const response = await fetch(
        "http://localhost:8080/consultas",
        {
            headers: {
                "Authorization": `Bearer ${token}`
            }
        }
    );

    if (!response.ok) {

        alert("Erro ao carregar consultas");
        return;
    }

    const consultas = await response.json();

    const tabela = document.getElementById(
        "tabelaConsultas"
    );

    tabela.innerHTML = "";

    consultas.forEach(consulta => {

        tabela.innerHTML += `

            <tr>

                <td>${consulta.id}</td>

                <td>${consulta.pacienteNome}</td>

                <td>${consulta.medicoNome}</td>

                <td>${consulta.dataHora}</td>

                <td>${consulta.status}</td>

                <td>${consulta.observacao ?? ""}</td>

                <td>

                    <button class="btn btn-warning btn-sm me-2"
                            onclick='editarConsulta(${JSON.stringify(consulta)})'>

                        Editar

                    </button>

                    <button class="btn btn-danger btn-sm"
                            onclick="deletarConsulta(${consulta.id})">

                        Excluir

                    </button>

                </td>

            </tr>

        `;
    });
}

async function salvarConsulta() {

    const token = localStorage.getItem("token");

    const consulta = {

        pacienteId:
        document.getElementById("pacienteId").value,

        medicoId:
        document.getElementById("medicoId").value,

        dataHora:
        document.getElementById("dataHora").value,

        observacao:
        document.getElementById("observacao").value,

        status:
        document.getElementById("status").value
    };

    let url = "http://localhost:8080/consultas";
    let method = "POST";

    if (consultaEditandoId !== null) {

        url =
            `http://localhost:8080/consultas/${consultaEditandoId}`;

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

            body: JSON.stringify(consulta)
        }
    );

    if (!response.ok) {

        alert("Erro ao salvar consulta");
        return;
    }

    alert("Consulta salva com sucesso!");

    limparFormulario();

    carregarConsultas();
}

function editarConsulta(consulta) {

    consultaEditandoId = consulta.id;

    document.getElementById("pacienteId").value =
        consulta.pacienteId;

    document.getElementById("medicoId").value =
        consulta.medicoId;

    document.getElementById("dataHora").value =
        consulta.dataHora;

    document.getElementById("observacao").value =
        consulta.observacao;

    document.getElementById("status").value =
        consulta.status;

    document.querySelector(".btn-success").innerText =
        "Atualizar";
}

async function deletarConsulta(id) {

    const token = localStorage.getItem("token");

    const confirmar = confirm(
        "Deseja realmente excluir esta consulta?"
    );

    if (!confirmar) {
        return;
    }

    const response = await fetch(
        `http://localhost:8080/consultas/${id}`,
        {
            method: "DELETE",

            headers: {
                "Authorization": `Bearer ${token}`
            }
        }
    );

    if (!response.ok) {

        alert("Erro ao excluir consulta");
        return;
    }

    alert("Consulta excluída com sucesso!");

    carregarConsultas();
}

function limparFormulario() {

    document.getElementById("pacienteId").value = "";
    document.getElementById("medicoId").value = "";
    document.getElementById("dataHora").value = "";
    document.getElementById("observacao").value = "";
    document.getElementById("status").value = "AGENDADA";

    consultaEditandoId = null;

    document.querySelector(".btn-success").innerText =
        "Salvar";
}

function logout() {

    localStorage.removeItem("token");

    window.location.href = "/login";
}

carregarPacientes();
carregarMedicos();
carregarConsultas();