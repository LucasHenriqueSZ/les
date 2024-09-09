$(document).ready(function () {
    // Máscara para CEP
    $('#zipCode').mask('00000-000');
    $('input[name="zipCode"]').mask('00000-000');

    // Máscara para CPF
    $('#cpf').mask('000.000.000-00');

    // Máscara para Telefone
    $(document).ready(function() {
        $('#phone').mask('(00) 0000-0000', {
            onKeyPress: function(phone, e, field, options) {
                const phoneNumber = phone.replace(/\D/g, '');
                if (phoneNumber.length > 10) {
                    $('#phone').mask('(00) 00000-0000', options);
                } else if (phoneNumber.length <= 10) {
                    $('#phone').mask('(00) 0000-0000?0', options);
                }
            }
        });
    });

    // Máscara para Número do Cartão
    $('#cardNumber').mask('0000 0000 0000 0000');
    $('input[name="cardNumber"]').mask('0000 0000 0000 0000');

    // Máscara para Data de Validade
    $('#expiryDate').mask('00/00');

    // Máscara para Código CVV
    $('#cvv').mask('0000');
});