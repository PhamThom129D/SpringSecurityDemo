
    const phoneGroup = document.getElementById('phoneGroup');
    const emailGroup = document.getElementById('emailGroup');
    const phoneInput = document.getElementById('phoneInput');
    const emailInput = document.getElementById('emailInput');
    const loginForm = document.getElementById('loginForm');

    document.querySelectorAll('input[name="loginMethod"]').forEach(radio => {
    radio.addEventListener('change', () => {
        if (radio.value === 'phone' && radio.checked) {
            phoneGroup.classList.remove('hidden');
            emailGroup.classList.add('hidden');
            phoneInput.setAttribute('required', 'required');
            emailInput.removeAttribute('required');
            emailInput.value = '';
        } else if (radio.value === 'email' && radio.checked) {
            emailGroup.classList.remove('hidden');
            phoneGroup.classList.add('hidden');
            emailInput.setAttribute('required', 'required');
            phoneInput.removeAttribute('required');
            phoneInput.value = '';
        }
    });
});
    document.addEventListener("DOMContentLoaded", function () {
        const message = document.getElementById("messageLogin")?.textContent;
        if (message && message.trim() !== "") {
            showSuccess(message)
        }
    });

    loginForm.addEventListener('submit', (e) => {
    const selectedMethod = document.querySelector('input[name="loginMethod"]:checked').value;
    if (selectedMethod === 'phone' && !phoneInput.value.trim()) {
    e.preventDefault();
    showError('Vui lòng nhập số điện thoại!')
    phoneInput.focus();
} else if (selectedMethod === 'email' && !emailInput.value.trim()) {
    e.preventDefault();
    showError("Vui lòng nhập email!")
    emailInput.focus();
}
});