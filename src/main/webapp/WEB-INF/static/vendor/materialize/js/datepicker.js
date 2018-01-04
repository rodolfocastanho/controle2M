var arrayMes = new Array(12);
  arrayMes[0] = "January";
  arrayMes[1] = "February";
  arrayMes[2] = "March";
  arrayMes[3] = "April";
  arrayMes[4] = "May";
  arrayMes[5] = "June";
  arrayMes[6] = "July";
  arrayMes[7] = "August";
  arrayMes[8] = "September";
  arrayMes[9] = "October";
  arrayMes[10] = "November";
  arrayMes[11] = "December";


  var diaSemana = [ 'Domingo', 'Segunda-Feira', 'Terca-Feira', 'Quarta-Feira', 'Quinta-Feira', 'Sexta-Feira', 'Sabado' ];
  var mesAno = [ 'Janeiro', 'Fevereiro', 'Marco', 'Abril', 'Maio', 'Junho', 'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro'           , 'Dezembro' ];
  var data = new Date();
  var hoje = diaSemana[data.getDay()] + ', ' + mesAno[data.getMonth()] + ' de ' + data.getFullYear();
  $("#dataPesquisa").attr("value", hoje);
  $(".datepicker").pickadate({
    monthsFull: mesAno,
    monthsShort: [ 'Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun', 'Jul', 'Ago', 'Set', 'Out', 'Nov', 'Dez' ],
    weekdaysFull: diaSemana,
    weekdaysShort: [ 'Dom', 'Seg', 'Ter', 'Qua', 'Qui', 'Sex', 'Sab' ],
    weekdaysLetter: [ 'D', 'S', 'T', 'Q', 'Q', 'S', 'S' ],
    selectMonths: true,
    selectYears: true,
    clear: false,
    //disable: [true],//disable days
    format: 'dd/mm/yyyy',
    today: "Hoje",
    close: "X",
    min: new Date(data.getFullYear() - 1, 0, 1),
    max: new Date(data.getFullYear() + 1, 11, 31),
    closeOnSelect: true
  });

  $("#dataPesquisa").click(function (event) {
        event.stopPropagation();
        $(".datepicker").first().pickadate("picker").open();
    });


  var diaSemana = [ 'Domingo', 'Segunda-Feira', 'Terca-Feira', 'Quarta-Feira', 'Quinta-Feira', 'Sexta-Feira', 'Sabado' ];
  var mesAno = [ 'Janeiro', 'Fevereiro', 'Marco', 'Abril', 'Maio', 'Junho', 'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro' ];
  var data1 = new Date();
  var data = new Date(Date.parse(arrayMes[data1.getMonth()] + ' 01, ' + data1.getFullYear()));
  var hoje = diaSemana[data.getDay()] + ', ' + mesAno[data.getMonth()] + ' de ' + data.getFullYear();
  $("#dataPesquisa").attr("value", hoje);
  $(".datepickermonth").pickadate({
    monthsFull: mesAno,
    monthsShort: [ 'Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun', 'Jul', 'Ago', 'Set', 'Out', 'Nov', 'Dez' ],
    weekdaysFull: diaSemana,
    weekdaysShort: [ 'Dom', 'Seg', 'Ter', 'Qua', 'Qui', 'Sex', 'Sab' ],
    weekdaysLetter: [ 'D', 'S', 'T', 'Q', 'Q', 'S', 'S' ],
    selectMonths: true,
    selectYears: true,
    clear: false,
    //disable: [true],//disable days
    format: '01/mm/yyyy',
    today: '',
    close: "X",
    min: new Date(data.getFullYear() - 1, 0, 1),
    max: new Date(data.getFullYear() + 1, 11, 31),
    closeOnSelect: true
  });

  $("#dataPesquisa").click(function (event) {
      event.stopPropagation();
      $(".datepickermonth").first().pickadate("picker").open();
  });




