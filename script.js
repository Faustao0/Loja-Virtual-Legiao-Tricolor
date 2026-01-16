const btn = document.getElementById('categoriesBtn');
const menu = document.getElementById('categoriesMenu');

btn.addEventListener('click', function (e) {
  e.stopPropagation();
  menu.style.display = menu.style.display === 'flex' ? 'none' : 'flex';
});

document.addEventListener('click', function () {
  menu.style.display = 'none';
});