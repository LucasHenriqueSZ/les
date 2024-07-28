$(document).ready(function () {
    // Máscara para CEP
    $('#zipCode').mask('00000-000');

    // Máscara para CPF
    $('#cpf').mask('000.000.000-00');

    // Máscara para Telefone
    $('#phone').mask('(00) 00000-0000');

    // Máscara para Número do Cartão
    $('#cardNumber').mask('0000 0000 0000 0000');

    // Máscara para Data de Validade
    $('#expiryDate').mask('00/00');

    // Máscara para Código CVV
    $('#cvv').mask('0000');
});