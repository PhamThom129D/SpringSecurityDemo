    const sendOtpBtn = document.getElementById('sendOtpBtn');
    const resendBtn = document.getElementById('resendBtn');
    const emailInput = document.getElementById('emailInput');
    const otpSection = document.getElementById('otpSection');
    const countdownSection = document.getElementById('countdownSection');
    const countdownEl = document.getElementById('countdown');
    const loginBtn = document.getElementById('loginBtn');
    const otpInput = document.getElementById('otpInput');

    let timeLeft = 180;
    let timerInterval = null;



    function updateCountdown() {
    const minutes = Math.floor(timeLeft / 60);
    const seconds = timeLeft % 60;
    countdownEl.textContent = `${minutes.toString().padStart(2,'0')}:${seconds.toString().padStart(2,'0')}`;
    if (timeLeft <= 0) {
    clearInterval(timerInterval);
    resendBtn.disabled = false;
} else {
    timeLeft--;
}
}

    function startCountdown() {
    timeLeft = 180;
    resendBtn.disabled = true;
    updateCountdown();
    timerInterval = setInterval(updateCountdown, 1000);
}

    sendOtpBtn.addEventListener('click', () => {
    const email = emailInput.value.trim();
    if (!email) {
    window.showError('Vui lòng nhập email!');
    emailInput.focus();
    return;
}

    sendOtpBtn.disabled = true;
    fetch('/emailMessage/sendOtp', {
    method: 'POST',
    headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' },
    body: new URLSearchParams({ email: email })
}).then(res => {
    if (res.ok) {
    window.showSuccess('Mã OTP đã được gửi đến email của bạn!').then(() => {
    otpSection.classList.remove('hidden');
    countdownSection.classList.remove('hidden');
    loginBtn.classList.remove('hidden');
    sendOtpBtn.classList.add('hidden');
    startCountdown();
});
} else {
    res.text().then(text => {
    window.showError(text);
    sendOtpBtn.disabled = false;
});
}
}).catch(() => {
    window.showError('Không thể kết nối đến máy chủ!');
    sendOtpBtn.disabled = false;

});
});

    resendBtn.addEventListener('click', () => {
    const email = emailInput.value.trim();
    if (!email) {
    window.showError('Vui lòng nhập email!');
    emailInput.focus();
    return;
}

    fetch('/emailMessage/sendOtp', {
    method: 'POST',
    headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' },
    body: new URLSearchParams({ email: email })
}).then(res => {
    if (res.ok) {
    window.showSuccess('Mã OTP đã được gửi lại!').then(() => {
    resendBtn.disabled = true;
    startCountdown();
});
} else {
    res.text().then(text => {
    window.showError(text);
});
}
}).catch(() => {
    window.showError('Không thể kết nối đến máy chủ!');
});
});

    loginBtn.addEventListener('click', () => {
    const email = emailInput.value.trim();
    const otp = otpInput.value.trim();

    if (!otp || otp.length !== 6 || !/^\d{6}$/.test(otp)) {
    window.showError('Vui lòng nhập đúng mã OTP (6 chữ số)!');
    otpInput.focus();
    return;
}

    fetch('/emailMessage/verifyOtp', {
    method: 'POST',
    headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' },
    body: new URLSearchParams({ email: email, code: otp })
}).then(res => {
    if (res.ok) {
    window.showSuccess('Xác thực thành công!').then(() => {
    window.location.href = '/emailMessage/home';
});
} else {
    res.text().then(text => {
    window.showError(text);
});
}
}).catch(() => {
    window.showError('Không thể kết nối đến máy chủ!');
});
});
