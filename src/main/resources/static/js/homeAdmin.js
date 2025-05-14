// Toggle sidebar on mobile
const menuToggle = document.getElementById("menuToggle");
const sidebar = document.getElementById("sidebar");
const mainContent = document.getElementById("mainContent");

menuToggle.addEventListener("click", () => {
    if (sidebar.classList.contains("-translate-x-full")) {
        sidebar.classList.remove("-translate-x-full");
        mainContent.classList.remove("ml-0");
        mainContent.classList.add("ml-64");
    } else {
        sidebar.classList.add("-translate-x-full");
        mainContent.classList.remove("ml-64");
        mainContent.classList.add("ml-0");
    }
});

// Dropdown toggles
const notificationBtn = document.getElementById("notificationBtn");
const notificationDropdown = document.getElementById("notificationDropdown");
const profileBtn = document.getElementById("profileBtn");
const profileDropdown = document.getElementById("profileDropdown");

// Close all dropdowns
function closeDropdowns() {
    notificationDropdown.classList.remove("show");
    profileDropdown.classList.remove("show");
}

notificationBtn.addEventListener("click", (e) => {
    e.stopPropagation();
    if (!notificationDropdown.classList.contains("show")) {
        closeDropdowns();
        notificationDropdown.classList.add("show");
    } else {
        notificationDropdown.classList.remove("show");
    }
});

profileBtn.addEventListener("click", (e) => {
    e.stopPropagation();
    if (!profileDropdown.classList.contains("show")) {
        closeDropdowns();
        profileDropdown.classList.add("show");
    } else {
        profileDropdown.classList.remove("show");
    }
});

// Close dropdowns when clicking outside
document.addEventListener("click", () => {
    closeDropdowns();
});

// Accessibility: close dropdowns on Escape key
document.addEventListener("keydown", (e) => {
    if (e.key === "Escape") {
        closeDropdowns();
    }
});
