document.getElementById('cliente-form').addEventListener('submit', function(event) {
    event.preventDefault();

    const clienteData = {
        nomeCliente: document.getElementById('nomeCliente').value,
        emailCliente: document.getElementById('emailCliente').value,
        telefone: document.getElementById('telefone').value,
        cidade: document.getElementById('cidade').value,
        bairro: document.getElementById('bairro').value,
        rua: document.getElementById('rua').value,
        uf: document.getElementById('uf').value
    };

    fetch('http://localhost:8080/api/clientes', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(clienteData)
    })
    .then(response => response.json())
    .then(data => {
        alert('Cliente cadastrado com sucesso!');
        listarClientes();
        document.getElementById('cliente-form').reset();
    })
    .catch(error => {
        console.error('Erro ao cadastrar cliente:', error);
        alert('Erro ao cadastrar cliente.');
    });
});

function listarClientes() {
    fetch('http://localhost:8080/api/clientes')
    .then(response => response.json())
    .then(data => {
        const clientesList = document.getElementById('clientes-list');
        clientesList.innerHTML = ''; // Limpar lista antes de adicionar novos itens
        data.forEach(cliente => {
            const li = document.createElement('li');
            li.textContent = `${cliente.nomeCliente} - ${cliente.emailCliente} - ${cliente.telefone}`;
            clientesList.appendChild(li);
        });
    })
    .catch(error => {
        console.error('Erro ao listar clientes:', error);
        alert('Erro ao listar clientes.');
    });
}

// Chama a função para listar clientes assim que a página carrega
document.addEventListener('DOMContentLoaded', listarClientes);
