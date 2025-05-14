const sidebar = document.getElementById('sidebar');
const mainContent = document.getElementById('mainContent');
const menuToggle = document.getElementById('menuToggle');

menuToggle.addEventListener('click', () => {
    if (sidebar.classList.contains('-translate-x-full')) {
        sidebar.classList.remove('-translate-x-full');
        mainContent.classList.remove('ml-0');
        mainContent.classList.add('ml-64');
    } else {
        sidebar.classList.add('-translate-x-full');
        mainContent.classList.remove('ml-64');
        mainContent.classList.add('ml-0');
    }
});

function handleResize() {
    if (window.innerWidth < 765) {
        sidebar.classList.add('-translate-x-full');
        mainContent.classList.remove('ml-64');
        mainContent.classList.add('ml-0');
    } else {
        sidebar.classList.remove('-translate-x-full');
        mainContent.classList.remove('ml-0');
        mainContent.classList.add('ml-64');
    }
}

window.addEventListener('resize', handleResize);
window.addEventListener('load', handleResize);

document.addEventListener("DOMContentLoaded", function () {
    const notificationBtn = document.getElementById("notificationBtn");
    const notificationDropdown = document.getElementById("notificationDropdown");
    const profileBtn = document.getElementById('profileBtn');
    const profileDropdown = document.getElementById('profileDropdown');
    notificationBtn.addEventListener("click", function (e) {
        e.stopPropagation();
        notificationDropdown.classList.toggle("show");
        profileDropdown.classList.remove("show");
    });
    profileBtn.addEventListener("click", function (e) {
        e.stopPropagation();
        profileDropdown.classList.toggle("show");
        notificationDropdown.classList.remove("show");
    });
    // Ẩn dropdown khi click ra ngoài
    document.addEventListener("click", function (e) {
        if (!notificationDropdown.contains(e.target) && !notificationBtn.contains(e.target)) {
            notificationDropdown.classList.remove("show");
        }
        if (!profileDropdown.contains(e.target) && !profileBtn.contains(e.target)) {
            profileDropdown.classList.remove("show");
        }
    });
})

