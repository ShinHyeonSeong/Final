// Add active class to clicked menu item
var menuItems = document.querySelectorAll('.sidebar li');
menuItems.forEach(function(menuItem) {
  menuItem.addEventListener('click', function() {
    menuItems.forEach(function(item) {
      item.classList.remove('active');
    });
    this.classList.add('active');
  });
});

// 미완료 완료 표시를 나타내주는 버튼 리스너




