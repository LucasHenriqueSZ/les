let selectedCards = [];
let appliedCoupon = null;

function getTotalPaymentAmount() {
    return parseFloat($('#totalPaymentAmount').text().replace('R$', '').replace(',', '.').trim());
}

function addCard() {
    const selectedCardId = $('#card').val();
    const selectedCardText = $('#card option:selected').text();
    const cardAmount = parseFloat($('#amount').val());

    if (!cardAmount) {
        alert('Valor não pode ser zero');
        return;
    }

    const totalPaymentAmount = getTotalPaymentAmount();

    if (cardAmount > 0 && cardAmount <= totalPaymentAmount) {
        const existingCard = selectedCards.find(card => card.cardId === selectedCardId);
        if (existingCard) {
            existingCard.amount += cardAmount;
        } else {
            selectedCards.push({cardId: selectedCardId, cardText: selectedCardText, amount: cardAmount});
        }

        updateCardList();
        updateHiddenPayments();

        const newTotalPaymentAmount = totalPaymentAmount - cardAmount;
        $('#totalPaymentAmount').text('R$ ' + formatToBRL(newTotalPaymentAmount));
        $('#amount').val('');
    } else {
        alert('Valor acima do total restante');
    }
}

function updateCardList() {
    const cardListElement = $('#cardList').empty();
    selectedCards.forEach((card, index) => {
        cardListElement.append(`
            <li class="list-group-item d-flex justify-content-between align-items-center">
                ${card.cardText} - R$ ${card.amount.toFixed(2)}
                <button class="btn btn-danger btn-sm" onclick="removeCard(${index})">Remover</button>
            </li>
        `);
    });
}

function removeCard(index) {
    const [removedCard] = selectedCards.splice(index, 1);
    const updatedTotalPaymentAmount = getTotalPaymentAmount() + removedCard.amount;
    $('#totalPaymentAmount').text('R$ ' + formatToBRL(updatedTotalPaymentAmount));
    updateCardList();
    updateHiddenPayments();
}

function formatToBRL(value) {
    return value.toLocaleString('pt-BR', {minimumFractionDigits: 2, maximumFractionDigits: 2});
}

function applyCoupon() {
    const couponCode = $('#coupon').val();

    if (appliedCoupon) {
        $('#couponWarning').text("Um cupom já foi aplicado.").show();
        return;
    }

    $.ajax({
        url: '/api/cupons/validate',
        method: 'GET',
        data: {code: couponCode},
        success: function (response) {
            const {used, discountPercentage, amount, type, id} = response;

            if (!used) {
                let discountValue = 0;
                const totalPaymentAmount = getTotalPaymentAmount();
                const totalPayment = parseFloat($('#totalAmount').text().replace('R$', '').replace(',', '.').trim());

                if (type === 'PERCENTAGE' && discountPercentage) {
                    discountValue = (totalPayment * discountPercentage) / 100;
                } else if (type === 'FIXED_AMOUNT' && amount) {
                    discountValue = amount;
                }

                if (discountValue > totalPaymentAmount) {
                    $('#couponWarning').text(`O valor do cupom excede o valor restante a ser pago. Você perderá R$ ${(discountValue - totalPaymentAmount).toFixed(2)} se continuar.`).show();
                } else {
                    appliedCoupon = {couponId: id, couponCode, discountValue};
                    const newTotalPaymentAmount = totalPaymentAmount - discountValue;
                    $('#totalPaymentAmount').text('R$ ' + formatToBRL(newTotalPaymentAmount));
                    $('#discountAmount').text(`${discountValue.toFixed(2)}`);
                    $('#coupon-text').text(couponCode);
                    $('#couponWarning').hide();
                    updateHiddenPayments();
                }
            } else {
                $('#couponWarning').text("Cupom inválido.").show();
            }
        },
        error: function (xhr) {
            const errorMessage = xhr.status === 400 ? (xhr.responseText || "Cupom inválido ou já utilizado.") : "Erro ao validar o cupom. Tente novamente.";
            $('#couponWarning').text(errorMessage).show();
        }
    });
}

function updateHiddenPayments() {
    const hiddenPaymentsDiv = $('#hiddenPayments').empty();

    selectedCards.forEach((card, index) => {
        hiddenPaymentsDiv.append(`
            <input type="hidden" name="payments[${index}].method" value="CARTAO">
            <input type="hidden" name="payments[${index}].amount" value="${card.amount}">
            <input type="hidden" name="payments[${index}].card.id" value="${card.cardId}">
            <input type="hidden" name="payments[${index}].details" value="Card ID: ${card.cardId}">
        `);
    });

    if (appliedCoupon) {
        const couponIndex = selectedCards.length;
        hiddenPaymentsDiv.append(`
            <input type="hidden" name="payments[${couponIndex}].method" value="CUPOM">
            <input type="hidden" name="payments[${couponIndex}].amount" value="${appliedCoupon.discountValue}">
            <input type="hidden" name="payments[${couponIndex}].cupon.id" value="${appliedCoupon.couponId}">
            <input type="hidden" name="payments[${couponIndex}].details" value="Cupom: ${appliedCoupon.couponCode}">
        `);
    }
}
