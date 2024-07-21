$(document).ready(function () {
    $('#zipCode').on('blur', function () {
        var cep = $(this).val().replace(/\D/g, '');

        if (cep !== "") {
            var validacep = /^[0-9]{8}$/;

            if (validacep.test(cep)) {
                $.getJSON(`https://viacep.com.br/ws/${cep}/json/`, function (dados) {
                    if (!("erro" in dados)) {
                        $('#street').val(dados.logradouro);
                        $('#neighborhood').val(dados.bairro);
                        $('#city').val(dados.localidade);
                        $('#state').val(dados.uf);
                        $('#zipCodeError').hide();
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
