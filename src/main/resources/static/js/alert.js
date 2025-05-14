function showError(msg) {
    Swal.fire({
        icon: 'error',
        title: msg,
        timer: 1500,
        showConfirmButton: false,
        toast: true,
        position: 'top'
    });
}

function showSuccess(msg) {
    Swal.fire({
        icon: 'success',
        title: msg,
        timer: 1500,
        showConfirmButton: false,
        toast: true,
        position: 'top'
    });
}