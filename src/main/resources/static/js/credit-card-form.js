$(document).ready(function () {
    $('#submitcreditCard').on('click', handleCreditCardSubmission);

    $('#creditCardContainer').on('click', '.remove-creditCard', function () {
        $(this).closest('.creditCard-item').remove();
    });
});

function handleCreditCardSubmission() {
    const form = $('#creditCardForm');
    const formData = form.serializeArray();
    const jsonData = serializeFormData(formData);
    jsonData['cardNumber'] = jsonData['cardNumber'].replace(/\s/g, '');

    clearToasts();

    if (isDuplicateCreditCard(jsonData)) {
        showToast('Cartão duplicado! Por favor, adicione um Cartão diferente.', 'danger');
        return;
    }

    validateAndAddCreditCard(jsonData, form);
}

function isDuplicateCreditCard(jsonData) {
    let isDuplicate = false;
    $('#creditCardContainer .creditCard-item').each(function (index, element) {
        const existingCardNumber = $(element).find('input[name$="cardNumber"]').val();
        const existingHolderName = $(element).find('input[name$="holderName"]').val();
        const existingExpiryDate = $(element).find('input[name$="expiryDate"]').val();
        if (
            existingCardNumber === jsonData.cardNumber &&
            existingHolderName === jsonData.holderName &&
            existingExpiryDate === jsonData.expiryDate
        ) {
            isDuplicate = true;
            return false;
        }
    });
    return isDuplicate;
}

function validateAndAddCreditCard(jsonData, form) {
    $.ajax({
        url: '/card/validate',
        method: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(jsonData),
        success: function (data) {
            addCreditCardToContainer(data);
            $('.btn-close').click();
            form[0].reset();
            clearErrors()
            showToast('Cartão adicionado com sucesso!', 'success');
        },
        error: function (response) {
            handleValidationErrors(response);
        }
    });
}

function addCreditCardToContainer(data) {
    const creditCardContainer = $('#creditCardContainer');
    const cardItem = $('<div class="creditCard-item"></div>');
    const index = creditCardContainer.children().length;
    cardItem.html(`
        <input type="hidden" name="cards[${index}].cardNumber" value="${data.cardNumber}">
        <input type="hidden" name="cards[${index}].holderName" value="${data.holderName}">
        <input type="hidden" name="cards[${index}].expiryDate" value="${data.expiryDate}">
        <input type="hidden" name="cards[${index}].cvv" value="${data.cvv}">
        <div class="card mb-3">
          <div class="card-body d-flex justify-content-between align-items-center">
            <div>
              <p class="mb-1"><strong>Número do Cartão:</strong> ${data.cardNumber}</p>
              <p class="mb-1"><strong>Nome do Titular:</strong> ${data.holderName}</p>
              <p class="mb-1"><strong>Data de Validade:</strong> ${data.expiryDate}</p>
              <p class="mb-1"><strong>Código CVV:</strong> ${data.cvv}</p>
            </div>
            <button type="button" class="btn btn-danger text-white btn-sm remove-address">
              <i class="ri-delete-bin-6-line"></i> Remover
            </button>
          </div>
        </div> 
    `);
    creditCardContainer.append(cardItem);
}
