$(document).ready(function () {
    function calcularFrete(cep) {
        let hash = 0;
        for (let i = 0; i < cep.length; i++) {
            hash = ((hash << 5) - hash) + cep.charCodeAt(i);
            hash |= 0;
        }
        let valorFrete = (Math.abs(hash) % 1000) / 100 + 10;
        return valorFrete.toFixed(2);
    }

    function atualizarFrete() {
        let selectedOption = $('#endereco option:selected');
        let cep = selectedOption.data('cep');
        let id = selectedOption.data('id');

        if (cep) {
            let valorFrete = calcularFrete(cep);

            let hoje = new Date();
            let diasEntregaInicial = 5;
            let diasEntregaFinal = 7;

            let dataEntregaInicial = new Date(hoje);
            dataEntregaInicial.setDate(hoje.getDate() + diasEntregaInicial);

            let dataEntregaFinal = new Date(hoje);
            dataEntregaFinal.setDate(hoje.getDate() + diasEntregaFinal);

            let formatarData = function (data) {
                let dia = String(data.getDate()).padStart(2, '0');
                let mes = String(data.getMonth() + 1).padStart(2, '0');
                let ano = data.getFullYear();
                return dia + '/' + mes + '/' + ano;
            };

            updateTotal(valorFrete);

            $('#valor-frete').text('R$ ' + formatToBRL(valorFrete));
            $('#data-entrega').text(formatarData(dataEntregaInicial) + ' - ' + formatarData(dataEntregaFinal));
            $('#resultado-frete').show();

            $('#payment-freight-checkout').text('R$ ' + formatToBRL(valorFrete));

            $('#input-valor-frete').val(valorFrete);
            $('#input-data-entrega-inicial').val(formatarData(dataEntregaInicial));
            $('#input-data-entrega-final').val(formatarData(dataEntregaFinal));
            $('#input-id').val(id);
        } else {
            alert('Por favor, selecione um endereço com CEP válido.');
        }
    }

    $('#endereco').change(function () {
        atualizarFrete();
    });
});

function formatToBRL(value) {
    return value.toLocaleString('pt-BR', {minimumFractionDigits: 2, maximumFractionDigits: 2});
}

function updateTotal(value) {
    const totalElement = $('#totalAmount');
    const totalPaymentAmountElement = $('#totalPaymentAmount');
    const freightElement = $('#payment-freight-checkout');

    const parseCurrency = (currencyText) => {
        return parseFloat(currencyText.replace('R$', '').replace(',', '.').trim());
    };

    const nowFreight = parseCurrency(freightElement.text());
    const currentTotal = parseCurrency(totalElement.text());
    const currentPaymentTotal = parseCurrency(totalPaymentAmountElement.text());

    const newTotal = currentTotal + parseFloat(value) - nowFreight;
    const newTotalPaymentAmount = currentPaymentTotal + parseFloat(value) - nowFreight;

    totalElement.text('R$' + formatToBRL(newTotal));
    totalPaymentAmountElement.text('R$' + formatToBRL(newTotalPaymentAmount));
}