var contadorImagemExtra = 0;

window.onload = function(){
	$(document).ready(function() {
		
		$("#previaImagemPrincipal").change(function (){
			this.setCustomValidity("");
			mostrarPreviaImagem(this);
		});
		
		$("#descricaoLonga").richText();
		//$("#descricaoCurta").richText();
		
		$("input[name='imagemExtra']").each(function(index){
			contadorImagemExtra++;
			
			$(this).change(function(){
				mostrarImagemExtra(this, index);
			});
		});
		
		$("a[name ='removerImagemExtra']").each(function(index){
			$(this).click(function(){
				removerImagemExtra(index);
			});
		});
	});	
	
	
	const produtoCriado = document.getElementById("dataCriacao");
	const produtoAtualizado = document.getElementById("dataAtualizacao");

	const dataAtual = Date.now();
	const dataUpdate = new Date(dataAtual);
	
	produtoCriado.value = dataUpdate.toLocaleDateString();
	produtoAtualizado.value = dataUpdate.toUTCString();
	
}



function mostrarPreviaImagem(arquivoEntrada){
	var arquivo = arquivoEntrada.files[0];
	var leitor = new FileReader();
	leitor.onload = function(e){
		$("#imagemPrincipal").attr("src", e.target.result);
	}
	
	leitor.readAsDataURL(arquivo);
}

function mostrarImagemExtra(arquivoEntrada, index){
	var arquivo = arquivoEntrada.files[0];
	var leitor = new FileReader();
	leitor.onload = function(e){
		$("#mostrarImagemExtra" + index).attr("src", e.target.result);
	}
	
	leitor.readAsDataURL(arquivo);
	
	if (index >= contadorImagemExtra - 1) {
		adicionarNovaImagemExtra(index + 1);		
	}
}

function adicionarNovaImagemExtra(index){
	divImagemExtra = `
	
		<div id="divExtraImagem${index}">
			<div class="m-2" id="headerImagem${index}"><label>${index + 1} Imagem Extra: </label></div>
		<div class="m-2">
			<img id="mostrarImagemExtra${index}" alt="Prévia da imagem extra" class="img-fluid" 
			th:src="@{/imagem/imagem-padrao.png}" />
		</div>
					
		<div>
			<input type="file" id="imagemExtra" name="imagemExtra" accept="image/*" 
			onchange="mostrarImagemExtra(this, ${index})"/>
		</div>
		</div>
	
	`;
	
	botaoRemover = `
		<a class="btn fas fa-times-circle fa-2x icon-dark float-right"
			href="javascript:removerImagemExtra(${index - 1})" 
			title="Remover essa imagem"></a>
	
	`;
	
	$("#imagemProduto").append(divImagemExtra);
	$("#headerImagem" + (index - 1)).append(botaoRemover);
	contadorImagemExtra++;
}

function removerImagemExtra(index){
	$("#divExtraImagem" + index).remove();
}