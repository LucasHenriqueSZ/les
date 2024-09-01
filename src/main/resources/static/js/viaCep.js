$(document).ready(function () {
    $('input[name="zipCode"]').on('blur', function () {
        const cep = $(this).val().replace(/\D/g, '');

        if (cep !== "") {
            const validacep = /^[0-9]{8}$/;

            if (validacep.test(cep)) {
                $.getJSON(`https://viacep.com.br/ws/${cep}/json/`, function (dados) {
                    if (!("erro" in dados)) {
                        $('input[name="street"]').val(dados.logradouro);
                        $('input[name="neighborhood"]').val(dados.bairro);
                        $('input[name="city"]').val(dados.localidade);
                        $('input[name="state"]').val(dados.uf);
                        $('input[name="zipCodeError"]').hide();
                    } else {
                        $('#zipCodeError').text("CEP não encontrado.").show();
                    }
                }).fail(function () {
                    $('#zipCodeError').text("Erro ao buscar o CEP.").show();
                });
            } else {
                $('#zipCodeError').text("Formato de CEP inválido.").show();
            }
        }
    });
});
