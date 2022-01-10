function ObjetoIdValor() {
	var _id = '';
	var _valor = '';

	var clazz = {
		setId : function(id) {
			_id = id;
		},
		getId : function() {
			return _id;
		},
		setValor : function(valor) {
			_valor = valor;
		},
		getValor : function() {
			return _valor;
		}
	}
	return clazz;
};

var ListaObjetoIdValor = function(name) {
	var _listaObjetoIdValor = new Array();
	var _name = name;
	var _indexOf = function(objetoIdValor) {
		for (var i = 0; i < _listaObjetoIdValor.length; i++) {
			var indexNaLista = _listaObjetoIdValor[i].getId();

			if (objetoIdValor == indexNaLista) {
				return i;
			}
		}
		return -1;
	}

	return {
		adiciona : function(objetoIdValor) {
			_listaObjetoIdValor.push(objetoIdValor);
			return this;
		},
		remove : function(objetoIdValor) {
			var index = _indexOf(objetoIdValor);
			if (index >= 0) {
				_listaObjetoIdValor.splice(index, 1);
			}
			return this;
		},

		size : function() {
			return _listaObjetoIdValor.length;
		},
		
		buscaPorId : function(id){
			for (var i = 0; i < _listaObjetoIdValor.length; i++) {
				var objeto = _listaObjetoIdValor[i];

				if (objeto.getId() == id) {
					return objeto;
				}
			}
			return undefined;
		},
		
		buscaPorValor : function(valor){
			var _encontrados = new Array();
			for (var i = 0; i < _listaObjetoIdValor.length; i++) {
				var objeto = _listaObjetoIdValor[i];
				var objetoValor = objeto.getValor().toLowerCase();				
				if (objetoValor.indexOf(valor.toLowerCase()) >= 0) {
					_encontrados.push(objeto);
				}
			}
			if(_encontrados.length > 0){
				return _encontrados;
			}
			return undefined;
		},

		listaTodos : function() {
			return _listaObjetoIdValor;
		},

		removeTodos : function() {
			_listaObjetoIdValor = new Array();
			return _listaObjetoIdValor;
		},
		nome : function(){
			return _name;
		}
	};
};