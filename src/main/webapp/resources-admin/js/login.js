var form = document.getElementById('login');
var buttonE1 = document.getElementById('e1');

buttonE1.addEventListener('click', function () {
  form.classList.add('error_1');
  setTimeout(function () {
    form.classList.remove('error_1');
  }, 3000);
});