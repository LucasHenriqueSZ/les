
// Cupom de desconto

document.getElementById('add-coupon-btn').addEventListener('click', function () {
    document.getElementById('coupon-input-section').style.display = 'block';
});

document.getElementById('apply-coupon-btn').addEventListener('click', function () {
    const couponCode = document.getElementById('coupon-code').value;
    if (couponCode) {
        const discountAmount = '- R$20,00';
        document.getElementById('coupon-text').textContent = couponCode;
        document.getElementById('discount-amount').textContent = discountAmount;
        document.getElementById('coupon-display').style.display = 'block';
        document.getElementById('coupon-input-section').style.display = 'none';
        document.getElementById('add-coupon-btn').style.display = 'none';
    }
});


// Frete

document.getElementById('calculate-freight-btn').addEventListener('click', function () {
    const cepCode = document.getElementById('cep-code').value;
    if (cepCode) {
        const freightAmount = 'R$15,00';
        document.getElementById('freight-amount').textContent = freightAmount;
        document.getElementById('freight-display').style.display = 'block';
    }
});