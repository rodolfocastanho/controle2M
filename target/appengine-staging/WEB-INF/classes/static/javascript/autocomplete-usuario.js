var ListaUsuarioDto = ListaUsuarioDto || {};

ListaUsuarioDto.Autocomplete = (function(){

    function Autocomplete(){

    }

    Autocomplete.prototype.iniciar = function(){
        $.ajax({
            type: 'GET',
            url: '/usuario/filtro?nome=',
            success: function(response){
                var usuarioServidor = response;
                var usuarioSugestao = {};

                for(var i=0; i < usuarioServidor.length; i++){
                    usuarioSugestao[usuarioServidor[i].nome] = usuarioServidor[i].flag;
                }

                $('input.autocomplete').autocomplete({
                    data: usuarioSugestao,
                    //limit: 20,  The max amount of results that can be shown at once. Default: Infinity.
                    onAutocomplete: function(texto) {
                      // Callback function when value is autcompleted.
                      onSendItem(texto);
                    },
                    minLength: 1 // The minimum length of the input for the autocomplete to start. Default: 1.
                  });

                  function onSendItem(texto){
                    var usuarioId = 0;

                    usuarioServidor.filter(function(obj){
                        if(obj.nome === texto){
                            usuarioId = obj.id;
                        }
                    });

                    if(usuarioId > 0){
                        $('#usuario-responsavel').attr('value', usuarioId);
                        //console.log('codigo', usuarioId);
                    }

                  }

                console.log(usuarioSugestao);
            }
        });
    };

    return Autocomplete

}());

$(function(){
    var autocomplete = new ListaUsuarioDto.Autocomplete();
    autocomplete.iniciar();
});