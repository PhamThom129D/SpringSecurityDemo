const roleSelect = document.getElementById('role_id');
const doctorDetails = document.getElementById('doctor-details');
const patientDetails = document.getElementById('patient-details');
const otherDetails = document.getElementById('other-details');

function updateDetailsVisibility() {
    const role = roleSelect.value;
    console.log(role)
    doctorDetails.classList.add('hidden');
    patientDetails.classList.add('hidden');
    otherDetails.classList.add('hidden');

    if (role === '3') {
        doctorDetails.classList.remove('hidden');
    } else if (role === '4') {
        patientDetails.classList.remove('hidden');
    } else if (role === '1' || role === '2') {
        otherDetails.classList.remove('hidden');
    }
}

roleSelect.addEventListener('change', updateDetailsVisibility);
window.addEventListener('load', updateDetailsVisibility);

function previewImage(input, previewId) {
    const preview = document.getElementById(previewId);
    if (input.files && input.files[0]) {
        const reader = new FileReader();
        reader.onload = function (e) {
            preview.src = e.target.result;
        };
        reader.readAsDataURL(input.files[0]);
    } else {
        if (previewId === 'previewDoctor') {
            preview.src = "https://placehold.co/400x300/png?text=Ảnh+đại+diện+bác+sĩ";
        } else if (previewId === 'previewPatient') {
            preview.src = "https://placehold.co/400x300/png?text=Ảnh+đại+diện+bệnh+nhân";
        }
    }
}

document.getElementById('avtPathDoctor').addEventListener('change', function () {
    previewImage(this, 'previewDoctor');
});

document.getElementById('avtPathPatient').addEventListener('change', function () {
    previewImage(this, 'previewPatient');
});


function validatePassword() {
    const passwordVal = password.value.trim();
    const confirmVal = confirmPassword.value.trim();

    if (passwordVal !== '' && confirmVal !== '') {
        const isValid = confirmVal === passwordVal;
        confirmMessage.classList.toggle('hidden', isValid);
        return isValid;
    } else {
        confirmMessage.classList.add('hidden');
        return true;
    }
}
