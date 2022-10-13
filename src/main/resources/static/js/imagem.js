$(document).ready(function (){
	$("#imagem").change(function (){
		mostrarPreviaImagem(this);
	});
});

function mostrarPreviaImagem(arquivoEntrada){
	var arquivo = arquivoEntrada.files[0];
	var leitor = new FileReader();
	leitor.onload = function(e){
		$("#imagemPrincipal").attr("src", e.target.result);
	}
	
	leitor.readAsDataURL(arquivo);
}