window.showError = function(msg) {
    return Swal.fire({
        icon: 'error',
        title: msg,
        timer: 1500,
        showConfirmButton: false,
        toast: true,
        position: 'top'
    });
}

window.showSuccess = function(msg) {
    return Swal.fire({
        icon: 'success',
        title: msg,
        timer: 1500,
        showConfirmButton: false,
        toast: true,
        position: 'top'
    });
}


window.showConfirmation = function(title, text) {
    return Swal.fire({
        title: title,
        text: text,
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        cancelButtonText: "Không",
        confirmButtonText: "Có"
    }).then(result => {
        return result.isConfirmed || false;
    });
}


window.handleAction = function(path, msg) {
    fetch(path, {
        method: 'POST'
    }).then(response => {
        if (response.ok) {
            window.showSuccess(msg).then(() => {
                location.reload();
            });
        } else {
            Swal.fire('Có lỗi xảy ra', '', 'error');
        }
    }).catch(() => {
        Swal.fire('Không thể kết nối đến máy chủ!', '', 'error');
    });
}



window.confirmAndHandle = async function(title, text, path, msg) {
    try {
        const confirmed = await window.showConfirmation(title, text);
        if (confirmed) {
            window.handleAction(path, msg);
        }
    } catch (error) {
        Swal.fire('Có lỗi xảy ra khi xử lý', '', 'error');
    }
}

document.addEventListener("DOMContentLoaded", function () {
    const successEl = document.getElementById("flash-success");
    const errorEl = document.getElementById("flash-error");

    if (successEl) {
        const msg = successEl.getAttribute("data-msg");
        if (msg) window.showSuccess(msg);
    }

    if (errorEl) {
        const msg = errorEl.getAttribute("data-msg");
        if (msg) window.showError(msg);
    }
});



