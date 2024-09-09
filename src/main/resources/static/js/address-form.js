$(document).ready(function () {
    $('#submitAddress').on('click', handleAddressSubmission);

    $('button[id^="submitAddress_"]').on('click', function (event) {
        event.preventDefault();
        const uniqueId = $(this).attr('id').split('_')[1];
        const form = $(`#addressForm_${uniqueId}`);
        const formData = form.serializeArray();
        const jsonData = serializeFormData(formData);
        if (validateFormDataAddress(jsonData, uniqueId)) {
            form.off('submit').submit();
        }
    });

    $('#addressesContainer').on('click', '.remove-address', function () {
        $(this).closest('.address-item').remove();
    });
});

function handleAddressSubmission() {
    const form = $('#addressForm');
    const formData = form.serializeArray();
    const jsonData = serializeFormData(formData);

    clearToasts();

    if (isDuplicateAddress(jsonData)) {
        showToast('Endereço duplicado! Por favor, adicione um endereço diferente.', 'danger');
        return;
    }

    validateAndAddAddress(jsonData, form);
}

function serializeFormData(formData) {
    const jsonData = {};
    $.each(formData, function () {
        jsonData[this.name] = this.value;
    });
    return jsonData;
}

function isDuplicateAddress(jsonData) {
    let isDuplicate = false;
    $('#addressesContainer .address-item').each(function () {
        const existingAddress = {
            zipCode: $(this).find('input[name$="zipCode"]').val(),
            street: $(this).find('input[name$="street"]').val(),
            number: $(this).find('input[name$="number"]').val(),
            complement: $(this).find('input[name$="complement"]').val(),
            neighborhood: $(this).find('input[name$="neighborhood"]').val(),
            city: $(this).find('input[name$="city"]').val(),
            state: $(this).find('input[name$="state"]').val()
        };

        if (
            existingAddress.zipCode === jsonData.zipCode &&
            existingAddress.street === jsonData.street &&
            existingAddress.number === jsonData.number &&
            existingAddress.complement === jsonData.complement &&
            existingAddress.neighborhood === jsonData.neighborhood &&
            existingAddress.city === jsonData.city &&
            existingAddress.state === jsonData.state
        ) {
            isDuplicate = true;
            return false;
        }
    });
    return isDuplicate;
}

function validateAndAddAddress(jsonData, form) {
    $.ajax({
        url: '/address/validate',
        method: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(jsonData),
        success: function (data) {
            addAddressToContainer(data);
            $('.btn-close').click();
            form[0].reset();
            clearErrors()
            showToast('Endereço adicionado com sucesso!', 'success');
        },
        error: function (response) {
            handleValidationErrors(response);
        }
    });
}

function validateFormDataAddress(jsonData, uniqueId) {
    let isValid = true;
    $.ajax({
        url: '/address/validate',
        method: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(jsonData),
        async: false,
        success: function () {
            clearErrors();
            isValid = true;
        },
        error: function (response) {
            handleValidationErrorsFormData(response, uniqueId);
            isValid = false;
        }
    });

    return isValid;
}

function handleValidationErrorsFormData(response, uniqueId) {
    const errors = response.responseJSON;
    $('.text-danger').hide();
    $.each(errors, function (index, error) {
        const field = error.field;
        const message = error.defaultMessage;
        $('#' + field + 'Error_' + uniqueId).text(message).show();
    });
}

function addAddressToContainer(data) {
    const addressesContainer = $('#addressesContainer');
    const addressItem = $('<div class="address-item"></div>');
    const index = addressesContainer.children().length;
    addressItem.html(`
        <input type="hidden" name="addresses[${index}].zipCode" value="${data.zipCode}">
        <input type="hidden" name="addresses[${index}].street" value="${data.street}">
        <input type="hidden" name="addresses[${index}].number" value="${data.number}">
        <input type="hidden" name="addresses[${index}].complement" value="${data.complement}">
        <input type="hidden" name="addresses[${index}].neighborhood" value="${data.neighborhood}">
        <input type="hidden" name="addresses[${index}].city" value="${data.city}">
        <input type="hidden" name="addresses[${index}].state" value="${data.state}">
        <div class="card mb-3">
          <div class="card-body d-flex justify-content-between align-items-center">
            <div>
              <p class="mb-1"><strong>CEP:</strong> ${data.zipCode}</p>
              <p class="mb-1"><strong>Rua:</strong> ${data.street}</p>
              <p class="mb-1"><strong>Número:</strong> ${data.number}</p>
              <p class="mb-1"><strong>Complemento:</strong> ${data.complement}</p>
              <p class="mb-1"><strong>Bairro:</strong> ${data.neighborhood}</p>
              <p class="mb-1"><strong>Cidade:</strong> ${data.city}</p>
              <p class="mb-1"><strong>Estado:</strong> ${data.state}</p>
            </div>
            <button type="button" class="btn btn-danger text-white btn-sm remove-address">
              <i class="ri-delete-bin-6-line"></i> Remover
            </button>
          </div>
        </div>  
    `);
    addressesContainer.append(addressItem);
}

function handleValidationErrors(response) {
    if (response.status === 400) {
        const errors = response.responseJSON;
        $('.text-danger').hide();
        $.each(errors, function (index, error) {
            const field = error.field;
            const message = error.defaultMessage;
            $('#' + field + 'Error').text(message).show();
        });
        return
    }
    showToast('Ocorreu um erro no servidor. Por favor, tente novamente mais tarde.', 'danger');
}

function clearErrors() {
    $('.text-danger').hide();
}