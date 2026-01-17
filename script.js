const btn = document.getElementById('categoriesBtn');
const menu = document.getElementById('categoriesMenu');

btn.addEventListener('click', function (e) {
  e.stopPropagation();
  menu.style.display = menu.style.display === 'flex' ? 'none' : 'flex';
});

document.addEventListener('click', function () {
  menu.style.display = 'none';
});

function adicionarAoCarrinho(produto) {
  let carrinho = JSON.parse(localStorage.getItem('carrinho')) || [];

  const produtoExistente = carrinho.find(p => p.id === produto.id);

  if (produtoExistente) {
    produtoExistente.quantidade += produto.quantidade;
  } else {
    carrinho.push(produto);
  }

  localStorage.setItem('carrinho', JSON.stringify(carrinho));

  alert('Produto adicionado ao carrinho!');
}
