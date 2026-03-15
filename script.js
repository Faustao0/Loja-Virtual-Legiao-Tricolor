const btn = document.getElementById("categoriesBtn");
const menu = document.getElementById("categoriesMenu");

if (btn && menu) {

  btn.addEventListener('click', function (e) {
    e.stopPropagation();
    menu.style.display =
      menu.style.display === 'flex' ? 'none' : 'flex';
  });

  document.addEventListener('click', function () {
    menu.style.display = 'none';
  });

}

function adicionarAoCarrinho(produto) {

  let carrinho = JSON.parse(localStorage.getItem("carrinho")) || [];

  const produtoExistente = carrinho.find(p => p.id === produto.id);

  if (produtoExistente) {
    produtoExistente.quantidade += 1;
  } else {
    produto.quantidade = 1;
    carrinho.push(produto);
  }

  localStorage.setItem("carrinho", JSON.stringify(carrinho));

  alert("Produto adicionado ao carrinho!");
}

// ==========================
// FORMULÁRIO DE CADASTRO
// ==========================
const form = document.getElementById("formCadastro");

if (form) {
  form.addEventListener("submit", async function (e) {
    e.preventDefault();

    const senha = document.getElementById("senha").value;
    const confirmar = document.getElementById("confirmar").value;

    if (senha !== confirmar) {
      alert("As senhas não coincidem!");
      return;
    }

    const dados = {
      name: document.getElementById("nome").value,
      cpf: document.getElementById("CPF").value,
      email: document.getElementById("email").value,
      telefone: document.getElementById("telefone").value,
      password: senha
    };

    try {
      const response = await fetch("https://loja-virtual-legiao-tricolor-backend-new.onrender.com/users/client", {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify(dados)
      });

      if (response.ok) {
        alert("Conta criada com sucesso!");
        window.location.href = "login.html";
      } else {
        const erro = await response.text();
        alert("Erro ao criar conta: " + erro);
      }

    } catch (error) {
      alert("Erro ao conectar com o servidor.");
      console.error(error);
    }
  });
}
const formLogin = document.getElementById("formLogin");

if (formLogin) {

  formLogin.addEventListener("submit", async function (e) {
    e.preventDefault();

    const email = document.getElementById("email").value;
    const senha = document.getElementById("senha").value;

    try {

      const response = await fetch("https://loja-virtual-legiao-tricolor-backend-new.onrender.com/auth/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify({
          email: email,
          password: senha
        })
      });

      if (response.ok) {

        const data = await response.json();

        // 🔐 Salva no localStorage
        localStorage.setItem("token", data.token);
        localStorage.setItem("userName", data.name);

        alert("Login realizado com sucesso!");
        window.location.href = "index.html";

      } else {
        alert("Email ou senha inválidos.");
      }

    } catch (error) {
      alert("Erro ao conectar com o servidor.");
      console.error(error);
    }

  });
}
// ==========================
// VERIFICAR SE ESTÁ LOGADO
// ==========================

document.addEventListener("DOMContentLoaded", function () {

  const userName = localStorage.getItem("userName");
  const userContainer = document.querySelector(".user-cart");

  if (userName && userContainer) {

    userContainer.innerHTML = `
      <div class="user-logged">
        <span class="user-name">Olá, ${userName}</span><br>
        <span class="logout-btn">Sair</span>
      </div>
      <a href="carrinho.html" class="cart">
        <img src="Imagens/Estética/carrinho-icon.png" alt="Carrinho">
      </a>
    `;

    const logoutBtn = document.querySelector(".logout-btn");

    logoutBtn.addEventListener("click", function () {
      localStorage.removeItem("token");
      localStorage.removeItem("userName");
      window.location.href = "index.html";
    });

  }
});

const formAdmin = document.getElementById("formAdminLogin");

if (formAdmin) {

  formAdmin.addEventListener("submit", async function (e) {
    e.preventDefault();

    const email = document.getElementById("adminEmail").value;
    const senha = document.getElementById("adminSenha").value;

    try {

      const response = await fetch("https://loja-virtual-legiao-tricolor-backend-new.onrender.com/auth/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify({
          email: email,
          password: senha
        })
      });

      if (response.ok) {

        const data = await response.json();

        // 🔥 VERIFICA SE É ADMIN
        if (data.role === "ADMIN") {

          localStorage.setItem("adminToken", data.token);
          localStorage.setItem("adminName", data.name);

          // 🚀 REDIRECIONA PARA O DASHBOARD
          window.location.href = "admin-dashboard.html";

        } else {
          alert("Você não tem permissão de administrador.");
        }

      } else {
        alert("Credenciais inválidas.");
      }

    } catch (error) {
      alert("Erro ao conectar com o servidor.");
      console.error(error);
    }

  });

}
function logoutAdmin() {
  localStorage.removeItem("adminToken");
  localStorage.removeItem("adminName");
  window.location.href = "index.html";
}

const formProduto = document.getElementById("formProduto");

if (formProduto) {

  formProduto.addEventListener("submit", async function (e) {
    e.preventDefault();

    const formData = new FormData();

    formData.append("name", document.getElementById("produtoNome").value);
    formData.append("description", document.getElementById("produtoDescricao").value);
    formData.append("price", document.getElementById("produtoPreco").value);
    formData.append("stockQuantity", document.getElementById("produtoEstoque").value);
    formData.append("active", true);
    formData.append("categoryName", document.getElementById("produtoCategoria").value);

    const imagem = document.getElementById("produtoImagem").files[0];
    formData.append("image", imagem);

    const token = localStorage.getItem("adminToken");

    try {

      const response = await fetch(
  "https://loja-virtual-legiao-tricolor-backend-new.onrender.com/admin/products",
  {
    method: "POST",
    headers: {
      "Authorization": "Bearer " + token
    },
    body: formData
  }
);

      if (response.ok) {
        alert("Produto criado com sucesso!");
        formProduto.reset();
      } else {
        const erro = await response.text();
        alert("Erro: " + erro);
      }

    } catch (error) {
      console.error(error);
      alert("Erro ao conectar com o servidor.");
    }

  });

}