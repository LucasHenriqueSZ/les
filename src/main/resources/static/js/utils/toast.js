function showToast(message, type) {
    const templateToast = $('#templateToast');
    templateToast.removeClass('text-bg-primary text-bg-success text-bg-danger');
    templateToast.addClass(`text-bg-${type}`);
    templateToast.find('.toast-body').text(message);
    templateToast.css('display', 'block');

    setTimeout(() => {
        templateToast.fadeOut(300);
    }, 5000);
}

function clearToasts() {
    const templateToast = $('#templateToast');
    templateToast.css('display', 'none');
}
